package com.mahaabhitechsolutions.eduvanta.ui.task.model

data class TaskModel(
    val statusCode: Int,
    val message: String,
    val data: List<Data>,
    val error: List<Any>
)