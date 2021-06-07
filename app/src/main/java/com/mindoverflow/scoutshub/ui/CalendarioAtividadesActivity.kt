package com.mindoverflow.scoutshub.ui

import android.content.Intent
import android.graphics.Color
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.domain.Event
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.models.Atividade
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.time.LocalDateTime
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

        supportActionBar!!.hide()

        findViewById<TextView>(R.id.textView5).setText("<")

        var listevent: MutableList<String> = arrayListOf()


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
        var coraleatoria = coreslist.random()

        findViewById<ImageView>(R.id.imageadicionar).setOnClickListener {
            val intent = Intent(this,NovaAtividadeActivity::class.java)
            startActivity(intent)

        }



        compactCalendar =
            findViewById<View>(R.id.compactcalendar_view) as CompactCalendarView
        compactCalendar!!.setUseThreeLetterAbbreviation(true)

        val listViewCalendarioEventos = findViewById<ListView>(R.id.listViewCalendario)

        adapterlisteventos =
            ArrayAdapter<String>(this, R.layout.row_calendario, listevent)

        var eventosapagar : MutableList<Event> = arrayListOf(
            Event(coraleatoria, 1607040400000L, "Teachers' Professional Day"),
            Event(coraleatoria, 1624273932000, "Tessdate"),
            Event(coraleatoria, 1624274932000, "Teste"),
            Event(coraleatoria, 1623082189198, "Dia 7"),
            Event(coraleatoria, 1626562800000, "Inicio Sao Joao"),
            Event(coraleatoria, 1626822000000, "Fim Sao Joao")
        )


        for(Event in eventosapagar){
          var coranterior = coraleatoria
            coraleatoria = coreslist.random()
           if(coranterior==coraleatoria){coraleatoria = coreslist.random()}
            compactCalendar!!.addEvent(Event(coraleatoria,Event.timeInMillis,Event.data))
        }



        findViewById<ImageView>(R.id.imageView5).setOnClickListener{
            finish()
        }


        val dataselecionada = findViewById<TextView>(R.id.dataselecionada)

        val firstDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = formatter.parse(firstDate)
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
        val anoinicial = DateTimeFormatter.ofPattern("yyyy").format(date)
        val dataatual = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(date)
        findViewById<TextView>(R.id.mesdocalendario).text = "$mesinicial- $anoinicial"

        dataselecionada.text = dataatual



        compactCalendar!!.setListener(object : CompactCalendarView.CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date) {
                listViewCalendarioEventos.setAdapter(null)
                listevent.clear()
                listViewCalendarioEventos.adapter = adapterlisteventos


                var formatter = SimpleDateFormat("dd/MM/yyyy");
                val formatterhour = SimpleDateFormat("hh:mm")
                var dataAtualClickedString = formatter.format(Date(dateClicked.time))
                dataselecionada.text = dataAtualClickedString



                for (Event in compactCalendar!!.getEvents(dateClicked.time)) {
                    listevent.add(formatterhour.format(Date(Event.timeInMillis)) + "        " + Event.data as String)
                    println(listevent)
                }
                adapterlisteventos.notifyDataSetChanged()

            }


            override fun onMonthScroll(firstDayOfNewMonth: Date) {
                findViewById<TextView>(R.id.mesdocalendario).text =
                    dateFormatMonth.format(firstDayOfNewMonth)
            }
        })





    }
}

