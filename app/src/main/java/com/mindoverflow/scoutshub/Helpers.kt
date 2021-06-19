package bit.linux.tinyspacex

import android.graphics.BitmapFactory
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.widget.ImageView
import com.mindoverflow.scoutshub.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

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

    fun URL(): String {

        return "http://mindoverflow.amipca.xyz:60000"
    }
}