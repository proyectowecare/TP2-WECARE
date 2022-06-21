package com.example.wecareapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wecareapp.api.RetroInstance
import com.example.wecareapp.model.Specialist
import com.example.wecareapp.model.SpecialistResponse
import com.example.wecareapp.model.User
import com.example.wecareapp.model.UserResponse
import com.example.wecareapp.services.RetroServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateSpecialistVM: ViewModel() {
    lateinit var createNewUserLiveData: MutableLiveData<SpecialistResponse?>
    init {
        createNewUserLiveData = MutableLiveData()
    }

    fun getCreateNewSpecialistObserver(): MutableLiveData<SpecialistResponse?> {
        return createNewUserLiveData
    }


    fun createNewSpecialist(user: Specialist) {
        val retroService  = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.createSpecialist(user)
        call.enqueue(object: Callback<SpecialistResponse?> {
            override fun onResponse(
                call: Call<SpecialistResponse?>,
                response: Response<SpecialistResponse?>
            ) {
                if(response.isSuccessful) {
                    createNewUserLiveData.postValue(response.body())
                } else {
                    createNewUserLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<SpecialistResponse?>, t: Throwable) {
                createNewUserLiveData.postValue(null)
            }

        })
    }
}

