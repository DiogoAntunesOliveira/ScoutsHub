package com.example.aplicacaoprojeto.ui;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.aplicacaoprojeto.R
import com.example.aplicacaoprojeto.models.Equipa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EquipasFragment : Fragment() {

    var equipas : MutableList<Equipa> = arrayListOf()
    lateinit var adapter : EquipaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_equipas, container, false)

        val listEquipa = rootView.findViewById<ListView>(R.id.listViewEquipa)
        adapter = EquipaAdapter()
        listEquipa.adapter = adapter

        val equipa = Equipa(null, "A", null)
        equipas.add(equipa)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch (Dispatchers.Main){
            adapter.notifyDataSetChanged()
        }
    }

    inner class EquipaAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return equipas.size
        }

        override fun getItem(position: Int): Any {
            return equipas[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_equipas, parent, false)

            val idEquipa = rowView.findViewById<TextView>(R.id.textViewIdEquipa)
            val nomeEquipa = rowView.findViewById<TextView>(R.id.textViewNomeEquipa)
            val idSeccao = rowView.findViewById<TextView>(R.id.textViewIdSeccaoFK)


            idEquipa.text = equipas[position].id_equipa.toString()
            nomeEquipa.text = equipas[position].nome_equipa
            idSeccao.text = equipas[position].id_seccao.toString()


            return rowView
        }
    }
}