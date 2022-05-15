package com.app.assessment.test.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.assessment.test.models.movie.MovieItem
import com.app.assessment.test.models.RemoteKeys


@Database(entities = [MovieItem::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class MovieDataBase: RoomDatabase()  {
    abstract fun movieItemDao():MovieDao
    abstract fun remoteKeyDao(): RemoteKeysDao

    companion object  {
        private const val DATABASE_NAME = "MovieDataBase.db"
        var INSTANCE: MovieDataBase? = null
        fun getInstance(context: Context): MovieDataBase? {
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
}