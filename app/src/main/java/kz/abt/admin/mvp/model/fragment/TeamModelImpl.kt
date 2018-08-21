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

import kz.abt.admin.mvp.model.fragment.interfaces.TeamModel
import kz.abt.admin.room.common.DataBaseRequest
import kz.abt.admin.room.table.Players
import kz.abt.admin.room.table.Team
import kz.abt.admin.ui.util.TeamJSON

class TeamModelImpl(private val readListener: OnReadListener) : TeamModel {
    private var idTournament = 1

    interface OnReadListener {

        fun updateContent(list: MutableList<Team>)

        fun openInfo(team: Team, players: Players)
    }

    override fun setReadListener() {

        DataBaseRequest.getTeamList(idTournament)
                .subscribe {
                    readListener.updateContent(it)
                }
    }

    override fun openInfo(team: Team) {

        DataBaseRequest.getPlayers(team.idTeam)
                .subscribe {
                    readListener.openInfo(team, it)
                }
    }

    override fun setTournament(idTournament: Int) {
        this.idTournament = idTournament
    }
}