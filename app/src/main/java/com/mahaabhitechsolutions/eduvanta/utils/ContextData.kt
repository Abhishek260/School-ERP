package com.mahaabhitechsolutions.eduvanta.utils

import android.app.DownloadManager
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.res.Configuration
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.LocaleList
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mahaabhitechsolutions.eduvanta.ENV.Companion.USER_ROLE_STUDENT
import com.mahaabhitechsolutions.eduvanta.ENV.Companion.USER_ROLE_TEACHER
import com.mahaabhitechsolutions.eduvanta.R
import com.mahaabhitechsolutions.eduvanta.utils.FragmentTransition.Companion.NEITHER_LEFT_NOR_RIGHT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

var dManager: DownloadManager? = null
var downloadID: Long = 0



fun checkUserName(
    langValue: HashMap<String, String>,
    firstName: String,
    mContext: Context,
): Boolean {

    val regex = Regex("^\\S+(\\s\\S+)?$")
//    return if (regex.matches(firstName) && firstName.length <= 35) {
    return if (firstName.length <= 35) {
        Log.d("checkUserName", "true")
        true
    } else {
        Log.d("checkUserName", "false")
        Toast.makeText(mContext, langValue["key_Invalid_name"], Toast.LENGTH_SHORT).show()
        false
    }
}

fun isNetwork(context: Context): Boolean {

    // register activity with the connectivity manager service
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // if the android version is equal to M
    // or greater we need to use the
    // NetworkCapabilities to check what type of
    // network has the internet connection
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        // Returns a Network object corresponding to
        // the currently active default data network.
        val network = connectivityManager.activeNetwork ?: return false

        // Representation of the capabilities of an active network.
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            // Indicates this network uses a Wi-Fi transport,
            // or WiFi has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

            // Indicates this network uses a Cellular transport. or
            // Cellular has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

            // else return false
            else -> false
        }
    } else {
        // if the android version is below M
        @Suppress("DEPRECATION") val networkInfo =
            connectivityManager.activeNetworkInfo ?: return false
        @Suppress("DEPRECATION")
        return networkInfo.isConnected
    }
}

fun isSlowNetwork(context: Context): Boolean {
    var isSlow: Boolean = false
    val connectivityManager =
        context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
    if (networkCapabilities != null) {
        // Check if the network has a good transport (like WIFI or Ethernet)
        if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        ) {
            // Check if the network is fast (e.g., 3G or 4G)
            isSlow =
                if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                ) {
                    if (networkCapabilities.linkUpstreamBandwidthKbps > 10) {
                        false
                    } else {
                        Toast.makeText(context, "Slow internet connection", Toast.LENGTH_SHORT)
                            .show()
                        true
                    }
                } else {
                    Toast.makeText(context, "Slow internet connection", Toast.LENGTH_SHORT).show()
                    true
                }
        } else {
            Toast.makeText(context, "No WIFI or No connection", Toast.LENGTH_SHORT)
                .show()
            isSlow = true
        }
    }
    return isSlow
}

fun saveList(context: Context, listName: String?, list: ArrayList<String>) {
    val gson = Gson()
    val json = gson.toJson(list)
    val pref = context.getSharedPreferences("PREFRENCE_SAVE", MODE_PRIVATE)
    val editor = pref.edit()//converting list to Json
    editor.putString(listName, json)
    editor.apply()
}

suspend fun saveListIO(context: Context, listName: String?, list: ArrayList<String>) {
    withContext(Dispatchers.IO) {
        try {
            val gson = Gson()
            val json = gson.toJson(list)
            val pref = context.applicationContext.getSharedPreferences("PREFRENCE_SAVE",
                MODE_PRIVATE
            )
            pref.edit().putString(listName, json).apply()
        } catch (e: Exception) {
            Log.e("saveListIO", "error saving list", e)
        }
    }
}

fun getList(context: Context, listName: String?): ArrayList<String>? {
    val gson = Gson()
    val pref = context.getSharedPreferences("PREFRENCE_SAVE", MODE_PRIVATE)
    val json = pref.getString(listName, null)
    return if (json != null) {
        val type =
            object : TypeToken<ArrayList<String>>() {}.type//converting the json to list
        gson.fromJson(json, type)//returning the list
    } else {
        null
    }
}

