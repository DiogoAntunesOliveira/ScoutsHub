package com.mindoverflow.scoutshub.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mindoverflow.scoutshub.MainActivity
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.ui.MateriaisFragment.Companion.TESTE_DIC_KEY
import com.mindoverflow.scoutshub.ui.MateriaisFragment.Companion.dataAtividade
import com.mindoverflow.scoutshub.ui.MateriaisFragment.Companion.descricao
import com.mindoverflow.scoutshub.ui.MateriaisFragment.Companion.nomeCompleto


class ConfirmNewActivity : AppCompatActivity() , AdapterView.OnItemSelectedListener{

    lateinit var spinner : Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_atividade)


        val dataAtividadePreviousActivity = intent.getStringExtra("dataAtividade" )
        val nomeCompletoPreviousActivity = intent.getStringExtra("nomeCompleto" )
        val descricaoPreviousActivity = intent.getStringExtra("descricao" )
        val selectedPreviousRowsID = intent.getSerializableExtra("IDs" )

        val equipamento = findViewById<Button>(R.id.equipamento)
        val buttonAddNewActivity = findViewById<Button>(R.id.buttonAddNewActivity)

        equipamento.visibility = View.VISIBLE
        buttonAddNewActivity.visibility = View.VISIBLE

        var spinner = findViewById<Spinner>(R.id.departamento)
        val adapter : ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this, R.array.departamentos, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        dataAtividade = dataAtividadePreviousActivity.toString()
        nomeCompleto = nomeCompletoPreviousActivity.toString()
        descricao = descricaoPreviousActivity.toString()

        findViewById<TextView>(R.id.dataAtividade).text = dataAtividade
        findViewById<TextView>(R.id.nomeCompleto).text = nomeCompleto



        val googleStreetLink = findViewById<EditText>(R.id.googleLink)



        equipamento.setOnClickListener{

            equipamento.visibility = View.INVISIBLE
            buttonAddNewActivity.visibility = View.INVISIBLE

            val fragment: Fragment = MateriaisFragment()
            val bundle = Bundle()
            bundle.putString(TESTE_DIC_KEY, "valor enviado")
            bundle.putString(nomeCompleto , nomeCompleto)
            bundle.putString(descricao , descricao)
            bundle.putString(dataAtividade , dataAtividade)
            fragment.arguments = bundle
            val fragmentManager: FragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.confirmarConstraint, fragment).commit()

        }

        buttonAddNewActivity.setOnClickListener{
            println(selectedPreviousRowsID)
            println(dataAtividade)
            println(nomeCompleto)
            println(descricao)
            val intent = Intent(this,MainActivity::class.java)
            this.startActivity(intent)
        }

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        parent.getItemAtPosition(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        parent.getItemAtPosition(0)
    }


}
