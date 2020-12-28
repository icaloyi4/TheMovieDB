package com.example.themoviedb.api

import com.example.themoviedb.api.response.DataResponse
import com.example.themoviedb.api.response.GenreResponse
import com.example.themoviedb.api.response.MovieResponse
import com.example.themoviedb.utils.App
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {
    @GET("discover/movie")
    fun getMovie(
        @Query("page") page : Int,
        @Query("with_genres") with_genres : String,
        @Query("api_key") apiKey : String = App.API_KEY
        ): Call<DataResponse<List<MovieResponse>>>

    @GET("genre/movie/list")
    fun getMovieGenre(@Query("api_key") apiKey : String = App.API_KEY): Call<GenreResponse>
}
