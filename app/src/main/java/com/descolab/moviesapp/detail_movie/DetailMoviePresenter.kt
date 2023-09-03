package com.descolab.moviesapp.detail_movie

import android.content.Context
import com.descolab.moviesapp.R
import com.descolab.moviesapp.base.BasePresenter
import com.descolab.moviesapp.service.ApiClient
import com.descolab.moviesapp.service.ApiService
import com.descolab.moviesapp.service.response.Movie
import com.descolab.moviesapp.service.response.ResponseReviews
import com.descolab.moviesapp.service.response.ResponseVideo
import com.descolab.moviesapp.service.response.Reviews
import com.descolab.moviesapp.service.response.Video
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class DetailMoviePresenter (val context: Context,
                            val mView: DetailMovieContract.View
)
    : BasePresenter(), DetailMovieContract.UserActionListener {
    private val apiService : ApiService = ApiClient.getClient().create(ApiService::class.java)

    override fun getDetailMovie(idMovie :String) {
        val call = apiService.getDetailMovie(idMovie)
        mView.showProgressDialog(true)
        call.enqueue(object : Callback<Movie> {
            override fun onResponse(
                call: Call<Movie>,
                response: Response<Movie>
            ) {
                mView.showProgressDialog(false)
                if (response.isSuccessful){
                    response.body()?.let { mView.showDetailMovie(it as Movie ) }
                }else{
                    showDialog(context, "Tidak ada data")
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                showDialog(context, "Internet Error ")
                mView.showProgressDialog(false)
                call.cancel()
            }
        })
    }

    override fun getTrailer(idMovie: String) {
        val call = apiService.getVideos(idMovie)
        mView.showProgressDialog(true)
        call.enqueue(object : Callback<ResponseVideo<ArrayList<Video>>> {
            override fun onResponse(
                call: Call<ResponseVideo<ArrayList<Video>>>,
                response: Response<ResponseVideo<java.util.ArrayList<Video>>>
            ) {
                mView.showProgressDialog(false)
                if (response.isSuccessful){
                    if (response.body()?.results?.size != 0) {
                        response.body()?.results?.let { mView.showVideos(it as ArrayList<Video>) }
                    } else {
                        showDialog(context, context.getString(R.string.text_no_data))
                    }
                }else{
                    showDialog(context, context.getString(R.string.text_no_data))
                }
            }
            override fun onFailure(call: Call<ResponseVideo<java.util.ArrayList<Video>>>, t: Throwable) {
                showDialog(context, context.getString(R.string.text_internet_error))
                mView.showProgressDialog(false)
                call.cancel()
            }
        })
    }

    override fun getReview(idMovie: String) {
        val call = apiService.getReviews(idMovie)
        mView.showProgressDialog(true)
        call.enqueue(object : Callback<ResponseReviews<ArrayList<Reviews>>> {
            override fun onResponse(
                call: Call<ResponseReviews<ArrayList<Reviews>>>,
                response: Response<ResponseReviews<java.util.ArrayList<Reviews>>>
            ) {
                mView.showProgressDialog(false)
                if (response.isSuccessful){
                    if (response.body()?.results?.size != 0) {
                        response.body()?.results?.let { mView.showReviews(it as ArrayList<Reviews>) }
                    } else {
                        showDialog(context, context.getString(R.string.text_no_data))
                    }
                }else{
                    showDialog(context, context.getString(R.string.text_no_data))
                }
            }
            override fun onFailure(call: Call<ResponseReviews<java.util.ArrayList<Reviews>>>, t: Throwable) {
                showDialog(context, context.getString(R.string.text_internet_error))
                mView.showProgressDialog(false)
                call.cancel()
            }
        })
    }
}