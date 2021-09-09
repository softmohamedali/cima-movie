package com.example.graduateproject.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.graduateproject.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ApplayViewModel @Inject constructor(
    application:Application
):AndroidViewModel(application) {

    fun applyPopularMovie():HashMap<String,String>{
        val query:HashMap<String,String> =HashMap()
        query[Constants.QUERY_API_KEY]= Constants.API_KEY
        query["page"]="1"
        query[Constants.SORTBY_KEY]="popularity.desc"
        return query
    }

    fun applyPopularSeries():HashMap<String,String>{
        val query:HashMap<String,String> =HashMap()
        query[Constants.QUERY_API_KEY]= Constants.API_KEY
        query["page"]="2"
        query[Constants.SORTBY_KEY]="popularity.desc"
        return query
    }

    fun applyPopularPeople():HashMap<String,String>{
        val query:HashMap<String,String> =HashMap()
        query[Constants.QUERY_API_KEY]= Constants.API_KEY
        query["page"]="1"
        return query
    }
    fun applyApiKey():HashMap<String,String>{
        val query:HashMap<String,String> =HashMap()
        query[Constants.QUERY_API_KEY]= Constants.API_KEY
        return query
    }
    fun appluQueri(page:String):HashMap<String,String> {
        val map=HashMap<String,String>()
        map[Constants.QUERY_API_KEY]= Constants.API_KEY
        map["page"]=page
        return map
    }
    fun appluSearchQueri(queryy:String,page:String):HashMap<String,String> {
        val query:HashMap<String,String> =HashMap()
        query[Constants.QUERY_API_KEY]= Constants.API_KEY
        query["query"]=queryy
        query["page"]=page
        return query
    }
}