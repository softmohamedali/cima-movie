package com.example.graduateproject.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.graduateproject.data.DataRepo
import com.example.graduateproject.data.local.MovieEntity
import com.example.graduateproject.models.*
import com.example.graduateproject.util.StatusResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DataViewModels @Inject constructor(
    var dataRepo:DataRepo,
    application: Application
) : AndroidViewModel(application) {
    //Loacl Source

    val allfavMovie=dataRepo.localSource.getAllFavMovie().asLiveData()

    fun insertFavMovie(movie:MovieEntity){
        viewModelScope.launch {
            dataRepo.localSource.insertFavMovie(movie)
        }
    }

    fun deleteFavMovie(movie: MovieEntity){
        viewModelScope.launch {
            dataRepo.localSource.deleteFavMovie(movie)
        }
    }

    //NetWork Source

    val movies=MutableLiveData<StatusResult<MovieResults>>()
    val series=MutableLiveData<StatusResult<SeriesResult>>()
    val people=MutableLiveData<StatusResult<PeopleResult>>()
    val movieById=MutableLiveData<StatusResult<Movie>>()
    val seriesById=MutableLiveData<StatusResult<Tv>>()
    val PeopleById=MutableLiveData<StatusResult<Actor>>()
    val vedio=MutableLiveData<StatusResult<Vedio>>()
    val searshMovie=MutableLiveData<StatusResult<MovieResults>>()

    fun getMovies(queryMap: HashMap<String,String>){
        viewModelScope.launch {
            getMovieSafeCall(queryMap)
        }
    }

    fun getSearshMovie(queryMap: HashMap<String,String>){
        viewModelScope.launch {
            getSearshMovieSafeCall(queryMap)
        }
    }

    private suspend fun getSearshMovieSafeCall(queryMap: java.util.HashMap<String, String>) {
        searshMovie.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            val mysearchMovie=dataRepo.remoteSource.getSearchMovie(queryMap)
            searshMovie.value=handleGetSearshMovie(mysearchMovie)
        }
        else{
            searshMovie.value=StatusResult.OnError("No Internet Connection")
        }
    }

    private fun handleGetSearshMovie(mysearchMovie: Response<MovieResults>): StatusResult<MovieResults>? {
        if (mysearchMovie.isSuccessful)
        {
            return StatusResult.OnSuccess(mysearchMovie.body()!!)
        }
        else if (mysearchMovie.body()==null)
        {
            return StatusResult.OnError("Movie is null")
        }
        else if (mysearchMovie.message().contains("time out"))
        {
            return StatusResult.OnError("server Time out")
        }
        else if (mysearchMovie.code()==404)
        {
            return StatusResult.OnError("server server Not Found")
        }
        else
        {
            return StatusResult.OnError("No Movie Found")
        }
    }

    fun getSeries(queryMap: HashMap<String, String>){
        viewModelScope.launch {
            getSeriesSaveCall(queryMap)
        }
    }
    fun getPopularPeoples(queryMap: HashMap<String, String>){
        viewModelScope.launch {
            getPopularPeopleSaveCall(queryMap)
        }
    }

    fun getMovieById(queryMap: HashMap<String, String>,id:Int){
        viewModelScope.launch {
            getMovieByIdSaveCall(queryMap,id)
        }
    }
    fun getTvById(queryMap: HashMap<String, String>,id:Int){
        viewModelScope.launch {
            getTvByIdSaveCall(queryMap,id)
        }
    }
    fun getActorById(queryMap: HashMap<String, String>,id:Int){
        viewModelScope.launch {
            getActorByIdSaveCall(queryMap,id)
        }
    }

    fun getVedio(queryMap: HashMap<String, String>,id:Int){
        viewModelScope.launch {
            getVedioSaveCall(queryMap,id)
        }
    }

    private suspend fun getVedioSaveCall(queryMap: java.util.HashMap<String, String>, id: Int) {
        vedio.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            val myvedio=dataRepo.remoteSource.getVedio(queryMap,id)
            vedio.value=handleGetVedio(myvedio)
        }
        else{
            vedio.value=StatusResult.OnError("No Internet Connection")
        }
    }

    private fun handleGetVedio(myvedio: Response<Vedio>): StatusResult<Vedio>{
        if (myvedio.isSuccessful)
        {
            return StatusResult.OnSuccess(myvedio.body()!!)
        }
        else if (myvedio.body()==null)
        {
            return StatusResult.OnError("Movie is null")
        }
        else if (myvedio.message().contains("time out"))
        {
            return StatusResult.OnError("server Time out")
        }
        else if (myvedio.code()==404)
        {
            return StatusResult.OnError("server server Not Found")
        }
        else
        {
            return StatusResult.OnError("No Movie Found")
        }
    }

    private suspend fun getMovieByIdSaveCall(queryMap: java.util.HashMap<String, String>, id: Int) {
        movieById.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            val mymoviedata=dataRepo.remoteSource.getMovie(queryMap,id)
            movieById.value=handleGetMovieById(mymoviedata)
        }
        else{
            movieById.value=StatusResult.OnError("No Internet Connection")
        }
    }
    private suspend fun getTvByIdSaveCall(queryMap: java.util.HashMap<String, String>, id: Int) {
        seriesById.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            val myTvdata=dataRepo.remoteSource.getTv(queryMap,id)
            seriesById.value=handleGetTvById(myTvdata)
        }
        else{
            seriesById.value=StatusResult.OnError("No Internet Connection")
        }
    }
    private suspend fun getActorByIdSaveCall(queryMap: java.util.HashMap<String, String>, id: Int) {
        PeopleById.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            val myactordata=dataRepo.remoteSource.getPeople(queryMap,id)
            PeopleById.value=handleGetPeopleById(myactordata)
        }
        else{
            PeopleById.value=StatusResult.OnError("No Internet Connection")
        }
    }

    private fun handleGetMovieById(myMoviedata: Response<Movie>): StatusResult<Movie> {
        if (myMoviedata.isSuccessful)
        {
            return StatusResult.OnSuccess(myMoviedata.body()!!)
        }
        else if (myMoviedata.body()==null)
        {
            return StatusResult.OnError("No Movie Found")
        }
        else if (myMoviedata.message().contains("time out"))
        {
            return StatusResult.OnError("server Time out")
        }
        else if (myMoviedata.code()==404)
        {
            return StatusResult.OnError("server server Not Found")
        }
        else
        {
            return StatusResult.OnError("No Movie Found")
        }
    }

    private fun handleGetTvById(myTvdata: Response<Tv>): StatusResult<Tv> {
        if (myTvdata.isSuccessful)
        {
            return StatusResult.OnSuccess(myTvdata.body()!!)
        }
        else if (myTvdata.body()==null)
        {
            return StatusResult.OnError("No Movie Found")
        }
        else if (myTvdata.message().contains("time out"))
        {
            return StatusResult.OnError("server Time out")
        }
        else if (myTvdata.code()==404)
        {
            return StatusResult.OnError("server server Not Found")
        }
        else
        {
            return StatusResult.OnError("No Movie Found")
        }
    }

    private fun handleGetPeopleById(myPeopledata: Response<Actor>): StatusResult<Actor> {
        if (myPeopledata.isSuccessful)
        {
            return StatusResult.OnSuccess(myPeopledata.body()!!)
        }
        else if (myPeopledata.body()==null)
        {
            return StatusResult.OnError("No Movie Found")
        }
        else if (myPeopledata.message().contains("time out"))
        {
            return StatusResult.OnError("server Time out")
        }
        else if (myPeopledata.code()==404)
        {
            return StatusResult.OnError("server server Not Found")
        }
        else
        {
            return StatusResult.OnError("No Movie Found")
        }
    }



    private suspend fun getPopularPeopleSaveCall(queryMap: java.util.HashMap<String, String>) {
        people.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            val mydata=dataRepo.remoteSource.apiCall.getPopulatePeople(queryMap)
            people.value=handleGetPopularPeople(mydata)
        }
        else{
            people.value=StatusResult.OnError("No Internet Connection")
        }
    }

    private fun handleGetPopularPeople(mydata: Response<PeopleResult>): StatusResult<PeopleResult>? {
        if (mydata.isSuccessful)
        {
            return StatusResult.OnSuccess(mydata.body()!!)
        }
        else if (mydata.body()!!.results.isNullOrEmpty())
        {
            return StatusResult.OnError("No Movie Found")
        }
        else if (mydata.message().contains("time out"))
        {
            return StatusResult.OnError("server Time out")
        }
        else if (mydata.code()==404)
        {
            return StatusResult.OnError("server server Not Found")
        }
        else
        {
            return StatusResult.OnError("No Movie Found")
        }
    }

    private suspend fun getSeriesSaveCall(queryMap: java.util.HashMap<String, String>) {
        series.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            val mydata=dataRepo.remoteSource.apiCall.getPopulateSeries(queryMap)
            series.value=handleGetSeries(mydata)
        }
        else{
            series.value=StatusResult.OnError("No Internet Connection")
        }
    }

    private fun handleGetSeries(mydata: Response<SeriesResult>): StatusResult<SeriesResult>? {
        if (mydata.isSuccessful)
        {
            return StatusResult.OnSuccess(mydata.body()!!)
        }
        else if (mydata.body()!!.results.isNullOrEmpty())
        {
            return StatusResult.OnError("No Movie Found")
        }
        else if (mydata.message().contains("time out"))
        {
            return StatusResult.OnError("server Time out")
        }
        else if (mydata.code()==404)
        {
            return StatusResult.OnError("server server Not Found")
        }
        else
        {
            return StatusResult.OnError("No Movie Found")
        }
    }

    private suspend fun getMovieSafeCall(queryMap: java.util.HashMap<String, String>) {
        movies.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            val mydata=dataRepo.remoteSource.apiCall.getPupulateMovie(queryMap)
            movies.value=handleGetMovie(mydata)
        }
        else{
            movies.value=StatusResult.OnError("No Internet Connection")
        }
    }

    private fun handleGetMovie(mydata: Response<MovieResults>): StatusResult<MovieResults>{
        if (mydata.isSuccessful)
        {
            return StatusResult.OnSuccess(mydata.body()!!)
        }
        else if (mydata.body()!!.results.isNullOrEmpty())
        {
            return StatusResult.OnError("No Movie Found")
        }
        else if (mydata.message().contains("time out"))
        {
            return StatusResult.OnError("server Time out")
        }
        else if (mydata.code()==404)
        {
            return StatusResult.OnError("server server Not Found")
        }
        else
        {
            return StatusResult.OnError("No Movie Found")
        }
    }


    private fun hasInternetConnection():Boolean {
        val connectivityManger=getApplication<Application>()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netWorkActive = connectivityManger.activeNetwork ?:return false
        val networkCapability=connectivityManger.getNetworkCapabilities(netWorkActive) ?:return false
        when{
            networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
            networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
            else->return false
        }

    }
}