package com.example.wecareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton

class MusicTypeSelector: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_type_selector)
        var buttonnature =findViewById<ImageButton>(R.id.btn_Naturaleza)
        buttonnature.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, NatureMediaPlayerActivity::class.java).apply {
                //putExtra("Username",user.name)
            }
            startActivity(intent)
        })

        var buttonlofi =findViewById<ImageButton>(R.id.btn_lofi)
        buttonlofi.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, LofiMediaPlayerActivity::class.java).apply {
                //putExtra("Username",user.name)
            }
            startActivity(intent)
        })
        var buttonmar =findViewById<ImageButton>(R.id.btn_mar)
        buttonmar.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, SeaMediaPlayerActivity::class.java).apply {
                //putExtra("Username",user.name)
            }
            startActivity(intent)
        })
        var buttonpiano =findViewById<ImageButton>(R.id.btn_piano)
        buttonpiano.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, PianoMediaPLayerActivity::class.java).apply {
                //putExtra("Username",user.name)
            }
            startActivity(intent)
        })

    }
}