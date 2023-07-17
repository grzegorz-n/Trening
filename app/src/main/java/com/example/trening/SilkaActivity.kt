package com.example.trening

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SilkaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_silka)

        val rounds: TextView = findViewById(R.id.rounds)
        val rest: TextView = findViewById(R.id.rest)
        val start: Button = findViewById(R.id.start)
        val time: TextView = findViewById(R.id.Time)
        val buttonRest: Button = findViewById(R.id.buttonRest)
        val roundsText: TextView = findViewById(R.id.roundsText)

        var roundInt: Int = 6
        var restInt: Int = 60

        start.setOnClickListener {
            roundInt =  rounds.text.toString().toInt()
            restInt = rest.text.toString().toInt()
            time.setText("Pozostały czas to: " + restInt)
        }

        buttonRest.setOnClickListener {
            val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
            coroutineScope.launch {
                while (restInt >= 0) {
                    time.setText("Pozostały czas to: " + restInt)
                    delay(1000)
                    restInt--
                }
                roundInt--
                roundsText.text = "Runda: " + roundInt
            }
        }

    }
}