package com.mahaabhitechsolutions.mahaKarya.common

data class TimePickerWithViewType(
    val viewType: String,
    val timeSelection: String,
    val withAdapter: Boolean = false,
    val index: Int = -1
)