package com.example.wecareapp

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView


class LofiMediaPlayerActivity : AppCompatActivity() {
    private lateinit var mp: MediaPlayer;
    private var totalTime: Int = 0;
    var canciones: IntArray = intArrayOf(R.raw.lofi,R.raw.lofi2,R.raw.lofi3,R.raw.lofi4)
    var posicion=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_player)


            mp = MediaPlayer.create(this, canciones[0])

        mp.isLooping = true
        mp.setVolume(0.5f, 0.5f)
        totalTime = mp.duration

        // Volume Bar
        var volumeBar= findViewById<SeekBar>(R.id.volumeBar)
        volumeBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        var volumeNum = progress / 100.0f
                        mp.setVolume(volumeNum, volumeNum)
                    }
                }
                override fun onStartTrackingTouch(p0: SeekBar?) {
                }
                override fun onStopTrackingTouch(p0: SeekBar?) {
                }
            }
        )

        // Position Bar
        var positionBar= findViewById<SeekBar>(R.id.positionBar)
        positionBar.max = totalTime
        positionBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        mp.seekTo(progress)
                    }
                }
                override fun onStartTrackingTouch(p0: SeekBar?) {
                }
                override fun onStopTrackingTouch(p0: SeekBar?) {
                }
            }
        )

        // Thread
        Thread(Runnable {
            while (mp != null) {
                try {
                    var msg = Message()
                    msg.what = mp.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                }
            }
        }).start()
    }

    @SuppressLint("HandlerLeak")
    var handler = object : Handler() {

        override fun handleMessage(msg: Message) {
            var positionBar= findViewById<SeekBar>(R.id.positionBar)
            var elapsedTimeLabel= findViewById<TextView>(R.id.elapsedTimeLabel)
            var remainingTimeLabel= findViewById<TextView>(R.id. remainingTimeLabel)

            var currentPosition = msg.what


            // Update positionBar
            positionBar.progress = currentPosition

            // Update Labels
            var elapsedTime = createTimeLabel(currentPosition)
            elapsedTimeLabel.text = elapsedTime

            var remainingTime = createTimeLabel(totalTime - currentPosition)
            remainingTimeLabel.text = "-$remainingTime"
        }
    }

    fun createTimeLabel(time: Int): String {
        var timeLabel = ""
        var min = time / 1000 / 60
        var sec = time / 1000 % 60

        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec

        return timeLabel
    }

    fun playBtnClick(v: View) {
        var playBtn= findViewById<Button>(R.id.playBtn)
        if (mp.isPlaying) {
            // Stop
            mp.pause()
            playBtn.setBackgroundResource(R.drawable.play)

        } else {
            // Start
            mp.start()
            playBtn.setBackgroundResource(R.drawable.stop)
        }
    }

    fun nextBtnClick(v: View) {
        posicion=posicion+1
        var nextBtn = findViewById<Button>(R.id.NextBtn)
            if (posicion > canciones.size-1)
            {
                posicion=0
                mp.pause()
                mp = MediaPlayer.create(this, canciones[posicion])
                totalTime=mp.duration
                playBtnClick(v)
            }
            else{
                mp.pause()
                mp = MediaPlayer.create(this, canciones[posicion])
                totalTime=mp.duration
                playBtnClick(v)

            }
    }

    fun previousBtnClick(v: View) {
        posicion=posicion-1
        var previousBtn = findViewById<Button>(R.id.previousBtn)
        if (posicion<0 )
        {
            posicion=canciones.size-1
            mp.pause()
            mp = MediaPlayer.create(this, canciones[posicion])
            totalTime=mp.duration
            playBtnClick(v)
        }
        else{
            mp.pause()
            mp = MediaPlayer.create(this, canciones[posicion])
            totalTime=mp.duration
            playBtnClick(v)

        }
    }

}