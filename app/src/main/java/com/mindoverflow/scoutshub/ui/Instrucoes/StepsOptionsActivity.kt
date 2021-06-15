package com.mindoverflow.scoutshub.ui.Instrucoes

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.mindoverflow.scoutshub.R

class StepsOptionsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opcoes_instrucoes)

        supportActionBar!!.hide()

        val option1 = findViewById<ImageButton>(R.id.option1)
        val option2 = findViewById<ImageButton>(R.id.option2)
        val option3 = findViewById<ImageButton>(R.id.option3)

        option1.setOnClickListener {

            val intent = Intent(this, StepsActivity::class.java)
            startActivity(intent)
        }

        option2.setOnClickListener {

            val intent = Intent(this, StepsActivity::class.java)
            startActivity(intent)
        }

        option3.setOnClickListener {

            val intent = Intent(this, StepsActivity::class.java)
            startActivity(intent)
        }
    }
}