suspend fun getListIO(context: Context, listName: String?): ArrayList<String>? {
    return withContext(Dispatchers.IO) {
        try {
            val gson = Gson()
            val pref = context.applicationContext.getSharedPreferences("PREFRENCE_SAVE",
                MODE_PRIVATE
            )
            val json = pref.getString(listName, null) ?: return@withContext null
            val type = object : TypeToken<ArrayList<String>>() {}.type
            gson.fromJson<ArrayList<String>>(json, type)
        } catch (e: Exception) {
            Log.e("getListIO", "error reading list", e)
            null
        }
    }
}

fun saveHashMap(context: Context, listName: String?, list: HashMap<String, String>) {
    val gson = Gson()
    val json = gson.toJson(list)
    val pref = context.getSharedPreferences("PREFRENCE_SAVE", MODE_PRIVATE)
    val editor = pref.edit()//converting list to Json
    editor.putString(listName, json)
    editor.apply()
}

suspend fun saveHashMapIO(context: Context, listName: String?, list: HashMap<String, String>) {
    withContext(Dispatchers.IO) {
        try {
            val gson = Gson()
            val json = gson.toJson(list)
            val pref = context.applicationContext.getSharedPreferences("PREFRENCE_SAVE",
                MODE_PRIVATE
            )
            pref.edit().putString(listName, json).apply()
        } catch (e: Exception) {
            Log.e("saveHashMapIO", "error saving hashmap", e)
        }
    }
}

//getting the list from shared preference
@Throws(NullPointerException::class)
fun getHashMap(context: Context, listName: String?): HashMap<String, String> {
    val gson = Gson()
    var json: String = ""
    var type: Type? = null
    try {
        val pref = context.getSharedPreferences("PREFRENCE_SAVE", MODE_PRIVATE)
//    json = pref.getString(listName, null) ?: throw NullPointerException()
        json = pref.getString(listName, "").toString()
        type = object : TypeToken<HashMap<String, String>>() {}.type//converting the json to list
    } catch (npe: NullPointerException) {
        Log.d("Null pointer exception", " error :- ${npe.toString()}")
    }
    return gson.fromJson(json, type)//returning the list
}
suspend fun getHashMapIO(context: Context, listName: String?): HashMap<String, String> {
    return withContext(Dispatchers.IO) {
        try {
            val gson = Gson()
            val pref = context.applicationContext.getSharedPreferences("PREFRENCE_SAVE",
                MODE_PRIVATE
            )
            // safe default "{}" so gson.fromJson doesn't throw on empty string
            val json = pref.getString(listName, "{}") ?: "{}"
            val type = object : TypeToken<HashMap<String, String>>() {}.type
            gson.fromJson<HashMap<String, String>>(json, type) ?: HashMap()
        } catch (e: Exception) {
            Log.e("getHashMapIO", "error reading hashmap", e)
            HashMap()
        }
    }
}

fun saveData(context: Context, key: String, value: String) {
    val pref = context.getSharedPreferences("PREFRENCE_SAVE", MODE_PRIVATE)
    val editor = pref.edit()
    editor.putString(key, value)
    editor.apply()
}

suspend fun saveDataIO(context: Context, key: String, value: String) {
    withContext(Dispatchers.IO) {
        try {
            val pref = context.applicationContext.getSharedPreferences("PREFRENCE_SAVE",
                MODE_PRIVATE
            )
            pref.edit().putString(key, value).apply()
        } catch (e: Exception) {
            Log.e("saveDataIO", "error saving data", e)
        }
    }
}



fun getProfileUrl(context: Context, key: String): String? {
    val pref = context.getSharedPreferences("PREFRENCE_SAVE", MODE_PRIVATE)
    return pref.getString(key, null)
}

fun clearData(context: Context, key: String) {
    val pref = context.getSharedPreferences("PREFRENCE_SAVE", MODE_PRIVATE)
    val editor = pref.edit()
    editor.remove(key)
    editor.clear()
    editor.apply()
}

fun getData(context: Context, key: String): String {
    val pref = context.getSharedPreferences("PREFRENCE_SAVE", MODE_PRIVATE)
    return pref.getString(key, "null").toString()
}
suspend fun getDataIO(context: Context, key: String): String {
    return withContext(Dispatchers.IO) {
        try {
            val pref = context.applicationContext.getSharedPreferences("PREFRENCE_SAVE",
                MODE_PRIVATE
            )
            pref.getString(key, "null").toString()
        } catch (e: Exception) {
            Log.e("getDataIO", "error getting data", e)
            "null"
        }
    }
}

