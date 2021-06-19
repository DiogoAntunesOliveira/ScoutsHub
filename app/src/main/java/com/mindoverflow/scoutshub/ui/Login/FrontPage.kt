package com.example.xmlperferfil1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.mindoverflow.scoutshub.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frontpage)

        supportActionBar!!.hide()

        val bt_signin = findViewById<Button>(R.id.bt_signin)
        val bt_signup = findViewById<Button>(R.id.bt_signup)

        bt_signin.setOnClickListener {

            val intent = Intent(this, WelcomebackActivity::class.java)
            startActivity(intent)
            finish()
        }

        bt_signup.setOnClickListener {

            val intent = Intent(this, Signup1Activity::class.java)
            startActivity(intent)
            finish()
        }
    }
}