package com.mindoverflow.scoutshub.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mindoverflow.scoutshub.R
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
            val usersFromJson: ArrayList<Perfil> = UsersFromJson()
            println(UsersFromJson())
            GlobalScope.launch(Dispatchers.Main) {
                fillUsersList(usersFromJson)
                println(fillUsersList(usersFromJson))
                setUpRecyclerView()
            }
        }


        supportActionBar!!.hide()
    }

    private fun UsersFromJson() : ArrayList<Perfil> {

        val users = ArrayList<Perfil>()

        val client = OkHttpClient()

        val request =
            Request.Builder().url("http://mindoverflow.amipca.xyz:60000/perfil/")
                .get()
                .build()

        client.newCall(request).execute().use { response ->

            val jsStr = (response.body!!.string())
            val jsonArray = JSONObject(jsStr).getJSONArray("perfis")

            for (index in 0 until jsonArray.length()) {
                val user = Perfil.fromJson(jsStr, index, "get_json_array_by_id")
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
        val image2 = R.drawable.death_valley

        // Deu um erro de index 2 : size 3 porque me faltava uma imagem
        val images = arrayListOf(image0, image1, image2)

        for (user in usersFromJson) {
            (recyclerList as ArrayList<RecyclerItem>).add(RecyclerItem(images[user.idPerfil!! - 1], user.nome!!))
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
                    //To do a verification of the existence of the inserted name


                    //The query is submitted
                    val intent = Intent (this@SearchBarActivity, ProfileViewActivity::class.java)
                    intent.putExtra("User" , query)

                    startActivity(intent)

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
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val searchView = findViewById<android.widget.SearchView>(R.id.searchView)

        //resets the search view and clears the focus on the search View
        searchView.setQuery("", false)
        searchView.isIconified = true
        searchView.clearFocus()
    }
}

