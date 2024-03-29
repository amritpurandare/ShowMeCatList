package com.showmecatlist.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.showmecatlist.R
import com.showmecatlist.utils.SPLASH_DELAY


// Launcher activity of the application
class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        Handler().postDelayed({

            val intent = Intent(this, HomeScreen::class.java)

            startActivity(intent)

            finish()

        }, SPLASH_DELAY)

    }
}
