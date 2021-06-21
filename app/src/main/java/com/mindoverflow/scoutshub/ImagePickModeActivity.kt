package com.mindoverflow.scoutshub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class ImagePickModeActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var imageButton: Button
    private lateinit var sendButton: Button
    private var imageData: ByteArray? = null
    private val postURL: String = "http://mindoverflow.amipca.xyz:60000/upload" // remember to use your own api

    companion object {
        private const val IMAGE_PICK_CODE = 999
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_pick_mode)

        supportActionBar!!.hide()

        val imageView = findViewById<Button>(R.id.imageView)
        val imageButton = findViewById<Button>(R.id.imageButton)

        imageButton.setOnClickListener {
            launchGallery()
        }
        sendButton = findViewById(R.id.sendButton)
        sendButton.setOnClickListener {
            //uploadImage()
        }
    }
    private fun launchGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

}