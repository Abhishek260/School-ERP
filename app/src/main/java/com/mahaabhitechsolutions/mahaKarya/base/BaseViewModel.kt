package com.mahaabhitechsolutions.mahaKarya.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor( protected val baseRepo: BaseRepository) : ViewModel() {

    lateinit var isError: LiveData<String>
//    get() = baseRepo.isError
}