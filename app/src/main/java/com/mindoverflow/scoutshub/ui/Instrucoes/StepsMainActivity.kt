package com.mindoverflow.scoutshub.ui.Instrucoes

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.SavedUserData

class StepsMainActivity: AppCompatActivity() {

    private var courseNameEdt: EditText? = null
    private var courseDescEdt: EditText? = null
    private var addBtn: Button? = null
    private var saveBtn: Button? = null
    private var courseRV: RecyclerView? = null

    private var adapter: StepsAdapter? = null
    private var courseModalArrayList: ArrayList<StepsModels>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instrucoes_steps_main)

        supportActionBar!!.hide()

        // go back to previous activity
        val backButton = findViewById<ImageButton>(R.id.buttonBack)
        backButton.setOnClickListener {
            finish()
        }

        courseNameEdt = findViewById(R.id.idEdtCourseName)
        courseDescEdt = findViewById(R.id.idEdtCourseDescription)
        addBtn = findViewById(R.id.idBtnAdd)
        saveBtn = findViewById(R.id.idBtnSave)
        courseRV = findViewById(R.id.idRVCourses)

        loadData()

        buildRecyclerView()

        if(SavedUserData.id_tipo != 1){

            courseNameEdt!!.visibility = View.GONE
            courseDescEdt!!.visibility = View.GONE
            addBtn!!.visibility = View.GONE
            saveBtn!!.visibility = View.GONE
        }
        else
        {
            addBtn!!.setOnClickListener(View.OnClickListener {
                courseModalArrayList!!.add(
                    StepsModels(
                        courseNameEdt!!.text.toString(),
                        courseDescEdt!!.text.toString()
                    )
                )

                adapter!!.notifyItemInserted(courseModalArrayList!!.size)

                courseNameEdt!!.setText("")
                courseDescEdt!!.setText("")
            })

            saveBtn!!.setOnClickListener(View.OnClickListener {
                saveData()
            })

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    val deleted: StepsModels = courseModalArrayList!![viewHolder.adapterPosition]

                    val position = viewHolder.adapterPosition

                    courseModalArrayList!!.removeAt(viewHolder.adapterPosition)

                    adapter!!.notifyItemRemoved(viewHolder.adapterPosition)

                    Snackbar.make(courseRV!!, deleted.line1, Snackbar.LENGTH_LONG)
                        .setAction("Undo",
                            View.OnClickListener {
                                courseModalArrayList!!.add(position, deleted)

                                adapter!!.notifyItemInserted(position)
                            }).show()
                }
            }).attachToRecyclerView(courseRV)
        }
    }

    private fun buildRecyclerView() {
        adapter = StepsAdapter(courseModalArrayList!!, this)
        val manager = LinearLayoutManager(this)
        courseRV!!.setHasFixedSize(true)
        courseRV!!.layoutManager = manager
        courseRV!!.adapter = adapter
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("courses", null)
        val type = object : TypeToken<ArrayList<StepsModels?>?>() {}.type
        courseModalArrayList = gson.fromJson(json, type)
        if (courseModalArrayList == null) {
            courseModalArrayList = ArrayList()
        }
    }

    private fun saveData() {
        val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(courseModalArrayList)
        editor.putString("courses", json)
        editor.apply()
        Toast.makeText(this, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show()
    }
}