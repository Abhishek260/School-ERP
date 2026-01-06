package com.mahaabhitechsolutions.eduvanta

class ENV {
    companion object {

        private const val LIVE_BASE_URL: String = "https://qa7.parentune.com/api/subscription/subscribe/v2/"
        private const val  OTHER_BASE_URL: String =  "https://quotable.io/"





        fun getBaseUrl(): String {
            return LIVE_BASE_URL;
        }

        fun getOtherUrl():String{
            return OTHER_BASE_URL;
        }
    }
}