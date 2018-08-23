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

package kz.abt.admin.ui.activity

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_game.*
import kz.abt.admin.R
import kz.abt.admin.mvp.presenter.GamePresenter
import kz.abt.admin.mvp.view.GameView

class GameActivity : MvpAppCompatActivity(), GameView {
    @InjectPresenter
    lateinit var presenter: GamePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game)

        presenter.setId(
                intent.getIntExtra("idTournament", 1),
                intent.getIntExtra("idTeamOne", 1),
                intent.getIntExtra("idTeamTwo", 1)
        )
    }

    override fun initTeams(titleTeamOne: String, titleTeamTwo: String) {
        title_1.text = titleTeamOne
        title_2.text = titleTeamTwo
    }
}
