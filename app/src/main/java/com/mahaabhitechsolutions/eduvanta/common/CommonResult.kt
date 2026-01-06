package com.mahaabhitechsolutions.eduvanta.common

data class CommonResult(
    val errorcode: String,
    val message: String,
    val response: List<Any>,
    val status: Int
)