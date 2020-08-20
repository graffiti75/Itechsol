package com.itechsol.app

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.itechsol.app.utils.openActivity

@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {

    //--------------------------------------------------
    // Activity Life Cycle
    //--------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            openActivity(MainActivity::class.java)
        }, 2000)
    }
}