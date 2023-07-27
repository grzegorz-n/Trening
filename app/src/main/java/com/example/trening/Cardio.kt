package com.example.trening

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Cardio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_cardio_training)

        var mediaPlayer: MediaPlayer = MediaPlayer.create(this, R.raw.beep)

        // tutaj definiuje okienka
        var rounds: TextView = findViewById(R.id.textView3)
        var time: TextView = findViewById(R.id.textView6)
        var status: TextView = findViewById(R.id.textView7)
        val layout: ConstraintLayout = findViewById(R.id.cardio)
        val orange = ContextCompat.getColor(this, R.color.orange)
        val red = ContextCompat.getColor(this, R.color.red)
        val blue = ContextCompat.getColor(this, R.color.blue)

        //tutaj przyjmuje wartosci z intenta
        val cycles: Int = intent.getIntExtra("cykle",30)
        val worki: Int = intent.getIntExtra("worki", 30)
        val resty: Int = intent.getIntExtra("resty", 30)
        var ifWork: Boolean = true

        //tutaj tworze zmienne temporary
        var tempCycles: Int = cycles
        var tempWorki: Int = worki
        var tempResty: Int = resty








        val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
        coroutineScope.launch {
            //przygotuj się
            layout.setBackgroundColor(orange)
            withContext(Dispatchers.Main) {
                rounds.text = tempCycles.toString()
                status.text = "Przygotuj się"
            }

            for (i in 5 downTo 1) {
                withContext(Dispatchers.Main) {
                    time.text = i.toString()
                }
                delay(1000)
            }

            withContext(Dispatchers.Main) {
                rounds.text = tempCycles.toString()
                time.text = tempWorki.toString()
                status.text = "Praca"
            }
            while (tempCycles != 0) {
                if (ifWork) {
                    layout.setBackgroundColor(red)
                    withContext(Dispatchers.Main) {
                        time.text = tempWorki.toString()
                        status.text = "Praca"
                    }
                    delay(1000)
                    tempWorki--

                    if (tempWorki in 1..3) {
                        mediaPlayer.release()
                    }

                    if (tempWorki == 0) {
                        ifWork = false
                        tempWorki = worki
                    }
                } else {
                    layout.setBackgroundColor(blue)
                    withContext(Dispatchers.Main) {
                        status.text = "Przerwa"
                        time.text = tempResty.toString()
                    }
                    delay(1000)
                    tempResty--

                    if (tempResty in 1..3) {
                        mediaPlayer.release()
                    }
                    if (tempResty == 0) {
                        ifWork = true
                        tempCycles--
                        tempResty = resty
                        withContext(Dispatchers.Main) {
                            rounds.text = tempCycles.toString()
                        }
                    }
                }
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, "Koniec treningu", Toast.LENGTH_LONG).show()
            }
            delay(1000)
            val backIntent: Intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(backIntent)

        }
    }
}


