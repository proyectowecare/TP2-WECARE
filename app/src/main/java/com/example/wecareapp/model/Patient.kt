package com.example.wecareapp.model

data class Patient(
    val Firstname: String,
    val Lastname: String,
    val Email: String,
    val Password: String,
    val ConfirmPassword: String,
)
data class PatientResponse(
    val patientId : Int,
    val patientName :  String? ,
    val patientLastname : String? ,
    val patientEmail : String? ,
    val specialist : SpecialistResponse
)



