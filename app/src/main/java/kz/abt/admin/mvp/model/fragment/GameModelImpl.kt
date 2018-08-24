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

package kz.abt.admin.mvp.model.fragment

import io.reactivex.Maybe
import io.reactivex.functions.BiFunction
import kz.abt.admin.mvp.model.fragment.interfaces.GameModel
import kz.abt.admin.room.common.DataBaseRequest
import kz.abt.admin.room.table.Team
import kz.abt.admin.ui.util.GameJSON

class GameModelImpl(private val readListener: OnReadListener) : GameModel {
    private var idTournament = 1

    interface OnReadListener {

        fun onGame(list: MutableList<GameJSON>)
    }

    override fun setReadListener() {

        DataBaseRequest.getGameList(idTournament)
                .map { it ->
                    val list: MutableList<GameJSON> = mutableListOf()
                    val zipList: MutableList<Maybe<GameJSON>> = mutableListOf()

                    for (i in 0 until it.size)
                        zipList.add(Maybe
                                .zip(
                                        DataBaseRequest.getTeam(it[i].idTeamOne),
                                        DataBaseRequest.getTeam(it[i].idTeamTwo),
                                        BiFunction<Team, Team, GameJSON> { one, two -> GameJSON(one, two, it[i].idGame) }
                                )
                        )

                    Maybe.concat(zipList)
                            .subscribe(
                                    { list.add(it) },
                                    { },
                                    { readListener.onGame(list) }
                            )
                }.subscribe()
    }

    override fun setTournament(idTournament: Int) {
        this.idTournament = idTournament
    }

    override fun getTournament(): Int = idTournament
}