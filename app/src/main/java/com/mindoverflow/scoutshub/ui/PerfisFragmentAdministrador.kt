package com.mindoverflow.scoutshub.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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


class PerfisFragmentAdministrador : Fragment() {

    //var perfis : MutableList<Perfil> = arrayListOf()
    //lateinit var adapter : PerfilAdapter

    lateinit var rootView : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.fragment_perfil_administrador, container, false)

        val image0 = R.drawable.bryce_canyon
        val image1 = R.drawable.cathedral_rock
        val image2 = R.drawable.death_valley
        val image3 = R.drawable.fitzgerald_marine_reserve
        val image4 = R.drawable.grand_canyon

        val images = arrayListOf(image0, image1, image2, image3, image4)

        //val perfil = Perfil(null, "Jorge", "30/5/2000", "M", 919923205, "Praceta Madalena Fonseca 120 Rés do chão", "9560-010", 123456789, 6, 5)


        //getting recyclerview from xml
        val recyclerView = rootView.findViewById(R.id.recyclerViewProfile) as RecyclerView

        //adding a layoutmanager
        //val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        //crating an arraylist to store users using the data class user
        val users = ArrayList<Atividade>()

        //adding some dummy data to the list
        users.add(Atividade(1, "acampamento", "canoagem",
            "divercao", 10, "Braga", "Braga",
            "Miami", "1717171717131517",
            "www.coinbase.com", "03/09/2021", "03/10/2095" ))

        users.add(Atividade(2, "dormir", "canoagem",
            "divercao", 10, "Braga", "Braga",
            "Miami", "1717171717131517",
            "www.coinbase.com", "03/09/2021", "03/10/2095" ))

        users.add(Atividade(3, "saltar", "canoagem",
            "divercao", 10, "Braga", "Braga",
            "Miami", "1717171717131517",
            "www.coinbase.com", "03/09/2021", "03/10/2095" ))


        //creating our adapter
        val adapter = CustomAdapter(users)

        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter

        //if the administrador clicks the search bar go to a certain activity

        val searchImage: ImageView = rootView.findViewById(R.id.imageViewSearch)
        val searchText: TextView = rootView.findViewById(R.id.textViewSearch)

        searchImage.setOnClickListener{
            val intent = Intent(activity, SearchBarActivity::class.java)
            startActivity(intent)
        }

        searchText.setOnClickListener{
            val intent = Intent(activity, SearchBarActivity::class.java)
            startActivity(intent)
        }


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nomeUtilizador = rootView.findViewById<TextView>(R.id.textViewPerfilAdminNome)
        val dtNasc = rootView.findViewById<TextView>(R.id.textViewPerfilAdminDataNasc)
        val genero = rootView.findViewById<TextView>(R.id.textViewPerfilAdminGenero)
        val contacto = rootView.findViewById<TextView>(R.id.textViewPerfilAdminContacto)
        val morada = rootView.findViewById<TextView>(R.id.textViewPerfilAdminMorada)
        val codigoPostal = rootView.findViewById<TextView>(R.id.textViewPerfilAdminCodigoPostal)
        val nin = rootView.findViewById<TextView>(R.id.textViewPerfilAdminNin)
        val totalAtivParticip = rootView.findViewById<TextView>(R.id.textViewPerfilAdminTotalAtivParticip)


        GlobalScope.launch(Dispatchers.IO) {

            val id = 1

            val client = OkHttpClient()

            val request = Request.Builder().url("http://mindoverflow.amipca.xyz:60000/perfil/$id").get().build()

            client.newCall(request).execute().use { response ->
                val jsStr = (response.body!!.string())

                //val jsonArray = JSONObject(jsStr).getJSONArray("perfis")
                //val jsonObject = JSONObject(jsonArray[0].toString())

                val perfilFromJson = Perfil.fromJson(jsStr, id-1, "get_json_array_by_id")


                GlobalScope.launch(Dispatchers.Main) {
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
        val searchImage: ImageView = view.findViewById(R.id.imageViewSearch)
        val searchText: TextView = view.findViewById(R.id.textViewSearch)

        searchImage.setOnClickListener{
            val intent = Intent(activity, SearchBarActivity::class.java)
            startActivity(intent)
        }

        searchText.setOnClickListener{
            val intent = Intent(activity, SearchBarActivity::class.java)
            startActivity(intent)
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
