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

package kz.abt.admin.ui.adapters

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_complete.view.*
import kz.abt.admin.R
import kz.abt.admin.ui.util.CompleteJSON

class CompleteAdapter : RecyclerView.Adapter<CompleteAdapter.CompleteHolder>() {
    private var list: MutableList<CompleteJSON> = mutableListOf()

    fun setList(list: MutableList<CompleteJSON>) {
        this.list = list

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): CompleteHolder = CompleteHolder(LayoutInflater
            .from(parent.context).inflate(R.layout.item_complete, parent, false))

    override fun getItemCount(): Int = list.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(p0: CompleteHolder, p1: Int) {

        p0.itemView.score.text = "${list[p1].s1} - ${list[p1].s2}"
        p0.itemView.title_1.text = list[p1].teamTitleOne
        p0.itemView.title_2.text = list[p1].teamTitleTwo

        p0.initScore(list[p1].s1, list[p1].s2)
        p0.initWinner(list[p1].s1, list[p1].s2, p1)
        p0.initHeader(p1)
    }

    class CompleteHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        fun initWinner(s1: Int, s2: Int, p1: Int) {

            when {
                s1 == s2 -> {

                    if ((p1 % 2) == 0) {

                        itemView.winner_one.setBackgroundColor(itemView.resources.getColor(R.color.colorAccent))
                        itemView.winner_two.setBackgroundColor(itemView.resources.getColor(R.color.colorAccent))
                    } else {

                        itemView.winner_one.setBackgroundColor(itemView.resources.getColor(R.color.colorPrimary))
                        itemView.winner_two.setBackgroundColor(itemView.resources.getColor(R.color.colorPrimary))
                    }
                }
                s1 > s2 -> {

                    itemView.winner_one.setBackgroundColor(itemView.resources.getColor(R.color.colorAccent))
                    itemView.winner_two.setBackgroundColor(itemView.resources.getColor(R.color.colorPrimary))
                }
                s1 < s2 -> {

                    itemView.winner_one.setBackgroundColor(itemView.resources.getColor(R.color.colorPrimary))
                    itemView.winner_two.setBackgroundColor(itemView.resources.getColor(R.color.colorAccent))
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun initHeader(p1: Int) {
            itemView.number.text = "${itemView.resources.getString(R.string.complete_game)} ${p1 + 1}"

            if ((p1 % 2) == 0)
                itemView.header.setBackgroundColor(itemView.resources.getColor(R.color.colorAccent))
            else
                itemView.header.setBackgroundColor(itemView.resources.getColor(R.color.colorPrimary))
        }

        @SuppressLint("SetTextI18n")
        fun initScore(s1: Int, s2: Int) {

            itemView.score.text = "${if (s1 <= 9) "0$s1" else s1} - ${if (s2 <= 9) "0$s2" else s2}"
        }
    }
}