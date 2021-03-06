package com.mindoverflow.scoutshub.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import bit.linux.tinyspacex.Helpers.getURL
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.SavedUserData
import com.mindoverflow.scoutshub.SplashScreen
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


class PerfisFragmentUser : Fragment() {

    //var perfis : MutableList<Perfil> = arrayListOf()
    //lateinit var adapter : PerfilAdapter

    var perfil : Perfil? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_perfil_user, container, false)

        val searchImage: ImageView = rootView.findViewById(R.id.imageViewSearch)
        val searchText: TextView = rootView.findViewById(R.id.textViewSearch)

        //Definir a searchImage e do searchText como Gone
        if(SavedUserData.id_tipo != 1){
            searchImage.visibility = View.GONE
            searchText.visibility = View.GONE
        }

        GlobalScope.launch(Dispatchers.IO) {

            //Get the user by Id, using a get request
            val userId = SavedUserData.id_utilizador
            perfil = GetUserPerfil(userId!!)

            GlobalScope.launch(Dispatchers.Main) {
                InsertDataIntoUser(perfil!!, rootView)
            }
        }

        //getting recyclerview from xml
        val recyclerView = rootView.findViewById(R.id.recyclerViewProfileUser) as RecyclerView

        //adding a layoutmanager
        //val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)



        //Adding activities to the recycler view
        GlobalScope.launch(Dispatchers.IO) {
            val userId = SavedUserData.id_utilizador

            val arrayTodasAtividades =  GettingAllActivities()
            val atividades = AddingActivities(arrayTodasAtividades, userId!!)


            GlobalScope.launch(Dispatchers.Main){

                val activityTitle = rootView.findViewById<TextView>(R.id.textViewAtv1)

                if(atividades.isNullOrEmpty()){
                    activityTitle.visibility = View.GONE
                }

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
        val editPerfil : ImageView = view.findViewById(R.id.edit_user)
        val logOut : ImageView = view.findViewById(R.id.LogOut)


        searchImage.setOnClickListener{
            val intent = Intent(activity, SearchBarActivity::class.java)
            startActivity(intent)
        }

        searchText.setOnClickListener{
            val intent = Intent(activity, SearchBarActivity::class.java)

            startActivity(intent)
        }

        editPerfil.setOnClickListener {
            val intent = Intent(activity, ProfileUserEditActivity::class.java)
            intent.putExtra("user_data", perfil!!.toJson().toString())

            startActivityForResult(intent, 1001)
        }

        logOut.setOnClickListener {
            val intent = Intent(activity, SplashScreen::class.java)

            startActivity(intent)
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
                            val perfilFromJson = Perfil.fromJson(userUpdated, null)
                            perfilFromJson.dtNasc = DateFormaterIngToPt(perfilFromJson.dtNasc.toString())
                            perfil = perfilFromJson
                            insertingDataIntoUserAfterPut(perfil!!)
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

        val url = getURL()
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


            toReturn = newData.toJson().toString()

        }
        return toReturn
    }

    //Ads the activities correspondent to the user
    private fun AddingActivities(arrayTodasAtividades: ArrayList<Atividade>, userId : Int): ArrayList<Atividade> {

        val atividades = java.util.ArrayList<Atividade>()

        val url = getURL()

        val client = OkHttpClient()

        for (index in 0 until arrayTodasAtividades.size){
            val request = Request.Builder().url("$url/participant/atividade/${arrayTodasAtividades[index].idAtividade}/utilizador/$userId")
                .get()
                .build()

            client.newCall(request).execute().use { response ->

                val response = (response.body!!.string())

                if(response != "{\"participante\":[]}"){
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

        val url = getURL()

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

    private fun GetUserPerfil(userId : Int) : Perfil{

        val client = OkHttpClient()
        val url = getURL()

        val request = Request.Builder().url("$url/perfil/user/$userId")
            .get()
            .build()

        client.newCall(request).execute().use { response ->
            val jsStr = (response.body!!.string())

            return Perfil.fromJson(jsStr, 0)
        }
    }
}
