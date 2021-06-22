package com.mindoverflow.scoutshub.ui.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import bit.linux.tinyspacex.Helpers.getURL
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.models.Perfil
import com.mindoverflow.scoutshub.models.Utilizador
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request

class PopUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_up)

        supportActionBar!!.hide()

        val sharedPreferences = getSharedPreferences("Scouts", MODE_PRIVATE)

        val bt_end = findViewById<Button>(R.id.bt_end)

        bt_end.setOnClickListener {

            val utilizador = Utilizador()

            utilizador.email_utilizador = sharedPreferences.getString("mail", null)
            utilizador.palavra_pass = sharedPreferences.getString("pass1", null)
            utilizador.id_tipo = 1

            GlobalScope.launch(Dispatchers.IO) {
                val url = getURL()

                val client = OkHttpClient()

                val utilizadorJson = utilizador.toJson().toString()

                val requestBody1 = RequestBody.create("application/json".toMediaTypeOrNull(), utilizadorJson)

                val request1 = Request.Builder()
                    .url("$url/user")
                    .post(requestBody1)
                    .build()

                client.newCall(request1).execute().use { response ->
                   println(response.body!!.string())
                }

                GlobalScope.launch(Dispatchers.Main){

                    val intent = Intent(this@PopUp, FrontPage::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}