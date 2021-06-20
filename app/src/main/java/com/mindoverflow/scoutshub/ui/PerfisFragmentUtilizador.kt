package com.mindoverflow.scoutshub.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mindoverflow.scoutshub.GetURL.Companion.URL
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.adapter.CustomAdapter
import com.mindoverflow.scoutshub.models.Atividade
import com.mindoverflow.scoutshub.models.Perfil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject


class PerfisFragmentUtilizador : Fragment() {

    //var perfis : MutableList<Perfil> = arrayListOf()
    //lateinit var adapter : PerfilAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_perfil_utilizador, container, false)


        val image0 = R.drawable.bryce_canyon
        val image1 = R.drawable.cathedral_rock
        val image2 = R.drawable.death_valley
        val image3 = R.drawable.fitzgerald_marine_reserve
        val image4 = R.drawable.grand_canyon

        val images = arrayListOf(image0, image1, image2, image3, image4)

        GlobalScope.launch(Dispatchers.IO) {

            //In the future pass the id as a parameter
            val userFromJson = GetUser()

            GlobalScope.launch(Dispatchers.Main) {
                InsertDataIntoUser(userFromJson, rootView)
            }
        }

        //getting recyclerview from xml
        val recyclerView = rootView!!.findViewById(R.id.recyclerViewProfile) as RecyclerView

        //adding a layoutmanager
        //val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


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

        return rootView
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

    private fun InsertDataIntoUser(userFromJson: Perfil, view: View){

        val nomeUtilizador = view.findViewById<TextView>(R.id.textViewPerfilUtilizadorNome)
        val dtNasc = view.findViewById<TextView>(R.id.textViewPerfilUtilizadorDataNasc)
        val genero = view.findViewById<TextView>(R.id.textViewPerfilUtilizadorGenero)
        val contacto = view.findViewById<TextView>(R.id.textViewPerfilUtilizadorContacto)
        val morada = view.findViewById<TextView>(R.id.textViewPerfilUtilizadorMorada)
        val codigoPostal = view.findViewById<TextView>(R.id.textViewPerfilUtilizadorCodigoPostal)
        val nin = view.findViewById<TextView>(R.id.textViewPerfilUtilizadorNin)
        val totalAtivParticip = view.findViewById<TextView>(R.id.textViewPerfilUtilizadorTotalAtivParticip)

        nomeUtilizador.text = userFromJson.nome
        dtNasc.text = userFromJson.dtNasc
        genero.text = userFromJson.genero
        contacto.text = userFromJson.contacto.toString()
        morada.text = userFromJson.morada
        codigoPostal.text = userFromJson.codigoPostal
        nin.text = userFromJson.nin.toString()
        totalAtivParticip.text = userFromJson.totalAtivParticip.toString()
    }

    private fun GetUser() : Perfil{
        val id = 1

        val url = URL()

        val client = OkHttpClient()

        val request = Request.Builder().url("$url/perfil/$id").get().build()

        client.newCall(request).execute().use { response ->
            val jsStr = (response.body!!.string())

            return Perfil.fromJson(jsStr, id)
        }
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //getting recyclerview from xml
        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)


        //crating an arraylist to store users using the data class user
        val users = ArrayList<Atividade>()

        //adding some dummy data to the list
        users.add(Atividade(1, "acampamento", "canoagem", "divercao", 10,  ))
        users.add(User("Ramiz Khan", "Ranchi Jharkhand"))
        users.add(User("Faiz Khan", "Ranchi Jharkhand"))
        users.add(User("Yashar Khan", "Ranchi Jharkhand"))

        //creating our adapter
        val adapter = CustomAdapter(users)

        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter
    }*/
}