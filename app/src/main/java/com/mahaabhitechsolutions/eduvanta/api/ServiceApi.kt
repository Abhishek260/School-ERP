package com.mahaabhitechsolutions.eduvanta.api
import com.mahaabhitechsolutions.eduvanta.common.CommonResult
import com.mahaabhitechsolutions.eduvanta.ui.task.model.TaskModel
import retrofit2.Call
import retrofit2.http.GET

interface ServiceApi {

    @GET("users/GetUsers")
    fun getBookingIndentInfo(
    ): Call<CommonResult>

    @GET("plans")
    fun getPlans(
    ): Call<TaskModel>
}