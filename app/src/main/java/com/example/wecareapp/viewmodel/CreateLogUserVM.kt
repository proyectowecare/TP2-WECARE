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
import com.example.wecareapp.model.Specialist
import com.example.wecareapp.model.SpecialistResponse
import com.example.wecareapp.model.User
import com.example.wecareapp.model.UserResponse
import com.example.wecareapp.services.RetroServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateLogUserVM: ViewModel() {
    lateinit var createNewUserLiveData: MutableLiveData<UserResponse?>
    init {
        createNewUserLiveData = MutableLiveData()
    }

    fun getCreateNewUserObserver(): MutableLiveData<UserResponse?> {
        return createNewUserLiveData
    }

    companion object {

        private lateinit var context: Context

        fun setContext(con: Context) {
            context=con
        }
    }

    fun createNewUser(user: User,con: Context): Boolean {

        var success= false
        val retroService  = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.createLog(user)
        call.enqueue(object: Callback<UserResponse?> {
            override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {
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

            override fun onFailure(call: Call<UserResponse?>, t: Throwable) {

                createNewUserLiveData.postValue(null)
            }
        })

        return success
    }
}


