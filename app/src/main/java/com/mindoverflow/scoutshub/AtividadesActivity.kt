package com.mindoverflow.scoutshub

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.airbnb.lottie.LottieAnimationView

class AtividadesActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atividades)

        // swipe to exit
        val viewSwipe = findViewById<View>(R.id.viewSwipe)
        val closeAnimation = findViewById<LottieAnimationView>(R.id.closeAnimation)

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


}