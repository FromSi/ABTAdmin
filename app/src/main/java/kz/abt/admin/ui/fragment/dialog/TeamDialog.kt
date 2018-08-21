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
import kotlinx.android.synthetic.main.dialog_team.view.*
import kz.abt.admin.R
import kz.abt.admin.ui.util.TeamJSON

class TeamDialog : DialogFragment() {
    private lateinit var clickListener: OnClickListener

    interface OnClickListener {

        fun onClick(teamJSON: TeamJSON)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = resources.getString(R.string.dialog_team_title)
        val positive = resources.getString(R.string.dialog_team_positive)
        val neutral = resources.getString(R.string.dialog_team_neutral)

        return AlertDialog.Builder(activity!!).apply {
            val customLayout = activity!!.layoutInflater.inflate(R.layout.dialog_team, null)

            setView(customLayout)
            setTitle(title)
            setPositiveButton(positive) { _, _ ->
                clickListener.onClick(
                        TeamJSON(
                                customLayout.title.text.toString(),
                                customLayout.p1.text.toString(),
                                customLayout.p2.text.toString(),
                                customLayout.p3.text.toString(),
                                customLayout.p4.text.toString(),
                                customLayout.p5.text.toString()
                        )
                )
            }
            setNeutralButton(neutral) { _, _ -> }
        }.create()
    }

    fun clickListener(clickListener: OnClickListener) {
        this.clickListener = clickListener
    }
}