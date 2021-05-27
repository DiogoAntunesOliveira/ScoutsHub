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
import com.example.aplicacaoprojeto.models.Instrucao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class InstrucoesFragment : Fragment() {
    var instrucoes : MutableList<Instrucao> = arrayListOf()
    lateinit var adapter : InstrucoesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_instrucoes, container, false)

        val listInstrucoes = rootView.findViewById<ListView>(R.id.listViewInstrucoes)
        adapter = InstrucoesAdapter()
        listInstrucoes.adapter = adapter

        val instrucao = Instrucao(null, "Como montar uma tenda", "Tutorial de como montar uma tenda", "fdsfsdfsdfte")
        instrucoes.add(instrucao)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch (Dispatchers.Main){
            adapter.notifyDataSetChanged()
        }
    }

    inner class InstrucoesAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return instrucoes.size
        }

        override fun getItem(position: Int): Any {
            return instrucoes[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_instrucoes, parent, false)
            
            //val idInstrucao = rowView.findViewById<TextView>(R.id.textViewIdInstrucao)
            val titulo = rowView.findViewById<TextView>(R.id.textViewTitulo)
            val descricao = rowView.findViewById<TextView>(R.id.textViewDescricao)
            val imagemURL = rowView.findViewById<TextView>(R.id.textViewImagem)

            //idInstrucao.text = instrucoes[position].idInstrucao.toString()
            titulo.text = instrucoes[position].titulo
            descricao.text = instrucoes[position].descricao
            imagemURL.text = instrucoes[position].imagem

            return rowView
        }
    }
}