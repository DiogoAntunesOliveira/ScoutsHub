package com.mindoverflow.scoutshub.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.INotificationSideChannel
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import bit.linux.tinyspacex.Helpers
import bit.linux.tinyspacex.Helpers.DateFormaterApi
import bit.linux.tinyspacex.Helpers.DateFormaterIngToPt
import bit.linux.tinyspacex.Helpers.getURL
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.SavedUserData
import com.mindoverflow.scoutshub.models.Atividade
import com.mindoverflow.scoutshub.models.Perfil
import com.mindoverflow.scoutshub.models.Utilizador
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject


class ProfileAdmEditActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editing_profile)

        supportActionBar!!.hide()


        val jsonString = intent.getStringExtra("user_data")

        val perfil = Perfil.fromJson(jsonString, null)

        val goBack = findViewById<ImageView>(R.id.imageViewGoBack)
        val buttonEdit = findViewById<Button>(R.id.buttonEdit)
        val buttonDelete = findViewById<Button>(R.id.buttonDelete)

        var user : Utilizador? = null

        GlobalScope.launch(Dispatchers.IO) {
            user = GetUser(perfil.idUtilizador!!)
            GlobalScope.launch(Dispatchers.Main) {
                User(perfil, user!!.email_utilizador!!)
            }
        }


        buttonEdit.setOnClickListener {
            val returnIntent = Intent()
            val perfilEdited = SaveChanges(perfil)

            GlobalScope.launch(Dispatchers.IO) {
                SaveEmailChage(user!!)
            }

            //Return to Profile view activity the edited user
            returnIntent.putExtra("perfil_editado", perfilEdited.toJson().toString())
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }

        buttonDelete.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                DeleteUser(perfil.idPerfil!!, perfil.idUtilizador!!)
                GlobalScope.launch(Dispatchers.Main) {
                    val returnIntent = Intent()
                    setResult(Activity.RESULT_CANCELED, returnIntent)
                    finish()
                }
            }
        }

        goBack.setOnClickListener {
            finish()
        }
    }
    fun DeleteUser(idPerfil: Int, idUtilizador: Int){

        val client = OkHttpClient()

        val url = getURL()

        val request1 = Request.Builder().url("$url/perfil/$idPerfil")
            .delete()
            .build()

        client.newCall(request1).execute().use { response ->
            println(response.body!!.string())
        }

        val request2 = Request.Builder().url("$url/user/$idUtilizador")
            .delete()
            .build()

        client.newCall(request2).execute().use { response ->
            println(response.body!!.string())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun User(perfil: Perfil, userEmail : String) {
        val editTextNome = findViewById<EditText>(R.id.textViewEditarNome)
        val editTextEmail = findViewById<EditText>(R.id.textViewEditarEmail)
        val editTextNin = findViewById<EditText>(R.id.textViewEditarNin)
        val editTextMorada = findViewById<EditText>(R.id.textViewEditarEndereco)
        val editTextCodigoPostal = findViewById<EditText>(R.id.textViewEditarCodigoPostal)
        val editTextDataNascimento = findViewById<EditText>(R.id.textViewEditarDataNascimento)
        val editTextNumeroTelefone = findViewById<EditText>(R.id.textViewEditarNumeroTelefone)

        val dateFormated : String

        if(perfil.dtNasc!!.length == 24){
            dateFormated = DateFormaterApi(perfil.dtNasc!!)
        }else {
            dateFormated = DateFormaterIngToPt(perfil.dtNasc!!)!!
        }


        editTextNome.setText(perfil.nome)
        editTextEmail.setText(userEmail)
        editTextNin.setText(perfil.nin.toString())
        editTextMorada.setText(perfil.morada)
        editTextCodigoPostal.setText(perfil.codigoPostal)
        editTextDataNascimento.setText(dateFormated)
        editTextNumeroTelefone.setText(perfil.contacto.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun SaveChanges(perfil: Perfil) : Perfil{
        val editTextNome = findViewById<EditText>(R.id.textViewEditarNome)
        val editTextNin = findViewById<EditText>(R.id.textViewEditarNin)
        val editTextMorada = findViewById<EditText>(R.id.textViewEditarEndereco)
        val editTextCodigoPostal = findViewById<EditText>(R.id.textViewEditarCodigoPostal)
        val editTextDataNascimento = findViewById<EditText>(R.id.textViewEditarDataNascimento)
        val editTextNumeroTelefone = findViewById<EditText>(R.id.textViewEditarNumeroTelefone)


        return Perfil(perfil.idPerfil, editTextNome.text.toString(), perfil.imagem,
               editTextDataNascimento.text.toString(), perfil.genero,
               editTextNumeroTelefone.text.toString().toInt(), editTextMorada.text.toString(),
               editTextCodigoPostal.text.toString(), editTextNin.text.toString().toInt(),
               perfil.totalAtivParticip.toString().toInt(), perfil.idEquipa.toString().toInt(), perfil.idUtilizador.toString().toInt())
    }

    fun SaveEmailChage(user : Utilizador){
        val client = OkHttpClient()

        val url = getURL()

        val editTextEmail = findViewById<EditText>(R.id.textViewEditarEmail)

        val userUpdated = Utilizador(user.id_utilizador, editTextEmail.text.toString(), user.palavra_pass, user.id_tipo)

        val requestBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            userUpdated.toJson().toString()
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



    fun GetUser(userId : Int) : Utilizador {
        val client = OkHttpClient()

        val user : Utilizador

        val url = getURL()

        val request = Request.Builder().url("$url/user/$userId")
            .get()
            .build()

        client.newCall(request).execute().use { response ->
            val jsStr = (response.body!!.string())
            user = Utilizador.fromJson(jsStr, 0)
        }
        return user
    }
}