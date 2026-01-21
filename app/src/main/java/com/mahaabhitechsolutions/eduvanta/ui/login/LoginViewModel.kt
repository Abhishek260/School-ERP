package com.mahaabhitechsolutions.eduvanta.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mahaabhitechsolutions.eduvanta.base.BaseViewModel
import com.mahaabhitechsolutions.eduvanta.ui.login.model.LoginModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val _repository: LoginRepository) : BaseViewModel() {
    init {
        isError = _repository.isError
    }
    val viewDialogLiveData: LiveData<Boolean>
        get()= _repository.viewDialogLiveData

    val loginLiveData:LiveData<List<LoginModel>>
        get() = _repository.loginLivedata

    fun validateLogin(username:String, password:String){
        viewModelScope.launch (Dispatchers.IO){
            _repository.validateLogin(username, password)
        }
    }
}