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

package kz.abt.admin.mvp.presenter.fragment

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.abt.admin.mvp.model.fragment.TeamModelImpl
import kz.abt.admin.mvp.model.fragment.interfaces.TeamModel
import kz.abt.admin.mvp.view.fragment.TeamView
import kz.abt.admin.room.table.Players
import kz.abt.admin.room.table.Team

@InjectViewState
class TeamPresenter : MvpPresenter<TeamView>(), TeamModelImpl.OnReadListener {
    private val model: TeamModel = TeamModelImpl(this)

    override fun updateContent(list: MutableList<Team>) {

        viewState.setList(list)
    }

    override fun openInfo(team: Team, players: Players) {

        viewState.openInfo(team, players)
    }

    fun initPresenter(idTournament: Int) {

        model.setTournament(idTournament)
        model.setReadListener()
    }

    fun openInfo(team: Team) {

        model.openInfo(team)
    }
}