fun setAppLocale(context: Context, language: String) {
    val activityRes: Resources = context.resources
    val activityConf = activityRes.configuration
    val newLocale = Locale(language)
    activityConf.setLocale(newLocale)
    activityRes.updateConfiguration(activityConf, activityRes.displayMetrics)

    val applicationRes: Resources = context.applicationContext.resources
    val applicationConf = applicationRes.configuration
    applicationConf.setLocale(newLocale)
    applicationRes.updateConfiguration(
        applicationConf,
        applicationRes.displayMetrics
    )
}

fun setLocale(context: Context, language: String?): Context? {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val resources: Resources = context.resources
    val configuration = Configuration(resources.configuration)
    configuration.setLayoutDirection(locale)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        configuration.setLocale(locale)
        val localeList = LocaleList(locale)
        LocaleList.setDefault(localeList)
        configuration.setLocales(localeList)
    } else {
        configuration.locale = locale
        configuration.setLocale(locale)
    }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
        context.createConfigurationContext(configuration)
    } else {
        resources.updateConfiguration(configuration, resources.displayMetrics)
        context
    }
}

fun checkIfFieldValid(text: String): Boolean {
    return text.length >= 2
}

fun checkIfUsernameValid(user: String): Boolean {
    val pattern = Regex("^[A-Za-z0-9_@]+\$")
    //val pattern = Regex("^[A-Za-z0-9_@.]+\$")
    return pattern.containsMatchIn(user)
}

fun checkIfPasswordValid(user: String): Boolean {
    val pattern = Regex("^[A-Za-z0-9_@]+\$")
    //val pattern = Regex("^[A-Za-z0-9_@.]+\$")
    return pattern.containsMatchIn(user)
}

fun validIndianNumber(mobile: String): Boolean {
    val pattern = Regex("^[6-9]\\d{9}\$")
    return pattern.containsMatchIn(mobile)
}

fun dwn_certificate(url: String?, name: String) {
    val request1 = DownloadManager.Request(Uri.parse(url))
    Log.d("dwn_certificate: ", "download : $url")
    //dwnType = 0
    request1.setDescription("Downloading Certificate") //appears the same in Notification bar while downloading
    request1.setTitle("Certificate $name")
    request1.setVisibleInDownloadsUi(true)
    request1.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    request1.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name)
    downloadID = dManager!!.enqueue(request1)
}

fun convertDateInS(): String {
    var formatted: String = ""
    formatted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        current.format(formatter)
    } else {
        val simpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val currentTime = Date()
        simpleDateFormat.format(currentTime)
    }
    return formatted.toString()
}

fun setEditTextNotCopyAble(view: EditText) {
    view.customSelectionActionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(actionMode: ActionMode?, item: MenuItem?): Boolean {
            return false
        }

        override fun onDestroyActionMode(actionMode: ActionMode?) {}
    }
}

fun downloadFileTest(fileName: String, desc: String, url: String, context: Context) {
    val request = DownloadManager.Request(Uri.parse(url))
        .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        .setTitle(fileName)
        .setDescription(desc)
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setAllowedOverMetered(true)
        .setAllowedOverRoaming(false)
        .setDestinationInExternalFilesDir(
            context,
            Environment.DIRECTORY_DOWNLOADS,
            ".pdf"
        )
    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val downloadIds = downloadManager.enqueue(request)
}



fun fromHtml(source: String?): Spanned? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(
            "<font color=#000000>$source</font> <font color=#ff002b>*</font>",
            Html.FROM_HTML_MODE_LEGACY
        )
    } else {
        Html.fromHtml("<font color=#000000>$source</font> <font color=#ff002b>*</font>")
    }
}

//fun fromHtmlLine(source: String?): Spanned {
//    val spannableStringBuilder = SpannableStringBuilder(source)
//
//    // Apply UnderlineSpan to the specified range
//    source?.let {
//        spannableStringBuilder.setSpan(
//            UnderlineSpan(),
//            0,
//            it.length,
//            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        )
//    }
//    return spannableStringBuilder
//}

