package com.example.wecareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView

class PendingPatients : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prototype_pendings)
        var proffbtn =findViewById<ImageButton>(R.id.profButton)
        var proffbtn2 =findViewById<ImageButton>(R.id.profButton2)
        var proffbtn3 =findViewById<ImageButton>(R.id.profButton3)

        proffbtn.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, PendingProfile::class.java).apply {
            }
            startActivity(intent)

        })
        proffbtn2.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, PendingProfile2::class.java).apply {
            }
            startActivity(intent)

        })
        proffbtn3.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, PendingProfile3::class.java).apply {
            }
            startActivity(intent)

        })
    }
}