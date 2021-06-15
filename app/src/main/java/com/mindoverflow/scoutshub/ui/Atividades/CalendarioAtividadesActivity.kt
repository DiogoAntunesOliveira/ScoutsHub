package com.mindoverflow.scoutshub.ui.Atividades

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.domain.Event
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.models.Atividade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class CalendarioAtividadesActivity : AppCompatActivity() {
    var atividades: MutableList<Atividade> = arrayListOf()
    var compactCalendar: CompactCalendarView? = null
    private val dateFormatMonth = SimpleDateFormat("MMMM- yyyy", Locale.getDefault())

    lateinit var adapterlisteventos: ArrayAdapter<String>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario)

        //Esconde a barra superior
        supportActionBar!!.hide()

        var listevent: MutableList<String> = arrayListOf()

        //Declara uma MutableList de tipo inteiro que contem um array com cores
        var coreslist: MutableList<Int> = arrayListOf(
            Color.parseColor("#ff0000"), Color.parseColor("#000000"),
            Color.parseColor("#FF8F00"), Color.parseColor("#038a34"),
            Color.parseColor("#00ff00"), Color.parseColor("#8591ff"),
            Color.parseColor("#00fff2"), Color.parseColor("#FFFF00"),
            Color.parseColor("#0040ff"), Color.parseColor("#6AA84F"),
            Color.parseColor("#ae00ff"), Color.parseColor("#97D6EC"),
            Color.parseColor("#ff0077"),
            Color.parseColor("#37474F"),
        )
        //Escolhe um item (neste caso cor) aleatorio do array
        var coraleatoria = coreslist.random()


        findViewById<ImageView>(R.id.imageadicionar).setOnClickListener {
            val intent = Intent(this, NovaAtividadeActivity::class.java)
            startActivity(intent)

        }







        compactCalendar =
            findViewById<View>(R.id.compactcalendar_view) as CompactCalendarView

        //Por o primeiro dia da semana como Domingo
        compactCalendar!!.setFirstDayOfWeek(2)

        //Fazer com o calendario use somente a abriviação de 3 letras dos dias da semana
        compactCalendar!!.setUseThreeLetterAbbreviation(true)

        val listViewCalendarioEventos = findViewById<ListView>(R.id.listViewCalendario)

        adapterlisteventos =
            ArrayAdapter<String>(this, R.layout.row_calendario, listevent)


        GlobalScope.launch(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder().url("http://mindoverflow.amipca.xyz:60000/activities/").build()
            client.newCall(request).execute().use { response ->

                val string : String = response.body!!.string()

                val jsonObject = JSONObject(string)

                val jsonArrayArticles = jsonObject.getJSONArray("activities")
                for ( index in  0 until jsonArrayArticles.length()) {
                    val jsonArticle : JSONObject = jsonArrayArticles.get(index) as JSONObject
                    val atividade = Atividade.fromJson(jsonArticle)
                    atividades.add(atividade)

                    // var atividadedatainicio = formatter.format(atividade.dataInicio)
                    val formatadoratividade = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.UK)
                    val dataatividadeinicio = LocalDateTime.parse(atividade.dataInicio, formatadoratividade)
                    val atividademilis = dataatividadeinicio.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

                    var coranterior = coraleatoria
                    coraleatoria = coreslist.random()
                    if(coranterior==coraleatoria){coraleatoria = coreslist.random()}
                    compactCalendar!!.addEvent(Event(coraleatoria,atividademilis,atividade.nome + " - " + atividade.descricao))
                }

            }


            GlobalScope.launch (Dispatchers.Main){
                adapterlisteventos.notifyDataSetChanged()
            }

            //println(str)
        }



        //Declara uma MutableList de tipo Evento que contem um array com eventos
        var eventosapagar : MutableList<Event> = arrayListOf(
            Event(coraleatoria, 1607040400000L, "Teachers' Professional Day"),
            Event(coraleatoria, 1624273932000, "Tessdate"),
            Event(coraleatoria, 1624274932000, "Teste"),
            Event(coraleatoria, 1623082189198, "Dia 7"),
            Event(coraleatoria, 1626562800000, "Inicio Sao Joao"),
            Event(coraleatoria, 1626822000000, "Fim Sao Joao")
        )


        //Vê se as cor aleatoria nao se repete a anterior
        //e para cada item que esteja no array dos eventos adiciona um evento no calendario
        for(Event in eventosapagar){
            var coranterior = coraleatoria
            coraleatoria = coreslist.random()
            if(coranterior==coraleatoria){coraleatoria = coreslist.random()}
            compactCalendar!!.addEvent(Event(coraleatoria,Event.timeInMillis,Event.data))
        }

       findViewById<ImageView>(R.id.leftArrowImage).setOnClickListener{
           compactCalendar!!.scrollLeft()
       }

        findViewById<ImageView>(R.id.rightArrowImage).setOnClickListener{
            compactCalendar!!.scrollRight()
        }

        //Ao clicar-se na imageview5 irá terminar a activity
        findViewById<ImageView>(R.id.imageView5).setOnClickListener{
            finish()
        }


        val dataselecionada = findViewById<TextView>(R.id.dataselecionada)

        //Obtem a data atual no formato ISO
        val firstDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)

        //Converte a data atual para Ano-Mes-Dia
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = formatter.parse(firstDate)

        //Verifica qual é o mês atual em numero e converte o mesmo para extenso
        var mesinicial = ""
        when (DateTimeFormatter.ofPattern("MM").format(date)) {
            "01" -> mesinicial = "January"
            "02" -> mesinicial = "February"
            "03" -> mesinicial = "March"
            "04" -> mesinicial = "April"
            "05" -> mesinicial = "May"
            "06" -> mesinicial = "June"
            "07" -> mesinicial = "July"
            "08" -> mesinicial = "August"
            "09" -> mesinicial = "September"
            "10" -> mesinicial = "October"
            "11" -> mesinicial = "November"
            "12" -> mesinicial = "December"
        }
        //Declara diferentes formatações da data
        val anoinicial = DateTimeFormatter.ofPattern("yyyy").format(date)
        val dataatual = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(date)

        //Declara o texto do mesdocalendario mes em extenso e o ano
        findViewById<TextView>(R.id.mesdocalendario).text = "$mesinicial- $anoinicial"


        dataselecionada.text = dataatual



        compactCalendar!!.setListener(object : CompactCalendarView.CompactCalendarViewListener {
            //Após ser clicado numa data do calendário , limpa a ListView e de seguida lista os diferentes eventos presentes nesse dia
            override fun onDayClick(dateClicked: Date) {
                listViewCalendarioEventos.setAdapter(null)
                listevent.clear()
                listViewCalendarioEventos.adapter = adapterlisteventos


                //Cria uma formataçao separadamente para hora e data
                //obtem a data selecionada e converte a mesma de milisegundos para uma data e aplica o mesmo num textview
                var formatter = SimpleDateFormat("dd/MM/yyyy");
                val formatterhour = SimpleDateFormat("hh:mm")
                var dataAtualClickedString = formatter.format(Date(dateClicked.time))
                dataselecionada.text = dataAtualClickedString



                //Obtem os eventos do dia e por cada evento no dia selecionado , adiciona a hora e a informação do Evento na ListView
                for (Event in compactCalendar!!.getEvents(dateClicked.time)) {
                    listevent.add(formatterhour.format(Date(Event.timeInMillis)) + "        " + Event.data as String)
                    println(listevent)
                }
                //Notifica ao Adapter da ListView que Data foi modificada ,para atualizar a ListView
                adapterlisteventos.notifyDataSetChanged()

            }


            //Após ser mudado de mês , obtem qual é o primeiro dia do mes e formata o mesmo e insere o mesmo numa textview
            override fun onMonthScroll(firstDayOfNewMonth: Date) {
                findViewById<TextView>(R.id.mesdocalendario).text = dateFormatMonth.format(firstDayOfNewMonth)
            }
        })





    }
}

