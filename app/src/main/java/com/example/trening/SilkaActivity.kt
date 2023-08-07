package com.example.trening

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SilkaActivity : AppCompatActivity() {

    lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_silka)

        mediaPlayer = MediaPlayer.create(this, R.raw.beep)
        val rounds: TextView = findViewById(R.id.rounds)
        val rest: TextView = findViewById(R.id.rest)
        val start: Button = findViewById(R.id.start)
        val time: TextView = findViewById(R.id.Time)
        val buttonRest: Button = findViewById(R.id.buttonRest)
        val roundsText: TextView = findViewById(R.id.roundsText)

        var roundInt: Int = 6
        var restInt: Int = 60
        var temRestInt: Int = restInt

        start.setOnClickListener {
            roundInt =  rounds.text.toString().toInt()
            restInt = rest.text.toString().toInt()
            temRestInt = restInt
            time.setText("Pozostały czas to: " + restInt)
            roundsText.text = "Runda: " + roundInt
        }

        buttonRest.setOnClickListener {
            val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
            coroutineScope.launch {
                while (temRestInt >= 0) {
                    time.setText("Pozostały czas to: " + temRestInt)
                    delay(1000)
                    temRestInt--
                    if (temRestInt <= 3) {
                        withContext(Dispatchers.Main) {
                            async(Dispatchers.IO) {
                                mediaPlayer.start()
                                delay(500)
                                mediaPlayer.pause()
                                mediaPlayer.seekTo(0)
                            }
                        }
                    }
                }
                roundInt--
                roundsText.text = "Runda: " + roundInt
                temRestInt = restInt
                time.setText("Pozostały czas to: " + temRestInt)
                if(roundInt == 0) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(applicationContext, "Koniec treningu", Toast.LENGTH_LONG).show()
                    }
                    delay(1000)
                    val backIntent: Intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(backIntent)
                }
            }

        }
    }
}