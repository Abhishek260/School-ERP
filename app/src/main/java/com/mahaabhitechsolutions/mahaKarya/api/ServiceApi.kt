package com.mahaabhitechsolutions.mahaKarya.api
import com.mahaabhitechsolutions.mahaKarya.common.CommonResult
import retrofit2.Call
import retrofit2.http.GET

interface ServiceApi {
    @GET("users/GetUsers")
    fun getBookingIndentInfo(
    ): Call<CommonResult>
}