package com.mindoverflow.scoutshub.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bit.linux.tinyspacex.Helpers.DateFormaterApi
import bit.linux.tinyspacex.Helpers.DateFormaterIngToPt
import bit.linux.tinyspacex.Helpers.DateFormaterPtToIng
import com.mindoverflow.scoutshub.GetURL.Companion.URL
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.adapter.CustomAdapter
import com.mindoverflow.scoutshub.models.Atividade
import com.mindoverflow.scoutshub.models.Perfil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject


class ProfileViewActivity : AppCompatActivity() {

    lateinit var perfil : Perfil

    lateinit var goBack : ImageView
    lateinit var goAhead : ImageView

    lateinit var user : Perfil

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile_view)

        supportActionBar!!.hide()

        // Get the information of the user that has been searched
        val jsonString = intent.getStringExtra("User")
        println(jsonString)

        //jsonString can´t be null or else we will be inserting empty data on the user
        if(jsonString != null){
            user = Perfil.fromJson(jsonString, null)
            insertingDataIntoUserBeforePut(user)
        }

        //getting recyclerview from xml
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewProfile)

        //adding a layout manager
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        //crating an arraylist to store users using the data class user
        var atividades: ArrayList<Atividade>

        //Adding activities to the recycler view
        GlobalScope.launch(Dispatchers.IO) {
            val arrayTodasAtividades =  GettingAllActivities()
            atividades = AddingActivities(arrayTodasAtividades)

            GlobalScope.launch(Dispatchers.Main){
                //creating our adapter
                val adapter = CustomAdapter(atividades)
                //now adding the adapter to recyclerview
                recyclerView.adapter = adapter
            }
        }


        goBack = findViewById<ImageView>(R.id.imageViewVoltarAtras)
        goAhead = findViewById<ImageView>(R.id.hamburguerButton)


        //Going foward to the activity "EditingProfile"
        goAhead.setOnClickListener {
            //To do - Get the information of the inserted user in the search view
            intent = Intent(this@ProfileViewActivity, PerfilEditActivity::class.java)

            intent.putExtra("user_data", user.toJson().toString())
            startActivityForResult(intent, 1001)

        }

        //Going back to the activity "SearchBarActivity"
        goBack.setOnClickListener {
            finish()
        }

    }

    private fun AddingActivities(arrayTodasAtividades: ArrayList<Atividade>): ArrayList<Atividade> {

        val idUtilizador = 1
        val atividades = java.util.ArrayList<Atividade>()

        val url = URL()

        val client = OkHttpClient()

        for (index in 0 until arrayTodasAtividades.size){
            val request = Request.Builder().url("$url/participant/${arrayTodasAtividades[index].idAtividade}/utilizador/$idUtilizador")
                .get()
                .build()

            client.newCall(request).execute().use { response ->
                if(response.body!!.string() != "\"participante\": []"){
                    atividades.add(
                        arrayTodasAtividades[index]
                    )
                }
            }
        }
        return atividades
    }

    private fun GettingAllActivities() : ArrayList<Atividade>{

        val client = OkHttpClient()

        val atividades = ArrayList<Atividade>()

        val url = URL()

        val request = Request.Builder().url("$url/activities")
            .get()
            .build()

        client.newCall(request).execute().use { response ->
            val jsStr = (response.body!!.string())

            val jsonArray = JSONObject(jsStr).getJSONArray("activities")

            for (index in 0 until jsonArray.length()) {
                val atividade = Atividade.fromJson(jsStr, index)
                atividades.add(atividade)
            }
        }
        return atividades
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                //Gets the updated information of the user
                val jsonString = data?.getStringExtra("perfil_editado")

                val newData = Perfil.fromJson(jsonString, null)


                //this formats the date so we can update the user on the database
                newData.dtNasc = DateFormaterPtToIng(newData.dtNasc.toString())


                //Updates the information of the user into the database via put request
                GlobalScope.launch(Dispatchers.IO) {
                    val userUpdated = updatingData(newData)
                    println(userUpdated)
                    if (userUpdated != null) {
                        GlobalScope.launch(Dispatchers.Main) {
                            //registers the update made to the user on the app
                            val dataToInsert = Perfil.fromJson(userUpdated, null)
                            user = dataToInsert
                            insertingDataIntoUserAfterPut(dataToInsert)
                        }
                    }
                }
            }
        }
        return
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertingDataIntoUserBeforePut(user : Perfil) {

        val nomeUtilizador = findViewById<TextView>(R.id.textViewPerfilEscuteiroNome)
        val dtNasc = findViewById<TextView>(R.id.textViewPerfilEscuteiroDataNasc)
        val genero = findViewById<TextView>(R.id.textViewPerfilEscuteiroGenero)
        val contacto = findViewById<TextView>(R.id.textViewPerfilEscuteiroContacto)
        val morada = findViewById<TextView>(R.id.textViewPerfilEscuteiroMorada)
        val codigoPostal = findViewById<TextView>(R.id.textViewPerfilEscuteiroCodigoPostal)
        val nin = findViewById<TextView>(R.id.textViewPerfilEscuteiroNin)
        val totalAtivParticip = findViewById<TextView>(R.id.textViewPerfilEscuteiroTotalAtivParticip)

        println("teste1")
        val dateFormated = DateFormaterApi(user.dtNasc.toString())

        println("teste2")

        nomeUtilizador.text = user.nome
        dtNasc.text = dateFormated
        genero.text = user.genero
        contacto.text = user.contacto.toString()
        morada.text = user.morada
        codigoPostal.text = user.codigoPostal
        nin.text = user.nin.toString()
        totalAtivParticip.text = user.totalAtivParticip.toString()


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertingDataIntoUserAfterPut(user : Perfil) {
        val nomeUtilizador = findViewById<TextView>(R.id.textViewPerfilEscuteiroNome)
        val dtNasc = findViewById<TextView>(R.id.textViewPerfilEscuteiroDataNasc)
        val genero = findViewById<TextView>(R.id.textViewPerfilEscuteiroGenero)
        val contacto = findViewById<TextView>(R.id.textViewPerfilEscuteiroContacto)
        val morada = findViewById<TextView>(R.id.textViewPerfilEscuteiroMorada)
        val codigoPostal = findViewById<TextView>(R.id.textViewPerfilEscuteiroCodigoPostal)
        val nin = findViewById<TextView>(R.id.textViewPerfilEscuteiroNin)
        val totalAtivParticip = findViewById<TextView>(R.id.textViewPerfilEscuteiroTotalAtivParticip)


        val dateFormated = DateFormaterIngToPt(user.dtNasc.toString())

        nomeUtilizador.text = user.nome
        dtNasc.text = dateFormated
        genero.text = user.genero
        contacto.text = user.contacto.toString()
        morada.text = user.morada
        codigoPostal.text = user.codigoPostal
        nin.text = user.nin.toString()
        totalAtivParticip.text = user.totalAtivParticip.toString()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updatingData(newData : Perfil) : String? {

        var toReturn : String? = null

        //val userJson = user.toJson()
        //GlobalScope.launch(Dispatchers.IO) {
        val client = OkHttpClient()

        val url = URL()
        //val dateFormated = DateFormaterIng(newData.dtNasc!!)

        val requestBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            newData.toJson().toString()
        )

        val request =
            Request.Builder()
                .url("$url/perfil/user/${user.idPerfil}")
                .put(requestBody)
                .build()

        client.newCall(request).execute().use { response ->

            val response = (response.body!!.string())

            if (response == "{\"message\":\"Perfil updated successfully\"}") {
                toReturn = newData.toJson().toString()
            }
        }
        println("teste5")
        return toReturn
    }
}