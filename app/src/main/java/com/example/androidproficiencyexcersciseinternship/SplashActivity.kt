package com.example.androidproficiencyexcersciseinternship

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            this.startActivity(Intent(this,MainActivity::class.java))
            this.finish()
    }
}