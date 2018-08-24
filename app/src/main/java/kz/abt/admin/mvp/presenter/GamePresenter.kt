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
import kz.abt.admin.mvp.model.GameModelImpl
import kz.abt.admin.mvp.model.interfaces.GameModel
import kz.abt.admin.mvp.view.GameView

@InjectViewState
class GamePresenter : MvpPresenter<GameView>(), GameModelImpl.OnReadListener {
    private val model: GameModel = GameModelImpl(this)

    override fun initTeams(titleTeamOne: String, titleTeamTwo: String) {

        viewState.initTeams(titleTeamOne, titleTeamTwo)
    }

    override fun setTime(time: String) {

        viewState.setTime(time)
    }

    fun setId(idTournament: Int, idTeamOne: Int, idTeamTwo: Int, idGame: Int) {

        model.setId(idTournament, idTeamOne, idTeamTwo, idGame)
    }

    fun setScoreOne(point: Int, pointOne: Int) {

        viewState.viewPoint(model.setScoreOne(point), point)

        if ((pointOne + point) > 9)
            viewState.setPointOne((pointOne + point).toString())
        else
            viewState.setPointOne("0${pointOne + point}")
    }

    fun setScoreTwo(point: Int, pointTwo: Int) {

        viewState.viewPoint(model.setScoreTwo(point), point)

        if ((pointTwo + point) > 9)
            viewState.setPointTwo((pointTwo + point).toString())
        else
            viewState.setPointTwo("0${pointTwo + point}")
    }

    override fun finishActivity(){

        model.finishActivity()
        viewState.finishActivity()
    }

    fun stateTimer() {

        if (model.getStateTimer())
            viewState.playTimer()
        else
            viewState.pauseTimer()
    }
}