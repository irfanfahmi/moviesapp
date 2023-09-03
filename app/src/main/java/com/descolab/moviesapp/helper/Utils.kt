package com.descolab.moviesapp.helper

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.descolab.moviesapp.R
import com.descolab.moviesapp.main.MainActivity
import java.io.File
import java.io.FileOutputStream
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class Utils {
    companion object {


        @JvmStatic
        fun isEmpty(ed: EditText): Boolean {
            val temp = ed.text.toString().replace("\\s".toRegex(), "")
            return TextUtils.isEmpty(temp)
        }

        @JvmStatic
        fun isFileEmpty(ed: File): Boolean {
            val temp = ed.toString().replace("\\s".toRegex(), "")
            return TextUtils.isEmpty(temp)
        }

        @JvmStatic
        fun getString(ed: EditText): String {
            return ed.text.toString()
        }

        @JvmStatic
        fun isPasswordValid(password: String): Boolean {
            if (password.length >= 8) {
                val letter = Pattern.compile("[a-zA-z]")
                val digit = Pattern.compile("[0-9]")
                val special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]")
                //Pattern eight = Pattern.compile (".{8}");

                val hasLetter = letter.matcher(password)
                val hasDigit = digit.matcher(password)
                val hasSpecial = special.matcher(password)

                return hasLetter.find() && hasDigit.find()

            } else
                return false
        }

        @JvmStatic
        val DATE_FORMAT = "yyyy-MM-dd"
        @JvmStatic
        val DATETIME_FORMAT = "dd/MM/yyyy HH:mm"

        @JvmStatic
        fun prettyTime(dateText: String): String {
            if (!TextUtils.isEmpty(dateText)) {
                val SECOND_MILLIS = 1000
                val MINUTE_MILLIS = 60 * SECOND_MILLIS
                val HOUR_MILLIS = 60 * MINUTE_MILLIS
                val DAY_MILLIS = 24 * HOUR_MILLIS
                try {
                    val dateFormat = SimpleDateFormat(Utils.DATETIME_FORMAT)
                    val date = dateFormat.parse(dateText)

                    val time = date.time

                    /*if (time < 1000000000000L) {
                        // if timestamp given in seconds, convert to millis
                        time *= 1000;
                    }*/

                    //Log.d("dddd", "ddd "+dateText+" "+Calendar.getInstance());

                    val now = Calendar.getInstance().timeInMillis
                    if (time > now)
                        return "Baru Saja"
                    else if (time <= 0) return Utils.getDate(dateText)

                    val diff = now - time
                    return if (diff < MINUTE_MILLIS) {
                        "Baru Saja"
                    } else if (diff < 2 * MINUTE_MILLIS) {
                        "1 Menit yang lalu"
                    } else if (diff < 50 * MINUTE_MILLIS) {
                        (diff / MINUTE_MILLIS).toString() + " Menit yang lalu"
                    } else if (diff < 90 * MINUTE_MILLIS) {
                        "1 Jam yang lalu"
                    } else if (diff < 24 * HOUR_MILLIS) {
                        (diff / HOUR_MILLIS).toString() + " Jam yang lalu"
                    } else if (diff < 48 * HOUR_MILLIS) {
                        "Kemarin"
                    } else if (diff / DAY_MILLIS <= 7) {
                        (diff / DAY_MILLIS).toString() + " Hari yang lalu"
                    } else
                        Utils.getDate(dateText)
                } catch (e: ParseException) {
                    e.printStackTrace()
                    return dateText
                }

            } else
                return ""
        }

        @JvmStatic
         fun getDateTime(s: String): String? {
            return try {
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val netDate = Date(s.toLong() * 1000 )
                sdf.format(netDate)
            } catch (e: Exception) {
                e.toString()
            }
        }

        @JvmStatic
        fun getDate(time: String): String {
            return if (!TextUtils.isEmpty(time) && !time.equals("null", ignoreCase = true)) {
                try {
                    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")

                    val date = sdf.parse(time)
                    val df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.UK)

                    df.format(date)
                } catch (e: Exception) {
                    e.printStackTrace()
                    time
                }

            } else
                ""
        }
        @JvmStatic
        fun changeDateFormat(strDate:String):String {
            // Buat objek SimpleDateFormat dengan pola yang sesuai
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val outputFormat = SimpleDateFormat("MMM dd, yyyy 'at' h:mm a")
            // Parsing string tanggal ke objek Date
            val date: Date = inputFormat.parse(strDate)

            // Format ulang tanggal menjadi format yang diinginkan
            val outputDateString = outputFormat.format(date)
            return outputDateString
        }

        @JvmStatic
        fun changeDatetoYear(strDate:String):String {
            // Buat objek SimpleDateFormat dengan pola yang sesuai
            val inputFormat = SimpleDateFormat("yyyy-MM-dd")
            val outputFormat = SimpleDateFormat("yyyy")
            // Parsing string tanggal ke objek Date
            val date: Date = inputFormat.parse(strDate)
            // Format ulang tanggal menjadi format yang diinginkan
            val outputDateString = outputFormat.format(date)
            return outputDateString
        }
        @JvmStatic
        fun getRunTime(minute:Int):String {
            val hours = minute / 60
            val minutes = minute % 60
            val runTime = ("$hours h, $minutes m")
            return runTime
        }




        @JvmStatic
        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

