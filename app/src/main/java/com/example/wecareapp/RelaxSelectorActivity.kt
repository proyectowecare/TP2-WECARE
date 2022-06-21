package com.example.wecareapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController

class RelaxSelectorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relax_selector)

        var buttonbreath =findViewById<ImageButton>(R.id.bt_respirar)
        buttonbreath.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, ButtonBreathActivity::class.java).apply {
                //putExtra("Username",user.name)
            }
            startActivity(intent)
        })
        var buttonmusic =findViewById<ImageButton>(R.id.bt_escuchar_musica)
        buttonmusic.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, MusicTypeSelector::class.java).apply {
                //putExtra("Username",user.name)
            }
            startActivity(intent)
        })
    }
}