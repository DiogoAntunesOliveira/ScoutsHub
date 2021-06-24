package com.mindoverflow.scoutshub

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.mindoverflow.scoutshub.ui.Login.FrontPage
import com.mindoverflow.scoutshub.ui.Login.WelcomebackActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        var lottieanimation : LottieAnimationView = findViewById(R.id.motionSplashAnimation)

        Handler().postDelayed({
            val intent = Intent(this, FrontPage::class.java)
            startActivity(intent)
            finish()
        }, 2000)

    }
}