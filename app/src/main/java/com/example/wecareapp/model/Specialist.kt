package com.example.wecareapp.model

data class Specialist(
    val Firstname: String,
    val Lastname: String,
    val Email: String,
    val Password: String,
    val Area: String,
    val TuitionNumber: String,
    val ConfirmPassword: String,
)

data class SpecialistResponse(
    val specialistId :Int?,
    val specialistName :   String? ,
    val specialistLastname :  String? ,
    val specialistEmail :  String?,
    val specialistArea :  String? ,
    val specialistTuitionNumber : String?,
    val patients : List<Patient>
)