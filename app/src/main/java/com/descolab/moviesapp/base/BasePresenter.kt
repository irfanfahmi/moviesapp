package com.descolab.moviesapp.base

import android.app.AlertDialog
import android.content.Context
import com.descolab.moviesapp.R
import org.json.JSONException
import org.json.JSONObject

open class BasePresenter {
    fun showDialog(context: Context, message: String) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(context.getString(R.string.app_name))
        builder.setMessage(message)
        builder.setPositiveButton("OK"){dialog, which ->
            dialog.dismiss()
        }

//        builder.setNegativeButton("No"){dialog,which ->
//        }

//        builder.setNeutralButton("Cancel"){_,_ ->
//        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun showErrorMessage(context: Context, errorMessage: String) {
        try {
            val `object` = JSONObject(errorMessage)
            showDialog(context, `object`.getString("ErrMsg"))
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }


}