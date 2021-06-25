package com.mindoverflow.scoutshub

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.mindoverflow.scoutshub.ui.Login.FrontPage
import com.mindoverflow.scoutshub.ui.Login.Signup1Activity
import com.mindoverflow.scoutshub.ui.Login.WelcomebackActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        var lottieanimation : LottieAnimationView = findViewById(R.id.motionSplashAnimation)
        val bt_signin = findViewById<Button>(R.id.bt_signin)
        val bt_signup = findViewById<Button>(R.id.bt_signup)

        bt_signin.setOnClickListener {
            val intent = Intent(this, WelcomebackActivity::class.java)
            startActivity(intent)
        }

        bt_signup.setOnClickListener {
            val intent = Intent(this, Signup1Activity::class.java)
            startActivity(intent)
        }

    }
}