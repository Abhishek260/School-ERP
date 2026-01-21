package com.mahaabhitechsolutions.eduvanta

class ENV {
    companion object {
        const val IS_DEBUG: Boolean = false
        const val USER_ROLE_TEACHER: String = "Teacher"
        const val USER_ROLE_STUDENT: String = "Student"

        private const val LIVE_BASE_URL: String = "https://qa7.parentune.com/api/subscription/subscribe/v2/"
        private const val  OTHER_BASE_URL: String =  "http://localhost:8080/api/"



        fun getBaseUrl(): String {
            if (IS_DEBUG) {
                return OTHER_BASE_URL;
            }
            return LIVE_BASE_URL;
        }

        fun getOtherUrl():String{
            return OTHER_BASE_URL;
        }
    }
}