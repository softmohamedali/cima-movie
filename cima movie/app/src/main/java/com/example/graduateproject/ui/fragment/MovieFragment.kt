package com.example.graduateproject.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduateproject.R
import com.example.graduateproject.adapters.CardMovieAdapter
import com.example.graduateproject.adapters.CardMovieSAdapter
import com.example.graduateproject.models.Result
import com.example.graduateproject.util.StatusResult
import com.example.graduateproject.viewmodels.ApplayViewModel
import com.example.graduateproject.viewmodels.DataViewModels
import com.todkars.shimmer.ShimmerRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_movie.view.*

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private val dataViewModel by viewModels<DataViewModels>()
    private val applyViewModel by viewModels<ApplayViewModel>()

    private lateinit var mview:View
    private val mAdapterMovie by lazy { CardMovieSAdapter() }
    private var currentPage=1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mview= inflater.inflate(R.layout.fragment_movie, container, false)
        setupRecy()

        observeAllData()


        mview.recy_movie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    currentPage++
                    applyViewModel.appluQueri(currentPage.toString())
                    observeAllData()


                }
            }
        })

        return mview
    }

    fun setupRecy()
    {
        mview.recy_movie.apply {
            adapter=mAdapterMovie
            layoutManager=GridLayoutManager(requireActivity(),2)
        }
    }
    fun observeAllData() {
        dataViewModel.getMovies(applyViewModel.appluQueri(currentPage.toString()))
        dataViewModel.movies.observe(viewLifecycleOwner, {
            if (it is StatusResult.OnSuccess) {
                mAdapterMovie.setNewData(it.data?.results as ArrayList<Result>)
                showShimmer(false, mview.recy_movie)
            }
            if (it is StatusResult.OnLoading) {
                showShimmer(true, mview.recy_movie)
            }
            if (it is StatusResult.OnError) {
                Toast.makeText(requireContext(), it.massage, Toast.LENGTH_LONG).show()
                showShimmer(true, mview.recy_movie)
            }
        })
    }

    fun showShimmer(show:Boolean,recy: ShimmerRecyclerView)
    {
        if (show)
        {
            recy.showShimmer()
        }else{
            recy.hideShimmer()
        }
    }

}