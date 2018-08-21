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

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_team.view.*
import kz.abt.admin.R
import kz.abt.admin.room.table.Team

class TeamAdapter : RecyclerView.Adapter<TeamAdapter.TeamHolder>() {
    private var list: MutableList<Team> = mutableListOf()
    private lateinit var clickListener: OnClickListener

    interface OnClickListener {

        fun clickListener(team: Team)
    }

    fun setList(list: MutableList<Team>) {
        this.list = list

        notifyDataSetChanged()
    }

    fun setClickListener(clickListener: OnClickListener) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): TeamHolder = TeamHolder(LayoutInflater
            .from(parent.context).inflate(R.layout.item_team, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(p0: TeamHolder, p1: Int) {

        p0.init(p1 + 1, list[p1].title, list[p1].point)
        p0.itemView.setOnClickListener { clickListener.clickListener(list[p1]) }
        p0.initBackground(p1)
    }

    class TeamHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        fun init(number: Int, title: String, point: Int) {
            itemView.number.text = number.toString()
            itemView.title.text = title
            itemView.point.text = point.toString()
        }

        fun initBackground(p1: Int) {

            if ((p1 % 2) == 0) {

                itemView.b.setBackgroundColor(itemView.resources.getColor(R.color.colorPrimary))
                itemView.point.setTextColor(itemView.resources.getColor(R.color.colorPrimary))
            } else {

                itemView.b.setBackgroundColor(itemView.resources.getColor(R.color.colorAccent))
                itemView.point.setTextColor(itemView.resources.getColor(R.color.colorAccent))
            }
        }
    }
}