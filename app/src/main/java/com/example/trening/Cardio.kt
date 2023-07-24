package com.example.trening

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Cardio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cardio_training)

        var rounds: TextView = findViewById(R.id.textView3)
        var time: TextView = findViewById(R.id.textView6)
        var status: TextView = findViewById(R.id.textView7)

        val cycles: Int = intent.getIntExtra("cykle",30)
        val worki: Int = intent.getIntExtra("worki", 30)
        val resty: Int = intent.getIntExtra("resty", 30)
        var ifWork: Boolean = true

        var tempCycles: Int = cycles
        var tempWorki: Int = worki
        var tempResty: Int = resty

        rounds.text = tempCycles.toString()
        time.text = tempWorki.toString()
        status.text = "Praca"

        val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
        coroutineScope.launch {
            while (tempCycles != 0) {
                if (ifWork) {
                    delay(1000)
                    tempWorki--
                    withContext(Dispatchers.Main) {
                        time.text = tempWorki.toString()
                    }
                    if (tempWorki == 0) {
                        ifWork = false
                        tempWorki = worki
                        withContext(Dispatchers.Main) {
                            status.text = "Przerwa"
                        }
                    }
                } else {
                    delay(1000)
                    tempResty--
                    withContext(Dispatchers.Main) {
                        time.text = tempResty.toString()
                    }
                    if (tempResty == 0) {
                        ifWork = true
                        tempResty = resty
                        tempCycles--
                        withContext(Dispatchers.Main) {
                            status.text = "Praca"
                            rounds.text = tempCycles.toString()
                        }
                    }
                }
            }
        }
    }
}


