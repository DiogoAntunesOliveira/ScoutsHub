package com.mindoverflow.scoutshub.ui.Instrucoes

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.mindoverflow.scoutshub.R

class StepsChooseOptions: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instrucoes_opcoes)

        supportActionBar!!.hide()

        // go back to previous activity
        val backButton = findViewById<ImageButton>(R.id.buttonBack)
        backButton.setOnClickListener {
            finish()
        }

        val option1 = findViewById<ImageButton>(R.id.image1)
        option1.setOnClickListener {
            val intent = Intent(this, StepsMainActivity::class.java)
            startActivity(intent)
        }

        val option2 = findViewById<ImageButton>(R.id.image2)
        option2.setOnClickListener {
            val intent = Intent(this, StepsMainActivity::class.java)
            startActivity(intent)
        }

        val option3 = findViewById<ImageButton>(R.id.image3)
        option3.setOnClickListener {
            val intent = Intent(this, StepsMainActivity::class.java)
            startActivity(intent)
        }

        val option4 = findViewById<ImageButton>(R.id.image4)
        option4.setOnClickListener {
            val intent = Intent(this, StepsMainActivity::class.java)
            startActivity(intent)
        }
    }
}