package com.example.wecareapp.model

import java.time.LocalDate
import java.util.*

data class Event (

    val eventName: String,
    val eventDescription: String,
    val eventScore: Int,
    val eventResult: String,
    val eventDetail: String,
    val eventDate:String,
    val patientId: Int
){}


