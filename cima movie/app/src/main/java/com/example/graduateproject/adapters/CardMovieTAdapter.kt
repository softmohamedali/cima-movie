package com.example.graduateproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.graduateproject.databinding.MovieCard2Binding
import com.example.graduateproject.databinding.MovieCard3Binding
import com.example.graduateproject.models.Result
import com.example.graduateproject.util.MyDiffUtil

class CardMovieTAdapter : RecyclerView.Adapter<CardMovieTAdapter.VH>() {

    var movies:ArrayList<Result> = ArrayList()

    class VH(private var view: MovieCard3Binding): RecyclerView.ViewHolder(view.root){

        fun bind(movie: Result)
        {
            view.movie=movie
            view.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup):VH
            {
                val view= MovieCard3Binding.inflate(LayoutInflater.from(parent.context),parent,false)

                return VH(view)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH.from(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (movies[position].posterPath.isNotEmpty())
        {
            holder.bind(movies[position])
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun setdata(data:ArrayList<Result>)
    {
        val mydiff= MyDiffUtil(movies,data)
        val clac= DiffUtil.calculateDiff(mydiff)
        movies=data
        clac.dispatchUpdatesTo(this)
    }
    fun setNewData(result: List<Result>)
    {
        val diffUtil = MyDiffUtil<Result>(movies,result)
        val diffresult= DiffUtil.calculateDiff(diffUtil)
        movies.clear()
        movies.addAll(result)
        diffresult.dispatchUpdatesTo(this)
    }
}