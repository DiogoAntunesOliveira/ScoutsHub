package com.mindoverflow.scoutshub.ui.Atividades

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.imageview.ShapeableImageView
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.ui.Atividades.CalendarioAtividadesActivity
import com.mindoverflow.scoutshub.ui.Instrucoes.InstrucoesActivity

class AtividadesActivity : AppCompatActivity() {

    var showOptions : MutableList<String> = arrayListOf()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atividades)

        //Swipe para sair
        val viewSwipe = findViewById<View>(R.id.viewSwipe)
        val closeAnimation = findViewById<LottieAnimationView>(R.id.closeAnimation)


        //É dado assignment dos buttons
        val calendario = findViewById<ConstraintLayout>(R.id.constraintLayout)
        val availableActivities = findViewById<ConstraintLayout>(R.id.constraintLayout2)
        val instructionsGuide = findViewById<ConstraintLayout>(R.id.constraintLayout3)

        calendario.setOnClickListener{
            val intent = Intent(this, CalendarioAtividadesActivity::class.java)
            this.startActivity(intent)
        }

        availableActivities.setOnClickListener {
            val intent = Intent(this, AvailableActivitiesActivity::class.java)
            startActivity(intent)
        }

        instructionsGuide.setOnClickListener {
            val intent = Intent(this, InstrucoesActivity::class.java)
            startActivity(intent)
        }

        closeAnimation.setOnClickListener{
            finish()
        }




        viewSwipe.isClickable = true
        viewSwipe.setOnTouchListener { view, motionEvent ->

            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> y1 = motionEvent.y
                MotionEvent.ACTION_UP -> {
                    y2 = motionEvent.y
                    val deltaY: Float = y2 - y1
                    if (Math.abs(deltaY) > MIN_DISTANCE) {
                        finish()
                    } else {
                        // consider as something else - a screen tap for example
                    }
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false

        }
    }

    private var y1 = 0f
    private var y2 = 0f
    val MIN_DISTANCE = 150


    inner class ActivitiesAdapter : BaseAdapter(){
        override fun getCount(): Int {
            return showOptions.size
        }

        override fun getItem(position: Int): Any {
            return showOptions[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_activities_option, parent, false)

            var imageViewOption = rowView.findViewById<ShapeableImageView>(R.id.optionImage)
            var tileViewOption = rowView.findViewById<ShapeableImageView>(R.id.optionTitle)
            var descriptionViewOption = rowView.findViewById<ShapeableImageView>(R.id.optionDescrition)

            return  rowView

        }

    }

}