package com.descolab.moviesapp.main

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.descolab.moviesapp.App
import com.descolab.moviesapp.R
import com.descolab.moviesapp.detail_movie.DetailMovieActivity
import com.descolab.moviesapp.service.response.Movie
import kotlinx.android.synthetic.main.activity_main.rvDiscover

class MainActivity : AppCompatActivity(), MainContract.View, ListMovieAdapter.ListMovieListener {

    companion object {
        @JvmStatic lateinit var instance: MainActivity
    }

    init {
        instance = this
    }
    private var progressDialog : ProgressDialog? = null
    private var mActionListener: MainPresenter? = null
    private var mAdapter: ListMovieAdapter? = null
    private var currentPage = 1
    private var totalAvailablePages = 1
    private lateinit var dataDiscoverList: ArrayList<Movie>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressDialog = ProgressDialog(this)
        progressDialog?.setMessage(getString(R.string.text_loading))
        progressDialog?.setCancelable(false)
        App.get().sharedPreferences1000
        mActionListener =  MainPresenter(this, this)
        dataDiscoverList = ArrayList()
        mAdapter = ListMovieAdapter(this, this)
        rvDiscover?.layoutManager = GridLayoutManager(this  ,2)
        rvDiscover?.setHasFixedSize(true)
        rvDiscover?.adapter = mAdapter
        rvDiscover?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!rvDiscover?.canScrollVertically(1)!!) {
                    if (currentPage <= totalAvailablePages) {
                        currentPage += 1
                        mActionListener?.getDiscover(currentPage)
                    }
                }
            }
        })
        mActionListener?.getDiscover(currentPage)

    }

    override fun toDetailMovie(item: Movie) {
        val i = Intent(this, DetailMovieActivity::class.java)
        i.putExtra("idMovie",item.id.toString())
        startActivity(i)
    }
    override fun showDiscover(data: ArrayList<Movie>) {
        val oldCount = data.size
        totalAvailablePages = data.size
        dataDiscoverList.addAll(data)
        mAdapter?.updateList(dataDiscoverList, oldCount,dataDiscoverList.size)
    }

    override fun showProgressDialog(show: Boolean) {
        if (show) progressDialog?.show()
        else progressDialog?.dismiss()
    }

}




