package com.mindoverflow.scoutshub.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mindoverflow.scoutshub.GetURL.Companion.URL
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.SavedUserData
import com.mindoverflow.scoutshub.adapter.RecycleViewAdapter
import com.mindoverflow.scoutshub.models.Perfil
import com.mindoverflow.scoutshub.models.RecyclerItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.*


class SearchBarActivity : AppCompatActivity() {


    private var adapter: RecycleViewAdapter? = null

    private var recyclerList: MutableList<RecyclerItem>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_bar)

        GlobalScope.launch(Dispatchers.IO) {
            val usersFromJson: ArrayList<Perfil> = GetUsers()
            GlobalScope.launch(Dispatchers.Main) {
                fillUsersList(usersFromJson)
                println(fillUsersList(usersFromJson))
                setUpRecyclerView()
            }
        }


        supportActionBar!!.hide()
    }

    override fun onRestart() {
        super.onRestart()

        finish()
    }

    private fun GetUsers() : ArrayList<Perfil> {

        val users = ArrayList<Perfil>()
        val userDontShow = SavedUserData.id_utilizador

        val client = OkHttpClient()

        val url = URL()

        val request =
            Request.Builder().url("$url/perfil/")
                .get()
                .build()

        client.newCall(request).execute().use { response ->

            val jsStr = (response.body!!.string())
            val jsonArray = JSONObject(jsStr).getJSONArray("perfis")

            for (index in 0 until jsonArray.length()) {
                val user = Perfil.fromJson(jsStr, index)
                if (user.idUtilizador != userDontShow)
                users.add(user)
            }
        }

        return users
    }

    //Adding some dummy data to the recycler List
    private fun fillUsersList(usersFromJson : ArrayList<Perfil>) {
        recyclerList = ArrayList()

        val image0 = R.drawable.bryce_canyon
        val image1 = R.drawable.cathedral_rock

        // Deu um erro de index 2 : size 3 porque me faltava uma imagem
        val images = arrayListOf(image0, image1)

        for (user in usersFromJson) {
            (recyclerList as ArrayList<RecyclerItem>).add(RecyclerItem(image1, user.nome!!))
        }
    }

    private fun setUpRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        //sets the layout of the RecyclerView to be vertical
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        adapter = RecycleViewAdapter(recyclerList!!)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val searchView = findViewById<android.widget.SearchView>(R.id.searchView)


        //replace the action button of the keyboard with a more appropriate one
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE


        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if(query != null && query.isNotBlank()) {

                    GlobalScope.launch(Dispatchers.IO) {
                        //The function UserNameVerification is making a verification of the validity of the insert user (query) on the search view
                        val userToView = UserNameVerification(query)

                        GlobalScope.launch(Dispatchers.Main) {
                            //if the user inserted into the search view exists then pass its id as an intent to the "ProfileViewActivity"
                            if(userToView != null){
                                //The id user inserted in the search view
                                val intent = Intent (this@SearchBarActivity, ProfileViewActivity::class.java)
                                intent.putExtra("User" , userToView.toJson().toString())
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@SearchBarActivity, "O utilizador inserido é invalido", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
                //The query isn´t submitted
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                //Filtering the text inserted on the search view
                adapter!!.filter.filter(newText)

                return false
            }
        })
        return true
    }

    private fun UserNameVerification(user : String) : Perfil? {
        //val users = ArrayList<Perfil>()

        var userToReturn : Perfil? = null

        val client = OkHttpClient()

        val ip = URL()

        val request =
            Request.Builder().url("$ip/perfil/")
                .get()
                .build()

        client.newCall(request).execute().use { response ->

            val jsStr = (response.body!!.string())
            val jsonArray = JSONObject(jsStr).getJSONArray("perfis")

            for (index in 0 until jsonArray.length()) {
                val userCompare = Perfil.fromJson(jsStr, index)

                if(user.equals(userCompare.nome!!, ignoreCase = true)){
                    userToReturn = userCompare
                }
            }
        }
        return userToReturn
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val searchView = findViewById<android.widget.SearchView>(R.id.searchView)

        //resets the search view and clears the focus on the search View
        searchView.setQuery("", false)
        searchView.isIconified = true
        searchView.clearFocus()
    }
}

