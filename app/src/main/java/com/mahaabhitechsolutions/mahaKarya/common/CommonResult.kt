package com.mahaabhitechsolutions.mahaKarya.common

data class CommonResult(
    val errorcode: String,
    val message: String,
    val response: List<Any>,
    val status: Int
)