//        @JvmStatic
//        fun copyText(context: Context, text: String, toast: Boolean) {
//            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//            val clipData = ClipData.newPlainText("uiconnect", text)
//            clipboardManager.primaryClip = clipData
//
//            if (toast) {
//                Toast.makeText(context, "Copied $text", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
//            }
//        }

        @JvmStatic
        fun getFormatMoneyValue(strValue: String): String {
            if(strValue.isEmpty()){
                return "Rp 0"
            }else {
                val value: Long = strValue.toLong()
                return "Rp " + String.format("%,d", value).replace(',', '.')
            }
        }

        @JvmStatic
        fun getFormatMoneyValueTextWatcher(strValue: String): String {
            val value: Long = strValue.toLong()
            return String.format("%,d", value)
        }

        @JvmStatic
        fun openWhatsApp(context: Context, pNumber: String) {
            try {
                var whatsapp = "https://wa.me/"
                if(pNumber.contains("+62")){
                    whatsapp+=pNumber.replace("+", "")
                }else if(pNumber.contains("+")){
                    whatsapp+=pNumber.replace("+", "")
                }else if(pNumber.contains("0")){
                    whatsapp+=pNumber.replace("0", "62")
                }else{
                    whatsapp+=pNumber
                }
                val webpage: Uri = Uri.parse(whatsapp)
                val myIntent = Intent(Intent.ACTION_VIEW, webpage)
                context.startActivity(myIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "Please install whatsapp on your phone", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }

        @JvmStatic
        fun applyRotationIfNeeded(imageFile: File) {
            val exif = ExifInterface(imageFile.absolutePath)
            val exifRotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
            val rotation = when (exifRotation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                else -> 0
            }
            if (rotation == 0) return
            val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
            val matrix = Matrix().apply { postRotate(rotation.toFloat()) }
            val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            bitmap.recycle()

            lateinit var out: FileOutputStream
            try {
                out = FileOutputStream(imageFile)
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            } catch (e: Exception) {

            } finally {
                rotatedBitmap.recycle()
                out.close()
            }
        }

        @JvmStatic
        fun shareLink(activity: Activity, myDynamicLink: Uri) {
            // [START ddl_share_link]
            val sendIntent = Intent()
            val msg = "Hey, check this out: $myDynamicLink"
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, msg)
            sendIntent.type = "text/plain"
            activity.startActivity(sendIntent)
            // [END ddl_share_link]
        }


    }
}