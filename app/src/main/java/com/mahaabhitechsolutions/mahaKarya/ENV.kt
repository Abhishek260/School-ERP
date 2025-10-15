package com.mahaabhitechsolutions.mahaKarya

class ENV {
    companion object {

        private const val LIVE_BASE_URL: String = "https://qa7.parentune.com/api/subscription/subscribe/v2/";




        fun getBaseUrl(): String {
            return LIVE_BASE_URL;
        }

        fun getOtherUrl():String{
            return LIVE_BASE_URL;
        }
    }
}