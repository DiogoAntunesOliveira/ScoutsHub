package com.mindoverflow.scoutshub.ui.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import bit.linux.tinyspacex.Helpers.URL
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

            val perfil = Perfil()
            val utilizador = Utilizador()

            perfil.nome = sharedPreferences.getString("nome", null)
            perfil.dtNasc = sharedPreferences.getString("dtNasc", null)
            perfil.codigoPostal = sharedPreferences.getString("codPostal", null)
            perfil.contacto = sharedPreferences.getString("Telemovel", null)?.toInt()
            perfil.morada = sharedPreferences.getString("Morada", null)
            perfil.nin = sharedPreferences.getString("nin", null)?.toInt()
            perfil.genero = sharedPreferences.getString("genero", null)
            utilizador.email_utilizador = sharedPreferences.getString("mail", null)
            utilizador.palavra_pass = sharedPreferences.getString("pass1", null)
            utilizador.id_tipo = 999
            perfil.totalAtivParticip = 0
            perfil.idEquipa = 1
            perfil.idUtilizador = 1


            GlobalScope.launch(Dispatchers.IO) {
                val url = URL()
                val client = OkHttpClient()

                val perfilJson = perfil.toJson().toString()
                val utilizadorJson = utilizador.toJson().toString()

                val requestBody1 = RequestBody.create("application/json".toMediaTypeOrNull(), perfilJson)

                val request1 = Request.Builder()
                    .url("$url/perfil")
                    .post(requestBody1)
                    .build()

                client.newCall(request1).execute().use { response ->
                    println(response.body!!.string())
                }

                val requestBody2 = RequestBody.create("application/json".toMediaTypeOrNull(), utilizadorJson)

                val request2 = Request.Builder()
                    .url("$url/user")
                    .post(requestBody2)
                    .build()

                client.newCall(request2).execute().use { response ->
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