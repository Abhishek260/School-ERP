package com.mahaabhitechsolutions.eduvanta.base
import androidx.lifecycle.MutableLiveData
import com.mahaabhitechsolutions.eduvanta.api.OtherService
import com.mahaabhitechsolutions.eduvanta.api.ServiceApi
import com.mahaabhitechsolutions.eduvanta.hilt.BaseUrl
import com.mahaabhitechsolutions.eduvanta.hilt.OtherUrl
import javax.inject.Inject

open class BaseRepository @Inject constructor() {
    @Inject
   @BaseUrl lateinit var serviceApi: ServiceApi
   @OtherUrl lateinit var otherService: OtherService
    val API_ERROR = "Could not get the data from the SERVER."
    val SERVER_ERROR = "Something Went Wrong Please Try Again Later"
    val INTERNET_ERROR = "Internet Not Working Please Check Your Internet Connection"
    val isError: MutableLiveData<String> = MutableLiveData()
    val viewDialogMutData: MutableLiveData<Boolean> = MutableLiveData()

}