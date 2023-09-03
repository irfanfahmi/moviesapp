package com.descolab.moviesapp.helper

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.descolab.moviesapp.R
import java.text.SimpleDateFormat
import java.util.*

class Tools {
    companion object {

        @JvmStatic
        fun displayImageOriginal(ctx: Context, img: ImageView, @DrawableRes drawable: Int) {
            try {
                Glide.with(ctx).load(drawable)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(img)
            } catch (e: Exception) {
            }

        }

        @JvmStatic
        fun displayImageRound(ctx: Context, img: ImageView, @DrawableRes drawable: Int) {
            try {
                Glide.with(ctx).load(drawable).asBitmap().centerCrop().into(object : BitmapImageViewTarget(img) {
                    override fun setResource(resource: Bitmap) {
                        val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(ctx.resources, resource)
                        circularBitmapDrawable.isCircular = true
                        img.setImageDrawable(circularBitmapDrawable)
                    }
                })
            } catch (e: Exception) {
            }

        }


        @JvmStatic
        fun displayImageOriginal(ctx: Context, img: ImageView, url: String) {
            val baseUrl = ctx.getString(R.string.url_image)
            try {
                Glide.with(ctx).load(baseUrl+url)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(img)
            } catch (e: Exception) {
            }

        }

        @JvmStatic
        fun getFormattedDateSimple(dateTime: Long?): String {
            val newFormat = SimpleDateFormat("MMMM dd, yyyy")
            return newFormat.format(Date(dateTime!!))
        }

        @JvmStatic
        fun getFormattedDateEvent(dateTime: Long?): String {
            val newFormat = SimpleDateFormat("EEE, MMM dd yyyy")
            return newFormat.format(Date(dateTime!!))
        }

        @JvmStatic
        fun getFormattedTimeEvent(time: Long?): String {
            val newFormat = SimpleDateFormat("h:mm a")
            return newFormat.format(Date(time!!))
        }

        @JvmStatic
        fun getEmailFromName(name: String?): String? {
            return if (name != null && name != "") {
                name.replace(" ".toRegex(), ".").toLowerCase() + "@mail.com"
            } else name
        }


        @JvmStatic
        fun insertPeriodically(text: String, insert: String, period: Int): String {
            val builder = StringBuilder(text.length + insert.length * (text.length / period) + 1)
            var index = 0
            var prefix = ""
            while (index < text.length) {
                builder.append(prefix)
                prefix = insert
                builder.append(text.substring(index, Math.min(index + period, text.length)))
                index += period
            }
            return builder.toString()
        }


        @JvmStatic
        fun rateAction(activity: Activity) {
            val uri = Uri.parse("market://details?id=" + activity.packageName)
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            try {
                activity.startActivity(goToMarket)
            } catch (e: ActivityNotFoundException) {
                activity.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + activity.packageName)
                    )
                )
            }

        }
    }
}