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

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import kotlinx.android.synthetic.main.dialog_tournament.view.*
import kz.abt.admin.R
import kz.abt.admin.room.table.Tournament
import android.widget.TextView
import java.util.*

class TournamentDialog : DialogFragment() {
    private lateinit var clickListener: OnClickListener

    interface OnClickListener {

        fun onClick(list: List<Tournament>)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val positive = resources.getString(R.string.dialog_tournament_positive)
        val neutral = resources.getString(R.string.dialog_tournament_neutral)

        return AlertDialog.Builder(activity!!).apply {
            val customLayout = activity!!.layoutInflater.inflate(R.layout.dialog_tournament, null)

            setView(customLayout)
            initView(customLayout)
            setPositiveButton(positive) { _, _ ->
                val list: MutableList<Tournament> = mutableListOf()

                for (i in (customLayout.date.childCount - 1) downTo 0)
                    list.add(Tournament(
                            customLayout.number.value,
                            i == 0,
                            (customLayout.date.getChildAt(i) as TextView).text.toString()
                    ))

                clickListener.onClick(list)
            }
            setNeutralButton(neutral) { _, _ -> }
        }.create()
    }

    fun clickListener(clickListener: OnClickListener) {
        this.clickListener = clickListener
    }

    private fun initView(view: View) {

        view.number.apply {
            minValue = 1
            maxValue = 12
        }

        for (i in 0 until view.date.childCount)
            view.date.getChildAt(i)
                    .setOnClickListener {
                        val calendar = Calendar.getInstance()

                        DatePickerDialog(
                                view.context,
                                initDatePicker(view.date.getChildAt(i) as TextView),
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }
    }

    @SuppressLint("SetTextI18n")
    private fun initDatePicker(
            textView: TextView
    ): DatePickerDialog.OnDateSetListener = DatePickerDialog.OnDateSetListener { p0, p1, p2, p3 ->
        val day: String = if (p3 >= 10) p3.toString() else "0$p3"
        val month: String = if (p2 >= 10) p2.toString() else "0$p2"
        textView.text = "$day.$month.$p1"
        textView.setTextColor(resources.getColor(R.color.colorAccent))
    }
}