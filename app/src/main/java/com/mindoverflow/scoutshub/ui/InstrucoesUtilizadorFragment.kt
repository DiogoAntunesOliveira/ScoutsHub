package com.mindoverflow.scoutshub.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.models.InstrucaoUtilizador
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class InstrucoesUtilizadorFragment : Fragment() {
    var instrucoesUtilizadores : MutableList<InstrucaoUtilizador> = arrayListOf()
    lateinit var adapter : InstrucoesUtilizadoresAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_instrucoes_utilizador, container, false)

        val listInstrucoesUtilizador = rootView.findViewById<ListView>(R.id.listViewInstrucaoUtilizador)
        adapter = InstrucoesUtilizadoresAdapter()
        listInstrucoesUtilizador.adapter = adapter

        val instrucao_utilizador = InstrucaoUtilizador(1, 1)
        instrucoesUtilizadores.add(instrucao_utilizador)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch (Dispatchers.Main){
            adapter.notifyDataSetChanged()
        }
    }

    inner class InstrucoesUtilizadoresAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return instrucoesUtilizadores.size
        }

        override fun getItem(position: Int): Any {
            return instrucoesUtilizadores[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_instrucoes_utilizador, parent, false)

            val instrucao = rowView.findViewById<TextView>(R.id.textViewIdInstrucaoFK)
            val utilizador = rowView.findViewById<TextView>(R.id.textViewIdUtilizadorFK)

            instrucao.text = instrucoesUtilizadores[position].idInstrucao.toString()
            utilizador.text = instrucoesUtilizadores[position].idUtilizador.toString()

            return rowView
        }
    }
}