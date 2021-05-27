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
import com.example.aplicacaoprojeto.models.TipoUtilizador
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TiposUtilizadorFragment : Fragment() {

    var tipoUtilizadores : MutableList<TipoUtilizador> = arrayListOf()
    lateinit var adapter : TipoUtilizadorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_tipos_utilizador, container, false)

        val listTipoUtilizador = rootView.findViewById<ListView>(R.id.listViewTipoUtilizador)
        adapter = TipoUtilizadorAdapter()
        listTipoUtilizador.adapter = adapter

        val tipoUtilizador = TipoUtilizador(1, "Administrador")
        tipoUtilizadores.add(tipoUtilizador)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch (Dispatchers.Main){
            adapter.notifyDataSetChanged()
        }

    }

    inner class TipoUtilizadorAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return tipoUtilizadores.size
        }

        override fun getItem(position: Int): Any {
            return tipoUtilizadores[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_tipos_utilizador, parent, false)

            val idTipoUtilizador = rowView.findViewById<TextView>(R.id.textViewIdTipo)
            val designacao = rowView.findViewById<TextView>(R.id.textViewDesignacao)


            idTipoUtilizador.text = tipoUtilizadores[position].id_tipo.toString()
            designacao.text = tipoUtilizadores[position].designacao

            return rowView
        }
    }
}