package com.descolab.moviesapp.detail_movie

import com.descolab.moviesapp.service.response.Movie
import com.descolab.moviesapp.service.response.Reviews
import com.descolab.moviesapp.service.response.Video

class DetailMovieContract {
    interface View{
        fun showDetailMovie(data: Movie)
        fun showVideos(data: ArrayList<Video>)
        fun showReviews(data: ArrayList<Reviews>)
        fun showProgressDialog(show: Boolean)
    }

    interface UserActionListener{
        fun getDetailMovie(idMovie: String)
        fun getTrailer(idMovie: String)
        fun getReview(idMovie: String)
    }
}