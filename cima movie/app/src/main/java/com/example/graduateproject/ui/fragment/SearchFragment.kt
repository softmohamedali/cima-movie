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
import com.example.graduateproject.adapters.CardMovieSAdapter
import com.example.graduateproject.adapters.CardMovieTAdapter
import com.example.graduateproject.models.Result
import com.example.graduateproject.util.StatusResult
import com.example.graduateproject.viewmodels.ApplayViewModel
import com.example.graduateproject.viewmodels.DataViewModels
import com.todkars.shimmer.ShimmerRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val dataViewModel by viewModels<DataViewModels>()
    private val applyViewModel by viewModels<ApplayViewModel>()
    private val mAdapterMovie by lazy { CardMovieTAdapter() }
    private var currentPage=1
    private var query="won"
    private lateinit var mview:View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mview=inflater.inflate(R.layout.fragment_search, container, false)
        setupRecy()
        mview.recy_searsh_searchfrag.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    currentPage++
                    applyViewModel.appluQueri(currentPage.toString())
                    getSearchMovie(query,currentPage)
                }
            }
        })
        mview.fab_searsh_searshfrag.setOnClickListener{
            query=mview.ed_searsh_searchfrag.text.toString()
            getSearchMovie(mview.ed_searsh_searchfrag.text.toString(),currentPage)
        }



        return mview
    }

    fun setupRecy()
    {
        mview.recy_searsh_searchfrag.apply {
            adapter=mAdapterMovie
            layoutManager= GridLayoutManager(requireActivity(),2)
        }
    }

    fun getSearchMovie(query:String,page:Int) {
        dataViewModel.getSearshMovie(applyViewModel.appluSearchQueri(query,page.toString()))
        dataViewModel.searshMovie.observe(viewLifecycleOwner, {
            if (it is StatusResult.OnSuccess) {
                mAdapterMovie.setNewData(it.data?.results as ArrayList<Result>)
                showShimmer(false, mview.recy_searsh_searchfrag)
            }
            if (it is StatusResult.OnLoading) {
                showShimmer(true, mview.recy_searsh_searchfrag)
            }
            if (it is StatusResult.OnError) {
                Toast.makeText(requireContext(), it.massage, Toast.LENGTH_LONG).show()
                showShimmer(true, mview.recy_searsh_searchfrag)
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