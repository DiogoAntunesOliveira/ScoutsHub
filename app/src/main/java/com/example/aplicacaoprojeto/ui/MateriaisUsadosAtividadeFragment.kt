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
import com.example.aplicacaoprojeto.models.MaterialUsadoAtividade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MateriaisUsadosAtividadeFragment: Fragment() {

    var materiaisUsadosAtividade : MutableList<MaterialUsadoAtividade> = arrayListOf()
    lateinit var adapter : MaterialUsadoAtividadeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_materiais_usado_atividade, container, false)

        val listMaterialUsadoAtividade = rootView.findViewById<ListView>(R.id.listViewMaterialUsadoAtividade)
        adapter = MaterialUsadoAtividadeAdapter()
        listMaterialUsadoAtividade.adapter = adapter

        val material = MaterialUsadoAtividade(5, 4, 20)
        materiaisUsadosAtividade.add(material)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch (Dispatchers.Main){
            adapter.notifyDataSetChanged()
        }
    }

    inner class MaterialUsadoAtividadeAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return materiaisUsadosAtividade.size
        }

        override fun getItem(position: Int): Any {
            return materiaisUsadosAtividade[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_materiais_usado_atividade, parent, false)

            val idAtividade = rowView.findViewById<TextView>(R.id.textViewIdAtividade)
            val idMaterial = rowView.findViewById<TextView>(R.id.textViewIdMaterialFK)
            val quantidade = rowView.findViewById<TextView>(R.id.textViewQuantidadeMaterialUsado)


            idAtividade.text = materiaisUsadosAtividade[position].idAtividade.toString()
            idMaterial.text = materiaisUsadosAtividade[position].idMaterial.toString()
            quantidade.text = materiaisUsadosAtividade[position].quantidade.toString()


            return rowView
        }
    }
}