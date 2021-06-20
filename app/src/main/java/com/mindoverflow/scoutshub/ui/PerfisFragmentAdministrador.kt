package com.mindoverflow.scoutshub.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bit.linux.tinyspacex.Helpers
import bit.linux.tinyspacex.Helpers.DateFormaterApi
import bit.linux.tinyspacex.Helpers.DateFormaterIngToPt
import bit.linux.tinyspacex.Helpers.getImageUrl
import com.mindoverflow.scoutshub.GetURL.Companion.URL
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.SavedUserData
import com.mindoverflow.scoutshub.adapter.CustomAdapter
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


class PerfisFragmentAdministrador : Fragment() {

    //var perfis : MutableList<Perfil> = arrayListOf()
    //lateinit var adapter : PerfilAdapter

    lateinit var user : Perfil

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_perfil_administrador, container, false)

        val userId = SavedUserData.id_utilizador.toString()

        GlobalScope.launch(Dispatchers.IO) {
            //Get the user by Id, using a get request
            user = GetUser(userId)

            GlobalScope.launch(Dispatchers.Main) {
                InsertDataIntoUser(user, rootView)
            }
        }

        //getting recyclerview from xml
        val recyclerView = rootView.findViewById(R.id.recyclerViewProfile) as RecyclerView

        //adding a layoutmanager
        //val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        var atividades: ArrayList<Atividade>

        //Adding activities to the recycler view
        GlobalScope.launch(Dispatchers.IO) {
            val arrayTodasAtividades =  GettingAllActivities()
            atividades = AddingActivities(arrayTodasAtividades, userId)

            GlobalScope.launch(Dispatchers.Main){
                //creating our adapter
                val adapter = CustomAdapter(atividades)
                //now adding the adapter to recyclerview
                recyclerView.adapter = adapter
            }
        }

        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val searchImage: ImageView = view.findViewById(R.id.imageViewSearch)
        val searchText: TextView = view.findViewById(R.id.textViewSearch)
        val editPerfil : Button = view.findViewById(R.id.buttonEditAdm1)

        searchImage.setOnClickListener{
            val intent = Intent(activity, SearchBarActivity::class.java)
            startActivity(intent)
        }

        searchText.setOnClickListener{
            val intent = Intent(activity, SearchBarActivity::class.java)

            startActivity(intent)
        }
        editPerfil.setOnClickListener {
            val intent = Intent(activity, ProfileAdmEditActivity::class.java)
            intent.putExtra("user_data", user.toJson().toString())

            startActivityForResult(intent, 1001)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1001) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                //Gets the updated information of the user
                val jsonString = data?.getStringExtra("perfil_editado")

                val newData = Perfil.fromJson(jsonString, null)


                //this formats the date so we can update the user on the database
                newData.dtNasc = Helpers.DateFormaterPtToIng(newData.dtNasc.toString())


                //Updates the information of the user into the database via put request
                GlobalScope.launch(Dispatchers.IO) {
                    val userUpdated = updatingData(newData)
                    if (userUpdated != null) {
                        GlobalScope.launch(Dispatchers.Main) {
                            //registers the update made to the user on the app
                            val userFromJson = Perfil.fromJson(userUpdated, null)
                            userFromJson.dtNasc = DateFormaterIngToPt(userFromJson.dtNasc.toString())
                            user = userFromJson
                            insertingDataIntoUserAfterPut(user)
                        }
                    }
                }
            }
        }
        return
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertingDataIntoUserAfterPut(user : Perfil) {

        val nomeUtilizador = activity?.findViewById<TextView>(R.id.textViewPerfilAdminNome)
        val dtNasc = activity?.findViewById<TextView>(R.id.textViewPerfilAdminDataNasc)
        val genero = activity?.findViewById<TextView>(R.id.textViewPerfilAdminGenero)
        val contacto = activity?.findViewById<TextView>(R.id.textViewPerfilAdminContacto)
        val morada = activity?.findViewById<TextView>(R.id.textViewPerfilAdminMorada)
        val codigoPostal = activity?.findViewById<TextView>(R.id.textViewPerfilAdminCodigoPostal)
        val nin = activity?.findViewById<TextView>(R.id.textViewPerfilAdminNin)
        val totalAtivParticip = activity?.findViewById<TextView>(R.id.textViewPerfilAdminTotalAtivParticip)


        nomeUtilizador!!.text = user.nome
        dtNasc!!.text = user.dtNasc
        genero!!.text = user.genero
        contacto!!.text = user.contacto.toString()
        morada!!.text = user.morada
        codigoPostal!!.text = user.codigoPostal
        nin!!.text = user.nin.toString()
        totalAtivParticip!!.text = user.totalAtivParticip.toString()
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
                .url("$url/perfil/user/${newData.idUtilizador}")
                .put(requestBody)
                .build()

        client.newCall(request).execute().use { response ->

            val response = (response.body!!.string())

            if (response == "{\"message\":\"Perfil updated successfully\"}") {
                toReturn = newData.toJson().toString()
            }
        }

        return toReturn
    }

    //Ads the activities correspondent to the user
    private fun AddingActivities(arrayTodasAtividades: ArrayList<Atividade>, userId : String): ArrayList<Atividade> {

        val atividades = java.util.ArrayList<Atividade>()

        val url = URL()

        val client = OkHttpClient()

        for (index in 0 until arrayTodasAtividades.size){
            val request = Request.Builder().url("$url/participant/${arrayTodasAtividades[index].idAtividade}/utilizador/$userId")
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
    private fun InsertDataIntoUser(user: Perfil, view: View){

        //Text Views
        val nomeUtilizador = view.findViewById<TextView>(R.id.textViewPerfilAdminNome)
        val dtNasc = view.findViewById<TextView>(R.id.textViewPerfilAdminDataNasc)
        val genero = view.findViewById<TextView>(R.id.textViewPerfilAdminGenero)
        val contacto = view.findViewById<TextView>(R.id.textViewPerfilAdminContacto)
        val morada = view.findViewById<TextView>(R.id.textViewPerfilAdminMorada)
        val codigoPostal = view.findViewById<TextView>(R.id.textViewPerfilAdminCodigoPostal)
        val nin = view.findViewById<TextView>(R.id.textViewPerfilAdminNin)
        val totalAtivParticip = view.findViewById<TextView>(R.id.textViewPerfilAdminTotalAtivParticip)

        //Image View
        val image = view.findViewById<ImageView>(R.id.imageViewPesquisa1)

        val dateFormated = DateFormaterApi(user.dtNasc.toString())

        nomeUtilizador.text = user.nome
        dtNasc.text = dateFormated
        genero.text = user.genero
        contacto.text = user.contacto.toString()
        morada.text = user.morada
        codigoPostal.text = user.codigoPostal
        nin.text = user.nin.toString()
        totalAtivParticip.text = user.totalAtivParticip.toString()

        getImageUrl(user.imagem!!, image)
    }

    private fun GetUser(userId : String) : Perfil{

        val client = OkHttpClient()
        val url = URL()

        val request = Request.Builder().url("$url/perfil/user/$userId")
            .get()
            .build()

        client.newCall(request).execute().use { response ->
            val jsStr = (response.body!!.string())
            println(jsStr)

            return Perfil.fromJson(jsStr, 0)
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
