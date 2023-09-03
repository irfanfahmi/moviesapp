package com.descolab.moviesapp.detail_movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.descolab.moviesapp.R
import com.descolab.moviesapp.helper.Tools
import com.descolab.moviesapp.helper.Utils
import com.descolab.moviesapp.service.response.Reviews
import com.descolab.moviesapp.service.response.Video
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.item_list_movie.view.ivImage
import kotlinx.android.synthetic.main.item_list_movie.view.tvMovieName
import kotlinx.android.synthetic.main.item_list_movie.view.tvMovieYear
import kotlinx.android.synthetic.main.item_list_movie.view.tvRating
import kotlinx.android.synthetic.main.item_review.view.ivAvatar
import kotlinx.android.synthetic.main.item_review.view.tvAuthors
import kotlinx.android.synthetic.main.item_review.view.tvContent
import kotlinx.android.synthetic.main.item_review.view.tvTime
import kotlinx.android.synthetic.main.item_trailer.view.youTubePlayerView

class ListReviewsAdapter(private val mContext: Context,
                         val mItems: ArrayList<Reviews>,
                         val listener: ListReviewsListener
) :
    RecyclerView.Adapter<ListReviewsAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListReviewsAdapter.ViewHolder {
        val view = mInflater.inflate(R.layout.item_review, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return mItems.size
        //return 5
    }

    override fun onBindViewHolder(holder: ListReviewsAdapter.ViewHolder, position: Int) {
        if (0 <= position && position < mItems.size) {
            val data = mItems[position]
            // Bind your data here
            holder.bind(data)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Reviews) {
            Tools.displayImageOriginal(mContext,itemView.ivAvatar,data.authorDetails?.avatarPath.toString())
            itemView.tvAuthors.text = data.author.toString()
            itemView.tvTime.text = Utils.changeDateFormat(data.updatedAt.toString()).toString()
            itemView.tvContent.text = data.content.toString()
            itemView.setOnClickListener {
                listener.toDetailReviews(data)
            }
        }
    }




    interface ListReviewsListener {
        fun toDetailReviews(item: Reviews)
    }
}