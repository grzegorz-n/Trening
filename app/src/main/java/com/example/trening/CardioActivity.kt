package com.example.trening

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class CardioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cardio)
        var cycles: Int
        var work: Int
        var rest: Int


        val cstart: Button = findViewById<Button>(R.id.cstart)
        val numberRounds: TextView = findViewById<Button>(R.id.numberRounds)
        val numberWork: TextView = findViewById<Button>(R.id.numberWork)
        val numberRest: TextView = findViewById<Button>(R.id.numberRest)

        cstart.setOnClickListener {
            cycles = numberRounds.text.toString().toInt()
            work = numberWork.text.toString().toInt()
            rest = numberRest.text.toString().toInt()
            val intent: Intent = Intent(this, Cardio::class.java)
            intent.putExtra("cykle", cycles)
            intent.putExtra("worki", work)
            intent.putExtra("resty", rest)
            startActivity(intent)
        }
    }
}