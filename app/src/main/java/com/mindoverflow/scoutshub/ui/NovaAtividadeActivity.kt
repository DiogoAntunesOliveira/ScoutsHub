package com.mindoverflow.scoutshub.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.mindoverflow.scoutshub.R
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class NovaAtividadeActivity : AppCompatActivity() {

    var dataAtividade : String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_atividade)

        supportActionBar!!.hide()

        val textocomdate = findViewById<TextView>(R.id.diaEscolhido)

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = formatter.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE))
        val dataatual = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(date)
        textocomdate.text = dataatual
        val editTextNomeCompleto = findViewById<EditText>(R.id.editTextNomeCompleto)
        val editTextDescricao = findViewById<EditText>(R.id.editTextDescricao)

        val calendarView = findViewById<com.applandeo.materialcalendarview.CalendarView>(R.id.calendarView)


        calendarView.setOnDayClickListener { eventDay ->
            val clickedDateMilis: Long = eventDay.calendar.timeInMillis

            val datemilitodate = Date(clickedDateMilis)
            val dateformatter = SimpleDateFormat("dd/MM/yyyy")
            textocomdate.text = dateformatter.format(datemilitodate)

            dataAtividade = dateformatter.format(datemilitodate)
            println(dataAtividade)
        }


        findViewById<Button>(R.id.buttonNextNewActivity).setOnClickListener{

            if(editTextNomeCompleto.text != null && editTextDescricao.text != null){

                val intent = Intent(this,ConfirmNewActivity::class.java)
                            intent.putExtra("dataAtividade", dataAtividade)
                            intent.putExtra("nomeCompleto", editTextNomeCompleto.text.toString())
                            intent.putExtra("descricao", editTextDescricao.text.toString())
                            this.startActivity(intent)

            }




        }

    }

    companion object {

    }
}