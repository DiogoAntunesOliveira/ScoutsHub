package com.mindoverflow.scoutshub.ui.Atividades

// author : José Diogo Antunes Oliveira
// data : 16/6/2021
// description : Show available activities and connect to MapsActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import bit.linux.tinyspacex.Helpers.getURL
import com.mindoverflow.scoutshub.MapsActivity
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.SavedUserData
import com.mindoverflow.scoutshub.adapter.CustomAdapter
import com.mindoverflow.scoutshub.models.Atividade
import com.mindoverflow.scoutshub.models.Participante
import com.mindoverflow.scoutshub.ui.Login.FrontPage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class AvailableActivitiesActivity : AppCompatActivity() {

    var cardAvalableActivities : MutableList<Atividade> = arrayListOf()
    lateinit var content : Atividade

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_activities)

        var listViewCards = findViewById<ListView>(R.id.listViewActivities)
        var availableActivitesAdapter = AvailableActivitesAdapter()
        supportActionBar?.hide()

        GlobalScope.launch(Dispatchers.IO){
            val client = OkHttpClient()
            val request = Request.Builder().url(getURL()+ "/activities").build()

            // Adding activities to the recycler view
            GlobalScope.launch(Dispatchers.IO) {
                // Make srequest to api "/activities"
                client.newCall(request).execute().use { response ->
                    // convert json in string
                    val activitiesString = (response.body!!.string())
                    // Take the string and found the json array
                    val jsonArray = JSONObject(activitiesString).getJSONArray("activities")

                    // Iterate until lengh of jsonArray
                    for (index in 0 until jsonArray.length()) {
                        // transform previous jsonArray to Atividade object
                        val atividade = Atividade.fromJson(activitiesString, index)
                        // add to our mutable array
                        cardAvalableActivities.add(atividade)
                    }

                }
                GlobalScope.launch(Dispatchers.Main){
                    availableActivitesAdapter.notifyDataSetChanged()
                }
            }

        }
        // Create content
        listViewCards.adapter = availableActivitesAdapter

        findViewById<ImageView>(R.id.backActivitiesButton).setOnClickListener{
            finish()
        }

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
            val cardImageView = rowView.findViewById<ImageView>(R.id.imageViewCard)
            val buttonAcepetRequest = rowView.findViewById<ImageView>(R.id.buttonCardAvailableActivityAccept)

            cardTitle.text = cardAvalableActivities[position].nome.toString()
            cardType.text = cardAvalableActivities[position].tipo.toString()
            cardBeginData.text = cardAvalableActivities[position].dataInicio.toString()
            cardOverData.text = cardAvalableActivities[position].dataFim.toString()

            cardImageView.setOnClickListener{
                val intent = Intent(this@AvailableActivitiesActivity, MapsActivity::class.java)
                intent.putExtra("LOCATION_LATITUDE", cardAvalableActivities[position].latitude)
                intent.putExtra("LOCATION_LONGITUDE", cardAvalableActivities[position].longitude)
                startActivity(intent)
            }

            buttonAcepetRequest.setOnClickListener{

                GlobalScope.launch(Dispatchers.IO) {
                    val client = OkHttpClient()
                    print("teste1")

                    // ATENTION: here i create a new constructor in Atividade class for only send
                    // post with confirmation
                    val participant = Participante(
                        confirmacao = 1
                    )
                    print("teste2")

                    // Create body for post in participant
                    val requestBody = RequestBody.create(
                        "application/json".toMediaTypeOrNull(),
                        participant.toJson().toString()
                    )

                    println(getURL() + "/participant/atividade/" +
                            cardAvalableActivities[position].idAtividade
                            + "/utilizador/" + SavedUserData.id_utilizador)

                    // Send and post previous body
                    val request = Request.Builder()
                        .url(
                            getURL() + "/participant/atividade/" +
                                    cardAvalableActivities[position].idAtividade
                                    + "/utilizador/" + SavedUserData.id_utilizador
                        )
                        .post(requestBody)
                        .build()

                    // Verification if succesfull
                    client.newCall(request).execute().use { response ->
                        Log.d("participante", response.message)

                        GlobalScope.launch(Dispatchers.Main) {
                            if (response.message == "OK"){
                                println("Successfully posted")
                            }else{
                                Toast.makeText(this@AvailableActivitiesActivity,
                                    "Ja esta inscrito nesta atividade", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            }

            return  rowView

        }
    }
}