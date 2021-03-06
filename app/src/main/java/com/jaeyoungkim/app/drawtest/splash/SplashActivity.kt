package com.jaeyoungkim.app.drawtest.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.jaeyoungkim.app.drawtest.R
import com.jaeyoungkim.app.drawtest.view.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity,MainActivity::class.java))
            finish()
        },1000)
    }
}
