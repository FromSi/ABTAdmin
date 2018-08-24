/*
 * Copyright 2018 Vlad Weber-Pflaumer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kz.abt.admin.mvp.model

import android.os.CountDownTimer
import io.reactivex.Maybe
import kz.abt.admin.mvp.model.interfaces.GameModel
import kz.abt.admin.room.common.DataBaseRequest
import kz.abt.admin.room.table.Game
import kz.abt.admin.room.table.Team
import java.util.concurrent.TimeUnit

class GameModelImpl(private val readListener: OnReadListener) : GameModel {
    private var idTournament = 0
    private var idGame = 0
    private var state = false
    private var tick: Long = 600000L
    private var scoreOne = 0
    private var scoreTwo = 0

    private lateinit var teamOne: Team
    private lateinit var teamTwo: Team
    private lateinit var timer: CountDownTimer

    interface OnReadListener {

        fun setTime(time: String)

        fun initTeams(titleTeamOne: String, titleTeamTwo: String)

        fun finishActivity()
    }

    override fun setScoreOne(scoreOne: Int): String {
        this.scoreOne = scoreOne

        return teamOne.title
    }

    override fun setScoreTwo(scoreTwo: Int): String {
        this.scoreTwo = scoreTwo

        return teamTwo.title
    }

    override fun finishActivity() {

        DataBaseRequest.deleteGame(
                Game(
                        idTournament,
                        teamOne.idTeam,
                        teamTwo.idTeam
                        ).apply { idGame = this@GameModelImpl.idGame }
        )
    }

    override fun setId(idTournament: Int, idTeamOne: Int, idTeamTwo: Int, idGame: Int) {
        var index = 0
        this.idTournament = idTournament
        this.idGame = idGame

        Maybe
                .concat(
                        DataBaseRequest.getTeam(idTeamOne),
                        DataBaseRequest.getTeam(idTeamTwo)
                )
                .subscribe(
                        {
                            if (index == 0) {
                                index++

                                teamOne = it
                            } else
                                teamTwo = it
                        },
                        { },
                        { readListener.initTeams(teamOne.title, teamTwo.title) }
                )
    }

    override fun getStateTimer(): Boolean = if (state) {
        state = false

        timer.cancel()

        false
    } else {
        state = true

        timer = object : CountDownTimer(tick, 1000) {
            override fun onFinish() {

                readListener.finishActivity()
            }

            override fun onTick(p0: Long) {
                tick = p0

                readListener.setTime(
                        String.format(
                                "%02d:%02d",
                                TimeUnit.MILLISECONDS
                                        .toMinutes(p0) - TimeUnit.MINUTES.toHours(
                                        TimeUnit.MILLISECONDS.toHours(p0)
                                ),
                                TimeUnit.MILLISECONDS
                                        .toSeconds(p0) - TimeUnit.MINUTES.toSeconds(
                                        TimeUnit.MILLISECONDS.toMinutes(p0)
                                )
                        )
                )
            }
        }.start()

        true
    }

    override fun getTimer(): CountDownTimer = timer
}