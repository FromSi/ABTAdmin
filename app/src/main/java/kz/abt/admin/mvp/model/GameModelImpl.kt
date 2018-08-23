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

import io.reactivex.Maybe
import kz.abt.admin.mvp.model.interfaces.GameModel
import kz.abt.admin.room.common.DataBaseRequest
import kz.abt.admin.room.table.Team

class GameModelImpl(private val readListener: OnReadListener) : GameModel {
    private var idTournament = 0

    private lateinit var teamOne: Team
    private lateinit var teamTwo: Team

    interface OnReadListener {

        fun initTeams(titleTeamOne: String, titleTeamTwo: String)
    }

    override fun setId(idTournament: Int, idTeamOne: Int, idTeamTwo: Int) {
        var index = 0
        this.idTournament = idTournament

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
}