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
import com.example.aplicacaoprojeto.models.Atividade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AtividadesFragment : Fragment() {

    var atividades : MutableList<Atividade> = arrayListOf()
    lateinit var adapter : AtividadeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_atividades, container, false)

        val listAtividade = rootView.findViewById<ListView>(R.id.listViewActivities)
        adapter = AtividadeAdapter()
        listAtividade.adapter = adapter

        val atividade = Atividade(1, "Acampar", "Acampamento na montanha", "Sei la", 580, "Famalicao", null, null, "a5hg456fdw", "gdsfufasofhw4uihfwgdf", "25-05-2000 22:52:00", "25-05-2000 22:52:00")
        atividades.add(atividade)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch (Dispatchers.Main){
            adapter.notifyDataSetChanged()
        }
    }

    inner class AtividadeAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return atividades.size
        }

        override fun getItem(position: Int): Any {
            return atividades[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_atividades, parent, false)

            val nome = rowView.findViewById<TextView>(R.id.textViewNomeAtividade)
            val tipo = rowView.findViewById<TextView>(R.id.textViewTipo)
            val descricao = rowView.findViewById<TextView>(R.id.textViewDescricao)
            val custo = rowView.findViewById<TextView>(R.id.textViewCusto)
            val local = rowView.findViewById<TextView>(R.id.textViewLocal)
            val localInicio = rowView.findViewById<TextView>(R.id.textViewLocalInicio)
            val localFim = rowView.findViewById<TextView>(R.id.textViewLocalFim)
            val coordenadas = rowView.findViewById<TextView>(R.id.textViewCoordenadas)
            val urlLocal = rowView.findViewById<TextView>(R.id.textViewUrlLocal)
            val dataInicio = rowView.findViewById<TextView>(R.id.textViewDataInicio)
            val dataFim = rowView.findViewById<TextView>(R.id.textViewDataFim)


            nome.text = atividades[position].nome
            tipo.text = atividades[position].tipo
            descricao.text = atividades[position].descricao
            custo.text = atividades[position].custo.toString()
            local.text = atividades[position].local
            localInicio.text = atividades[position].localInicio
            localFim.text = atividades[position].localFim
            coordenadas.text = atividades[position].coordenadas
            urlLocal.text = atividades[position].urlLocal
            dataInicio.text = atividades[position].dataInicio
            dataFim.text = atividades[position].dataFim


            return rowView
        }
    }
}