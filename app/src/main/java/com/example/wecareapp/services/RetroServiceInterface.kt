package com.example.wecareapp.services

import android.text.AutoText
import com.example.wecareapp.model.*
import retrofit2.Call
import retrofit2.http.*

interface RetroServiceInterface {

    @POST("identity/register_patient")
    @Headers("Accept:application/json", "Content-Type:application/json"/*,
        "Authorization: Bearer 73668350bdf06c66f3388408c1a712b378c3e25da599753b21b664a6261e246c"*/)
    fun createPatient(@Body params: Patient): Call<PatientResponse>


    @POST("identity/register_specialist")
    @Headers("Accept:application/json", "Content-Type:application/json"/*,
        "Authorization: Bearer 73668350bdf06c66f3388408c1a712b378c3e25da599753b21b664a6261e246c"*/)
    fun createSpecialist(@Body params: Specialist): Call<SpecialistResponse>

    @POST("identity/login")
    @Headers("Accept:application/json", "Content-Type:application/json"/*,
        "Authorization: Bearer 73668350bdf06c66f3388408c1a712b378c3e25da599753b21b664a6261e246c"*/)
    fun createLog(@Body params: User): Call<UserResponse>

/*

    @GET("events/today/{user_id}")
    @Headers("Accept:application/json", "Content-Type:application/json"/*,
        "Authorization: Bearer 73668350bdf06c66f3388408c1a712b378c3e25da599753b21b664a6261e246c"*/)
   fun getEventList (@Path("user_id") user_id:String): Call<List<EventGet>>

 */
}