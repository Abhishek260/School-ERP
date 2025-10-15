package com.mahaabhitechsolutions.mahaKarya.ui.task.model

data class TaskModel(
    val statusCode: Int,
    val message: String,
    val data: List<Data>,
    val error: List<Any>
)