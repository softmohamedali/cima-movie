package com.example.graduateproject.data.local

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.graduateproject.models.Movie
import com.example.graduateproject.models.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DatabaseTypeConverter {
    val gson=Gson()

    @TypeConverter
    fun fromMovieToString(movie:Result):String{
        return gson.toJson(movie)
    }

    @TypeConverter
    fun fromJsonToMovie(json:String):Result{
        val typeToken=object :TypeToken<Result>(){}.type
        return gson.fromJson(json,typeToken)
    }

}