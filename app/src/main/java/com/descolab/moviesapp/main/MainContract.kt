package com.descolab.moviesapp.main

import com.descolab.moviesapp.service.response.Movie

class MainContract {
    interface View{
        fun showDiscover(data: ArrayList<Movie>)
        fun showProgressDialog(show: Boolean)
    }

    interface UserActionListener{
        fun getDiscover(page: Int)
    }
}