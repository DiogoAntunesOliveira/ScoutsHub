package com.mindoverflow.scoutshub.ui.Login


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import bit.linux.tinyspacex.Helpers.URL
import com.mindoverflow.scoutshub.MainActivity
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.models.Utilizador
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

        val bt_back = findViewById<ImageView>(R.id.bt_back)
        val login_bt = findViewById<Button>(R.id.login_bt)

        bt_back.setOnClickListener {

            val intent = Intent(this, FrontPage::class.java)
            startActivity(intent)
            finish()
        }

        login_bt.setOnClickListener {

            val mail = findViewById<EditText>(R.id.loginmail)
            val pass = findViewById<EditText>(R.id.loginpassword)

            // se estiver preenchido
            if (mail.text.toString().isNotEmpty() && pass.text.toString().isNotEmpty()) {

                println("TEST TEST TEST")

                // para correr noutra thread da main para nao crashar
                GlobalScope.launch(Dispatchers.IO) {

                    println("EST TEST TEST2")

                    println(mail.text.toString().trim())

                    val userToLogin = UserNameVerification(mail.text.toString().trim(), pass.text.toString().trim())

                    println("EST TEST TEST3")


                    // acedes textview que esta na main por ex
                    GlobalScope.launch(Dispatchers.Main) {

                        // se o return nao for null
                        if (userToLogin != null) {

                            println("test succefull")

                            val returnIntent = Intent(this@WelcomebackActivity, MainActivity::class.java)
                            //returnIntent.putExtra("userToLogin", userToLogin.toJson().toString())
                            //setResult(Activity.RESULT_OK, returnIntent)
                            //finish()

                            println(userToLogin.toJson().toString())

                            startActivity(returnIntent)

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

            println("test user1")

            val jsStr = (response.body!!.string())
            println(jsStr)
            val jsonArray = JSONObject(jsStr).getJSONArray("users")
            println(jsonArray)

            // for para ver todos os user inseridos
            for (index in 0 until jsonArray.length()) {

                val userCompare = Utilizador.fromJson(jsStr, index)

                println(userCompare)

                if(mail == userCompare.email_utilizador!! && pass == userCompare.palavra_pass!! && userCompare.id_tipo != 999){

                    userToLogin = userCompare
                }
            }

            println(response.body!!.toString())
        }

        println(userToLogin)
        return userToLogin
    }
}