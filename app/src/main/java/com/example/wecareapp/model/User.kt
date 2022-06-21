package com.example.wecareapp.model

data class User (
    val Email: String,
    val Password: String,
)
data class UserResponse(
    val name: String,
    val email: String,
    val unique_name:String,
    val family_name: String,
    val role: String,
    val nbf: Int,
    val exp: Int,
    val iat: Int
)