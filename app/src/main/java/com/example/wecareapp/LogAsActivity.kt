package com.example.wecareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LogAsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_log_as)
        val btAsPatient = findViewById<Button>(R.id.btAs_patient)
        val btAsSpetialist = findViewById<Button>(R.id.btAs_spetialist)
        btAsPatient.setOnClickListener{
            val intent= Intent(this, SelectorActivity::class.java)
            startActivity(intent)
        }
        btAsSpetialist.setOnClickListener{
            val intent= Intent(this, SpetialistSelectorActivity::class.java)
            startActivity(intent)
        }

    }
}