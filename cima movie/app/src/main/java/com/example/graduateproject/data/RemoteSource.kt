package com.example.graduateproject.data

import javax.inject.Inject

class RemoteSource @Inject constructor(
    var apiCall: ApiCall
) {

    suspend fun getRecomendedMovie(queryMap:HashMap<String,String>)=apiCall.getPupulateMovie(queryMap)
    suspend fun getRecomendedSeries(queryMap:HashMap<String,String>)=apiCall.getPopulateSeries(queryMap)
    suspend fun getPobularPeople(queryMap:HashMap<String,String>)=apiCall.getPopulatePeople(queryMap)
    suspend fun getMovie(queryMap:HashMap<String,String>,id:Int)=apiCall.getMovie(id,queryMap)
    suspend fun getTv(queryMap:HashMap<String,String>,id:Int)=apiCall.getTv(id,queryMap)
    suspend fun getPeople(queryMap:HashMap<String,String>,id:Int)=apiCall.getPeople(id,queryMap)
    suspend fun getVedio(queryMap:HashMap<String,String>,id:Int)=apiCall.getVedio(id,queryMap)
    suspend fun getSearchMovie(queryMap:HashMap<String,String>)=apiCall.getSearchMovie(queryMap)

}

