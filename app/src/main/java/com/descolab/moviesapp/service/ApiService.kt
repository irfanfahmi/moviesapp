package com.descolab.moviesapp.service

import com.descolab.moviesapp.service.response.Movie
import com.descolab.moviesapp.service.response.ResponseMovies
import com.descolab.moviesapp.service.response.ResponseReviews
import com.descolab.moviesapp.service.response.ResponseVideo
import com.descolab.moviesapp.service.response.Reviews
import com.descolab.moviesapp.service.response.Video
import retrofit2.Call
import retrofit2.http.*
import java.util.*


interface ApiService {

    @GET("discover/movie?include_adult=false&include_video=false&language=en-US&sort_by=popularity.desc")
    fun getDiscoverMovie(@Query("page") page: Int): Call<ResponseMovies<ArrayList<Movie>>>

    @GET("movie/{idMovie}")
    fun getDetailMovie(@Path("idMovie") idMovie: String): Call<Movie>

    @GET("movie/{idMovie}/videos")
    fun getVideos(@Path("idMovie") idMovie: String): Call<ResponseVideo<ArrayList<Video>>>

    @GET("movie/{idMovie}/reviews")
    fun getReviews(@Path("idMovie") idMovie: String): Call<ResponseReviews<ArrayList<Reviews>>>
}
