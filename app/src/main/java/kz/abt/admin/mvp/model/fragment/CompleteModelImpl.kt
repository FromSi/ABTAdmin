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
import kz.abt.admin.mvp.model.fragment.interfaces.CompleteModel
import kz.abt.admin.room.common.DataBaseRequest
import kz.abt.admin.room.table.Team
import kz.abt.admin.ui.util.CompleteJSON

class CompleteModelImpl(private val readListener: OnReadListener) : CompleteModel {
    private var idTournament = 1

    interface OnReadListener {

        fun onComplete(list: MutableList<CompleteJSON>)
    }

    override fun setTournament(idTournament: Int) {
        this.idTournament = idTournament
    }

    override fun setReadListener() {

        DataBaseRequest.getCompleteList(idTournament)
                .map{ it ->
                    val list: MutableList<CompleteJSON> = mutableListOf()
                    val zipList: MutableList<Maybe<CompleteJSON>> = mutableListOf()

                    //Собирает list для ассихронного запроса Maybe из Room
                    for (i in 0 until it.size)
                        zipList.add(Maybe
                                .zip(
                                        DataBaseRequest.getTeam(it[i].idTeam1),
                                        DataBaseRequest.getTeam(it[i].idTeam2),
                                        BiFunction<Team, Team, CompleteJSON> { one, two ->
                                            CompleteJSON(
                                                    one.title,
                                                    two.title,
                                                    it[i].s1,
                                                    it[i].s2,
                                                    it[i].idComplete
                                            )
                                        }
                                )
                        )

                    //Собирает объекты и отправляет готовый list presenter'у
                    Maybe.concat(zipList)
                            .subscribe(
                                    { list.add(it) },
                                    { },
                                    { readListener.onComplete(list) }
                            )
                }.subscribe()
    }
}