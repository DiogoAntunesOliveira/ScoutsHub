package com.mindoverflow.scoutshub.ui.Atividades

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.domain.Event
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.SavedUserData
import com.mindoverflow.scoutshub.models.Atividade
import com.mindoverflow.scoutshub.models.Participante
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class CalendarioAtividadesActivity : AppCompatActivity() {
    var atividades: MutableList<Atividade> = arrayListOf()
    lateinit var adapterlisteventos : CalendarioAtividadesAdapter
    var eventos: MutableList<Event> = arrayListOf()
    var compactCalendar: CompactCalendarView? = null
    private val dateFormatMonth = SimpleDateFormat("MMMM- yyyy", Locale.getDefault())

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






        //Declara o calendario
        compactCalendar =
            findViewById<View>(R.id.compactcalendar_view) as CompactCalendarView

        //Por o primeiro dia da semana como Domingo
        compactCalendar!!.setFirstDayOfWeek(2)

        //Fazer com o calendario use somente a abrivia????o de 3 letras dos dias da semana
        compactCalendar!!.setUseThreeLetterAbbreviation(true)

        val listViewCalendarioEventos = findViewById<ListView>(R.id.listViewCalendario)

        adapterlisteventos = CalendarioAtividadesAdapter()
        listViewCalendarioEventos.adapter = adapterlisteventos

        GlobalScope.launch(Dispatchers.IO) {
            val client = OkHttpClient()

            var listapart: MutableList<Participante> = arrayListOf()

            var id_utilizador = SavedUserData.id_utilizador

            //Indica qual endere??o para fazer pedidos HTTP
            val participanterequest =
               Request.Builder()
               .url("http://mindoverflow.amipca.xyz:60000/participant/utilizador/$id_utilizador/")
               .build()

            //Obtem os todos as atividades que o utilizador est?? a participar e envia-os para uma lista
            client.newCall(participanterequest).execute().use { response ->
                val string: String = response.body!!.string()

                val jsonObjectTotal = JSONObject(string)


                val jsonparticipante: JSONArray =
                    jsonObjectTotal.getJSONArray("participante") as JSONArray
                for (index in 0 until jsonparticipante.length()) {
                    val getjson: JSONObject = jsonparticipante.get(index) as JSONObject
//                        var iconTempo = if (!jsonweather.isNull("id_atividades")){ jsonweather.getInt("id_atividades")} else null
                    val calendarioparticipante = Participante.fromJson(getjson)
                    if (calendarioparticipante.confirmacao != 0 &&
                        calendarioparticipante.confirmacao != null) {

                        listapart.add(calendarioparticipante)
                        println(listapart)
                    }
                }
            }

            //Obtem todas as informa????es das atividades que o utilizador participa e converte os mesmos para eventos no calendario
            val request = Request.Builder()
                .url("http://mindoverflow.amipca.xyz:60000/activities/")
                .build()
            client.newCall(request).execute().use { response ->

                val string : String = response.body!!.string()

                val jsonObject = JSONObject(string)
                val jsonArrayAtividades = jsonObject.getJSONArray("activities")

                for ( index in  0 until jsonArrayAtividades.length()) {
                    val atividade = Atividade.fromJson(string, index)
                    atividades.add(atividade)
                    for(size in 0 until listapart.size){
                        if(atividade.idAtividade == listapart[size].id_atividade){

                        val formatadoratividade = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.UK)
                        val dataatividadeinicio = LocalDateTime.parse(atividade.dataInicio, formatadoratividade)
                        val atividademilis = dataatividadeinicio.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

                            var coranterior = coraleatoria
                            coraleatoria = coreslist.random()
                            if(coranterior==coraleatoria){coraleatoria = coreslist.random()}
                            compactCalendar!!.addEvent(Event(coraleatoria,atividademilis,atividade.nome + "*" + atividade.descricao))
                        }}


                }
                response.body!!.close()
            }


            GlobalScope.launch (Dispatchers.Main){
                adapterlisteventos.notifyDataSetChanged()
            }




            //println(str)
        }


        //Para testes declara uma MutableList de tipo Evento que contem um array com eventos
 /*       var eventosapagar : MutableList<Event> = arrayListOf(
            Event(coraleatoria, 1607040400000L, "Teachers' Professional Day * Welcome to Teachers Day"),
            Event(coraleatoria, 1624273932000, "Tessdate * Description Test"),
            Event(coraleatoria, 1624274932000, "Teste * MegaTest"),
            Event(coraleatoria, 1623082189198, "Dia 7 * Ja passou"),
            Event(coraleatoria, 1626562800000, "Inicio Sao Joao * Um mes a frente"),
            Event(coraleatoria, 1626822000000, "Fim Sao Joao * Um mes a frente")
        )


        //V?? se as cor aleatoria nao se repete a anterior
        //e para cada item que esteja no array dos eventos adiciona um evento no calendario
        for(Event in eventosapagar){
            var coranterior = coraleatoria
            coraleatoria = coreslist.random()
            if(coranterior==coraleatoria){coraleatoria = coreslist.random()}
            compactCalendar!!.addEvent(Event(coraleatoria,Event.timeInMillis,Event.data))
        }*/

       findViewById<ImageView>(R.id.leftArrowImage).setOnClickListener{
           compactCalendar!!.scrollLeft()
       }

        findViewById<ImageView>(R.id.rightArrowImage).setOnClickListener{
            compactCalendar!!.scrollRight()
        }

        //Ao clicar-se na imageview5 ir?? terminar a activity
        findViewById<ImageView>(R.id.imageView5).setOnClickListener{
            finish()
        }



        //Obtem a data atual no formato ISO
        val firstDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)

        //Converte a data atual para Ano-Mes-Dia
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = formatter.parse(firstDate)

        //Verifica qual ?? o m??s atual em numero e converte o mesmo para extenso
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
        //Declara diferentes formata????es da data
        val anoinicial = DateTimeFormatter.ofPattern("yyyy").format(date)
        val dataatual = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(date)

        //Declara o texto do mesdocalendario mes em extenso e o ano
        findViewById<TextView>(R.id.mesdocalendario).text = "$mesinicial- $anoinicial"





        compactCalendar!!.setListener(object : CompactCalendarView.CompactCalendarViewListener {
            //Ap??s ser clicado numa data do calend??rio , limpa a ListView e de seguida lista os diferentes eventos presentes nesse dia
            override fun onDayClick(dateClicked: Date) {
                listViewCalendarioEventos.setAdapter(null)
                eventos.clear()
                listViewCalendarioEventos.adapter = adapterlisteventos


                //Cria uma formata??ao separadamente para hora e data
                //obtem a data selecionada e converte a mesma de milisegundos para uma data e aplica o mesmo num textview
                var formatter = SimpleDateFormat("dd/MM/yyyy");
                var dataAtualClickedString = formatter.format(Date(dateClicked.time))



                //Obtem os eventos do dia e por cada evento no dia selecionado , adiciona a hora e a informa????o do Evento na ListView
                for (Event in compactCalendar!!.getEvents(dateClicked.time)) {
                    eventos.add(Event(Event.color,Event.timeInMillis,Event.data))
                    println(eventos)
                    println(eventos.size)
                }
                //Notifica ao Adapter da ListView que Data foi modificada ,para atualizar a ListView
                adapterlisteventos.notifyDataSetChanged()

            }


            //Ap??s ser mudado de m??s , obtem qual ?? o primeiro dia do mes e formata o mesmo e insere o mesmo numa textview
            override fun onMonthScroll(firstDayOfNewMonth: Date) {
                findViewById<TextView>(R.id.mesdocalendario).text = dateFormatMonth.format(firstDayOfNewMonth)
            }
        })


    }

    //Declara uma classe adapter
    inner class CalendarioAtividadesAdapter : BaseAdapter() {
        //Obtem quantos items o eventos cont??m
        override fun getCount(): Int {
            return eventos.size
        }

        //Obtem o item associado com determinada posi????o
        override fun getItem(position: Int): Any {
            return eventos[position]

        }

        //Obtem o id da linha associada com uma posi??ao especifica na listView
        override fun getItemId(position: Int): Long {
            return 0
        }

        //Obtem a view que vai ser utilizada para realizar a listView
        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            //?? dado inflate da row_calendarios
            val rowView = layoutInflater.inflate(R.layout.row_calendario, parent, false)
            val formatterhour = SimpleDateFormat("hh:mm")

            val dateEventlist = rowView.findViewById<TextView>(R.id.dateEventList)
            val titleEventList = rowView.findViewById<TextView>(R.id.titleEventList)
            val descEventList = rowView.findViewById<TextView>(R.id.descEventList)
            val colorTintEventList = rowView.findViewById<ImageView>(R.id.colorTintEventList)

            //Obtem a data do evento e separa-o em uma lista com 2 strings
            val datamaster = eventos[position].data.toString()
            val datadescricaotitulo : List<String> = datamaster.split("*")

            val expandableView = rowView.findViewById<ConstraintLayout>(R.id.expandableView);
            val buttonExpand = rowView.findViewById<Button>(R.id.buttonExpand);
            val cardView = rowView.findViewById<CardView>(R.id.card_view);

            buttonExpand.setOnClickListener(View.OnClickListener {
                if (expandableView.getVisibility() === View.GONE) {
                    TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                    expandableView.setVisibility(View.VISIBLE)
                    buttonExpand.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                } else {
                    TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                    expandableView.setVisibility(View.GONE)
                    buttonExpand.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                }
            })

            dateEventlist.text = formatterhour.format(Date(eventos[position].timeInMillis))
            titleEventList.text = datadescricaotitulo[0]
            descEventList.text = datadescricaotitulo[1]
            colorTintEventList.setColorFilter(eventos[position].color)



            return rowView
        }

    }

}

