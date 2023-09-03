package com.descolab.moviesapp.detail_movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.descolab.moviesapp.R
import com.descolab.moviesapp.service.response.GenresItem
import kotlinx.android.synthetic.main.item_genre.view.tvTitle

class ListGenreAdapter(private val mContext: Context,
                       val mItems: ArrayList<GenresItem?>
) :
    RecyclerView.Adapter<ListGenreAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListGenreAdapter.ViewHolder {
        val view = mInflater.inflate(R.layout.item_genre, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return mItems.size
        //return 5
    }

    override fun onBindViewHolder(holder: ListGenreAdapter.ViewHolder, position: Int) {
        if (0 <= position && position < mItems.size) {
            val data = mItems[position]
            // Bind your data here
            if (data != null) {
                holder.bind(data)
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: GenresItem) {
            itemView.tvTitle.text = data.name.toString()

        }
    }

}