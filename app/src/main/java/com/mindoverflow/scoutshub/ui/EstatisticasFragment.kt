package com.mindoverflow.scoutshub.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.google.android.gms.location.*
import com.mindoverflow.scoutshub.PedidosAcesso
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.SavedUserData
import com.mindoverflow.scoutshub.models.Atividade
import com.mindoverflow.scoutshub.models.Participante
import com.mindoverflow.scoutshub.ui.Atividades.AtividadesActivity
import com.mindoverflow.scoutshub.ui.Login.Signup1Activity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.roundToInt


class EstatisticasFragment : Fragment() {

    var lat: Double = 0.0
    var lng: Double = 0.0
    var cidade : String = ""
    var listaComConfirmacao: MutableList<Participante> = arrayListOf()
    lateinit var locationRequest: LocationRequest
    var atividadesvalidas: MutableList<Atividade> = arrayListOf()
    var todasatividades: MutableList<Atividade> = arrayListOf()
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var arraydaysuser : Array<Any> = arrayOf(0,0,0,0,0,0,0,0,0,0)
    var arraydayseveryone : Array<Any> = arrayOf(0,0,0,0,0,0,0,0,0,0)




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

        ): View? {




        val rootView = inflater.inflate(R.layout.fragment_estatisticas, container, false)
        val btacessos = rootView.findViewById<ImageView>(R.id.button3)
        val textviewcoordenadas = rootView.findViewById<TextView>(R.id.textViewCoordenadas)


        btacessos.setOnClickListener{

            val intent = Intent(requireContext(), PedidosAcesso::class.java)
            startActivity(intent)
        }



        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())


        //Se não houver permissões , exibe um popup  , se houver obtem a localização
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
            rootView.findViewById<TextView>(R.id.textViewCoordenadas).text = ""
        } else {
           getLastLocation()


            /* LocationServices.getFusedLocationProviderClient(requireActivity()).lastLocation
                .addOnSuccessListener { location: Location? ->
                    lat = location?.latitude
                    lng = location?.longitude
                }*/

            GlobalScope.launch(Dispatchers.IO) {
                var id_utilizador = SavedUserData.id_utilizador


                delay(100)
                val client = OkHttpClient()

                val participanterequest = Request.Builder()
                    .url("http://mindoverflow.amipca.xyz:60000/participant/utilizador/$id_utilizador/")
                    .build()


                //Obtem todos os objetos participantes
                //Os objetos que tiverem confirmacao diferente de 0 são adicionados a uma lista
                client.newCall(participanterequest).execute().use { response ->
                    val string: String = response.body!!.string()

                    val jsonObjectTotal = JSONObject(string)


                    val jsonparticipante: JSONArray =
                        jsonObjectTotal.getJSONArray("participante") as JSONArray
                    for (index in 0 until jsonparticipante.length()) {
                        val getjson: JSONObject = jsonparticipante.get(index) as JSONObject
//                        var iconTempo = if (!jsonweather.isNull("id_atividades")){ jsonweather.getInt("id_atividades")} else null
                        val participante = Participante.fromJson(getjson)
                        if (participante.confirmacao != 0 && participante.confirmacao != null) {

                            listaComConfirmacao.add(participante)
                            println(listaComConfirmacao)
                        }
                    }
                }

                    val atividadesvalidasinforequest = Request.Builder()
                    .url("http://mindoverflow.amipca.xyz:60000/activities/")
                    .build()
                    client.newCall(atividadesvalidasinforequest).execute().use { response ->
                        val string: String = response.body!!.string()
                        println(string)
                        val jsonObject = JSONObject(string)
                        val jsonArrayAtividadesValidas = jsonObject.getJSONArray("activities") as JSONArray

                        for (index in 0 until jsonArrayAtividadesValidas.length()) {
                            val atividadevalida = Atividade.fromJson(string, index)
                            todasatividades.add(atividadevalida)
                                for(size in 0 until listaComConfirmacao.size){
                                    if(atividadevalida.idAtividade == listaComConfirmacao[size].id_atividade){
                                        atividadesvalidas.add(atividadevalida)
                                        println(atividadesvalidas)
                            }}

                        }

                        }

                        GlobalScope.launch(Dispatchers.Main) {
                            //Documentation https://github.com/AAChartModel/AAChartCore-Kotlin

                            for(atividades in todasatividades) {
                                val datenow = Calendar.getInstance().time

                                val atividadefor =
                                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.UK)
                                val atividadeday: Date = atividadefor.parse(atividades.dataInicio)

                                val diff: Long = atividadeday.time - datenow.time
                                val dayCount = diff.toFloat() / (24 * 60 * 60 * 1000)
                                val dayCountLimited : Int = (dayCount / 3).toInt()

                                if (diff.toFloat() / (24 * 60 * 60 * 1000) > 0) {
                                    if (dayCount < 30) {
                                        when(arraydayseveryone[dayCountLimited]){
                                            0->arraydayseveryone[dayCountLimited] = 1
                                            1->arraydayseveryone[dayCountLimited] = 2
                                            2->arraydayseveryone[dayCountLimited] = 3
                                            3->arraydayseveryone[dayCountLimited] = 4
                                            4->arraydayseveryone[dayCountLimited] = 5
                                            5->arraydayseveryone[dayCountLimited] = 6
                                            6->arraydayseveryone[dayCountLimited] = 7
                                            7->arraydayseveryone[dayCountLimited] = 8
                                            8->arraydayseveryone[dayCountLimited] = 9
                                            9->arraydayseveryone[dayCountLimited] = 10
                                            10->arraydayseveryone[dayCountLimited] = 11
                                        }
                                    }
                                }
                            }
                            for(atividadevalida in atividadesvalidas){
                                val datenow = Calendar.getInstance().time

                                val atividadefor = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.UK)
                                val atividadeday: Date = atividadefor.parse(atividadevalida.dataInicio)

                                val diff: Long = atividadeday.time - datenow.time
                                val dayCount = diff.toFloat() / (24 * 60 * 60 * 1000)
                                var dayCountLimited : Int = (dayCount / 3).toInt()

                                if (diff.toFloat() / (24 * 60 * 60 * 1000) > 0) {
                                    if(dayCount <30)
                                    {
                                        when(arraydaysuser[dayCountLimited]){
                                            0->arraydaysuser[dayCountLimited] = 1
                                            1->arraydaysuser[dayCountLimited] = 2
                                            2->arraydaysuser[dayCountLimited] = 3
                                            3->arraydaysuser[dayCountLimited] = 4
                                            4->arraydaysuser[dayCountLimited] = 5
                                            5->arraydaysuser[dayCountLimited] = 6
                                            6->arraydaysuser[dayCountLimited] = 7
                                            7->arraydaysuser[dayCountLimited] = 8
                                            8->arraydaysuser[dayCountLimited] = 9
                                            9->arraydaysuser[dayCountLimited] = 10
                                            10->arraydaysuser[dayCountLimited] = 11
                                        }
                                    }
                                }
                            }


                                val aaChartView = rootView.findViewById<AAChartView>(R.id.aa_chart_view)
                                val aaChartModel: AAChartModel = AAChartModel()
                                    .chartType(AAChartType.Area)
                                    //            .title("Participated Activities")
                                    .dataLabelsEnabled(false)
                                    .yAxisLabelsEnabled(false)
                                    .xAxisLabelsEnabled(true)
                                    .markerRadius(4F)
                                    .categories(arrayOf("Hoje a 3 dias","4-6 dias","7-9 dias","10-12 dias","13-15 dias","16-18 dias","19-21 dias","22-24 dias","25-27 dias","28-30 dias"))
                                    .axesTextColor("#4871bd")
                                    .xAxisGridLineWidth(0.0F)
                                    .yAxisGridLineWidth(0.0F)
                                    .yAxisTitle("")
                                    .series(arrayOf(
                                        AASeriesElement()
                                            .name("Atividades Existentes")
                                            .color("#A254F2")
                                            .data(
                                                arrayOf(
                                                    arraydayseveryone[0],
                                                    arraydayseveryone[1],
                                                    arraydayseveryone[2],
                                                    arraydayseveryone[3],
                                                    arraydayseveryone[4],
                                                    arraydayseveryone[5],
                                                    arraydayseveryone[6],
                                                    arraydayseveryone[7],
                                                    arraydayseveryone[8],
                                                    arraydayseveryone[9])),
                                        AASeriesElement()
                                            .name("Atividades Participadas")
                                            .color("#48C9BB")
                                            .data(
                                                arrayOf(
                                                    arraydaysuser[0],
                                                    arraydaysuser[1],
                                                    arraydaysuser[2],
                                                    arraydaysuser[3],
                                                    arraydaysuser[4],
                                                    arraydaysuser[5],
                                                    arraydaysuser[6],
                                                    arraydaysuser[7],
                                                    arraydaysuser[8],
                                                    arraydaysuser[9]))

                                    ))
                                //This method is called only for the first time after you create an AAChartView instance object

                                aaChartView.aa_drawChartWithChartModel(aaChartModel)




                            //This method is recommended to be called for updating the series data dynamically(Usar na API?)
                            //aaChartView.aa_onlyRefreshTheChartDataWithChartModelSeries(chartModelSeriesArray)
                         }
                        //println(str)
                    }

        }
        return rootView

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun getLastLocation() {
        if (isLocationEnabled() == true) {
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
                        getActivity()?.findViewById<TextView>(R.id.textViewCoordenadas)?.text = location.longitude.toString() + " " + location.latitude.toString()
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


    fun getCityName(lat: Double, lng: Double): String {
        var city = ""
        var country = ""


        var geocoder = Geocoder(requireContext(), Locale.getDefault())
        var addresses = geocoder.getFromLocation(lat, lng, 2)
        println(addresses)

        if(addresses.get(0).locality == null){
            if(addresses.get(1).locality == null){
                city = addresses.get(0).countryName
            }
            else{
                city = addresses.get(1).locality
                country = addresses.get(1).countryName
            }
        }
        else{
            city = addresses.get(0).locality
            country = addresses.get(0).countryName
        }

        globalScopeweather()
        println("Return :" + city + country)


        return city
    }


    fun globalScopeweather() {

        GlobalScope.launch(Dispatchers.IO){
            delay(100)
            val textviewcidade = activity?.findViewById<TextView>(R.id.cidade)
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?q=${textviewcidade?.text}&appid=d0378a78177e7edd9d0648161be50dae&units=metric")
                .build()
            println("https://api.openweathermap.org/data/2.5/weather?q=${textviewcidade?.text}&appid=d0378a78177e7edd9d0648161be50dae&units=metric")
            // println("https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lng + "&appid=d0378a78177e7edd9d0648161be50dae")

            client.newCall(request).execute().use { response ->
                val jsStr: String = response.body!!.string()
                println(jsStr)


                val jsonObjectTotal = JSONObject(jsStr)


                val jsonTemperatura: JSONObject = jsonObjectTotal.getJSONObject("main") as JSONObject
                val temp: String? =
                    if (!jsonTemperatura.isNull("temp")) jsonTemperatura.getString("temp") else null

                lateinit var iconTempo : String
                val jsonTempo: JSONArray = jsonObjectTotal.getJSONArray("weather") as JSONArray
                for ( index in  0 until jsonTempo.length()) {
                    val jsonweather : JSONObject = jsonTempo.get(index) as JSONObject
                    iconTempo = if (!jsonweather.isNull("icon")) jsonweather.getString("icon") else "API Nao Disponivel"
                }


                GlobalScope.launch(Dispatchers.Main) {
                    val textviewtempo = activity?.findViewById<TextView>(R.id.tempoEstatisticas)
                    val imageIconTempo = activity?.findViewById<ImageView>(R.id.imageIconTempo)


                    when (iconTempo) {
                        "01d", "01n" ->{ textviewtempo?.text = "Céu Limpo"
                            imageIconTempo?.setBackgroundResource(R.drawable.weather_sun)}
                        "02d" ,"02n" -> {textviewtempo?.text = "Céu pouco nublado"
                            imageIconTempo?.setBackgroundResource(R.drawable.weather_low_clouds)}
                        "03d" , "03n" , "04d" , "04n" -> {textviewtempo?.text = "Ceu nublado"
                            imageIconTempo?.setBackgroundResource(R.drawable.weather_clouds)}
                        "09d" , "09n" -> {textviewtempo?.text = "Chuva"
                            imageIconTempo?.setBackgroundResource(R.drawable.weather_rain)}
                        "10d" , "10n" -> {textviewtempo?.text = "Aguaceiros"
                            imageIconTempo?.setBackgroundResource(R.drawable.weather_aguaceiros)}
                        "11d" , "11n" -> {textviewtempo?.text = "Tempestade"
                            imageIconTempo?.setBackgroundResource(R.drawable.weather_storm)}
                        "13d" , "13n" -> {textviewtempo?.text = "Neve"
                            imageIconTempo?.setBackgroundResource(R.drawable.weather_snow)}
                        "50d" , "50n" -> {textviewtempo?.text = "Nevoeiro"
                            imageIconTempo?.setBackgroundResource(R.drawable.weather_fog)}
                        else -> {
                            activity?.findViewById<TextView>(R.id.tempoEstatisticas)?.text = iconTempo
                        }
                    }

                    if (temp != null) {
                        val tempfloat = temp.toFloat().roundToInt()
                        activity?.findViewById<TextView>(R.id.textView3)?.text =
                            "$tempfloat ºC"
                    //String.format("%.2f", tempfloat) + "ºC"
                    } else {
                        activity?.findViewById<TextView>(R.id.textView3)?.text = "--- ºC"
                    }
                }
            }
        }

    }

    @SuppressLint("MissingPermission")
    fun NewLocationData() {
        //Esta função atualiza a localização
        //nessa localização atualizada , define uma prioridade , um intervalo e quantos updates irá haver na mesma
        var locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 6
        locationRequest.fastestInterval = 3
        locationRequest.numUpdates = 3
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )
    }

    fun isLocationEnabled(): Boolean {
        //Esta funcao retorna o estado do GPS , se o GPS estiver ligado , retorna true , se não ,false
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
                requireActivity().findViewById<TextView>(R.id.textViewCoordenadas).text =
                    "Para isso , renicie a aplicação por favor"
            } else {
                var lastLocation: Location = locationResult.lastLocation
                lat = locationResult.lastLocation.latitude
                lng = locationResult.lastLocation.longitude
                getActivity()?.findViewById<TextView>(R.id.textViewCoordenadas)?.text =
                    (lastLocation.longitude + lastLocation.latitude).toString()
            }
        }
    }
}