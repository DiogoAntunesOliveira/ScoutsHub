package com.mindoverflow.scoutshub.ui.Instrucoes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.models.Instrucao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject

class StepsActivityEditData: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instrucoes_steps_editdata)

        supportActionBar!!.hide()

        val title = findViewById<EditText>(R.id.editBox1)
        val description = findViewById<EditText>(R.id.editBox2)
        val saveButton = findViewById<Button>(R.id.btnSave)
        val backButton = findViewById<ImageButton>(R.id.btnBack)

        backButton.setOnClickListener {
            finish()
        }

        saveButton.setOnClickListener {
            if(title.text.trim().isNotEmpty()) {
                GlobalScope.launch(Dispatchers.IO) {
                    val client = OkHttpClient()

                    val instrucao = Instrucao(
                        null,
                        title.text.toString(),
                        description.text.toString())

                    val requestBody = RequestBody.create(
                        "application/json".toMediaTypeOrNull(),
                        instrucao.toJson().toString()
                    )

                    Log.d("data", instrucao.toJson().toString())

                    val request = Request.Builder()
                        .url("http://mindoverflow.amipca.xyz:60000/intructions")
                        .post(requestBody)
                        .build()

                    client.newCall(request).execute().use { response ->
                        Log.i("data", response.message)
                    }
                }
                val intent = Intent(this, StepsActivityShowData::class.java)
                startActivity(intent)
            }
            else
            {
                Toast.makeText(this,"Preencha os campos",Toast.LENGTH_LONG).show()
            }
        }
    }
}