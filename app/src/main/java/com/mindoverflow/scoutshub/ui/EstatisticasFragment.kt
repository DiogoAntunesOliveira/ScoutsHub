
package com.mindoverflow.scoutshub.ui

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import bit.linux.tinyspacex.Helpers
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.google.android.gms.common.internal.service.Common
import com.google.android.material.imageview.ShapeableImageView
import com.mindoverflow.scoutshub.R
import org.json.JSONObject
import java.lang.Exception
import java.net.URL


class EstatisticasFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_estatisticas, container, false)

        //Documentation https://github.com/AAChartModel/AAChartCore-Kotlin
        val aaChartView = rootView.findViewById<AAChartView>(R.id.aa_chart_view)

        val aaChartModel : AAChartModel = AAChartModel()
                .chartType(AAChartType.Area)
                .title("Participated Activities")
                .dataLabelsEnabled(false)
                .yAxisLabelsEnabled(false)
                .yAxisTitle("")
                .series(arrayOf(
                        AASeriesElement()
                                .name("Tokyo")
                                .data(arrayOf(7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6)),
                        AASeriesElement()
                                .name("NewYork")
                                .data(arrayOf(0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5))
                )
                )
        //This method is called only for the first time after you create an AAChartView instance object
        aaChartView.aa_drawChartWithChartModel(aaChartModel)

        //This method is recommended to be called for updating the series data dynamically(Usar na API?)
        //aaChartView.aa_onlyRefreshTheChartDataWithChartModelSeries(chartModelSeriesArray)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}