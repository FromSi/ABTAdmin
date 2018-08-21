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
import kotlinx.android.synthetic.main.item_tournament.view.*
import kz.abt.admin.R
import kz.abt.admin.room.table.Tournament

class TournamentAdapter : RecyclerView.Adapter<TournamentAdapter.TournamentHolder>() {
    private var list: MutableList<Tournament> = mutableListOf()
    private lateinit var clickListener: OnClickListener

    interface OnClickListener {

        fun clickListener(idTournament: Int)
    }

    fun setList(list: MutableList<Tournament>) {
        this.list = list

        notifyDataSetChanged()
    }

    fun setClickListener(clickListener: OnClickListener) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): TournamentHolder = TournamentHolder(LayoutInflater
            .from(parent.context).inflate(R.layout.item_tournament, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(p0: TournamentHolder, p1: Int) {

        p0.init(list[p1].typeTournament, list[p1].date, list[p1].number)
        p0.itemView.card.setOnClickListener { clickListener.clickListener(list[p1].idTournament) }
    }

    class TournamentHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        @SuppressLint("SetTextI18n")
        fun init(typeTournament: Boolean, date: String, number: Int) {
            itemView.date.text = date
            itemView.number.text = "#$number"

            if (typeTournament)
                itemView.type_tournament.text = itemView.resources.getString(R.string.tournament_one)
            else
                itemView.type_tournament.text = itemView.resources.getString(R.string.tournament_two)
        }
    }
}