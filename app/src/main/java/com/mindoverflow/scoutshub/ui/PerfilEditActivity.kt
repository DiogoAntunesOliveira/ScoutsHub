package com.mindoverflow.scoutshub.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import bit.linux.tinyspacex.Helpers.DateFormaterApi
import bit.linux.tinyspacex.Helpers.DateFormaterIngToPt
import com.mindoverflow.scoutshub.GetURL.Companion.URL
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.models.Perfil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

class PerfilEditActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editing_profile)

        val jsonString = intent.getStringExtra("user_data")

        val user = Perfil.fromJson(jsonString, null)

        //here i get the user email
        //Email()

        User(user)

        supportActionBar!!.hide()

        val buttonEdit = findViewById<Button>(R.id.buttonEdit)
        val buttonDelete = findViewById<Button>(R.id.buttonDelete)


        buttonEdit.setOnClickListener {
            val returnIntent = Intent()

            val userEdited = SaveChanges(user)

            //Return to Profile view activity the edited user
            returnIntent.putExtra("perfil_editado", userEdited.toJson().toString())
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }

        buttonDelete.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                DeleteUser(user.idPerfil!!)
                GlobalScope.launch(Dispatchers.Main) {
                    finish()
                }
            }
        }

        val goBack = findViewById<ImageView>(R.id.imageViewGoBack1)

        goBack.setOnClickListener {
            finish()
        }
    }
    fun DeleteUser(id: Int){

        val client = OkHttpClient()

        val url = URL()

        val request = Request.Builder().url("$url/perfil/${id}")
            .delete()
            .build()

        client.newCall(request).execute().use { response ->

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun User(user: Perfil) {
        val editTextNome = findViewById<EditText>(R.id.textViewEditarNome)
        val editTextEmail = findViewById<EditText>(R.id.textViewEditarEmail)
        val editTextNin = findViewById<EditText>(R.id.textViewEditarNin)
        val editTextMorada = findViewById<EditText>(R.id.textViewEditarEndereco)
        val editTextCodigoPostal = findViewById<EditText>(R.id.textViewEditarCodigoPostal)
        val editTextDataNascimento = findViewById<EditText>(R.id.textViewEditarDataNascimento)
        val editTextNumeroTelefone = findViewById<EditText>(R.id.textViewEditarNumeroTelefone)

        val dateFormated : String

        if(user.dtNasc!!.length == 24){
            dateFormated = DateFormaterApi(user.dtNasc!!)
        }else {
            dateFormated = DateFormaterIngToPt(user.dtNasc!!)!!
        }


        editTextNome.setText(user.nome)
        editTextEmail.setText("")
        editTextNin.setText(user.nin.toString())
        editTextMorada.setText(user.morada)
        editTextCodigoPostal.setText(user.codigoPostal)
        editTextDataNascimento.setText(dateFormated)
        editTextNumeroTelefone.setText(user.contacto.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun SaveChanges(user: Perfil) : Perfil{
        val editTextNome = findViewById<EditText>(R.id.textViewEditarNome)
        val editTextDataNascimento = findViewById<EditText>(R.id.textViewEditarDataNascimento)
        val editTextNumeroTelefone = findViewById<EditText>(R.id.textViewEditarNumeroTelefone)
        val editTextMorada = findViewById<EditText>(R.id.textViewEditarEndereco)
        val editTextCodigoPostal = findViewById<EditText>(R.id.textViewEditarCodigoPostal)
        val editTextNin = findViewById<EditText>(R.id.textViewEditarNin)
        //val editTextEmail = findViewById<EditText>(R.id.textViewEditarEmail)


        val idPerfil = user.idPerfil
        val nome = editTextNome.text.toString()
        val imagem = user.imagem
        val dataNascimento = editTextDataNascimento.text.toString()
        val genero = user.genero
        val numeroTelefone = editTextNumeroTelefone.text.toString()
        val morada = editTextMorada.text.toString()
        val codigoPostal = editTextCodigoPostal.text.toString()
        val nin = editTextNin.text.toString()
        val totalAtivParticip = user.totalAtivParticip
        val idEquipa = user.idEquipa

        //val dateNascimentoFormated = DateFormaterTeste(dataNascimento)


        return Perfil(idPerfil, nome, imagem, dataNascimento, genero, numeroTelefone.toInt(), morada, codigoPostal, nin.toInt(), totalAtivParticip!!.toInt(), idEquipa!!.toInt())
    }
}