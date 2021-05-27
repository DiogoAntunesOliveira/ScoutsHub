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
import com.mindoverflow.scoutshub.models.Perfil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PerfisFragment : Fragment() {

    var perfis : MutableList<Perfil> = arrayListOf()
    lateinit var adapter : PerfilAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_perfis, container, false)

        val listPerfil = rootView.findViewById<ListView>(R.id.listViewPerfil)
        adapter = PerfilAdapter()
        listPerfil.adapter = adapter

        val perfil = Perfil(null, "Jorge", "30/5/2000", "M", 919923205, "Praceta Madalena Fonseca 120 Rés do chão", "9560-010", 123456789, 6, 5)
        perfis.add(perfil)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch (Dispatchers.Main){
            adapter.notifyDataSetChanged()
        }
    }

    inner class PerfilAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return perfis.size
        }

        override fun getItem(position: Int): Any {
            return perfis[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_perfis, parent, false)

            //val idPerfil = rowView.findViewById<TextView>(R.id.textViewIdPerfil)
            val nomeUtilizador = rowView.findViewById<TextView>(R.id.textViewNomeUtilizador)
            val dtNasc = rowView.findViewById<TextView>(R.id.textViewDataNasc)
            val genero = rowView.findViewById<TextView>(R.id.textViewGenero)
            val contacto = rowView.findViewById<TextView>(R.id.textViewContacto)
            val morada = rowView.findViewById<TextView>(R.id.textViewMorada)
            val codigoPostal = rowView.findViewById<TextView>(R.id.textViewCodPostal)
            val nin = rowView.findViewById<TextView>(R.id.textViewNin)
            val totalAtivParticip = rowView.findViewById<TextView>(R.id.textViewTotalAtivParticip)
            //val idEquipa = rowView.findViewById<TextView>(R.id.textViewIdEquipa)

            //idPerfil.text = perfis[position].idPerfil.toString()
            nomeUtilizador.text = perfis[position].nome
            dtNasc.text = perfis[position].dtNasc
            genero.text = perfis[position].genero
            contacto.text = perfis[position].contacto.toString()
            morada.text = perfis[position].morada
            codigoPostal.text = perfis[position].codigoPostal
            nin.text = perfis[position].dtNasc
            totalAtivParticip.text = perfis[position].totalAtivParticip.toString()
            //idEquipa.text = perfis[position].idEquipa.toString()

            return rowView
        }

    }
}