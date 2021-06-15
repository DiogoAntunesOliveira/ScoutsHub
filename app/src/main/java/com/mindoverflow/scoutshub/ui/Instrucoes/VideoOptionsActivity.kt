package com.mindoverflow.scoutshub.ui.Instrucoes

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.mindoverflow.scoutshub.R

class VideoOptionsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opcoes_instrucoes)

        supportActionBar!!.hide()

        // declaração de variáveis
        val fire = findViewById<ImageButton>(R.id.option1)
        val tent = findViewById<ImageButton>(R.id.option2)
        val map = findViewById<ImageButton>(R.id.option3)

        /*fire.setOnClickListener {

            // enviar para a outra activity o "link" do video que queremos visualizar (fogueira)
            val intent = Intent(this, VideoActivity::class.java)
            intent.putExtra("data", "android.resource://" + packageName.toString() + "/" + R.raw.fire_video)
            startActivity(intent)
        }

        tent.setOnClickListener {

            // enviar para a outra activity o "link" do video que queremos visualizar (tenda)
            val intent = Intent(this, VideoActivity::class.java)
            intent.putExtra("data", "android.resource://" + packageName.toString() + "/" + R.raw.tent_video)
            startActivity(intent)
        }

        map.setOnClickListener {

            // enviar para a outra activity o "link" do video que queremos visualizar (mapa)
            val intent = Intent(this, VideoActivity::class.java)
            intent.putExtra("data", "android.resource://" + packageName.toString() + "/" + R.raw.map_video)
            startActivity(intent)
        }*/
    }
}