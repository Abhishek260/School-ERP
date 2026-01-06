package com.mahaabhitechsolutions.eduvanta.utils

interface FragmentTransition {
    companion object {
        const val LEFT_TO_RIGHT = 1
        const val RIGHT_TO_LEFT = 2
        const val NEITHER_LEFT_NOR_RIGHT = 3
        const val TOP_TO_DOWN = 4
        const val DOWN_TO_TOP = 5
    }
}