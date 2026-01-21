package com.mahaabhitechsolutions.eduvanta.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var baseRepo: BaseRepository
    lateinit var isError: LiveData<String>
    lateinit var massage: LiveData<String>
}