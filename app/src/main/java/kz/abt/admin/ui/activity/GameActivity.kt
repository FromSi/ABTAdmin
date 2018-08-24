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
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_game.*
import kz.abt.admin.R
import kz.abt.admin.mvp.presenter.GamePresenter
import kz.abt.admin.mvp.view.GameView
import kz.abt.admin.ui.fragment.dialog.ExitDialog
import kotlin.math.PI

class GameActivity : MvpAppCompatActivity(), GameView {
    @InjectPresenter
    lateinit var presenter: GamePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game)
        presenter.setId(
                intent.getIntExtra("idTournament", 1),
                intent.getIntExtra("idTeamOne", 1),
                intent.getIntExtra("idTeamTwo", 1),
                intent.getIntExtra("idGame", 1)
        )
        fab.setOnClickListener { presenter.stateTimer() }
        exit.setOnClickListener { exitGame() }
        initClickPoint()
    }

    override fun initTeams(titleTeamOne: String, titleTeamTwo: String) {
        title_1.text = titleTeamOne
        title_2.text = titleTeamTwo
    }

    override fun setTime(time: String) {

        timer.text = time
    }

    override fun finishActivity() {

        finish()
    }

    override fun playTimer() {

        fab.setImageDrawable(resources.getDrawable(R.drawable.pause))
    }

    override fun pauseTimer() {

        fab.setImageDrawable(resources.getDrawable(R.drawable.play))
    }

    override fun viewPoint(team: String, point: Int) {

        Snackbar.make(
                coordinator_layout,
                team,
                Snackbar.LENGTH_SHORT
        ).apply {

            setAction("+$point") {  }
            setActionTextColor(resources.getColor(R.color.colorAccent))

            val params = view.layoutParams as CoordinatorLayout.LayoutParams

            params.setMargins(
                    params.leftMargin,
                    params.topMargin,
                    params.rightMargin,
                    params.bottomMargin + bar.height + (fab.height / (PI / 2)).toInt()
            )

            view.layoutParams = params

            show()
        }
    }

    override fun setPointOne(text: String) {
        score_1.text = text
    }

    override fun setPointTwo(text: String) {
        score_2.text = text
    }

    private fun initClickPoint() {

        team_one_1.setOnClickListener { presenter.setScoreOne(1, (score_1.text.toString()).toInt()) }
        team_one_2.setOnClickListener { presenter.setScoreOne(2, (score_1.text.toString()).toInt()) }
        team_two_1.setOnClickListener { presenter.setScoreTwo(1, (score_2.text.toString()).toInt()) }
        team_two_2.setOnClickListener { presenter.setScoreTwo(2, (score_2.text.toString()).toInt()) }
    }

    private fun exitGame() {

        ExitDialog().apply {

            setOnClickListener(object : ExitDialog.OnClickListener {
                override fun onClick() {

                    presenter.finishActivity()
                }
            })
            show(supportFragmentManager, "exit_dialog")
        }
    }
}
