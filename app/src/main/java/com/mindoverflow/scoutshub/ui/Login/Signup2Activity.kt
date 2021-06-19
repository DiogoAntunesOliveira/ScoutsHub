package com.mindoverflow.scoutshub.ui.Login

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.mindoverflow.scoutshub.R

class Signup2Activity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup2)

        supportActionBar!!.hide()

        sharedPreferences = getSharedPreferences("Scouts", MODE_PRIVATE)

        val bt_back = findViewById<ImageView>(R.id.bt_back)
        val bt_next2 = findViewById<Button>(R.id.bt_next2)

        bt_back.setOnClickListener {

            val intent = Intent(this, Signup1Activity::class.java)
            startActivity(intent)
        }

        bt_next2.setOnClickListener {

            var nin = findViewById<EditText>(R.id.NIN)
            val codPostal = findViewById<EditText>(R.id.PostalAddress)
            val morada = findViewById<EditText>(R.id.Address)
            val dtNasc = findViewById<EditText>(R.id.BirthDate)
            val telemovel = findViewById<EditText>(R.id.TextNumber)

            // editor
            val editor: SharedPreferences.Editor = sharedPreferences.edit()

            // por dados em sharedpreferences
            editor.putString("nin", nin.toString())
            editor.putString("codPostal", codPostal.toString())
            editor.putString("Morada", morada.toString())
            editor.putString("dtNasc", dtNasc.toString())
            editor.putString("Telemovel", telemovel.toString())

            // apilcar em sharedpreferences
            editor.apply()

            if (morada.text.toString().isNotEmpty() && codPostal.text.toString().isNotEmpty() && dtNasc.text.toString().isNotEmpty()) {

                val intent = Intent(this, PopUp::class.java)
                startActivity(intent)

            } else {

                Toast.makeText(this, "Por favor preencha os campos obrigatorios", Toast.LENGTH_SHORT).show()
            }
        }
    }
}