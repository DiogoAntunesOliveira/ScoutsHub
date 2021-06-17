package com.mindoverflow.scoutshub.ui.Atividades

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.mindoverflow.scoutshub.R
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class NovaAtividadeActivity : AppCompatActivity() {

    var dataHoraAtividade : String = ""
   var dataAtividade : String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_atividade)

        supportActionBar!!.hide()

        Calendar.getInstance(Locale.FRANCE).getFirstDayOfWeek()



        val editTextNomeActividade = findViewById<EditText>(R.id.editTextNomeCompleto)
        val editTextDescricaoAtividade = findViewById<EditText>(R.id.editTextDescricao)

        val calendarView = findViewById<MaterialCalendarView>(R.id.calendarView)

        //Ao clicar-se numa data no calendario é obtida a data em milisegundos da mesma e convertido para data,
        // esta mesma é depois enviada para uma variavel
        calendarView.setOnDateChangedListener { widget, date, selected ->
            val datemilitodate = Date(date.calendar.timeInMillis)
            val dateformatter = SimpleDateFormat("dd/MM/yyyy")

            dataAtividade = dateformatter.format(datemilitodate)
            findViewById<TextView>(R.id.textViewData).setText(dataAtividade)
        }

        findViewById<Button>(R.id.buttonHora).setOnClickListener{

            // Cria uma instancia OnTimeSetListener , a instancia ira desaparecer apos clicar-se no okay ao selecionar a hora
            val onTimeSetListener = TimePickerDialog.OnTimeSetListener() { timePicker: TimePicker, hour: Int, minute: Int ->
                var hourfixed = hour.toString()
                var minutefixed = minute.toString()
                if (hour < 10 ){hourfixed = "0$hour"
                }
                if (minutefixed == "0") {minutefixed= minutefixed + "0"}
                var hourfinal = hourfixed+":"+minutefixed
                val timePickerValueTextView = findViewById<TextView>(R.id.textViewHora);
                timePickerValueTextView.setText(hourfinal);
            }

            val now = Calendar.getInstance();
            var hour = now.get(Calendar.HOUR)
            var minute = now.get(Calendar.MINUTE)

            // Define se prefere tempo em 24 horas ou em 12
            val is24Hour = true;

            val timePickerDialog = TimePickerDialog(this,
                onTimeSetListener,
                hour,
                minute,
                is24Hour);

            timePickerDialog.show();
        }

        //Ao clicar-se no botão Next é verificado se os dois editTexts são diferentes de null
        findViewById<Button>(R.id.buttonNextNewActivity).setOnClickListener{

            if(editTextNomeActividade.text != null && editTextDescricaoAtividade.text != null || editTextNomeActividade.text.toString() != "" && editTextDescricaoAtividade.text.toString() != ""  ){


                val notprocesseddata = (findViewById<TextView>(R.id.textViewData).text.toString() + findViewById<TextView>(R.id.textViewHora).text.toString())
                val dataHoraParser = SimpleDateFormat("dd/MM/yyyyHH:mm").parse(notprocesseddata)
                val dataHoraFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(dataHoraParser)

                dataHoraAtividade = dataHoraFormatter



                //Se não forem null é criado um Intent com o contexto como esta Activity e a proxima activity como classe
                val intent = Intent(this, ConfirmNewActivity::class.java)
                //De seguida é colocado as strings de informação (data , nome e descricao) dentro da string com os seus devidos ids/nomes
                intent.putExtra("dataAtividade", dataHoraAtividade)
                intent.putExtra("nomeCompleto", editTextNomeActividade.text.toString())
                intent.putExtra("descricao", editTextDescricaoAtividade.text.toString())
                //Inicia a proxima actividade
                this.startActivity(intent)

            }

        }
    }

}