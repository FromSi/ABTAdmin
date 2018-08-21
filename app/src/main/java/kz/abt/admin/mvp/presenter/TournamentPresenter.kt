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
import kz.abt.admin.mvp.model.TournamentModelImpl
import kz.abt.admin.mvp.model.interfaces.TournamentModel
import kz.abt.admin.mvp.view.TournamentView
import kz.abt.admin.room.table.Tournament

@InjectViewState
class TournamentPresenter : MvpPresenter<TournamentView>(), TournamentModelImpl.OnReadListener {
    private val model: TournamentModel = TournamentModelImpl()

    override fun updateContent(list: MutableList<Tournament>) {

        viewState.setList(list)
    }

    fun initPresenter() {

        model.setReadListener(this)
    }

    fun insertTournament(list: List<Tournament>) {

        model.insertTournament(list)
    }
}