package com.mindoverflow.scoutshub.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.adapter.CustomAdapter
import com.mindoverflow.scoutshub.models.Atividade
import com.mindoverflow.scoutshub.models.Perfil
import org.json.JSONObject

class ProfileViewActivity : AppCompatActivity() {

    lateinit var perfil : Perfil

    lateinit var goBack : ImageView
    lateinit var goAhead : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {

        // Get the information of the user that has been searched
        val message = intent.getStringExtra("User")

        // I won´t be using an if here, it´s just for testing
        if (message!!.toLowerCase() == "jorge" || message!!.toLowerCase() == "miguel" || message!!.toLowerCase() == "joana" || message!!.toLowerCase() == "maria" || message!!.toLowerCase() == "zé" || message!!.toLowerCase() == "tatiana") {

            super.onCreate(savedInstanceState)

            setContentView(R.layout.activity_profile_view)

            supportActionBar!!.hide()

            val image0 = R.drawable.bryce_canyon
            val image1 = R.drawable.cathedral_rock
            val image2 = R.drawable.death_valley
            val image3 = R.drawable.fitzgerald_marine_reserve
            val image4 = R.drawable.grand_canyon

            val images = arrayListOf(image0, image1, image2, image3, image4)

            val nomeUtilizador = findViewById<TextView>(R.id.textViewPerfilEscuteiroNome)
            val dtNasc = findViewById<TextView>(R.id.textViewPerfilEscuteiroDataNasc)
            val genero = findViewById<TextView>(R.id.textViewPerfilEscuteiroGenero)
            val contacto = findViewById<TextView>(R.id.textViewPerfilEscuteiroContacto)
            val morada = findViewById<TextView>(R.id.textViewPerfilEscuteiroMorada)
            val codigoPostal = findViewById<TextView>(R.id.textViewPerfilEscuteiroCodigoPostal)
            val nin = findViewById<TextView>(R.id.textViewPerfilEscuteiroNin)
            val totalAtivParticip =
                findViewById<TextView>(R.id.textViewPerfilEscuteiroTotalAtivParticip)


            //Dummy data
            nomeUtilizador.text = "jorge"
            dtNasc.text = "2/2/2017"
            genero.text = "masculino"
            contacto.text = "919923205"
            morada.text = "Praceta da Madalena Fonseca"
            codigoPostal.text = "5456-4543"
            nin.text = "3423432"
            totalAtivParticip.text = "24"

            //To do - Get the information of the inserted user in the search view


            //Adding some dummy data
            perfil = Perfil(
                2,
                nomeUtilizador.text.toString(),
                //"dfgdfgdfghr",
                dtNasc.text.toString(),
                genero.text.toString(),
                contacto.text.toString().toInt(),
                morada.text.toString(),
                codigoPostal.text.toString(),
                nin.text.toString().toInt(),
                totalAtivParticip.text.toString().toInt(),
                5
            )

            /*
            nomeUtilizador.text = perfil.nome
            dtNasc.text = perfil.dtNasc
            genero.text = perfil.genero
            contacto.text = perfil.contacto.toString()
            morada.text = perfil.morada
            codigoPostal.text = perfil.codigoPostal
            nin.text = perfil.nin.toString()
            totalAtivParticip.text = perfil.totalAtivParticip.toString()*/


            //getting recyclerview from xml
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewProfile)

            //adding a layout manager
            recyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


            //crating an arraylist to store users using the data class user
            val users = ArrayList<Atividade>()

            //adding some dummy data to the list
            users.add(
                Atividade(
                    1, "acampamento", "canoagem",
                    "divercao", 10, "Braga", "Braga",
                    "Miami", "1717171717131517",
                    "www.coinbase.com", "03/09/2021", "03/10/2095"
                )
            )

            users.add(
                Atividade(
                    2, "dormir", "canoagem",
                    "divercao", 10, "Braga", "Braga",
                    "Miami", "1717171717131517",
                    "www.coinbase.com", "03/09/2021", "03/10/2095"
                )
            )

            users.add(
                Atividade(
                    3, "saltar", "canoagem",
                    "divercao", 10, "Braga", "Braga",
                    "Miami", "1717171717131517",
                    "www.coinbase.com", "03/09/2021", "03/10/2095"
                )
            )


            //creating our adapter
            val adapter = CustomAdapter(users)

            //now adding the adapter to recyclerview
            recyclerView.adapter = adapter

            goBack = findViewById<ImageView>(R.id.imageViewVoltarAtras)
            goAhead = findViewById<ImageView>(R.id.hamburguerButton)

            //Going back to the fragment "PerfisFragmentAdministrador"
            goBack.setOnClickListener {
                finish()
            }


            //Going foward to the activity "ProfileViewActivity"
            goAhead.setOnClickListener {
                //To do - Get the information of the inserted user in the search view
                intent = Intent(this@ProfileViewActivity, EditingProfile::class.java)
                intent.putExtra("user_data", perfil.toJson().toString())
                startActivityForResult(intent, 1001)
            }
        } else {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001) {
            if (resultCode == RESULT_OK){
                val jsonString = data?.getStringExtra("perfil_editado")

                val jsonObject = JSONObject(jsonString)
                val perfilFromJson = Perfil.fromJson(jsonObject)

                val nomeUtilizador = findViewById<TextView>(R.id.textViewPerfilEscuteiroNome)
                val dtNasc = findViewById<TextView>(R.id.textViewPerfilEscuteiroDataNasc)
                val genero = findViewById<TextView>(R.id.textViewPerfilEscuteiroGenero)
                val contacto = findViewById<TextView>(R.id.textViewPerfilEscuteiroContacto)
                val morada = findViewById<TextView>(R.id.textViewPerfilEscuteiroMorada)
                val codigoPostal = findViewById<TextView>(R.id.textViewPerfilEscuteiroCodigoPostal)
                val nin = findViewById<TextView>(R.id.textViewPerfilEscuteiroNin)
                val totalAtivParticip = findViewById<TextView>(R.id.textViewPerfilEscuteiroTotalAtivParticip)

                nomeUtilizador.text = perfilFromJson.nome
                dtNasc.text = perfilFromJson.dtNasc
                genero.text = perfilFromJson.genero
                contacto.text = perfilFromJson.contacto.toString()
                morada.text = perfilFromJson.morada
                codigoPostal.text = perfilFromJson.codigoPostal
                nin.text = perfilFromJson.nin.toString()
                totalAtivParticip.text = perfilFromJson.totalAtivParticip.toString()
            }
        }
    }
}