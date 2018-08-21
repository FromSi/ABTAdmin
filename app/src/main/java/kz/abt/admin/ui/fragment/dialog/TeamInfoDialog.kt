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

package kz.abt.admin.ui.fragment.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import kotlinx.android.synthetic.main.dialog_team_info.view.*
import kz.abt.admin.R
import kz.abt.admin.room.table.Players
import kz.abt.admin.room.table.Team

class TeamInfoDialog : DialogFragment() {
    private lateinit var team: Team
    private lateinit var players: Players

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val positive = resources.getString(R.string.dialog_team_info_positive)

        return AlertDialog.Builder(activity!!).apply {
            val customLayout = activity!!.layoutInflater.inflate(R.layout.dialog_team_info, null)

            setView(customLayout)
            initView(customLayout)
            setPositiveButton(positive) { _, _ -> }
        }.create()
    }

    fun setData(team: Team, players: Players) {
        this.team = team
        this.players = players
    }

    private fun initView(view: View) {
        view.title.text = team.title
        view.p1.text = players.p1
        view.p2.text = players.p2
        view.p3.text = players.p3
        view.p4.text = players.p4
        view.p5.text = players.p5
    }
}