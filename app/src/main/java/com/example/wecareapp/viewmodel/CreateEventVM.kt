package com.example.wecareapp.viewmodel

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wecareapp.MainActivity
import com.example.wecareapp.SelectRolActivity
import com.example.wecareapp.SelectorActivity
import com.example.wecareapp.api.RetroInstance
import com.example.wecareapp.model.*
import com.example.wecareapp.services.RetroServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
/*
class CreateEventVM: ViewModel() {
    lateinit var createNewUserLiveData: MutableLiveData<EventResponse?>
    init {
        createNewUserLiveData = MutableLiveData()
    }

    fun getCreateNewUserObserver(): MutableLiveData<EventResponse?> {
        return createNewUserLiveData
    }

    companion object {

        private lateinit var context: Context

        fun setContext(con: Context) {
            context=con
        }
    }


    fun createNewEvent(user: Event, con: Context){
        var success= false
        val retroService  = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.createEvent(user)
        call.enqueue(object: Callback<EventResponse?> {
            override fun onResponse(call: Call<EventResponse?>, response: Response<EventResponse?>) {
                if(response.isSuccessful) {
                    createNewUserLiveData.postValue(response.body())
                    success= true
                    val intent = Intent(con, SelectorActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(con,intent,null)

                } else {
                    createNewUserLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<EventResponse?>, t: Throwable) {

                createNewUserLiveData.postValue(null)
            }
        })
    }



}


 */


