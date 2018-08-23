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

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_game_team.view.*
import kz.abt.admin.R
import kz.abt.admin.room.table.Team

class GameTeamAdapter : RecyclerView.Adapter<GameTeamAdapter.GameHolder>() {
    private var list: MutableList<Team> = mutableListOf()
    private var pOne = -1
    private var pTwo = -1

    fun setList(list: MutableList<Team>) {
        this.list = list

        notifyDataSetChanged()
    }

    fun getTeamOne(): Int = list[pOne].idTeam

    fun getTeamTwo(): Int = list[pTwo].idTeam

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): GameHolder = GameHolder(LayoutInflater
            .from(parent.context).inflate(R.layout.item_game_team, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(p0: GameHolder, p1: Int) {

        p0.init(p1 + 1, list[p1].title, list[p1].point)
        p0.itemView.setOnClickListener {

            if ((p1 != pOne) && (p1 != pTwo)) {

                if (pOne == -1) {
                    pOne = p1

                    p0.itemView.item.setBackgroundColor(Color.BLUE)
                } else if (pTwo == -1) {
                    pTwo = p1

                    p0.itemView.item.setBackgroundColor(Color.YELLOW)
                }
            } else if (p1 == pOne) {
                pOne = -1

                p0.itemView.item.setBackgroundColor(Color.WHITE)
            } else if (p1 == pTwo) {
                pTwo = -1

                p0.itemView.item.setBackgroundColor(Color.WHITE)
            }
        }

        if (p1 == pOne) {

            p0.itemView.item.setBackgroundColor(Color.BLUE)
        } else if (p1 == pTwo) {

            p0.itemView.item.setBackgroundColor(Color.YELLOW)
        }
    }

    class GameHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        fun init(number: Int, title: String, point: Int) {
            itemView.number.text = number.toString()
            itemView.title.text = title
            itemView.point.text = point.toString()
        }
    }
}