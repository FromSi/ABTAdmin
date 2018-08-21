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

import kz.abt.admin.mvp.model.interfaces.MainModel
import kz.abt.admin.room.common.DataBaseRequest
import kz.abt.admin.ui.util.TeamJSON

class MainModelImpl : MainModel {
    private var idTournament = 1
    private var state = State.GAME

    enum class State {
        GAME, TEAM, COMPLETE
    }

    override fun setState(state: State) {
        this.state = state
    }

    override fun insertTeam(teamJSON: TeamJSON) {

        DataBaseRequest.insertTeam(idTournament, teamJSON)
    }

    override fun getState(): State = state

    override fun getTournament(): Int = idTournament

    override fun setTournament(idTournament: Int) {
        this.idTournament = idTournament
    }
}