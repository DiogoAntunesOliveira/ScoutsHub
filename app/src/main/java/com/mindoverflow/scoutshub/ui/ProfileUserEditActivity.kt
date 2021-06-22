package com.mindoverflow.scoutshub.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import bit.linux.tinyspacex.Helpers
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.SavedUserData
import com.mindoverflow.scoutshub.models.Perfil
import com.mindoverflow.scoutshub.models.Utilizador
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody


class ProfileUserEditActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editing_profile)


        val buttonEdit = findViewById<Button>(R.id.buttonEdit)
        val goBack = findViewById<ImageView>(R.id.imageViewGoBack)
        val buttonDelete = findViewById<Button>(R.id.buttonDelete)

        buttonDelete.visibility = View.GONE

        val jsonString = intent.getStringExtra("user_data")


        val user = Perfil.fromJson(jsonString, null)

        //here i get the user email
        //Email()

        User(user)

        supportActionBar!!.hide()



        buttonEdit.setOnClickListener {
                val returnIntent = Intent()
                val userEdited = SaveChanges(user)

                GlobalScope.launch(Dispatchers.IO) {
                    SavingUserEmail()
                }

                //Return to Profile view activity the edited user
                returnIntent.putExtra("perfil_editado", userEdited.toJson().toString())
                setResult(Activity.RESULT_OK, returnIntent)
                finish()


        }


        goBack.setOnClickListener {
            finish()
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

        if(user.dtNasc!!.length == 24){
            user.dtNasc = Helpers.DateFormaterApi(user.dtNasc!!)
        }

        val userEmail = SavedUserData.email_utilizador

        editTextNome.setText(user.nome)
        editTextEmail.setText(userEmail)
        editTextNin.setText(user.nin.toString())
        editTextMorada.setText(user.morada)
        editTextCodigoPostal.setText(user.codigoPostal)
        editTextDataNascimento.setText(user.dtNasc)
        editTextNumeroTelefone.setText(user.contacto.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun SaveChanges(user: Perfil) : Perfil {
        val editTextNome = findViewById<EditText>(R.id.textViewEditarNome)
        val editTextDataNascimento = findViewById<EditText>(R.id.textViewEditarDataNascimento)
        val editTextNumeroTelefone = findViewById<EditText>(R.id.textViewEditarNumeroTelefone)
        val editTextMorada = findViewById<EditText>(R.id.textViewEditarEndereco)
        val editTextCodigoPostal = findViewById<EditText>(R.id.textViewEditarCodigoPostal)
        val editTextNin = findViewById<EditText>(R.id.textViewEditarNin)
        val editTextEmail = findViewById<EditText>(R.id.textViewEditarEmail)

        SavedUserData.email_utilizador = editTextEmail.text.toString()

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


        return Perfil(idPerfil, nome, imagem, dataNascimento, genero, numeroTelefone.toInt(), morada, codigoPostal, nin.toInt(), totalAtivParticip!!.toInt(), idEquipa!!.toInt(), user.idUtilizador!!.toInt())
    }
    fun SavingUserEmail() {

        val client = OkHttpClient()

        val url = Helpers.getURL()
        //val dateFormated = DateFormaterIng(newData.dtNasc!!)

        val user = Utilizador(SavedUserData.id_utilizador, SavedUserData.email_utilizador, SavedUserData.palavra_pass, SavedUserData.id_tipo)

        val requestBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            user.toJson().toString()
        )

        val request =
            Request.Builder()
                .url("$url/user/${user.id_utilizador}")
                .put(requestBody)
                .build()

        client.newCall(request).execute().use { response ->
            val response = (response.body!!.string())
        }
    }
}