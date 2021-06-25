package com.mindoverflow.scoutshub.ui.Instrucoes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.models.Instrucao
import com.mindoverflow.scoutshub.models.InstrucaoUtilizador
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class StepsActivityShowData: AppCompatActivity() {

    var mExampleList : MutableList<Instrucao> = arrayListOf()
    private var mListView: ListView? = null
    private var mAdapter: StepsActivityAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instrucoes_steps_showdata)

        supportActionBar!!.hide()

        val addButton = findViewById<ImageButton>(R.id.btnAddInstruction)
        val backButton = findViewById<ImageButton>(R.id.btnBack)

        backButton.setOnClickListener {
            finish()
        }

        //encontrar os ids do xml
        mListView = findViewById(R.id.list)
        mAdapter = StepsActivityAdapter()
        mListView?.adapter = mAdapter

        //get request para receber todos os catalogos existentes na api e envia os para o array catalogos
        GlobalScope.launch(Dispatchers.IO) {
            val client = OkHttpClient()
            val requestInstrucoes = Request.Builder()
                .url("http://mindoverflow.amipca.xyz:60000/intructions")
                .build()
            client.newCall(requestInstrucoes).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Toast.makeText(this@StepsActivityShowData, "Algo correu mal: " + e.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call, response: Response) {
                    mExampleList.clear()

                    val jsStr: String = response.body!!.string()

                    val jsonArrayInstrucoes = JSONArray(jsStr)

                    for (index in 0 until jsonArrayInstrucoes.length()) {
                        val jsonArticle: JSONObject = jsonArrayInstrucoes.get(index) as JSONObject
                        val intrucao = Instrucao.fromJson(jsonArticle.toString(), index)
                        mExampleList.add(intrucao)
                    }
                    GlobalScope.launch(Dispatchers.Main) {
                        mAdapter!!.notifyDataSetChanged()
                    }
                }
            })
        }

        addButton.setOnClickListener {
            val intent = Intent(this, StepsActivityEditData::class.java)
            startActivity(intent)
            finish()
        }
    }

    inner class StepsActivityAdapter : BaseAdapter() {
        override fun getCount(): Int {

            //tamanho do array catalogos
            return mExampleList.size
        }

        override fun getItem(position: Int): Any {

            //posicao do array catalogos
            return mExampleList[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.activity_instrucoes_steps_rows, parent, false)

            //declaração das textViews
            val textViewNome = rowView.findViewById<TextView>(R.id.showTitle)
            val textViewCat = rowView.findViewById<TextView>(R.id.showDescription)

            //enviar os dados da classe utilizador para as textViews
            textViewNome.text = mExampleList[position].titulo
            textViewCat.text = mExampleList[position].descricao

            return rowView
        }
    }
}