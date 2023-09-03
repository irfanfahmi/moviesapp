package com.descolab.moviesapp.detail_movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.descolab.moviesapp.R
import com.descolab.moviesapp.helper.Tools
import com.descolab.moviesapp.service.response.Video
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.item_list_movie.view.ivImage
import kotlinx.android.synthetic.main.item_list_movie.view.tvMovieName
import kotlinx.android.synthetic.main.item_list_movie.view.tvMovieYear
import kotlinx.android.synthetic.main.item_list_movie.view.tvRating
import kotlinx.android.synthetic.main.item_trailer.view.youTubePlayerView

class ListVideoAdapter(private val mContext: Context,
                       val mItems: ArrayList<Video>,
                       val listener: ListVideosListener
) :
    RecyclerView.Adapter<ListVideoAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListVideoAdapter.ViewHolder {
        val view = mInflater.inflate(R.layout.item_trailer, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return mItems.size
        //return 5
    }

    override fun onBindViewHolder(holder: ListVideoAdapter.ViewHolder, position: Int) {
        if (0 <= position && position < mItems.size) {
            val data = mItems[position]
            // Bind your data here
            holder.bind(data)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Video) {
            itemView.youTubePlayerView.getPlayerUiController()
            itemView.youTubePlayerView.toggleFullScreen()
            itemView.youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    // loading the selected video
                    // into the YouTube Player
                    youTubePlayer.loadVideo(data.key.toString(), 0f)
                }

                override fun onStateChange(
                    youTubePlayer: YouTubePlayer,
                    state: PlayerConstants.PlayerState
                ) {
                    // this method is called if video has ended,
                    super.onStateChange(youTubePlayer, state)
                }
            })
            itemView.setOnClickListener {
                listener.toDetailVideos(data)
            }
        }
    }




    interface ListVideosListener {
        fun toDetailVideos(item: Video)
    }
}