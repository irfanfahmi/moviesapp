package com.descolab.moviesapp.main

import android.content.Context
import com.descolab.moviesapp.R
import com.descolab.moviesapp.base.BasePresenter
import com.descolab.moviesapp.service.ApiClient
import com.descolab.moviesapp.service.ApiService
import com.descolab.moviesapp.service.response.Movie
import com.descolab.moviesapp.service.response.ResponseMovies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class MainPresenter (val context: Context,
                     val mView: MainContract.View
)
    : BasePresenter(), MainContract.UserActionListener {
    private val apiService : ApiService = ApiClient.getClient().create(ApiService::class.java)

    override fun getDiscover(page :Int) {
        val call = apiService.getDiscoverMovie(page)
        mView.showProgressDialog(true)
        call.enqueue(object : Callback<ResponseMovies<ArrayList<Movie>>> {
            override fun onResponse(
                call: Call<ResponseMovies<ArrayList<Movie>>>,
                response: Response<ResponseMovies<ArrayList<Movie>>>
            ) {
                mView.showProgressDialog(false)
                if (response.isSuccessful){
                    if (response.body()?.results?.size != 0) {
                        response.body()?.results?.let { mView.showDiscover(it as ArrayList<Movie>) }
                    } else {
                        showDialog(context, context.getString(R.string.text_no_data))

                    }
                }

            }

            override fun onFailure(call: Call<ResponseMovies<ArrayList<Movie>>>, t: Throwable) {
                showDialog(context, context.getString(R.string.text_internet_error))
                mView.showProgressDialog(false)
                call.cancel()
            }


        })
    }
}