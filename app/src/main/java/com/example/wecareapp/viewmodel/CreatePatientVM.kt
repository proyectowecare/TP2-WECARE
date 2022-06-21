package com.example.wecareapp.viewmodel

import android.app.AlertDialog
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wecareapp.SelectorActivity
import com.example.wecareapp.api.RetroInstance
import com.example.wecareapp.model.Patient
import com.example.wecareapp.model.PatientResponse
import com.example.wecareapp.services.RetroServiceInterface
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreatePatientVM: ViewModel() {
    lateinit var createNewUserLiveData: MutableLiveData<PatientResponse?>
    init {
        createNewUserLiveData = MutableLiveData()
    }

    fun getCreateNewPatientObserver(): MutableLiveData<PatientResponse?> {
        return createNewUserLiveData
    }


    fun createNewPatient(user: Patient) {

        // TODO: GUARDAR LOS DATOS DEL USUARIO EN LA BASE DE DATOS
        // TODO: ANTES-> LLAMA AL BACKEND
        /*val retroService  = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.createPatient(user)
        call.enqueue(object: Callback<PatientResponse?> {
            override fun onFailure(call: Call<PatientResponse?>, t: Throwable) {
                createNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<PatientResponse?>, response: Response<PatientResponse?>) {
                if(response.isSuccessful) {
                    createNewUserLiveData.postValue(response.body())
                } else {
                    createNewUserLiveData.postValue(null)
                }
            }
        })*/

    }




}