package com.ngapak.dev.latihan.watchit.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteEntity(
    @PrimaryKey
    val id: Int,
    val posterPath: String,
    val title: String
)