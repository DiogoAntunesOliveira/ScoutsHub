package com.mindoverflow.scoutshub.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.models.Perfil
import org.json.JSONObject

class EditingProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editing_profile)

        val jsonString = intent.getStringExtra("user_data")

        val jsonObject = JSONObject(jsonString)
        val perfilFromJson = Perfil.fromJson(jsonObject)

        val editTextNome = findViewById<EditText>(R.id.textViewEditarNome)
        val editTextEmail = findViewById<EditText>(R.id.textViewEditarEmail)
        val editTextNin = findViewById<EditText>(R.id.textViewEditarNin)
        val editTextMorada = findViewById<EditText>(R.id.textViewEditarEndereco)
        val editTextCodigoPostal = findViewById<EditText>(R.id.textViewEditarCodigoPostal)
        val editTextDataNascimento = findViewById<EditText>(R.id.textViewEditarDataNascimento)
        val editTextNumeroTelefone = findViewById<EditText>(R.id.textViewEditarNumeroTelefone)

        editTextNome.setText(perfilFromJson.nome)
        editTextEmail.setText("")
        editTextNin.setText(perfilFromJson.nin.toString())
        editTextMorada.setText(perfilFromJson.morada)
        editTextCodigoPostal.setText(perfilFromJson.codigoPostal)
        editTextDataNascimento.setText(perfilFromJson.dtNasc)
        editTextNumeroTelefone.setText(perfilFromJson.contacto.toString())

        supportActionBar!!.hide()

        val perfil = Perfil(perfilFromJson.idPerfil, editTextNome.text.toString(), editTextDataNascimento.text.toString(), perfilFromJson.genero, editTextNumeroTelefone.text.toString().toInt(), editTextMorada.text.toString(), editTextCodigoPostal.text.toString(), editTextNin.text.toString().toInt(), 6, 5)

        val buttonEdit = findViewById<Button>(R.id.buttonEdit)

        buttonEdit.setOnClickListener {

            val returnIntent = Intent()
            returnIntent.putExtra("perfil_editado", perfil.toJson().toString())
            //println(result.dataRecolha)
            setResult(Activity.RESULT_OK, returnIntent)

            finish()
        }

        val goBack = findViewById<ImageView>(R.id.imageView7)

        goBack.setOnClickListener {
            finish()
        }
    }
}