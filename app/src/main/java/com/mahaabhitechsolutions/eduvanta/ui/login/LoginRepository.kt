package com.mahaabhitechsolutions.eduvanta.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mahaabhitechsolutions.eduvanta.base.BaseRepository
import com.mahaabhitechsolutions.eduvanta.common.CommonResult
import com.mahaabhitechsolutions.eduvanta.ui.login.model.LoginModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor() : BaseRepository() {

    private val loginMutData = MutableLiveData<List<LoginModel>>()

    val loginLivedata : LiveData<List<LoginModel>>
        get() = loginMutData
    val viewDialogLiveData: LiveData<Boolean>
        get() = viewDialogMutData

    fun validateLogin(username:String, password:String) {
        viewDialogMutData.postValue(true)
        serviceApi.validateLogin(username,password).enqueue(object : Callback<CommonResult> {
            override fun onResponse(call: Call<CommonResult>, response: Response<CommonResult>) {
                viewDialogMutData.postValue(false)
                if (response.body() != null) {
                    val result = response.body()!!
                    val gson = Gson()
                    if (result.status==1){
                        val responseData = result.response
                        val listType =
                            object : TypeToken<List<LoginModel>>() {}.type
                        val loginList: List<LoginModel> = gson.fromJson(  gson.toJson(responseData), listType)
                        loginMutData.postValue(loginList)
                        Log.d("API","${result.response}")

                    } else{
                        messageMutData.postValue(result.errorcode)
                        Log.d("API", result.message)
                    }
                } else {
                    isError.postValue(SERVER_ERROR)
                    Log.d("API", SERVER_ERROR)
                }
            }
            override fun onFailure(call: Call<CommonResult?>, t: Throwable) {
                viewDialogMutData.postValue(false)
                isError.postValue(t.message)
                Log.d("API","${t.message}")
            }
        })
    }
}