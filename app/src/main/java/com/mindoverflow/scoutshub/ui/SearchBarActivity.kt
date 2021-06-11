package com.mindoverflow.scoutshub.ui

import android.os.Bundle
import android.view.Menu
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.models.RecyclerItem
import java.util.ArrayList


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
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        adapter = RecycleViewAdapter(recyclerList!!)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val searchView = findViewById<android.widget.SearchView>(R.id.searchView)

        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter!!.filter.filter(newText)
                return false
            }
        })
        return true
    }
}

