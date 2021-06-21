package com.mindoverflow.scoutshub.ui.Instrucoes

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.mindoverflow.scoutshub.R


class VideoOptionsActivity: AppCompatActivity() {

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
            // enviar para a outra activity o "link" do video que queremos visualizar (fogueira)
            val intent = Intent(this, VideoActivity::class.java)
            //intent.putExtra("data", "android.resource://" + packageName.toString() + "/" + R.raw.fire_video)
            startActivity(intent)
        }

        option2.setOnClickListener {
            // enviar para a outra activity o "link" do video que queremos visualizar (tenda)
            val intent = Intent(this, VideoActivity::class.java)
            //intent.putExtra("data", "android.resource://" + packageName.toString() + "/" + R.raw.tent_video)
            startActivity(intent)
        }

        option3.setOnClickListener {
            // enviar para a outra activity o "link" do video que queremos visualizar (mapa)
            val intent = Intent(this, VideoActivity::class.java)
            //intent.putExtra("data", "android.resource://" + packageName.toString() + "/" + R.raw.knot_video)
            startActivity(intent)
        }

        option4.setOnClickListener {
            // enviar para a outra activity o "link" do video que queremos visualizar (mapa)
            val intent = Intent(this, VideoActivity::class.java)
            //intent.putExtra("data", "android.resource://" + packageName.toString() + "/" + R.raw.map_video)
            startActivity(intent)
        }
    }
}