/*fun fromHtmlLine(source: String?): Spanned {
    val safeSource = source ?: ""
    val spannableStringBuilder = SpannableStringBuilder(safeSource)

    // Apply UnderlineSpan to the specified range
    if (safeSource.isNotEmpty() || (safeSource != null)) {
        spannableStringBuilder.setSpan(
            UnderlineSpan(),
            0,
            safeSource.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return spannableStringBuilder
}*/

fun fromHtmlLine(source: String?): Spanned {
    val safeSource = source ?: ""
    val spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(safeSource, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(safeSource)
    }

    val spannableStringBuilder = SpannableStringBuilder(spanned)

    // Apply UnderlineSpan to the specified range
    if (spannableStringBuilder.isNotEmpty() || (spannableStringBuilder != null)) {
        spannableStringBuilder.setSpan(
            UnderlineSpan(),
            0,
            spannableStringBuilder.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return spannableStringBuilder
}

fun boldText(source: String?): Spanned? {
//    var str = "<b>$source</b>"

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(
            "<font color=#ff002b>$source</font>",
            Html.FROM_HTML_MODE_LEGACY
        )
    } else {
        Html.fromHtml("<font color=#ff002b>$source</font>")
    }
}

//Method to set locale for system function default app language

fun setLocaleLang(mContext: Context) {
    if (getData(mContext, mContext.getString(R.string.key_lang_id)) == "1") {
        setAppLocale(mContext, "en")
        Locale.setDefault(Locale("en"))
    } else if (getData(mContext, mContext.getString(R.string.key_lang_id)) == "2") {
        setAppLocale(mContext, "hi")
        Locale.setDefault(Locale("hi"))
    } else if (getData(mContext, mContext.getString(R.string.key_lang_id)) == "3") {
        setAppLocale(mContext, "bn")
        Locale.setDefault(Locale("bn"))
    } else if (getData(mContext, mContext.getString(R.string.key_lang_id)) == "4") {
        setAppLocale(mContext, "gu")
        Locale.setDefault(Locale("gu"))
    }
}

//fun openFragment(mContext: Context, fragment: Fragment?) {
//    val fragmentUtil = FragmentUtil()
//    if (fragment != null) {
//        fragmentUtil.replaceFragment(
//            mContext,
//            fragment,
//            R.id.home_container,
//            true,
//            NEITHER_LEFT_NOR_RIGHT
//        )
//    }
//}

fun isNotificationsEnabled(context: Context): Boolean {
    val notificationManager = NotificationManagerCompat.from(context)
    return notificationManager.areNotificationsEnabled()
}


fun checkUserRole(userTypeId: String): String {
    var userRole = ""
    when (userTypeId) {
        "7" -> {
            userRole = USER_ROLE_STUDENT
        }

        "8" -> {
            userRole = USER_ROLE_TEACHER
        }
    }
    return userRole
}

//Common method of scrolling animation for Screening1, Profiler form & Advance Screening
fun setAnimation(
    viewToAnimate: View,
    position: Int,
    lastPosition: Int,
    mContext: Context,
) {

    if (position >= lastPosition) {
        val animation: Animation =
            AnimationUtils.loadAnimation(mContext, R.anim.frag_enter_right_1)
        viewToAnimate.startAnimation(animation)
    }
}


fun hideKeyboard(view: View) {
    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}
/*private fun showKeyboard(view: View) {
    val imm =  view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}*/

fun saveBannerStatusPreference(context: Context, i: String) {
    val pref =
        context.getSharedPreferences("PREFRENCE_SAVE", MODE_PRIVATE)
    val editor = pref.edit()
    editor.putString(context.getString(R.string.banner_show), i)
    editor.apply()
}

fun getBannerStatusPref(context: Context): String {
    val pref = context.getSharedPreferences("PREFRENCE_SAVE", MODE_PRIVATE)
    return pref.getString(context.getString(R.string.banner_show), "0").toString()
}
suspend fun getBannerStatusPrefIO(context: Context): String {
    return withContext(Dispatchers.IO) {
        val pref = context.applicationContext.getSharedPreferences("PREFRENCE_SAVE", MODE_PRIVATE)
        pref.getString(context.getString(R.string.banner_show), "0").toString()
    }
}
fun handleException(tag: String, e: Exception, context: Context) {
    e.printStackTrace()
    Log.e(tag, "Exception: ${e.message}", e)
    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
}
