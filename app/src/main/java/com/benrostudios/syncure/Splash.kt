package com.benrostudios.syncure

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import com.benrostudios.syncure.ui.auth.Auth
import com.benrostudios.syncure.utils.SharedPrefManager
import org.koin.android.ext.android.inject

class Splash : AppCompatActivity() {

    private val sharedPrefManager: SharedPrefManager by inject()

    private val SPLASH_TIME_OUT = 1000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_splash)
        Handler().postDelayed(
            {
                userChecker()
            }, SPLASH_TIME_OUT
        )
    }

    private fun userChecker() {
        if (sharedPrefManager.jwtStored.isEmpty()) {
            startActivity(Intent(this, Auth::class.java))
            this.finish()
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }
    }

}