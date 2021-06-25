package com.mindoverflow.scoutshub.ui.Instrucoes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mindoverflow.scoutshub.R
import java.util.ArrayList

class StepsAdapter(
    private val courseModalArrayList: ArrayList<StepsModels>, context: Context
) :
    RecyclerView.Adapter<StepsAdapter.ViewHolder>() {
    private val context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_instrucoes_steps_rows, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modal = courseModalArrayList[position]
        holder.courseNameTV.text = modal.line1
        holder.courseDescTV.text = modal.line2
    }

    override fun getItemCount(): Int {
        return courseModalArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseNameTV: TextView
        val courseDescTV: TextView

        init {
            courseNameTV = itemView.findViewById(R.id.idTVCourseName)
            courseDescTV = itemView.findViewById(R.id.idTVCourseDescription)
        }
    }

    init {
        this.context = context
    }
}