package com.mindoverflow.scoutshub.ui.Atividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.mindoverflow.scoutshub.MapsActivity
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.models.Atividade
import com.mindoverflow.scoutshub.ui.Login.FrontPage

class AvailableActivitiesActivity : AppCompatActivity() {

    var cardAvalableActivities : MutableList<Atividade> = arrayListOf()
    lateinit var content : Atividade

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_activities)

        var listViewCards = findViewById<ListView>(R.id.listViewActivities)
        var availableActivitesAdapter = AvailableActivitesAdapter()
        listViewCards.adapter = availableActivitesAdapter
        supportActionBar?.hide()

        cardAvalableActivities.add(Atividade(idAtividade = 1,
                                            nome = "DockerActivity",
                                            tipo  = "Programar",
                                            imagem = "https://img.ibxk.com.br/2020/09/07/07121720532021.jpg",
                                            descricao = "Divertir" ,
                                            custo = "50",
                                            local = "Braga",
                                            localInicio   = "Braga" ,
                                            localFim   = "miami",
                                            coordenadas  = "1231311516186111dw61da1d5wa1d",
                                            urlLocal     = "https://www.google.com/maps/universe",
                                            dataInicio  = "1/5/2035",
                                            dataFim = "1/6/4565"))

        cardAvalableActivities.add(Atividade(idAtividade = 2,
                                            nome = "Linux",
                                            tipo  = "BashFiles",
                                            imagem = "https://img.wallpapersafari.com/desktop/1280/1024/53/59/W6u2aO.jpg",
                                            descricao = "Divertir" ,
                                            custo = "50",
                                            local = "Braga",
                                            localInicio   = "Braga" ,
                                            localFim   = "Los Angeles",
                                            coordenadas  = "1231311516186111dw61da1d5wa1d",
                                            urlLocal     = "https://www.google.com/maps/universe",
                                            dataInicio  = "1/5/2035",
                                            dataFim = "1/6/4565"))

        cardAvalableActivities.add(Atividade(idAtividade = 3,
                                            nome = "Kotlin",
                                            tipo  = "kt files",
                                            imagem = "https://img.wallpapersafari.com/desktop/1280/1024/17/52/9Sl4iQ.jpg",
                                            descricao = "Divertir" ,
                                            custo = "50",
                                            local = "Braga",
                                            localInicio   = "Braga" ,
                                            localFim   = "Tokyo",
                                            coordenadas  = "1231311516186111dw61da1d5wa1d",
                                            urlLocal     = "https://www.google.com/maps/universe",
                                            dataInicio  = "1/5/2035",
                                            dataFim = "1/6/4565"))

    }

    inner class AvailableActivitesAdapter : BaseAdapter(){
        override fun getCount(): Int {
            return cardAvalableActivities.size
        }

        override fun getItem(position: Int): Any {
            return cardAvalableActivities[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_see_atividades, parent, false)

            var cardTitle = rowView.findViewById<TextView>(R.id.cardTitleActivities)
            var cardType = rowView.findViewById<TextView>(R.id.typeTextView)
            var cardBeginData = rowView.findViewById<TextView>(R.id.BeginDataTextView)
            var cardOverData = rowView.findViewById<TextView>(R.id.OverDataTextView)

            cardTitle.text = cardAvalableActivities[position].nome.toString()
            cardType.text = cardAvalableActivities[position].tipo.toString()
            cardBeginData.text = cardAvalableActivities[position].dataInicio.toString()
            cardOverData.text = cardAvalableActivities[position].dataFim.toString()

            val cardImageView = rowView.findViewById<ImageView>(R.id.imageViewCard)

            cardImageView.setOnClickListener{
                val intent = Intent(this@AvailableActivitiesActivity, MapsActivity::class.java)
                intent.putExtra("LOCATION_LATITUDE", 41.50683511835558)
                intent.putExtra("LOCATION_LONGITUDE", -8.335268312668875)
                startActivity(intent)
            }

            return  rowView

        }
    }
}