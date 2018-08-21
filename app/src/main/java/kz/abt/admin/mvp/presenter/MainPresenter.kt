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

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.abt.admin.mvp.model.MainModelImpl
import kz.abt.admin.mvp.model.interfaces.MainModel
import kz.abt.admin.mvp.view.MainView
import kz.abt.admin.ui.util.TeamJSON

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    private val model: MainModel = MainModelImpl()

    fun initPresenter(idTournament: Int) {

        model.setTournament(idTournament)
        viewState.initClickMenu()
    }

    fun openDialog() {

        viewState.openDialog(model.getState())
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