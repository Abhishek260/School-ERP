package com.mahaabhitechsolutions.eduvanta

class ENV {
    companion object {
        const val IS_DEBUG: Boolean  = true
        const val USER_ROLE_TEACHER: String = "Teacher"
        const val USER_ROLE_STUDENT: String = "Student"

        private const val LIVE_BASE_URL: String = "https://qa7.parentune.com/api/subscription/subscribe/v2/"
//        private const val  OTHER_BASE_URL: String =  "http://192.168.1.12:8080/api/"   /* for real device */
        private const val OTHER_BASE_URL = "http://10.0.2.2:8080/api/"               /* for emulator  */





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