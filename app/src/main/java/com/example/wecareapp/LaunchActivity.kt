package com.example.wecareapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        val sharedPref = this?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
            val editor= sharedPref.edit()
            editor.putString("prueba","prueba1")
            editor.commit()


        Handler().postDelayed(Runnable {
            val intent = Intent(this, MainActivity::class.java).apply {
                //putExtra("Username",user.name)
            }
            startActivity(intent)
        }, 3000)
    }
}