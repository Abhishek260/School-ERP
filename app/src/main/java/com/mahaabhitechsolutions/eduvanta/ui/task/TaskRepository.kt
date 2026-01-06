package com.mahaabhitechsolutions.eduvanta.ui.task

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahaabhitechsolutions.eduvanta.base.BaseRepository
import com.mahaabhitechsolutions.eduvanta.ui.task.model.Data
import com.mahaabhitechsolutions.eduvanta.ui.task.model.TaskModel
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call
import javax.inject.Inject

class TaskRepository  @Inject constructor() : BaseRepository() {

    private val taskMutData = MutableLiveData<List<Data>>()
    val taskLivedata : LiveData<List<Data>>
        get() = taskMutData

    val viewDialogLiveData: LiveData<Boolean>
        get() = viewDialogMutData


    fun getPlans() {
        viewDialogMutData.postValue(true)
        serviceApi.getPlans().enqueue(object : Callback<TaskModel> {
            override fun onResponse(call: Call<TaskModel>, response: Response<TaskModel>) {
                viewDialogMutData.postValue(false)
                if (response.body() != null) {
                    val result = response.body()!!
                    if (result.statusCode==200){
                        taskMutData.postValue(result.data)
                        Log.d("API","${result.data}")
                    }

                } else {
                    isError.postValue(SERVER_ERROR)
                    Log.d("API", SERVER_ERROR)
                }
            }

            override fun onFailure(call: Call<TaskModel?>, t: Throwable) {
                viewDialogMutData.postValue(false)
                isError.postValue(t.message)
                Log.d("API","${t.message}")
            }
        })
    }
}