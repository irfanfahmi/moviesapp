package com.descolab.moviesapp.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.descolab.moviesapp.R
import com.descolab.moviesapp.helper.Tools
import com.descolab.moviesapp.service.response.Movie
import kotlinx.android.synthetic.main.item_list_movie.view.ivImage
import kotlinx.android.synthetic.main.item_list_movie.view.tvMovieName
import kotlinx.android.synthetic.main.item_list_movie.view.tvMovieYear
import kotlinx.android.synthetic.main.item_list_movie.view.tvRating

class ListMovieAdapter(private val mContext: Context ,
                       val listener: ListMovieListener) :
    RecyclerView.Adapter<ListMovieAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var mItems = ArrayList<Movie>()

    fun updateList(mItems: ArrayList<Movie>, oldCount: Int, mItemsSize: Int) {
        this.mItems = mItems
        notifyDataSetChanged()
        notifyItemRangeInserted(oldCount, mItemsSize)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMovieAdapter.ViewHolder {
        val view = mInflater.inflate(R.layout.item_list_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return mItems.size
        //return 5
    }

    override fun onBindViewHolder(holder: ListMovieAdapter.ViewHolder, position: Int) {
        if (0 <= position && position < mItems.size) {
            val data = mItems[position]
            // Bind your data here
            holder.bind(data)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Movie) {
            itemView.tvMovieName.text = data.title.toString()
            itemView.tvRating.text = data.voteAverage.toString()
            itemView.tvMovieYear.text = data.releaseDate.toString()
            Tools.displayImageOriginal(mContext,itemView.ivImage,data.posterPath.toString())
            itemView.setOnClickListener {
                listener.toDetailMovie(data)
            }
        }
    }




    interface ListMovieListener {
        fun toDetailMovie(item: Movie)
    }
}