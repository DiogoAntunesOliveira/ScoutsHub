package com.mindoverflow.scoutshub.ui;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.models.Seccao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SeccoesFragment : Fragment() {

    var seccoes : MutableList<Seccao> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_seccoes, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch (Dispatchers.Main){
        }
    }
}