package com.example.graduateproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.graduateproject.R
import com.example.graduateproject.models.ResultXX
import com.example.graduateproject.util.MyDiffUtil
import kotlinx.android.synthetic.main.gener_card.view.*

class CardgenersAdpter():RecyclerView.Adapter<CardgenersAdpter.Vh> (){

    private var geners=ArrayList<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        val layout=LayoutInflater.from(parent.context).inflate(R.layout.gener_card,parent,false)
        return Vh(layout)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.text.text=geners[position]
    }

    override fun getItemCount(): Int {
        return geners.size
    }
    class Vh(view: View):RecyclerView.ViewHolder(view){
        var text=view.tv_geners_generscard
    }

    fun setdata(data:ArrayList<String>)
    {
        val mydiff= MyDiffUtil(geners,data)
        val clac= DiffUtil.calculateDiff(mydiff)
        geners=data
        clac.dispatchUpdatesTo(this)
    }
}