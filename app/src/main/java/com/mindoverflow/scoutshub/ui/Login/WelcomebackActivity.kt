package com.mindoverflow.scoutshub.ui.Login


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import bit.linux.tinyspacex.Helpers.getURL
import com.mindoverflow.scoutshub.MainActivity
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.SavedUserData
import com.mindoverflow.scoutshub.models.Perfil
import com.mindoverflow.scoutshub.models.Utilizador
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject


class WelcomebackActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcomeback)

        val sharedPreferences = getSharedPreferences("Scouts", MODE_PRIVATE)

        supportActionBar!!.hide()

        val bt_back = findViewById<ImageView>(R.id.bt_back)
        val login_bt = findViewById<Button>(R.id.login_bt)

        bt_back.setOnClickListener {
            finish()
        }

        login_bt.setOnClickListener {

            val mail = findViewById<EditText>(R.id.loginmail)
            val pass = findViewById<EditText>(R.id.loginpassword)

            // se estiver preenchido
            if (mail.text.toString().isNotEmpty() && pass.text.toString().isNotEmpty()) {

                // para correr noutra thread da main para nao crashar
                GlobalScope.launch(Dispatchers.IO) {

                    val userToLogin = UserNameVerification(mail.text.toString().trim(), pass.text.toString().trim())

                    // acedes textview que esta na main por ex

                    // se o return nao for null
                    if (userToLogin != null) {

                        SavedUserData.id_utilizador = userToLogin.id_utilizador
                        SavedUserData.email_utilizador = userToLogin.email_utilizador
                        SavedUserData.palavra_pass = userToLogin.palavra_pass
                        SavedUserData.id_tipo = userToLogin.id_tipo

                        val perfil = Perfil()

                        perfil.nome = sharedPreferences.getString("nome", null)
                        perfil.dtNasc = sharedPreferences.getString("dtNasc", null)
                        perfil.codigoPostal = sharedPreferences.getString("codPostal", null)
                        perfil.contacto = sharedPreferences.getInt("Telemovel", 123456789)
                        perfil.morada = sharedPreferences.getString("Morada", null)
                        perfil.nin = sharedPreferences.getInt("nin", 123456789)
                        perfil.genero = sharedPreferences.getString("genero", null)
                        perfil.totalAtivParticip = 0
                        perfil.idEquipa = 1
                        perfil.imagem = "https://cdn.discordapp.com/attachments/689891154149376033/857678265888276540/Craig-Federighi-Memoji.png"


                        val perfilJson = perfil.toJson().toString()

                        val client = OkHttpClient()
                        val url = getURL()

                        val requestBody1 = RequestBody.create("application/json".toMediaTypeOrNull(), perfilJson)

                        val request1 = Request.Builder()
                            .url("$url/perfil/user/${userToLogin.id_utilizador}")
                            .post(requestBody1)
                            .build()

                        client.newCall(request1).execute().use { response ->
                            println(response.body!!.string())
                        }

                        //Verificação de que o perfil já existe
                        val perfilExistence = perfilExistence()

                        println("####teste1 Delete#########")
                        if(perfilExistence != null){
                            println("####teste2 Delete#########")
                            deletePerfil(perfilExistence)
                        }

                        GlobalScope.launch(Dispatchers.Main) {

                            val returnIntent = Intent(this@WelcomebackActivity, MainActivity::class.java)
                            returnIntent.putExtra("user_loged_in", userToLogin.toJson().toString())

                            startActivity(returnIntent)
                        }
                    // se o return for null
                    } else {
                        GlobalScope.launch(Dispatchers.Main) {
                            Toast.makeText(this@WelcomebackActivity, "Dados Incorretos. Tente novamente", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            // se os campos nao tiver preenchidos
            } else {
                Toast.makeText(this@WelcomebackActivity, "Campos em Branco. Por favor preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun UserNameVerification(mail : String, pass : String) : Utilizador? {

        var userToLogin : Utilizador? = null

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

                println("user to compare")
                println(userCompare)

                if(mail == userCompare.email_utilizador!! && pass == userCompare.palavra_pass!! && userCompare.id_tipo != 999){

                    userToLogin = userCompare
                }
            }

            println(response.body!!.toString())
        }

        return userToLogin
    }

    private fun perfilExistence() : Perfil? {
        val client = OkHttpClient()
        val url = getURL()

        val request1 = Request.Builder()
            .url("$url/perfil")
            .get()
            .build()

        //Verificação do perfil já existir
        client.newCall(request1).execute().use { response ->
            val jsStr = (response.body!!.string())

            val jsonArray = JSONObject(jsStr).getJSONArray("perfis")

            for (a in 0 until jsonArray.length() - 1) {
                for (b in a + 1 until jsonArray.length()){
                    val perfil1 = Perfil.fromJson(jsStr, a)
                    val perfil2 = Perfil.fromJson(jsStr, b)

                    if(perfil1.idUtilizador == perfil2.idUtilizador){
                        return perfil2
                    }
                }
            }
        }
        return null
    }

    private fun deletePerfil(perfil : Perfil){
        val client = OkHttpClient()
        val url = getURL()

        val request1 = Request.Builder()
            .url("$url/perfil/${perfil.idPerfil}")
            .delete()
            .build()

        //Verificação do perfil já existir
        client.newCall(request1).execute().use { response ->
            println(response.body!!.string())
        }
    }
}

