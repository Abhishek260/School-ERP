package com.mahaabhitechsolutions.mahaKarya.api
import com.mahaabhitechsolutions.mahaKarya.common.CommonResult
import com.mahaabhitechsolutions.mahaKarya.ui.task.model.TaskModel
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