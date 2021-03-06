package com.mindoverflow.scoutshub.ui.Atividades

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mindoverflow.scoutshub.MainActivity
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.models.Atividade
import com.mindoverflow.scoutshub.models.Equipa
import com.mindoverflow.scoutshub.ui.MateriaisFragment
import com.mindoverflow.scoutshub.ui.MateriaisFragment.Companion.TESTE_DIC_KEY
import com.mindoverflow.scoutshub.ui.MateriaisFragment.Companion.dataAtividade
import com.mindoverflow.scoutshub.ui.MateriaisFragment.Companion.descricao
import com.mindoverflow.scoutshub.ui.MateriaisFragment.Companion.nomeCompleto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class CreateNewActivity : AppCompatActivity() , AdapterView.OnItemSelectedListener{

    lateinit var spinner : Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_atividade)

        //Esconder barra superior
        supportActionBar!!.hide()

        //Obter as informações da activity anterior
        val dataAtividadePreviousActivity = intent.getStringExtra("dataAtividade")
        val nomeCompletoPreviousActivity = intent.getStringExtra("nomeCompleto")
        val descricaoPreviousActivity = intent.getStringExtra("descricao")
        val selectedPreviousRowsID = intent.getSerializableExtra("IDs")


        val buttonAddNewActivity = findViewById<Button>(R.id.buttonAddNewActivity)

       /* var departamentos: MutableList<String> = arrayListOf()

        //Fazer os botões visiveis
      //  equipamento.visibility = View.VISIBLE
        buttonAddNewActivity.visibility = View.VISIBLE

        GlobalScope.launch(Dispatchers.IO) {
            val client = OkHttpClient()
            val atividadesvalidasinforequest = Request.Builder()
                .url("http://mindoverflow.amipca.xyz:60000/team")
                .build()
            client.newCall(atividadesvalidasinforequest).execute().use { response ->
                val string: String = response.body!!.string()
                println(string)
                val jsonObject = JSONObject(string)
                val jsonArrayTeams = jsonObject.getJSONArray("teams") as JSONArray

                for (index in 0 until jsonArrayTeams.length()) {
                    val jsonTeam : JSONObject = jsonArrayTeams.get(index) as JSONObject
                    val equipa = Equipa.fromJson(jsonTeam)
                    departamentos.add("Departamento " + equipa.nome_equipa!!)
                }

            } }*/

        //Declarar o spinner
        val spinner = findViewById<Spinner>(R.id.departamento)

        //Formata o ArrayAdapter utilizando o modelo simple_spinner_item
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this,
            R.array.departamentos,
            android.R.layout.simple_spinner_item
        )
        //Define o como o adapter era ser mostrado e define quem e o adapter do spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        //Converte a data anterior de String? para String para facilidade de codigo
        dataAtividade = dataAtividadePreviousActivity.toString()
        nomeCompleto = nomeCompletoPreviousActivity.toString()
        descricao = descricaoPreviousActivity.toString()


        val parserAtividade = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(dataAtividade)
        val formatadorAtividade = SimpleDateFormat("dd/MM/yyyy HH:mm").format(parserAtividade)
        findViewById<TextView>(R.id.dataAtividade).text = formatadorAtividade

        findViewById<TextView>(R.id.nomeCompleto).text = nomeCompleto


        val longitude = findViewById<EditText>(R.id.longitudeActivity)
        val latitude = findViewById<EditText>(R.id.latitudeActivity)
        val localizacao = findViewById<EditText>(R.id.localizacao)

        //Ao clicar no botão equipamento os botões ficam invisiveis
      /*  equipamento.setOnClickListener {

            equipamento.visibility = View.INVISIBLE
            buttonAddNewActivity.visibility = View.INVISIBLE

            //Associa o fragment MateriaisFragment com a variavel e cria um Bundle para colocar informações e de seguida
            //insere as informaçoes de activities anteriores nesse bundle
            val fragment: Fragment = MateriaisFragment()
            val bundle = Bundle()
            bundle.putString(TESTE_DIC_KEY, "valor enviado")
            bundle.putString(nomeCompleto, nomeCompleto)
            bundle.putString(descricao, descricao)
            bundle.putString(dataAtividade, dataAtividade)
            //Adiciona o bundle como argumentos na inicializaçao do fragment e declara o gerenciador de fragments
            fragment.arguments = bundle
            val fragmentManager: FragmentManager = supportFragmentManager
            //Declara as operações para iniciar o fragment nestas são ,substitui o constraintlayout pelo fragment e no final inicia o fragment
            fragmentManager.beginTransaction().replace(R.id.confirmarConstraint, fragment).commit()

        }*/


        //Faz um post um request de POST com as informações anteriores
        buttonAddNewActivity.setOnClickListener {
            println(selectedPreviousRowsID)
            println(dataAtividade)
            println(nomeCompleto)
            println(descricao)
            GlobalScope.launch(Dispatchers.IO) {
                lateinit var idjson: String

                val httpclient = OkHttpClient()

                val novaAtividade = Atividade(
                    null,
                    nomeCompleto,
                    "",
                    "https://www.decorfacil.com/wp-content/uploads/2017/03/20171011fachada-casa-simples-pequena-99-960x600.jpg",
                    descricao,
                    "0",
                    localizacao.text.toString(),
                    "",
                    "",
                    latitude.text.toString(),
                    longitude.text.toString(),
                    dataAtividade,
                    dataAtividade
                )

                val requestBody = RequestBody.create(
                    "application/json; charset=utf-8".toMediaTypeOrNull(),
                    novaAtividade.toJson().toString()
                )
                Log.d("novaatividade", novaAtividade.toJson().toString())
                val postrequest = Request.Builder()
                    .url("http://mindoverflow.amipca.xyz:60000/activities/")
                    .post(requestBody)
                    .build()
                httpclient.newCall(postrequest).execute().use { response ->
                    Log.d("novaatividade", response.message)
                    Log.d("novaatividade", response.body.toString())
                    Log.d("novaatividade", response.code.toString())
                    GlobalScope.launch(Dispatchers.Main) {
                        if (response.message == "OK") {

                        }
                    }
                }
            }

                Toast.makeText(baseContext, "Atividade Criada", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent)
            }


        }

        //A ser selecionado um item no AdapterView é o obtido a informação e posiçao do mesmo
        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            parent.getItemAtPosition(position)
        }

        //Se nada for selecionado no AdapterView , será escolhido o item da posiçao 0
        override fun onNothingSelected(parent: AdapterView<*>) {
            parent.getItemAtPosition(0)
        }


}
