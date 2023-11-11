package com.ngapak.dev.latihan.watchit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ngapak.dev.latihan.watchit.data.local.entities.MoviesEntity

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies")
    suspend fun getMovies(): List<MoviesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<MoviesEntity>)
}