package com.mindoverflow.scoutshub.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mindoverflow.scoutshub.R


class NovaAtividadeActivity : AppCompatActivity() {

    var dataAtividade : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_atividade)

        val texto = findViewById<TextView>(R.id.textView2)




        val calendarView = findViewById<CalendarView>(R.id.calendarView)

        calendarView.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            val realmonth = month +1
            dataAtividade = "$dayOfMonth/$realmonth/$year"
        })


        val editTextNomeCompleto = findViewById<EditText>(R.id.editTextNomeCompleto)
        val editTextDescricao = findViewById<EditText>(R.id.editTextDescricao)


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