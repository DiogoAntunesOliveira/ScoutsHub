package com.mindoverflow.scoutshub.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bit.linux.tinyspacex.Helpers.getURL
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

        supportActionBar!!.hide()

        GlobalScope.launch(Dispatchers.IO) {
            val users: ArrayList<Perfil> = GetUsers()
            GlobalScope.launch(Dispatchers.Main) {

                fillUsersList(users)
                setUpRecyclerView()
            }
        }
    }

    //When this activity restarts,
    //its going back to the Perfis Fragment user
    override fun onRestart() {
        super.onRestart()

        finish()
    }

    //Getting all of the users in the data base with the exception of the user using the search view
    private fun GetUsers() : ArrayList<Perfil> {

        val users = ArrayList<Perfil>()
        val userDontShow = SavedUserData.id_utilizador

        val client = OkHttpClient()

        val url = getURL()

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

    //Adding user data to the recycler view
    private fun fillUsersList(users : ArrayList<Perfil>) {
        recyclerList = ArrayList()

        //val image0 = R.drawable.bryce_canyon
        //val image1 = R.drawable.cathedral_rock

        // Deu um erro de index 2 : size 3 porque me faltava uma imagem
        //val images = arrayListOf(image0, image1)

        for (user in users) {
            (recyclerList as ArrayList<RecyclerItem>).add(RecyclerItem(user.imagem.toString(), user.nome!!))
        }
    }

    private fun setUpRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        //sets the size of the recycler view to be fixed
        recyclerView.setHasFixedSize(true)
        //sets the layout of the RecyclerView to be vertical
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        adapter = RecycleViewAdapter(recyclerList!!)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val searchView = findViewById<android.widget.SearchView>(R.id.searchView)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

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
                                val toast = Toast.makeText(this@SearchBarActivity, "O utilizador inserido é invalido", Toast.LENGTH_LONG)
                                toast.setGravity(Gravity.CENTER, 0, 0)
                                toast.show()
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

        val url = getURL()

        val request =
            Request.Builder().url("$url/perfil/")
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

