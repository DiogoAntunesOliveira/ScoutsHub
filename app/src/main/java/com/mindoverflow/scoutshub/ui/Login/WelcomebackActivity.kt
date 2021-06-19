package com.example.xmlperferfil1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.xmlperferfil1.httpHelper.GetURL.Companion.URL
import com.example.xmlperferfil1.models.Utilizador
import kotlinx.android.synthetic.main.activity_signup1.*
import kotlinx.android.synthetic.main.activity_signup1.bt_back
import kotlinx.android.synthetic.main.activity_welcomeback.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject


class WelcomebackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcomeback)

        supportActionBar!!.hide()

        bt_back.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        login_bt.setOnClickListener {

            var mail = loginmail.text.toString().trim()
            var pass = loginpassword.text.toString().trim()

            // se estiver preenchido
            if (mail.isNotEmpty() && pass.isNotEmpty()) {

                // para correr noutra thread da main para nao crashar
                GlobalScope.launch(Dispatchers.IO) {

                    val userToLogin = UserNameVerification(mail, pass)

                    // acedes textview que esta na main por ex
                    GlobalScope.launch(Dispatchers.Main) {

                        // se o return nao for null
                        if (userToLogin != null) {

                            val returnIntent = Intent()
                            returnIntent.putExtra("userToLogin", userToLogin.toJson().toString())
                            setResult(Activity.RESULT_OK, returnIntent)
                            finish()

                        // se o return for null
                        } else {

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
        val url = URL()

        val request =
            Request.Builder().url("$url/user/")
                .get()
                .build()

        client.newCall(request).execute().use { response ->

            val jsStr = (response.body!!.string())
            val jsonArray = JSONObject(jsStr).getJSONArray("user")

            // for para ver todos os user inseridos
            for (index in 0 until jsonArray.length()) {
                val userCompare = Utilizador.fromJson(jsStr, index, "get_json_array_by_id")

                if(mail == userCompare.email_utilizador!! && pass == userCompare.palavra_pass!!){

                    userToLogin = userCompare
                }
            }
        }
        return userToLogin
    }
}