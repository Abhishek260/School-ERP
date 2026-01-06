package com.mahaabhitechsolutions.eduvanta.common

data class SingleDatePickerWIthViewTypeModel(
    val viewType: String,
    val periodSelection: PeriodSelection,
    val withAdapter: Boolean = false,
    val index: Int = -1
)