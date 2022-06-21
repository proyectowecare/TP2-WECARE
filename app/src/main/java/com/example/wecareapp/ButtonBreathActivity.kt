package com.example.wecareapp

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController

class ButtonBreathActivity : AppCompatActivity() {
    var isOn=true
    var seconds=10

    private lateinit var mp: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_breath)
        val breath=findViewById<ImageButton>(R.id.bt_breath)
        var txt= findViewById<TextView>(R.id.tv_resp_indiacion)
        var txt2= findViewById<TextView>(R.id.tv_indicacion)
        var tv_timer=findViewById<TextView>(R.id.tv_timer)

        val timer= object :CountDownTimer(10000,1000){
        override fun onTick(p0: Long) {
            tv_timer.text=seconds.toString()


            if (seconds== 4)
            {
                txt.text="Por la nariz, durante 4 segundos"
                txt2.text="Exhala"
            }
            --seconds

        }

        override fun onFinish() {
            breath.setImageResource(R.drawable.off)
            isOn=true
            txt.text="Profundamente por la nariz, durante 6 segundos"
            txt2.text="Inhala"
            seconds=10
            tv_timer.text=seconds.toString()
        }

    }


        breath.setOnClickListener(View.OnClickListener {

            mp = MediaPlayer.create(this, R.raw.click)
            mp.start()
            if(isOn) {
                breath.setImageResource(R.drawable.on)
                isOn=false
                timer.start()
            }

        })
    }
}