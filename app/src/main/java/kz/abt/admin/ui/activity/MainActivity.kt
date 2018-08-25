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

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kz.abt.admin.R
import kz.abt.admin.mvp.model.MainModelImpl
import kz.abt.admin.mvp.presenter.MainPresenter
import kz.abt.admin.mvp.view.MainView
import kz.abt.admin.room.table.Team
import kz.abt.admin.ui.fragment.CompleteFragment
import kz.abt.admin.ui.fragment.GameFragment
import kz.abt.admin.ui.fragment.TeamFragment
import kz.abt.admin.ui.fragment.dialog.TeamDialog
import kz.abt.admin.ui.fragment.sheet.GameBottomSheet
import kz.abt.admin.ui.util.TeamJSON
import me.imid.swipebacklayout.lib.SwipeBackLayout
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper

class MainActivity : MvpAppCompatActivity(), MainView {
    private var doubleBackToExitPressedOnce = false

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        setContentView(R.layout.activity_main)
        SwipeBackActivityHelper(this)
                .apply {

                    onActivityCreate()
                    swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
                    onPostCreate()
                }
        fab.setOnClickListener { presenter.clickFab() }
        presenter.initPresenter(intent.getIntExtra("idTournament", 1))
        text_1.setTextColor(resources.getColor(R.color.colorAccent))
        icon_1.setColorFilter(resources.getColor(R.color.colorAccent))
    }

    override fun openDialogTeam() {

        TeamDialog().apply {

            clickListener(object : TeamDialog.OnClickListener {
                override fun onClick(teamJSON: TeamJSON) {

                    presenter.insertTeam(teamJSON)
                }
            })
            show(supportFragmentManager, "team_dialog")
        }
    }

    override fun openSheetGame(list: MutableList<Team>) {

        GameBottomSheet().apply {

            setClickListener(object : GameBottomSheet.OnClickListener {
                override fun getAccept(one: Int, two: Int) {

                    presenter.insertGame(one, two)
                }
            })
            setList(list)
            show(supportFragmentManager, "bottom_sheet_game")
        }
    }

    override fun initClickMenu() {
        for (i in 0 until menu.childCount)
            menu.getChildAt(i).setOnClickListener {

                presenter.setFragment(
                        when (i) {
                            0 -> MainModelImpl.State.GAME
                            1 -> MainModelImpl.State.TEAM
                            2 -> MainModelImpl.State.COMPLETE
                            else -> MainModelImpl.State.GAME
                        }
                )
            }
    }

    override fun setFragment(state: MainModelImpl.State, idTournament: Int) {

        supportFragmentManager
                .beginTransaction()
                .apply {

                    replace(R.id.fragment, getFragment(state, idTournament))
                    commit()
                }
    }

    override fun initFragment(state: MainModelImpl.State, idTournament: Int) {

        supportFragmentManager
                .beginTransaction()
                .apply {

                    replace(R.id.fragment, getFragment(state, idTournament))
                    commit()
                }
    }

    override fun onBackPressed() {

        if (doubleBackToExitPressedOnce) {

            super.onBackPressed()

            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)

            return
        }

        this.doubleBackToExitPressedOnce = true

        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    private fun getFragment(state: MainModelImpl.State, idTournament: Int): Fragment = when (state) {
        MainModelImpl.State.GAME -> {

            text_1.setTextColor(resources.getColor(R.color.colorAccent))
            icon_1.setColorFilter(resources.getColor(R.color.colorAccent))
            text_2.setTextColor(Color.BLACK)
            icon_2.setColorFilter(Color.BLACK)
            text_3.setTextColor(Color.BLACK)
            icon_3.setColorFilter(Color.BLACK)
            presenter.setState(MainModelImpl.State.GAME)
            GameFragment().apply {
                arguments = Bundle().apply {
                    putInt("idTournament", idTournament)
                }
            }
        }
        MainModelImpl.State.TEAM -> {

            text_1.setTextColor(Color.BLACK)
            icon_1.setColorFilter(Color.BLACK)
            text_2.setTextColor(resources.getColor(R.color.colorAccent))
            icon_2.setColorFilter(resources.getColor(R.color.colorAccent))
            text_3.setTextColor(Color.BLACK)
            icon_3.setColorFilter(Color.BLACK)
            presenter.setState(MainModelImpl.State.TEAM)
            TeamFragment().apply {
                arguments = Bundle().apply {
                    putInt("idTournament", idTournament)
                }
            }
        }
        MainModelImpl.State.COMPLETE -> {

            text_1.setTextColor(Color.BLACK)
            icon_1.setColorFilter(Color.BLACK)
            text_2.setTextColor(Color.BLACK)
            icon_2.setColorFilter(Color.BLACK)
            text_3.setTextColor(resources.getColor(R.color.colorAccent))
            icon_3.setColorFilter(resources.getColor(R.color.colorAccent))
            presenter.setState(MainModelImpl.State.COMPLETE)
            CompleteFragment().apply {
                arguments = Bundle().apply {
                    putInt("idTournament", idTournament)
                }
            }
        }
    }
}
