package com.example.graduateproject.data.local

import androidx.room.*
import androidx.room.Database

@Database(
        entities = arrayOf(MovieEntity::class),
        version = 1,
        exportSchema = false
)
@TypeConverters(DatabaseTypeConverter::class)
 abstract class Database : RoomDatabase(){
  abstract fun getDao():Dao
}