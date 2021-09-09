package com.example.graduateproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.graduateproject.databinding.PeopleCardBinding
import com.example.graduateproject.models.ResultXX
import com.example.graduateproject.util.MyDiffUtil

class CardPeopleAdapter : RecyclerView.Adapter<CardPeopleAdapter.VH>() {

    var people:ArrayList<ResultXX> = ArrayList()

    class VH(private var view: PeopleCardBinding): RecyclerView.ViewHolder(view.root){

        fun bind(people: ResultXX)
        {
            view.people=people
            view.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup):VH
            {
                val view= PeopleCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)

                return VH(view)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH.from(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (!(people[position].profilePath.isNullOrEmpty()))
        {
            holder.bind(people[position])
        }
    }

    override fun getItemCount(): Int {
        return people.size
    }

    fun setdata(data:ArrayList<ResultXX>)
    {
        val mydiff= MyDiffUtil(people,data)
        val clac= DiffUtil.calculateDiff(mydiff)
        people=data
        clac.dispatchUpdatesTo(this)
    }

}