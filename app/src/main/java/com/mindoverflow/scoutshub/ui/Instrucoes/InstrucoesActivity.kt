package com.mindoverflow.scoutshub.ui.Instrucoes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.mindoverflow.scoutshub.R

class  InstrucoesActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_instrucoes)

        supportActionBar!!.hide()

        // go back to previous activity
        val backButton = findViewById<ImageButton>(R.id.buttonBack)
        backButton.setOnClickListener {
            finish()
        }

        // travel to video activity
        val videoButton = findViewById<Button>(R.id.buttonVideo)
        videoButton.setOnClickListener {
            val intent = Intent (this, VideoChooseOptions::class.java)
            startActivity(intent)
        }

        // travel to steps activity
        val stepsButton = findViewById<Button>(R.id.buttonSteps)
        stepsButton.setOnClickListener {
            val intent = Intent (this, StepsChooseOptions::class.java)
            startActivity(intent)
        }
    }
}