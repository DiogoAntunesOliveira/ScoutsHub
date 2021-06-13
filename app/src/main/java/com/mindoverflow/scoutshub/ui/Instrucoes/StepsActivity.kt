package com.mindoverflow.scoutshub.ui.Instrucoes

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.mindoverflow.scoutshub.R

class StepsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_steps_instrucoes)

        supportActionBar!!.hide()

        val doneBtn = findViewById<Button>(R.id.doneBtn)
        val text = findViewById<EditText>(R.id.description)

        doneBtn.setOnClickListener {

            text.setText("")

            val intent = intent
            finish()
            startActivity(intent)
        }
    }
}