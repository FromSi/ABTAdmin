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
import kotlinx.android.synthetic.main.item_game.view.*
import kz.abt.admin.R
import kz.abt.admin.ui.util.GameJSON

class GameAdapter : RecyclerView.Adapter<GameAdapter.GameHolder>() {
    private var list: MutableList<GameJSON> = mutableListOf()

    private lateinit var clickListener: OnClickListener

    interface OnClickListener {

        fun onClick(idTeamOne: Int, idTeamTwo: Int, idGame: Int)
    }

    fun setList(list: MutableList<GameJSON>) {
        this.list = list

        notifyDataSetChanged()
    }

    fun setOnClickListener(clickListener: OnClickListener) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): GameHolder = GameHolder(LayoutInflater
            .from(parent.context).inflate(R.layout.item_game, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(p0: GameHolder, p1: Int) {

        p0.init(p1 + 1, list[p1])
        p0.itemView.card.setOnClickListener {

            clickListener.onClick(
                    list[p1].teamOne.idTeam,
                    list[p1].teamTwo.idTeam,
                    list[p1].idGame
            )
        }
    }

    class GameHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        fun init(number: Int, gameJSON: GameJSON) {

            itemView.number.text = number.toString()
            itemView.title_1.text = gameJSON.teamOne.title
            itemView.title_2.text = gameJSON.teamTwo.title
        }
    }
}