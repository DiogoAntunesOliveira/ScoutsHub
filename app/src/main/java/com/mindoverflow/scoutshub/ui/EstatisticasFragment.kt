
package com.mindoverflow.scoutshub.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import androidx.fragment.app.Fragment
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.google.android.gms.location.*
import com.mindoverflow.scoutshub.ui.Atividades.AtividadesActivity
import com.mindoverflow.scoutshub.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.wait
import okio.IOException
import org.json.JSONObject
import org.w3c.dom.Text
import java.util.*


class EstatisticasFragment : Fragment() {

     var lat: Double = 0.0
    var lng: Double = 0.0
    var cidade : String = ""
    lateinit var locationRequest: LocationRequest
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

        ): View? {

        val rootView = inflater.inflate(R.layout.fragment_estatisticas, container, false)

        val textviewcidade = rootView.findViewById<TextView>(R.id.cidade)
        val textviewcoordenadas = rootView.findViewById<TextView>(R.id.textView5)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(context,
                "Permissões negadas ou insuficientes",
                Toast.LENGTH_LONG)
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION), 1
            )
            rootView.findViewById<TextView>(R.id.textView3).text = "Por favor dê permissões de localização"
            rootView.findViewById<TextView>(R.id.cidade).text = ""
            rootView.findViewById<TextView>(R.id.textView5).text = "Para isso , renicie a aplicação por favor"


        } else {


            getLastLocation()
                        /* LocationServices.getFusedLocationProviderClient(requireActivity()).lastLocation
                .addOnSuccessListener { location: Location? ->
                    lat = location?.latitude
                    lng = location?.longitude
                }*/




        GlobalScope.launch(Dispatchers.IO) {
            Thread.sleep(1000)
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?q=${textviewcidade.text}&appid=d0378a78177e7edd9d0648161be50dae&units=metric")
                .build()
            println("https://api.openweathermap.org/data/2.5/weather?q=${textviewcidade.text}&appid=d0378a78177e7edd9d0648161be50dae&units=metric")
         //   println("https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lng + "&appid=d0378a78177e7edd9d0648161be50dae")

            client.newCall(request).execute().use { response ->
                val jsStr: String = response.body!!.string()
                println(jsStr)


                val jsonObjectTotal = JSONObject(jsStr)


                val jsonTemperatura: JSONObject = jsonObjectTotal.getJSONObject("main") as JSONObject
                val temp: String? =
                    if (!jsonTemperatura.isNull("temp")) jsonTemperatura.getString("temp") else null


                GlobalScope.launch(Dispatchers.Main) {
                    if (temp != null) {
                        val tempfloat = temp.toFloat()
                        rootView.findViewById<TextView>(R.id.textView3).text =
                            String.format("%.2f", tempfloat) + "ºC"
                    } else {
                        rootView.findViewById<TextView>(R.id.textView3).text = "--- ºC"
                    }
                }
            }
        }

        //Documentation https://github.com/AAChartModel/AAChartCore-Kotlin
        val aaChartView = rootView.findViewById<AAChartView>(R.id.aa_chart_view)

        val aaChartModel: AAChartModel = AAChartModel()
            .chartType(AAChartType.Area)
            .title("Participated Activities")
            .dataLabelsEnabled(false)
            .yAxisLabelsEnabled(false)
            .yAxisTitle("")
            .series(arrayOf(
                AASeriesElement()
                    .name("Tokyo")
                    .data(arrayOf(7.0,
                        6.9,
                        9.5,
                        14.5,
                        18.2,
                        21.5,
                        25.2,
                        26.5,
                        23.3,
                        18.3,
                        13.9,
                        9.6)),
                AASeriesElement()
                    .name("NewYork")
                    .data(arrayOf(0.2,
                        0.8,
                        5.7,
                        11.3,
                        17.0,
                        22.0,
                        24.8,
                        24.1,
                        20.1,
                        14.1,
                        8.6,
                        2.5))
            )
            )
        //This method is called only for the first time after you create an AAChartView instance object
        aaChartView.aa_drawChartWithChartModel(aaChartModel)

        //This method is recommended to be called for updating the series data dynamically(Usar na API?)
        //aaChartView.aa_onlyRefreshTheChartDataWithChartModelSeries(chartModelSeriesArray)
        }
        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun getLastLocation() {
        if (isLocationEnabled()) {
            if (ActivityCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        NewLocationData()
                    } else {
                        lat.plus(location.latitude)
                        lng.plus(location.longitude)
                        getActivity()?.findViewById<TextView>(R.id.textView5)?.text = location.longitude.toString() + " " + location.latitude.toString()
                        getActivity()?.findViewById<TextView>(R.id.cidade)?.text = getCityName(location.latitude, location.longitude)
                    }
            }

            }
        } else {
            Toast.makeText(requireContext(), "Por favor ative o GPS do seu Dispostivo", Toast.LENGTH_LONG).show()
            val intent = Intent(activity,  AtividadesActivity::class.java)
            activity?.startActivity(intent)
        }
    }


    private fun getCityName(lat: Double, lng: Double): String {
        var city = ""
        var country = ""


        var geocoder = Geocoder(requireContext(), Locale.getDefault())
        var addresses = geocoder.getFromLocation(lat, lng, 1)

        city = addresses.get(0).locality.toString()
        country = addresses.get(0).countryName.toString()

        cidade = city
        println(city + country)

        return city
    }


    @SuppressLint("MissingPermission")
    fun NewLocationData() {
        var locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )
    }

    fun isLocationEnabled(): Boolean {
        //this function will return to us the state of the location service
        //if the gps or the network provider is enabled then it will return true otherwise it will return false
        var locationManager =
            getActivity()?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requireActivity().findViewById<TextView>(R.id.textView3).text =
                    "Por favor dê permissões de localização"
                requireActivity().findViewById<TextView>(R.id.cidade).text = ""
                requireActivity().findViewById<TextView>(R.id.textView5).text =
                    "Para isso , renicie a aplicação por favor"
            } else {
                var lastLocation: Location = locationResult.lastLocation
                lat = locationResult.lastLocation.latitude
                lng = locationResult.lastLocation.longitude
                getActivity()?.findViewById<TextView>(R.id.textView5)?.text =
                    (lastLocation.longitude + lastLocation.latitude).toString()
            }
        }
    }
}