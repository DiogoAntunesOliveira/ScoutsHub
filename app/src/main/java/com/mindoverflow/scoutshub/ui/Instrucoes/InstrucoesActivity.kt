package com.mindoverflow.scoutshub.ui.Instrucoes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.mindoverflow.scoutshub.R

class InstrucoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_instrucoes)

        supportActionBar!!.hide()

        val VideoBtn = findViewById<Button>(R.id.video_btn)
        val StepsBtn = findViewById<Button>(R.id.steps_btn)

        // caso selecione o botão de video, mudamos para a activity destinada a mostrar o video
        VideoBtn.setOnClickListener {
            val intent = Intent(this, VideoOptionsActivity::class.java)
            startActivity(intent)
        }

        // caso selecione o botão de video, mudamos para a activity destinada a mostrar os passos
        StepsBtn.setOnClickListener {
            val intent = Intent(this, StepsOptionsActivity::class.java)
            startActivity(intent)
        }
    }
}