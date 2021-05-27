package com.example.aplicacaoprojeto.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.aplicacaoprojeto.R
import com.example.aplicacaoprojeto.models.Material
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MateriaisFragment : Fragment() {
    var materiais : MutableList<Material> = arrayListOf()
    lateinit var adapter : MaterialAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_materiais, container, false)

        val listEquipa = rootView.findViewById<ListView>(R.id.listViewMaterial)
        adapter = MaterialAdapter()
        listEquipa.adapter = adapter

        val equipa = Material(null, "Tendas", 14)
        materiais.add(equipa)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch (Dispatchers.Main){
            adapter.notifyDataSetChanged()
        }
    }

    inner class MaterialAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return materiais.size
        }

        override fun getItem(position: Int): Any {
            return materiais[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_materiais, parent, false)

            val idMaterial = rowView.findViewById<TextView>(R.id.textViewIdMaterial)
            val tipoMaterial = rowView.findViewById<TextView>(R.id.textViewTipo)
            val quantidade = rowView.findViewById<TextView>(R.id.textViewQuantidade)


            idMaterial.text = materiais[position].idMaterial.toString()
            tipoMaterial.text = materiais[position].tipo
            quantidade.text = materiais[position].quantidade.toString()


            return rowView
        }
    }
}