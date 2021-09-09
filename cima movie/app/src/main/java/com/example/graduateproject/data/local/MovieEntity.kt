package com.example.graduateproject.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.graduateproject.models.Movie
import com.example.graduateproject.models.Result
import com.example.graduateproject.util.Constants

@Entity(tableName = Constants.FAV_MOVIE_TABLR_NAME)
class MovieEntity (
        @PrimaryKey(autoGenerate = true)
        var id:Int=0,
        var movie:Result
){

}