package com.mindoverflow.scoutshub.ui.Login

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.mindoverflow.scoutshub.R

class Signup1Activity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup1)

        supportActionBar!!.hide()

        sharedPreferences = getSharedPreferences("Scouts", MODE_PRIVATE)

        val bt_back = findViewById<ImageView>(R.id.bt_back)
        val bt_next = findViewById<Button>(R.id.bt_next)


        bt_back.setOnClickListener {

            val intent = Intent(this, FrontPage::class.java)
            startActivity(intent)
            finish()
        }

        bt_next.setOnClickListener {

            // input de dados
            val nome = findViewById<EditText>(R.id.FullName)
            val mail = findViewById<EditText>(R.id.EmailAddress)
            val pass = findViewById<EditText>(R.id.Password)
            val pass1 = findViewById<EditText>(R.id.Password1)
            val Masculino = findViewById<RadioButton>(R.id.Masculino)
            val Femenino = findViewById<RadioButton>(R.id.Femenino)
            var genero = String()

            if (Masculino.isChecked){

                genero = "Masculino"

            } else if (Femenino.isChecked) {

                genero = "Femenino"
            }

            // editor
            val editor: SharedPreferences.Editor = sharedPreferences.edit()

            // por dados em sharedpreferences
            editor.putString("nome", nome.text.toString())
            editor.putString("mail", mail.text.toString())
            editor.putString("pass", pass.text.toString())
            editor.putString("pass1", pass1.text.toString())
            editor.putString("genero", genero)

            // apilcar em sharedpreferences
            editor.apply()

            if (nome.text.toString().isNotEmpty() && mail.text.toString().isNotEmpty() && (pass.text.toString().isNotEmpty() == pass1.text.toString().isNotEmpty())){

                val intent = Intent(this, Signup2Activity::class.java)
                startActivity(intent)

            }else {

                Toast.makeText(this, "Por favor preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}