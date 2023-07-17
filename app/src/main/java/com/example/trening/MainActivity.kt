package com.example.trening

import android.app.SearchManager.OnCancelListener
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sil: Button = findViewById<Button>(R.id.silka)
        val cardio: Button = findViewById<Button>(R.id.cardio)


        sil.setOnClickListener {
            val silIntent: Intent = Intent(this, SilkaActivity::class.java)
            startActivity(silIntent)
        }
        cardio.setOnClickListener {
            val silIntent: Intent = Intent(this, CardioActivity::class.java)
            startActivity(silIntent)
        }
    }
}