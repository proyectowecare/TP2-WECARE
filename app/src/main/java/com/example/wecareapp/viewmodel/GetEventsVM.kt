package com.demo.retrofitwithpost

import android.media.metrics.Event
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wecareapp.api.RetroInstance
//import com.example.wecareapp.model.EventGet
import com.example.wecareapp.services.RetroServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetEventsVM: ViewModel() {


   // lateinit var recyclerListData: MutableLiveData<List<EventGet>?>

    init {
     //   recyclerListData = MutableLiveData()

    }

/*
    fun getUserListObserverable() : MutableLiveData<List<EventGet>?> {
        return recyclerListData
    }

 */
/*
    fun EventList() {

        val retroInstance = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        var num=1
        val call = retroInstance.getEventList(num.toString())
        call.enqueue(object : Callback<List<EventGet>>{
            override fun onFailure(call: Call<List<EventGet>?>, t: Throwable) {
                recyclerListData.postValue(null)
            }

            override fun onResponse(call: Call<List<EventGet>?>, response: Response<List<EventGet>>) {
                if(response.isSuccessful) {
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }
        })
    }

 */

    /*fun searchUser(searchText: String) {

        val retroInstance = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroInstance.searchEvent(searchText)
        call.enqueue(object : Callback<Event>{
            override fun onFailure(call: Call<Event>, t: Throwable) {
                recyclerListData.postValue(null)
            }

            override fun onResponse(call: Call<Event>, response: Response<Event>) {
                if(response.isSuccessful) {
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }
        })
    }*/
}

private fun <T> Call<T>.enqueue(callback: Callback<Event>) {

}
