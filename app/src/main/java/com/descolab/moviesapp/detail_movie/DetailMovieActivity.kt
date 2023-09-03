package com.descolab.moviesapp.detail_movie

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.descolab.moviesapp.R
import com.descolab.moviesapp.helper.Tools
import com.descolab.moviesapp.helper.Utils
import com.descolab.moviesapp.service.response.Movie
import com.descolab.moviesapp.service.response.Reviews
import com.descolab.moviesapp.service.response.Video
import kotlinx.android.synthetic.main.activity_detail_movie.ivImageBackdrop
import kotlinx.android.synthetic.main.activity_detail_movie.ivImagePoster
import kotlinx.android.synthetic.main.activity_detail_movie.rvGenre
import kotlinx.android.synthetic.main.activity_detail_movie.rvReview
import kotlinx.android.synthetic.main.activity_detail_movie.rvTrailer
import kotlinx.android.synthetic.main.activity_detail_movie.tvOriginalLanguage
import kotlinx.android.synthetic.main.activity_detail_movie.tvOriginalTitle
import kotlinx.android.synthetic.main.activity_detail_movie.tvOverview
import kotlinx.android.synthetic.main.activity_detail_movie.tvRating
import kotlinx.android.synthetic.main.activity_detail_movie.tvRealeaseDate
import kotlinx.android.synthetic.main.activity_detail_movie.tvRunTime

class DetailMovieActivity : AppCompatActivity(), DetailMovieContract.View ,ListVideoAdapter.ListVideosListener,ListReviewsAdapter.ListReviewsListener{
    private var progressDialog : ProgressDialog? = null
    private var mActionListener: DetailMoviePresenter? = null
    private var mAdapterTrailer: ListVideoAdapter? = null
    private var mAdapterReviews: ListReviewsAdapter? = null
    private var mAdapterGenreAdapter: ListGenreAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        progressDialog = ProgressDialog(this)
        progressDialog?.setMessage(getString(R.string.text_loading))
        progressDialog?.setCancelable(false)
        val idMovie = intent.getStringExtra("idMovie")

        mActionListener =  DetailMoviePresenter(this, this)
        if (idMovie != null) {
            mActionListener!!.getDetailMovie(idMovie)
            mActionListener!!.getReview(idMovie)
            mActionListener!!.getTrailer(idMovie)
        }

    }

    override fun showDetailMovie(data: Movie) {
        tvOriginalTitle.setText(data.title)
        tvRating.setText("%.1f".format(data.voteAverage))
        tvOriginalLanguage.setText(data.originalLanguage)
        tvRunTime.setText(data.runtime?.let { Utils.getRunTime(it) })
        tvRealeaseDate.setText(Utils.changeDatetoYear(data.releaseDate.toString()))
        tvOverview.setText(data.overview.toString())
        Tools.displayImageOriginal(this,ivImageBackdrop,data.backdropPath.toString())
        Tools.displayImageOriginal(this,ivImagePoster,data.posterPath.toString())

        if (data.genres!=null){
            mAdapterGenreAdapter = ListGenreAdapter(this,data.genres)
        }
        rvGenre?.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        rvGenre?.setHasFixedSize(true)
        rvGenre?.adapter = mAdapterGenreAdapter
    }

    override fun showVideos(data: ArrayList<Video>) {
        mAdapterTrailer = ListVideoAdapter(this,data, this)
        rvTrailer?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvTrailer?.setHasFixedSize(true)
        rvTrailer?.adapter = mAdapterTrailer
    }

    override fun showReviews(data: ArrayList<Reviews>) {
        mAdapterReviews = ListReviewsAdapter(this,data, this)
        rvReview?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvReview?.setHasFixedSize(true)
        rvReview?.adapter = mAdapterReviews
    }

    override fun showProgressDialog(show: Boolean) {
        if (show) progressDialog?.show()
        else progressDialog?.dismiss()
    }

    override fun toDetailVideos(item: Video) {
    }

    override fun toDetailReviews(item: Reviews) {
    }
}