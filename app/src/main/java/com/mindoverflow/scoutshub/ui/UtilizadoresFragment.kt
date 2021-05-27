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
import com.mindoverflow.scoutshub.models.Utilizador
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UtilizadoresFragment : Fragment() {

    var Utilizadores : MutableList<Utilizador> = arrayListOf()
    lateinit var adapter : UtilizadorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_utilizadores, container, false)

        val listUtilizador = rootView.findViewById<ListView>(R.id.listViewUtilizador)
        adapter = UtilizadorAdapter()
        listUtilizador.adapter = adapter

        val utilizador = Utilizador(1, "jorgemiguelsa@zonmail.pt", "fx25", 1, 1, 1)
        Utilizadores.add(utilizador)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch (Dispatchers.Main){
            adapter.notifyDataSetChanged()
        }

    }
    inner class UtilizadorAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return Utilizadores.size
        }

        override fun getItem(position: Int): Any {
            return Utilizadores[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_utilizadores, parent, false)

            val idUtilizador = rowView.findViewById<TextView>(R.id.textViewIdUtilizador)
            val emailUtilizador = rowView.findViewById<TextView>(R.id.textViewEmailUtilizador)
            val palavraPassUtilizador = rowView.findViewById<TextView>(R.id.textViewPalavraPass)
            val idTipo = rowView.findViewById<TextView>(R.id.textViewIdTipoFK)
            val idPerfil = rowView.findViewById<TextView>(R.id.textViewIdPerfil)
            val idInstrucao = rowView.findViewById<TextView>(R.id.textViewIdInstrucao)

            idUtilizador.text = Utilizadores[position].id_utilizador.toString()
            emailUtilizador.text = Utilizadores[position].email_utilizador
            palavraPassUtilizador.text = Utilizadores[position].palavra_pass
            idTipo.text = Utilizadores[position].id_tipo.toString()
            idPerfil.text = Utilizadores[position].id_perfil.toString()
            idInstrucao.text = Utilizadores[position].id_instruçao.toString()

            return rowView
        }
    }
}