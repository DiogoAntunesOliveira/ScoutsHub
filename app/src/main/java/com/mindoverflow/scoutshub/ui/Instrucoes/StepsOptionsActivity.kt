package com.mindoverflow.scoutshub.ui.Instrucoes

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.mindoverflow.scoutshub.R

class StepsOptionsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instrucoes_opcoes)

        supportActionBar!!.hide()

        val backButton = findViewById<ImageButton>(R.id.btnBack)

        backButton.setOnClickListener {
            val intent = Intent(this, InstrucoesActivity::class.java)
            startActivity(intent)
        }

        // declaração de variáveis
        val option1 = findViewById<ImageButton>(R.id.image1)
        val option2 = findViewById<ImageButton>(R.id.image2)
        val option3 = findViewById<ImageButton>(R.id.image3)
        val option4 = findViewById<ImageButton>(R.id.image4)

        option1.setOnClickListener {

            val intent = Intent(this, StepsActivityShowData::class.java)
            startActivity(intent)
        }

        option2.setOnClickListener {

            val intent = Intent(this, StepsActivityShowData::class.java)
            startActivity(intent)
        }

        option3.setOnClickListener {

            val intent = Intent(this, StepsActivityShowData::class.java)
            startActivity(intent)
        }

        option4.setOnClickListener {

            val intent = Intent(this, StepsActivityShowData::class.java)
            startActivity(intent)
        }
    }
}