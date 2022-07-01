package com.example.wecareapp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.wecareapp.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalField
import java.time.temporal.WeekFields
import java.util.*

class RegisterEventActivity : AppCompatActivity() {
    //lateinit var viewModel: CreateEventVM
    var db = FirebaseFirestore.getInstance();

    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_event)
        initViewModel()


        var result = " "
        var feelingNum: Float = 0.0F


        var feelingM = findViewById<SeekBar>(R.id.feelingM)

        var ib_tup = findViewById<ImageButton>(R.id.ib_tup)
        var ib_tdown = findViewById<ImageButton>(R.id.ib_tdown)
        var feeling_numberM = findViewById<TextView>(R.id.feeling_numberM)

        var tv_time_hour = findViewById<TextClock>(R.id.tv_time_hour)
        var tv_date = findViewById<TextView>(R.id.tv_date)
        var ib_sendevent=findViewById<ImageButton>(R.id.ib_sendevent)


        val fieldUS: TemporalField = WeekFields.of(Locale.US).dayOfWeek()

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val formatted = current.format(formatter)
        val formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formatted1 = current.format(formatter1)
        tv_date.text = formatted1.toString()
        tv_time_hour.text = formatted.toString()



        ib_sendevent.setOnClickListener(View.OnClickListener {
            createEvent(this, feelingNum, result,)
            /*val intent = Intent(this, HomeFragment::class.java).apply {

            }
            startActivity(intent)*/
        })

/*
        "eventName": "string",
        "eventDescription": "string",
        "eventScore": 0,
        "eventResult": "string",
        "eventDetail": "string",
        "patientId": 0
*/


        feelingM.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekbar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        feelingNum = progress / 10f
                        feeling_numberM.text = feelingNum.toInt().toString()
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }
            }
        )

        ib_tup.setOnClickListener(View.OnClickListener {
            ib_tup.setImageResource(R.drawable.ic_baseline_thumb_up_25)
            ib_tdown.setImageResource(R.drawable.ic_baseline_thumb_down_24)

            result = "Bien"
        })

        ib_tdown.setOnClickListener(View.OnClickListener {

            ib_tup.setImageResource(R.drawable.ic_baseline_thumb_up_24)
            ib_tdown.setImageResource(R.drawable.ic_baseline_thumb_down_25)
            result = "Mal"

        })
    }

    private fun createEvent(con: Context, feelingNum: Float, result: String) {

        // solo capturo aqui el contenido que se redacta, para que sea capturado
        var et_event = findViewById<EditText>(R.id.eT_Event).text.toString()
        var et_reasonevent = findViewById<EditText>(R.id.eT_reasonevent).text.toString()
        var tv_detail_event =
            findViewById<AutoCompleteTextView>(R.id.tv_detail_event).text.toString()
        val currentUser = FirebaseAuth.getInstance().currentUser;
        //val eventAnterior = Event(et_event, et_reasonevent, feelingNum.toInt(), result, tv_detail_event, "17-04-2022  15:30" ,1)
        //viewModel.createNewEvent(event, con)


        //Firebase
        val events = db.collection("events");
        val event = hashMapOf(
            "eventDetail" to tv_detail_event,
            "eventDescription" to et_reasonevent,
            "eventName" to et_event,
            "eventScore" to feelingNum,
            "eventResult" to result,
            "patientId" to currentUser?.uid,
        )

        events.add(event).addOnSuccessListener {
            //Log.d("Mensaje","Se ha logrado crear el evento")
            Toast.makeText(this, "Se ha logrado crear el evento", Toast.LENGTH_LONG).show()}
            .addOnFailureListener {
                //Log.e("Error","No se ha podido crear el evento")
                Toast.makeText(this, "No se ha podido crear el evento", Toast.LENGTH_LONG).show() };

        //

    }

    private fun initViewModel() {
        /*
        viewModel = ViewModelProvider(this).get(CreateEventVM::class.java)
        viewModel.getCreateNewUserObserver().observe(this,{

            if (it == null) {
                Toast.makeText(this, "Failed to create User", Toast.LENGTH_LONG).show()
            } else {
                //{"code":201,"meta":null,"data":{"id":2877,"name":"xxxxxaaaaabbbbb","email":"xxxxxaaaaabbbbb@gmail.com","gender":"male","status":"active"}}
                Toast.makeText(this, "Successfully created User", Toast.LENGTH_LONG).show()
            }
        })

         */
    }
}
