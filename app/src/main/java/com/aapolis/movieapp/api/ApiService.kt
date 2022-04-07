package com.aapolis.movieapp.api

import com.aapolis.movieapp.Constants
import com.aapolis.movieapp.data.response.MovieDetails
import com.aapolis.movieapp.data.response.SearchMovieResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/movie")
    fun search(
        @Query("query") query: String,
        @Query("page") pageNo: Int
    ): Call<SearchMovieResponse>

    @GET("search/movie")
    fun searchMovie(
        @Query("query") query: String,
        @Query("page") pageNo: Int
    ): Response<SearchMovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Long
    ): Call<MovieDetails>

    companion object {
        fun getInstance() = ApiClient.retrofit.create(ApiService::class.java)
    }
}