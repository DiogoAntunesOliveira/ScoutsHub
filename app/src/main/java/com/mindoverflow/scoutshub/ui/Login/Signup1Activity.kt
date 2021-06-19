package com.example.xmlperferfil1

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signup1.*

class Signup1Activity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup1)

        supportActionBar!!.hide()

        sharedPreferences = getSharedPreferences("Scouts", MODE_PRIVATE)

        bt_back.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        bt_next.setOnClickListener {

            // input de dados
            val nome = FullName.text.toString().trim()
            val mail = EmailAddress.text.toString().trim()
            val pass = Password.text.toString().trim()
            val pass1 = Password1.text.toString().trim()
            var genero = String()


            if (Masculino.isChecked){

                genero = "Masculino"

            } else if (Femenino.isChecked) {

                genero = "Femenino"
            }

            // editor
            val editor: SharedPreferences.Editor = sharedPreferences.edit()

            // por dados em sharedpreferences
            editor.putString("nome", nome)
            editor.putString("mail", mail)
            editor.putString("pass", pass)
            editor.putString("pass1", pass1)
            editor.putString("genero", genero)

            // apilcar em sharedpreferences
            editor.apply()

            if (nome.isNotEmpty() && mail.isNotEmpty() && (pass.isNotEmpty() == pass1.isNotEmpty())){

                val intent = Intent(this, Signup2Activity::class.java)
                startActivity(intent)

            }else {

                Toast.makeText(this, "Por favor preencha todos os campos", Toast.LENGTH_SHORT).show()

            }

        }
    }
}