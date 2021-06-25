package com.mindoverflow.scoutshub.ui.Instrucoes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.mindoverflow.scoutshub.MainActivity
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.ui.Atividades.AtividadesActivity

class  InstrucoesActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_instrucoes)

        supportActionBar!!.hide()

        val backButton = findViewById<ImageButton>(R.id.btnBack)

        backButton.setOnClickListener {
            finish()
        }

        val videoBtn = findViewById<Button>(R.id.btnVideo)
        val stepsBtn = findViewById<Button>(R.id.btnSteps)

        // caso selecione o botão de video, mudamos para a activity destinada a mostrar o video
        videoBtn.setOnClickListener {
            val intent = Intent (this, VideoOptionsActivity::class.java)
            startActivity(intent)
        }

        // caso selecione o botão de video, mudamos para a activity destinada a mostrar os passos
        stepsBtn.setOnClickListener {
            val intent = Intent (this, StepsOptionsActivity::class.java)
            startActivity(intent)
        }
    }
}