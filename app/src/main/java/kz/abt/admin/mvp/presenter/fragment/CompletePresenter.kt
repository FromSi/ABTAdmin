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
import kz.abt.admin.mvp.model.fragment.CompleteModelImpl
import kz.abt.admin.mvp.model.fragment.interfaces.CompleteModel
import kz.abt.admin.mvp.view.fragment.CompleteView
import kz.abt.admin.ui.util.CompleteJSON

@InjectViewState
class CompletePresenter : MvpPresenter<CompleteView>(), CompleteModelImpl.OnReadListener {
    private val model: CompleteModel = CompleteModelImpl(this)

    override fun onComplete(list: MutableList<CompleteJSON>) {

        viewState.setList(list)
    }

    fun initPresenter(idTournament: Int) {

        model.setTournament(idTournament)
        model.setReadListener()
    }
}