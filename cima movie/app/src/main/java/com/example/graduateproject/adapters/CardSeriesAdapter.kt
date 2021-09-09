package com.example.graduateproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.graduateproject.databinding.SeriesCardBinding
import com.example.graduateproject.models.Result
import com.example.graduateproject.models.ResultX
import com.example.graduateproject.util.MyDiffUtil

class CardSeriesAdapter : RecyclerView.Adapter<CardSeriesAdapter.VH>() {

    var series:ArrayList<ResultX> = ArrayList()

    class VH(private var view: SeriesCardBinding): RecyclerView.ViewHolder(view.root){

        fun bind(series: ResultX)
        {
            view.series=series
            view.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup):VH
            {
                val view= SeriesCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)

                return VH(view)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH.from(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (!(series[position].posterPath.isNullOrEmpty()))
        {
            holder.bind(series[position])
        }
    }

    override fun getItemCount(): Int {
        return series.size
    }

    fun setdata(data:ArrayList<ResultX>)
    {
        val mydiff= MyDiffUtil(series,data)
        val clac= DiffUtil.calculateDiff(mydiff)
        series=data
        clac.dispatchUpdatesTo(this)
    }

}