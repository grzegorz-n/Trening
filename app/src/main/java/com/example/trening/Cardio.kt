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
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class Cardio : AppCompatActivity() {

    // KLASA W KTÓREJ SĄ INTERWAŁY
    // tutaj definiuje okienka
    lateinit var rounds: TextView
    lateinit var time: TextView
    lateinit var status: TextView
    lateinit var layout: ConstraintLayout
    var orange: Int = 1
    var red: Int = 1
    var blue: Int = 1
    var ifWork: Boolean = true //zmienna czy praca czy odpoczynek

    //tutaj przyjmuje wartosci z intenta
    var cycles: Int = 30
    var worki: Int = 30
    var resty: Int = 30

    //tutaj tworze zmienne temporary
    var tempCycles: Int = cycles
    var tempTime: Int = worki



    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_cardio_training)

        //tutaj przyjmuje wartosci z intenta
        cycles = intent.getIntExtra("cykle",30)
        worki = intent.getIntExtra("worki", 30)
        resty = intent.getIntExtra("resty", 30)

        //tutaj tworze zmienne temporary
        tempCycles = cycles
        tempTime = worki
        mediaPlayer = MediaPlayer.create(this, R.raw.beep)

        // tutaj definiuje okienka
        rounds = findViewById(R.id.textView3)
        time = findViewById(R.id.textView6)
        status = findViewById(R.id.textView7)
        layout = findViewById(R.id.cardio)
        orange = ContextCompat.getColor(this, R.color.orange)
        red = ContextCompat.getColor(this, R.color.red)
        blue = ContextCompat.getColor(this, R.color.blue)


        CoroutineScope(Dispatchers.Default).launch {
            try {
                CoroutineScope(Dispatchers.Default).launch{
                    prepare()
                    while(tempCycles >= 0) {
                        if (ifWork) {
                            doWork()
                        } else {
                            doRest()
                        }
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "Jakieś błąd", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    private suspend fun prepare() {
        layout.setBackgroundColor(orange)
        withContext(Dispatchers.Main) {
            rounds.text = tempCycles.toString()
            status.text = "Przygotuj się"
        }
        for (i in 5 downTo 1) {
            withContext(Dispatchers.Main) {
                time.text = i.toString()
                async(Dispatchers.IO) {
                    mediaPlayer.start()
                    delay(500)
                    mediaPlayer.pause()
                    mediaPlayer.seekTo(0)
                }
            }
            delay(1000)
        }

    }
    private suspend fun doWork() {
        layout.setBackgroundColor(red)
        withContext(Dispatchers.Main) {
            rounds.text = tempCycles.toString()
            time.text = tempTime.toString()
            status.text = "Praca"
        }

        if (tempTime <= 3) {
            if (tempTime == 0) {
                tempTime = resty
                ifWork = false
            }
            withContext(Dispatchers.Main) {
                async(Dispatchers.IO) {
                    mediaPlayer.start()
                    delay(500)
                    mediaPlayer.pause()
                    mediaPlayer.seekTo(0)
                }
            }
        }
        tempTime--
        delay(1000)
    }
    private suspend fun doRest() {
        layout.setBackgroundColor(orange)
        withContext(Dispatchers.Main) {
            rounds.text = tempCycles.toString()
            time.text = tempTime.toString()
            status.text = "Przerwa"
        }

        if (tempTime <= 3) {
            if (tempTime == 0) {
                tempCycles--
                tempTime = worki
                ifWork = true
                if(tempCycles == 0) {
                    goHome()
                }
            }
            withContext(Dispatchers.Main) {
                async(Dispatchers.IO) {
                    mediaPlayer.start()
                    delay(500)
                    mediaPlayer.pause()
                    mediaPlayer.seekTo(0)
                }
            }
        }
        tempTime--
        delay(1000)
    }

    private suspend fun goHome() {
        Toast.makeText(applicationContext, "Koniec treningu", Toast.LENGTH_LONG).show()
        val backIntent: Intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(backIntent)
    }

}


