package com.ngapak.dev.latihan.watchit.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ngapak.dev.latihan.watchit.data.local.entities.FavoriteEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_movies")
    suspend fun getMovies(): List<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertFavoriteMovie(movie: FavoriteEntity)

    @Delete
    fun deleteMovie(movie: FavoriteEntity)
}