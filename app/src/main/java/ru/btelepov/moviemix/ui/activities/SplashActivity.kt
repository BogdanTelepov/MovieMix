package ru.btelepov.moviemix.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import ru.btelepov.moviemix.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var biding: ActivitySplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(biding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashActivity, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
}