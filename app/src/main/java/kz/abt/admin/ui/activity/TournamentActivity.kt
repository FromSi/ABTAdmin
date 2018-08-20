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
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_tournament.*
import kz.abt.admin.R
import kz.abt.admin.mvp.presenter.TournamentPresenter
import kz.abt.admin.mvp.view.TournamentView
import kz.abt.admin.room.table.Tournament
import kz.abt.admin.ui.fragment.dialog.TournamentDialog

class TournamentActivity : MvpAppCompatActivity(), TournamentView {
    @InjectPresenter
    lateinit var presenter: TournamentPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tournament)
        fab.setOnClickListener(initClickFab())
    }

    private fun initClickFab(): View.OnClickListener = View.OnClickListener {
        TournamentDialog().apply {

            clickListener(object : TournamentDialog.OnClickListener {
                override fun onClick(list: MutableList<Tournament>) {

                }
            })
            show(supportFragmentManager, "tournament_dialog")
        }
    }
}
