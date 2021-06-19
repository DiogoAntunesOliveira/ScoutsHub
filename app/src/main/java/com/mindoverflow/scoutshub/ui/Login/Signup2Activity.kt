package com.example.xmlperferfil1

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signup2.*
import kotlinx.android.synthetic.main.activity_signup2.bt_back
import kotlinx.android.synthetic.main.activity_signup2.bt_next2

class Signup2Activity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup2)

        supportActionBar!!.hide()

        sharedPreferences = getSharedPreferences("Scouts", MODE_PRIVATE)

        bt_back.setOnClickListener {

            val intent = Intent(this, Signup1Activity::class.java)
            startActivity(intent)

        }

        bt_next2.setOnClickListener {

            val nin = NIN.text.toString().trim()
            val codPostal = PostalAddress.text.toString().trim()
            val morada = Address.text.toString().trim()
            val dtNasc = BirthDate.text.toString().trim()
            val telemovel = TextNumber.text.toString().trim()

            // editor
            val editor: SharedPreferences.Editor = sharedPreferences.edit()

            // por dados em sharedpreferences
            editor.putString("nin", nin)
            editor.putString("codPostal", codPostal)
            editor.putString("Morada", morada)
            editor.putString("dtNasc", dtNasc)
            editor.putString("Telemovel", telemovel)

            // apilcar em sharedpreferences
            editor.apply()

            if (morada.isNotEmpty() && codPostal.isNotEmpty() && dtNasc.isNotEmpty()) {

                val intent = Intent(this, PopUp::class.java)
                startActivity(intent)

            } else {

                Toast.makeText(this, "Por favor preencha os campos obrigatorios", Toast.LENGTH_SHORT).show()
            }
        }
    }
}