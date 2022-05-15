package com.app.assessment.test.movie.list.database


import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.assessment.test.movie.list.models.MovieItem

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieItem>): List<Long>

    @Query("SELECT * FROM MovieItem")
    fun getMovies(): PagingSource<Int, MovieItem>

    @Query("SELECT COUNT(id) from MovieItem")
    fun count(): Int
}