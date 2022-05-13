package com.app.assessment.test.movie.list.database


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.assessment.test.movie.list.models.MovieItem

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieItem: MovieItem): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieItem>): LiveData<List<Long>>

    @Query("SELECT * FROM MovieItem")
    fun getMovies(): LiveData<List<MovieItem>>
}