package com.example.graduateproject.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.graduateproject.R
import com.example.graduateproject.adapters.CardgenersAdpter
import com.example.graduateproject.data.local.MovieEntity
import com.example.graduateproject.models.Result
import com.example.graduateproject.models.ResultX
import com.example.graduateproject.models.ResultXX
import com.example.graduateproject.util.Constants
import com.example.graduateproject.util.StatusResult
import com.example.graduateproject.viewmodels.ApplayViewModel
import com.example.graduateproject.viewmodels.DataViewModels
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_details.view.*
import java.lang.Exception

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var mview:View
    private val dataViewModel by viewModels<DataViewModels>()
    private val applyViewModel by viewModels<ApplayViewModel>()
    private val navArgs by navArgs<DetailsFragmentArgs>()
    private val genersAdapter by lazy { CardgenersAdpter() }
    private var isSave=false
    private var movieSavedId=0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        mview =inflater.inflate(R.layout.fragment_movie_details, container, false)
        val movie=navArgs.result
        val series=navArgs.series
        val actor=navArgs.actor

        if (movie!=null){
            setUprecy()
            setInfo(movie)
            getMovieinfo(movie.id)
            dataViewModel.allfavMovie.observe(viewLifecycleOwner,{
                it.forEach {
                    if (movie.id==it.movie.id)
                    {
                        isSave=true
                        movieSavedId=it.id
                        setHeartColor(R.color.red)
                    }
                    else{
                        isSave=false
                        setHeartColor(R.color.hint)
                    }
                }
            })
            mview.imgb_fav_infofrag.setOnClickListener {
                if (isSave){
                    dataViewModel.deleteFavMovie(MovieEntity(movieSavedId,movie))
                    isSave=false
                    setHeartColor(R.color.hint)
                }else{
                    dataViewModel.insertFavMovie(MovieEntity(0,movie))
                    isSave=true
                    setHeartColor(R.color.red)
                }
            }
        }
        if (series!=null){
            setUprecy()
            setInfo(series)
            getMovieinfo(series.id)
        }
        if (actor!=null){
            setUprecy()
            setInfo(actor)
        }
        mview.imgb_back_modfrag.setOnClickListener {
            findNavController().popBackStack()
        }


        return mview
    }

    fun setInfo(result: Any)
    {
        if (result is Result){
            mview.img_backposter_moviedetilfrag.load(Constants.BASE_IMG_URL+result.backdropPath){
                this.crossfade(600)
            }
            mview.img_poster_moviedetilfrag.load(Constants.BASE_IMG_URL+result.posterPath) {
                this.crossfade(600)
            }
            mview.tv_lang_moviedetialfrag.text=result.originalLanguage
            mview.tv_vote_moviedetialfrag.text=result.voteAverage.toString()
            mview.tv_name_moviedetialfrag.text=result.title
            mview.tv_overview_moviedetialfrag.text=result.overview
            mview.tv_popul_moviedetialfrag.text=result.popularity.toString()
            mview.tv_relesedate_moviedetialfrag.text=result.releaseDate
            mview.tv_votecount_moviedetialfrag.text=result.voteCount.toString()
            mview.tv_mediatype_moviedetails.text="Movie"
            val player=mview.vv_triler_details
            lifecycle.addObserver(player)
            player.addYouTubePlayerListener(object :AbstractYouTubePlayerListener(){
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    Log.d("iiid",result.id.toString())
                    val viedio =getVedioInfo(result.id)
                    youTubePlayer.loadVideo(viedio,0f)
                }
            })
        }
        if (result is ResultX){
            mview.img_backposter_moviedetilfrag.load(Constants.BASE_IMG_URL+result.backdropPath){
                this.crossfade(600)
            }
            mview.img_poster_moviedetilfrag.load(Constants.BASE_IMG_URL+result.posterPath) {
                this.crossfade(600)
            }
            mview.tv_lang_moviedetialfrag.text=result.originalLanguage
            mview.tv_vote_moviedetialfrag.text=result.voteAverage.toString()
            mview.tv_name_moviedetialfrag.text=result.name
            mview.tv_overview_moviedetialfrag.text=result.overview
            mview.tv_popul_moviedetialfrag.text=result.popularity.toString()
            mview.tv_relesedate_moviedetialfrag.text=result.firstAirDate
            mview.tv_votecount_moviedetialfrag.text=result.voteCount.toString()
            mview.tv_mediatype_moviedetails.text="Tv"
            mview.imgb_fav_infofrag.visibility=View.VISIBLE

        }
        if (result is ResultXX){
            mview.img_backposter_moviedetilfrag.load(Constants.BASE_IMG_URL+result.profilePath){
                this.crossfade(600)
            }
            mview.img_poster_moviedetilfrag.load(Constants.BASE_IMG_URL+result.profilePath) {
                this.crossfade(600)
            }
            mview.tv_lang_moviedetialfrag.visibility=View.GONE
            mview.tv_vote_moviedetialfrag.visibility=View.GONE
            mview.tv_name_moviedetialfrag.text=result.name
            mview.tv_overview_moviedetialfrag.visibility=View.GONE
            mview.tv_popul_moviedetialfrag.text=result.popularity.toString()
            mview.tv_relesedate_moviedetialfrag.visibility=View.GONE
            mview.tv_votecount_moviedetialfrag.visibility=View.GONE
            mview.tv_mediatype_moviedetails.text="Actor"
            mview.tv_language_details.visibility=View.GONE
            mview.tv_vote_details.visibility=View.GONE
            mview.tv_votecount_details.visibility=View.GONE
            mview.tv_relesed_details.visibility=View.GONE
            mview.tv_type_details.visibility=View.GONE
            mview.tv_overview_details.visibility=View.GONE
            mview.tv_trailer_details.visibility=View.GONE
            mview.vv_triler_details.visibility=View.GONE
            mview.imgb_fav_infofrag.visibility=View.VISIBLE
        }
    }

    fun getMovieinfo(id:Int) {
        dataViewModel.getMovieById(applyViewModel.applyApiKey(),id)
        dataViewModel.movieById.observe(viewLifecycleOwner,{
            if (it is StatusResult.OnSuccess) {
                try {
                    val geners=ArrayList<String>()
                    it.data!!.genres.forEach {
                        geners.add(it.name)
                    }
                    genersAdapter.setdata(geners)

                }
                catch (e:Exception){

                }
            }
            if (it is StatusResult.OnLoading) {

            }
            if (it is StatusResult.OnError) {
                Toast.makeText(requireContext(),it.massage, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun getSeriesinfo(id:Int) {
        dataViewModel.getTvById(applyViewModel.applyApiKey(),id)
        dataViewModel.seriesById.observe(viewLifecycleOwner,{
            if (it is StatusResult.OnSuccess) {
                try {
                    val geners=ArrayList<String>()
                    it.data!!.genres.forEach {
                        geners.add(it.name)
                    }
                    genersAdapter.setdata(geners)

                }
                catch (e:Exception){

                }
            }
            if (it is StatusResult.OnLoading) {

            }
            if (it is StatusResult.OnError) {
                Toast.makeText(requireContext(),it.massage, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun getPeopleinfo(id:Int) {
        dataViewModel.getActorById(applyViewModel.applyApiKey(),id)
        dataViewModel.PeopleById.observe(viewLifecycleOwner,{
            if (it is StatusResult.OnSuccess) {
                try {


                }
                catch (e:Exception){

                }
            }
            if (it is StatusResult.OnLoading) {

            }
            if (it is StatusResult.OnError) {
                Toast.makeText(requireContext(),it.massage, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun getVedioInfo(id:Int):String {
        var key=""
        dataViewModel.getVedio(applyViewModel.applyApiKey(),id)
        dataViewModel.vedio.observe(viewLifecycleOwner,{
            if (it is StatusResult.OnSuccess) {
                if (it.data!!.results[0].site=="YouTube")
                {
                    key=it.data!!.results[0].key
                    Log.d("iiid",key)
                }
            }
            if (it is StatusResult.OnLoading) {

            }
            if (it is StatusResult.OnError) {
                Toast.makeText(requireContext(),it.massage, Toast.LENGTH_LONG).show()
            }
        })
        return key
    }

    fun setUprecy(){
        mview.recy_gener_moviedetails.apply {
            adapter=genersAdapter
            layoutManager= FlexboxLayoutManager(requireActivity(),FlexDirection.ROW)
        }
    }

    fun  setHeartColor(color:Int){
        mview.imgb_fav_infofrag.setColorFilter(ContextCompat.getColor(requireActivity(),color))
    }




}