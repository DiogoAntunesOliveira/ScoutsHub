package com.example.aplicacaoprojeto.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginLeft
import androidx.fragment.app.Fragment
import com.example.aplicacaoprojeto.R
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlin.math.sin


class EstatisticasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_estatisticas, container, false)

        val graph = rootView.findViewById<GraphView>(R.id.graph)

        var x = -5.0
        var y = 0.0

        val series = LineGraphSeries<DataPoint>()
        val gridLabel: GridLabelRenderer = graph.gridLabelRenderer

        graph.title = "Percentagem de atividades participadas desde o inicio"
        graph.titleTextSize = 35F

        gridLabel.horizontalAxisTitle = "X Axis Title"
        gridLabel.verticalAxisTitle = "Y Axis Title"


        for(i in 0..200){
            x += 0.1
            y = sin(x)
            series.appendData(DataPoint(x, y), true, 500)
        }

        graph.addSeries(series)


        return rootView
    }


}