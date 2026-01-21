package com.mahaabhitechsolutions.eduvanta.common

data class CommonResult(
    val status: Int,
    val errorcode: String,
    val message: String,
    val response: List<Any>
)