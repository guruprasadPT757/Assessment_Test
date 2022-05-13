package com.app.assessment.test.movie.list.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.assessment.test.movie.list.models.MovieItem


@Database(entities = [MovieItem::class], version = 1, exportSchema = false)
abstract class MovieDataBase: RoomDatabase()  {
    companion object  {
        const val DATABASE_NAME = "MovieDataBase.db"
        var INSTANCE: MovieDataBase? = null
    }

    open fun getInstance(context: Context): MovieDataBase? {
        if (INSTANCE == null) {
            synchronized(MovieDataBase::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, MovieDataBase::class.java, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
        }
        return INSTANCE
    }
}