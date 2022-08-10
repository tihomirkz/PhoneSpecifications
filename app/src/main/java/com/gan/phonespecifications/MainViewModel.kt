package com.gan.phonespecifications

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gan.phonespecifications.data.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel : ViewModel() {

    var phoneList = MutableLiveData<List<Phone>>()
    var data = MutableLiveData<Data>()
    val errorMessage = MutableLiveData<String>()
    var details = MutableLiveData<DetailData>()


    fun getPhonesData(get: Call<Result>) {

        get.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                if (response.body() != null){
                    data.postValue(response.body()?.data)
                    phoneList.postValue(response.body()?.data?.phones)
                } else if (response.errorBody() != null) {
                    try {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        errorMessage.postValue(jObjError.getJSONObject("error").getString("message"))
                    } catch (e: Exception) {
                        errorMessage.postValue(e.message)
                    }
                }
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun getDetails(get: Call<Details>) {

        get.enqueue(object : Callback<Details> {
            override fun onResponse(call: Call<Details>, response: Response<Details>) {
                if (response.body() != null){
                    details.postValue(response.body()?.data)
                } else if (response.errorBody() != null) {
                    try {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        errorMessage.postValue(jObjError.getJSONObject("error").getString("message"))
                    } catch (e: Exception) {
                        errorMessage.postValue(e.message)
                    }
                }
            }

            override fun onFailure(call: Call<Details>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}


