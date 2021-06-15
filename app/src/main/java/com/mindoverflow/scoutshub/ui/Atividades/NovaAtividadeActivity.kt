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

        Calendar.getInstance(Locale.FRANCE).getFirstDayOfWeek()

        //Declara um formatador de data , obtem a data atual no formato ISO e formata para a formatação definida no formatador
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = formatter.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE))
        dataAtividade = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(date)


        val editTextNomeCompleto = findViewById<EditText>(R.id.editTextNomeCompleto)
        val editTextDescricao = findViewById<EditText>(R.id.editTextDescricao)

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

                var hourfinal = "$hour:$minute"
                val timePickerValueTextView = findViewById<TextView>(R.id.textViewHora);
                timePickerValueTextView.setText(hourfinal);
            }

            val now = Calendar.getInstance();
            var hour = now.get(Calendar.HOUR)
            if (hour <10){hour = Integer.parseInt("0$hour")}
            var minute = now.get(Calendar.MINUTE)

            // Whether show time in 24 hour format or not.
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

            if(editTextNomeCompleto.text != null && editTextDescricao.text != null){

                //Se não forem null é criado um Intent com o contexto como esta Activity e a proxima activity como classe
                val intent = Intent(this, ConfirmNewActivity::class.java)
                //De seguida é colocado as strings de informação (data , nome e descricao) dentro da string com os seus devidos ids/nomes
                intent.putExtra("dataAtividade", dataAtividade)
                intent.putExtra("nomeCompleto", editTextNomeCompleto.text.toString())
                intent.putExtra("descricao", editTextDescricao.text.toString())
                //Inicia a proxima actividade
                this.startActivity(intent)

            }

        }
    }

}