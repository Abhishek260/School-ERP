package com.mahaabhitechsolutions.eduvanta.api
import com.mahaabhitechsolutions.eduvanta.common.CommonResult
import com.mahaabhitechsolutions.eduvanta.ui.task.model.TaskModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    @GET("users/GetUsers")
    fun getUsers(
    ): Call<CommonResult>

    @GET("users/validateLogin")
    fun validateLogin(
        @Query("username") companyId: String,
        @Query("password") userCode: String?
    ): Call<CommonResult>

    @GET("plans")
    fun getPlans(
    ): Call<TaskModel>
}