package com.example.themoviedb.api

import com.example.themoviedb.api.response.*
import com.example.themoviedb.utils.App
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {
    @GET("discover/movie")
    fun getMovie(
        @Query("page") page : Int,
        @Query("with_genres") with_genres : String,
        @Query("api_key") apiKey : String = App.API_KEY
        ): Call<DataResponse<List<MovieResponse>>>

    @GET("movie/{movie_id}/reviews")
    fun getReview(
        @Path("movie_id") movie_id : String,
        @Query("page") page : Int,
        @Query("api_key") apiKey : String = App.API_KEY
    ): Call<ReviewResponse>

    @GET("movie/{movie_id}/videos")
    fun getYTVid(
        @Path("movie_id") movie_id : String,
        @Query("api_key") apiKey : String = App.API_KEY
    ): Call<YoutubeResponse>

    @GET("genre/movie/list")
    fun getMovieGenre(@Query("api_key") apiKey : String = App.API_KEY): Call<GenreResponse>
}
