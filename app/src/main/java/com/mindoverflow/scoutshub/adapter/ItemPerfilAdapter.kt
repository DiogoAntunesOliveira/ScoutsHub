package com.mindoverflow.scoutshub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bit.linux.tinyspacex.Helpers.getImageUrl
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.models.Atividade

class CustomAdapter(val userList: ArrayList<Atividade>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_past_activities, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        holder.bindItems(userList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is holding the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(atividade: Atividade) {
            val textViewName = itemView.findViewById(R.id.card_item_name) as TextView
            val textViewAddress  = itemView.findViewById(R.id.card_item_description) as TextView
            val textViewImage  = itemView.findViewById(R.id.vard_item_image) as ImageView

            textViewName.text = atividade.nome
            textViewAddress.text = atividade.localInicio
            getImageUrl(atividade.imagem.toString(), textViewImage)
        }
    }
}