package com.example.graduateproject.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduateproject.R
import com.example.graduateproject.adapters.CardMovieAdapter
import com.example.graduateproject.adapters.CardPeopleAdapter
import com.example.graduateproject.adapters.CardSeriesAdapter
import com.example.graduateproject.models.Result
import com.example.graduateproject.models.ResultX
import com.example.graduateproject.models.ResultXX
import com.example.graduateproject.util.StatusResult
import com.example.graduateproject.viewmodels.ApplayViewModel
import com.example.graduateproject.viewmodels.DataViewModels
import com.todkars.shimmer.ShimmerRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.view.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val dataViewModel by viewModels<DataViewModels>()
    private val applyViewModel by viewModels<ApplayViewModel>()
    private lateinit var mview:View
    private val mAdapterMovie by lazy { CardMovieAdapter() }
    private val mAdapterSeries by lazy { CardSeriesAdapter() }
    private val mAdapterPeople by lazy { CardPeopleAdapter() }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mview =inflater.inflate(R.layout.fragment_home, container, false)
        setupRecy()
        dataViewModel.getMovies(applyViewModel.applyPopularMovie())
        dataViewModel.getSeries(applyViewModel.applyPopularSeries())
        dataViewModel.getPopularPeoples(applyViewModel.applyPopularPeople())
        observeAllData()

        mview.tv_seeall_movie_homefrag.setOnClickListener {
            findNavController().navigate(R.id.action_home_item_to_movie_item)
        }
        mview.tv_seeall_series_homefrag.setOnClickListener {
            findNavController().navigate(R.id.action_home_item_to_tv_item)
        }
        mview.tv_seeall_people_homefrag.setOnClickListener {
            findNavController().navigate(R.id.action_home_item_to_person_item)
        }

        mview.tv_search_homefrag.setOnClickListener{
            findNavController().navigate(R.id.action_home_item_to_searchFragment2)
        }

        return mview
    }

    fun setupRecy(){
        mview.sr_movie_homefrag.adapter=mAdapterMovie
        mview.sr_movie_homefrag.layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.HORIZONTAL,false)
        mview.sr_series_homefrag.adapter=mAdapterSeries
        mview.sr_series_homefrag.layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.HORIZONTAL,false)
        mview.sr_person_homefrag.adapter=mAdapterPeople
        mview.sr_person_homefrag.layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.HORIZONTAL,false)

    }

    fun showShimmer(show:Boolean,recy:ShimmerRecyclerView)
    {
        if (show)
        {
            recy.showShimmer()
        }else{
           recy.hideShimmer()
        }
    }

    fun observeAllData()
    {
        dataViewModel.movies.observe(viewLifecycleOwner,{
            if (it is StatusResult.OnSuccess) {
                mAdapterMovie.setdata(it.data?.results as ArrayList<Result>)
                showShimmer(false,mview.sr_movie_homefrag)
            }
            if (it is StatusResult.OnLoading) {
                showShimmer(true,mview.sr_movie_homefrag)
            }
            if (it is StatusResult.OnError) {
                Toast.makeText(requireContext(),it.massage,Toast.LENGTH_LONG).show()
                showShimmer(true,mview.sr_movie_homefrag)
            }
        })
        dataViewModel.series.observe(viewLifecycleOwner,{
            if (it is StatusResult.OnSuccess) {
                mAdapterSeries.setdata(it.data?.results as ArrayList<ResultX>)
                showShimmer(false,mview.sr_series_homefrag)
            }
            if (it is StatusResult.OnLoading) {
                showShimmer(true,mview.sr_series_homefrag)
            }
            if (it is StatusResult.OnError) {
                Toast.makeText(requireContext(),it.massage,Toast.LENGTH_LONG).show()
                showShimmer(true,mview.sr_series_homefrag)
            }
        })

        dataViewModel.people.observe(viewLifecycleOwner,{
            if (it is StatusResult.OnSuccess) {
                mAdapterPeople.setdata(it.data?.results as ArrayList<ResultXX>)
                showShimmer(false,mview.sr_person_homefrag)
            }
            if (it is StatusResult.OnLoading) {
                showShimmer(true,mview.sr_person_homefrag)
            }
            if (it is StatusResult.OnError) {
                Toast.makeText(requireContext(),it.massage,Toast.LENGTH_LONG).show()
                showShimmer(true,mview.sr_person_homefrag)
            }
        })
    }


}