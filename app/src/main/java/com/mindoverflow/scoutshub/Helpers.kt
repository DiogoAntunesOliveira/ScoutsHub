package bit.linux.tinyspacex

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object Helpers {

    fun getImageUrl(url: String, imageView: ImageView) {

        GlobalScope.launch(Dispatchers.IO) {

            try {
                val input = URL(url).openStream()
                val bitmap = BitmapFactory.decodeStream(input)

                GlobalScope.launch(Dispatchers.Main) {
                    imageView.setImageBitmap(bitmap)
                }

            }catch (e: Exception){
                println(e.localizedMessage)

                GlobalScope.launch(Dispatchers.Main) {
                    //imageView.setImageResource(R.mipmap.rocket)
                }
            }

        }
    }

    fun getURL(): String {
        return "http://mindoverflow.amipca.xyz:60000"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun DateFormaterApi(date : String) : String {
        val inputFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)
        val date = LocalDate.parse(date, inputFormatter)
        val formattedDate = outputFormatter.format(date)

        return formattedDate
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun DateFormaterPtToIng(date: String): String? {

        val inputFormatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
        val date = LocalDate.parse(date, inputFormatter)
        val formattedDate = outputFormatter.format(date)

        return formattedDate
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun DateFormaterIngToPt(date: String): String? {

        val inputFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)
        val date = LocalDate.parse(date, inputFormatter)
        val formattedDate = outputFormatter.format(date)

        return formattedDate
    }
}