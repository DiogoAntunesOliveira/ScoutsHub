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
    //lateinit var adapter : UtilizadorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_utilizadores, container, false)

       // val listUtilizador = rootView.findViewById<ListView>(R.id.listViewUtilizador)
       // adapter = UtilizadorAdapter()
       // listUtilizador.adapter = adapter

       // val utilizador = Utilizador(1, "jorgemiguelsa@zonmail.pt", "fx25", 1, 1, 1)
        //Utilizadores.add(utilizador)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch (Dispatchers.Main){
            //adapter.notifyDataSetChanged()
        }

    }
}