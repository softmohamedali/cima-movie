package com.example.graduateproject.data

import com.example.graduateproject.data.local.Dao
import com.example.graduateproject.data.local.MovieEntity
import javax.inject.Inject

class Localsource @Inject constructor(private var dao:Dao) {

    suspend fun insertFavMovie(movie:MovieEntity)=dao.insertFavMovie(movie)
    suspend fun deleteFavMovie(movie:MovieEntity)=dao.deletefavMovie(movie)
    fun getAllFavMovie()=dao.getAllFav()

}