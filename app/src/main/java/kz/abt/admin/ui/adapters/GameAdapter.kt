package kz.abt.admin.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_game_team.view.*
import kz.abt.admin.R
import kz.abt.admin.room.table.Team

class GameAdapter : RecyclerView.Adapter<GameAdapter.GameHolder>() {
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
    ): GameHolder = GameHolder(LayoutInflater
            .from(parent.context).inflate(R.layout.item_game_team, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(p0: GameHolder, p1: Int) {

        p0.init(p1 + 1, list[p1].title, list[p1].point)
        p0.itemView.setOnClickListener { clickListener.clickListener(list[p1]) }
    }

    class GameHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        fun init(number: Int, title: String, point: Int) {
            itemView.number.text = number.toString()
            itemView.title.text = title
            itemView.point.text = point.toString()
        }
    }
}