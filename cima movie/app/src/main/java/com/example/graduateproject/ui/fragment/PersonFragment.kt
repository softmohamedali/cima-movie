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
import com.example.graduateproject.adapters.CardPeopleAdapter
import com.example.graduateproject.adapters.CardPeopleSAdapter
import com.example.graduateproject.adapters.CardSeriesAdapter
import com.example.graduateproject.models.ResultX
import com.example.graduateproject.models.ResultXX
import com.example.graduateproject.util.StatusResult
import com.example.graduateproject.viewmodels.ApplayViewModel
import com.example.graduateproject.viewmodels.DataViewModels
import com.todkars.shimmer.ShimmerRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_movie.view.*
import kotlinx.android.synthetic.main.fragment_person.view.*
import kotlinx.android.synthetic.main.fragment_tv.view.*

@AndroidEntryPoint
class PersonFragment : Fragment() {

    private val dataViewModel by viewModels<DataViewModels>()
    private val applyViewModel by viewModels<ApplayViewModel>()
    private lateinit var mview:View
    private val mAdapterPeople by lazy { CardPeopleSAdapter() }
    private var currentPage=1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mview= inflater.inflate(R.layout.fragment_person, container, false)
        setupRecy()

        observeAllData()
        mview.recy_personfrag.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
    fun setupRecy(){
        mview.recy_personfrag.apply {
            adapter=mAdapterPeople
            layoutManager= GridLayoutManager(requireActivity(),2)
        }
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
    fun observeAllData() {
        dataViewModel.getPopularPeoples(applyViewModel.appluQueri(currentPage.toString()))
        dataViewModel.people.observe(viewLifecycleOwner,{
            if (it is StatusResult.OnSuccess) {
                mAdapterPeople.setdata(it.data?.results as ArrayList<ResultXX>)
                showShimmer(false,mview.recy_personfrag)
            }
            if (it is StatusResult.OnLoading) {
                showShimmer(true,mview.recy_personfrag)
            }
            if (it is StatusResult.OnError) {
                Toast.makeText(requireContext(),it.massage,Toast.LENGTH_LONG).show()
                showShimmer(true,mview.recy_personfrag)
            }
        })
    }


}