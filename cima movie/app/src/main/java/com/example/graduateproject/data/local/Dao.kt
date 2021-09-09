package com.example.graduateproject.data.local

import androidx.room.*
import androidx.room.Dao
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavMovie(movie:MovieEntity)

    @Delete
    suspend fun deletefavMovie(movie:MovieEntity)

    @Query("SELECT * FROM favmovie")
    fun getAllFav():Flow<List<MovieEntity>>

}