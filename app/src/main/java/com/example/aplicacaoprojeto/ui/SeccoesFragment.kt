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
import com.example.aplicacaoprojeto.models.Seccao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SeccoesFragment : Fragment() {

    var seccoes : MutableList<Seccao> = arrayListOf()
    lateinit var adapter : SeccaoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_seccoes, container, false)

        val listSeccao = rootView.findViewById<ListView>(R.id.listViewSeccao)
        adapter = SeccaoAdapter()
        listSeccao.adapter = adapter

        val seccao = Seccao(null, "B")
        seccoes.add(seccao)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch (Dispatchers.Main){
            adapter.notifyDataSetChanged()
        }
    }

    inner class SeccaoAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return seccoes.size
        }

        override fun getItem(position: Int): Any {
            return seccoes[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_seccoes, parent, false)

            val idSeccao = rowView.findViewById<TextView>(R.id.textViewIdSeccao)
            val nomeSeccao = rowView.findViewById<TextView>(R.id.textViewNomeSeccao)


            idSeccao.text = seccoes[position].id_seccao.toString()
            nomeSeccao.text = seccoes[position].nome_seccao

            return rowView
        }

    }
}