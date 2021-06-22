package com.mindoverflow.scoutshub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import bit.linux.tinyspacex.Helpers
import bit.linux.tinyspacex.Helpers.getURL
import com.mindoverflow.scoutshub.models.Perfil
import com.mindoverflow.scoutshub.models.Utilizador
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class PedidosAcesso : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedidos_acesso)

        // texts views
        val nintext = findViewById<TextView>(R.id.nintext)
        val datatext = findViewById<TextView>(R.id.datatext)
        val contactotext = findViewById<TextView>(R.id.contactotext)
        val moradatext = findViewById<TextView>(R.id.moradatext)

        // "butoes"
        val bt_recusar = findViewById<ImageView>(R.id.bt_recusar)
        val bt_aceitar = findViewById<ImageView>(R.id.bt_aceitar)

        val perfil = Perfil()

        val client = OkHttpClient()

        // buscar o URL
        val url = getURL()

        val request =
            Request.Builder().url("$url/user/")
                .get()
                .build()

        client.newCall(request).execute().use { response ->

            val jsStr = (response.body!!.string())
            val jsonArray = JSONObject(jsStr).getJSONArray("users")

            // for para ver todos os user inseridos
            for (index in 0 until jsonArray.length()) {

                val userCompare = Utilizador.fromJson(jsStr, index)

                if( userCompare.id_tipo == 999){

                    perfil.idUtilizador = userCompare.id_utilizador

                    nintext.text = perfil.nin.toString()
                    datatext.text = perfil.dtNasc
                    contactotext.text = perfil.contacto.toString()
                    moradatext.text = perfil.morada
                }

                bt_aceitar.setOnClickListener {

                    userCompare.id_tipo = 3

                    val intent = Intent(this, PedidosAcesso::class.java)
                    startActivity(intent)
                }

                bt_recusar.setOnClickListener {

                    val request1 =
                        Request.Builder().url("$url/user/${userCompare.id_utilizador}")
                            .delete()
                            .build()

                    val request2 =
                        Request.Builder().url("$url/perfil/${perfil.idPerfil}")
                            .delete()
                            .build()

                    client.newCall(request1).execute().use { response ->

                        println(response.body!!.toString())
                    }
                }
            }
            println(response.body!!.toString())
        }
    }
}