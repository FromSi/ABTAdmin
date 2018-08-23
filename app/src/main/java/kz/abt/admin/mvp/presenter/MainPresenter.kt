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

package kz.abt.admin.mvp.presenter

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.abt.admin.mvp.model.MainModelImpl
import kz.abt.admin.mvp.model.interfaces.MainModel
import kz.abt.admin.mvp.view.MainView
import kz.abt.admin.room.table.Team
import kz.abt.admin.ui.util.TeamJSON

@InjectViewState
class MainPresenter : MvpPresenter<MainView>(), MainModelImpl.OnReadListener {
    private val model: MainModel = MainModelImpl(this)

    override fun onTeam(list: MutableList<Team>) {

        if (list.size >= 2)
            viewState.openSheetGame(list)
        else
            Log.d("NoNormalSize", list.size.toString())
    }

    fun initPresenter(idTournament: Int) {

        model.setTournament(idTournament)
        viewState.initClickMenu()
        viewState.initFragment(MainModelImpl.State.GAME, model.getTournament())
    }

    fun insertGame(one: Int, two: Int) {

        model.insertGame(one, two)
    }

    fun clickFab() {

        when (model.getState()) {
            MainModelImpl.State.GAME -> {

                model.openSheetGame()
            }
            MainModelImpl.State.TEAM -> {

                viewState.openDialogTeam()
            }
            MainModelImpl.State.COMPLETE -> {


            }
        }
    }

    fun setState(state: MainModelImpl.State) {

        model.setState(state)
    }

    fun setFragment(state: MainModelImpl.State) {

        if (state != model.getState())
            viewState.setFragment(state, model.getTournament())
    }

    fun insertTeam(teamJSON: TeamJSON) {

        model.insertTeam(teamJSON)
    }
}