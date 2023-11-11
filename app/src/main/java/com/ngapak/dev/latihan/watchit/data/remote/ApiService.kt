package com.ngapak.dev.latihan.watchit.data.remote

import com.ngapak.dev.latihan.watchit.data.remote.model.MovieDetailResponse
import com.ngapak.dev.latihan.watchit.data.remote.model.MoviesModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getMovies(
        @Query("language") lang: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("region") region: String = "ID",
    ): MoviesModel

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") id: Int,
        @Query("language") lang: String = "en-US",
    ): MovieDetailResponse
}