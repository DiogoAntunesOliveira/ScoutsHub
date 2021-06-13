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
import com.mindoverflow.scoutshub.models.RecyclerItem
import java.util.*


class SearchBarActivity : AppCompatActivity() {


    private var adapter: RecycleViewAdapter? = null

    private var recyclerList: MutableList<RecyclerItem>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_bar)
        fillExampleList()
        setUpRecyclerView()

        supportActionBar!!.hide()
    }

    //Adding some dummy data to the recycler List
    private fun fillExampleList() {
        recyclerList = ArrayList()
        (recyclerList as ArrayList<RecyclerItem>).add(RecyclerItem(R.drawable.bryce_canyon, "Jorge"))
        (recyclerList as ArrayList<RecyclerItem>).add(RecyclerItem(R.drawable.cathedral_rock, "Miguel"))
        (recyclerList as ArrayList<RecyclerItem>).add(RecyclerItem(R.drawable.bryce_canyon, "Joana"))
        (recyclerList as ArrayList<RecyclerItem>).add(RecyclerItem(R.drawable.cathedral_rock, "Maria"))
        (recyclerList as ArrayList<RecyclerItem>).add(RecyclerItem(R.drawable.bryce_canyon, "Zé"))
        (recyclerList as ArrayList<RecyclerItem>).add(RecyclerItem(R.drawable.cathedral_rock, "Tatiana"))
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

                    //To do a verification of the existence of the inserted name and then send its ID


                    val intent = Intent (this@SearchBarActivity, ProfileViewActivity::class.java)
                    intent.putExtra("User" , query)

                    startActivity(intent)

                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter!!.filter.filter(newText)
                return false
            }
        })
        return true
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val searchView = findViewById<android.widget.SearchView>(R.id.searchView)

        searchView.setQuery("", false)
        searchView.isIconified = true
        searchView.clearFocus()
    }
}

