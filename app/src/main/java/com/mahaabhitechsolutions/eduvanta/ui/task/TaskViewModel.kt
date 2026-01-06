package com.mahaabhitechsolutions.eduvanta.ui.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mahaabhitechsolutions.eduvanta.base.BaseViewModel
import com.mahaabhitechsolutions.eduvanta.ui.task.model.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val _repository: TaskRepository) : BaseViewModel() {
    init {
        isError = _repository.isError
    }
    val taskLiveData : LiveData<List<Data>>
        get() = _repository.taskLivedata

    val viewDialogLiveData: LiveData<Boolean>
        get()= _repository.viewDialogLiveData

    fun getPlans() {
        viewModelScope.launch(Dispatchers.IO){
            _repository.getPlans()
        }
    }
}