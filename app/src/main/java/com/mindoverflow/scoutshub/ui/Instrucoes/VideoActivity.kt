package com.mindoverflow.scoutshub.ui.Instrucoes

import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.mindoverflow.scoutshub.R

class VideoActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_instrucoes)

        supportActionBar!!.hide()

        val videoView = findViewById<VideoView>(R.id.video_view)

        // recolher os valores do intent que enviamos da activity anterior
        val uri:String = intent.getStringExtra("data").toString()
        videoView.setVideoPath(uri)

        // adicionar no display os botões de "play", "next", "previous" e a barra de progresso
        val mediaController = MediaController(this)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)
    }
}