package com.mahaabhitechsolutions.mahaKarya.base

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.media.RingtoneManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.MutableLiveData
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.mahaabhitechsolutions.mahaKarya.R
import com.mahaabhitechsolutions.mahaKarya.common.PeriodSelection
import com.mahaabhitechsolutions.mahaKarya.common.SingleDatePickerWIthViewTypeModel
import com.mahaabhitechsolutions.mahaKarya.common.TimePickerWithViewType
import com.mahaabhitechsolutions.mahaKarya.common.TimeSelection
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
open class BaseActivity @Inject constructor(): AppCompatActivity() {
    lateinit var mContext: Context

    private lateinit var storagePermission: Array<String>
    private lateinit var uri: Uri
    private lateinit var file : File
    private lateinit var camIntent: Intent
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<String>
    private var dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
    var progressDialog: ProgressDialog? = null
    private var prefs: SharedPreferences? = null

    private lateinit var imageLauncher: ActivityResultLauncher<Intent>
    var imageBase64List = ArrayList<String>()
    var imageBitmapList = ArrayList<Bitmap>()
    var imageUriList = ArrayList<Uri>()

    fun setUpToolbar(title: String) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = title
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext=this@BaseActivity
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }
    private fun setObservers() {

    }

    fun setUpToolbar(activity: AppCompatActivity, title: String) {
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setDisplayShowHomeEnabled(true)
        activity.supportActionBar?.title = title
    }

    companion object {
        //        cameraSetup
        const val CAMERA_REQUEST = 100
        const val STORAGE_REQUEST = 200
        var capturedImage: MutableLiveData<Uri> = MutableLiveData()

        open fun getSqlCurrentDate(): String {
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val date = Date()
            return formatter.format(date)
        }

        open fun getViewCurrentDate(): String {
            val formatter = SimpleDateFormat("dd-MM-yyyy")
            val date = Date()
            return formatter.format(date)
        }

        open fun getSqlCurrentTime(): String {
            val formatter = SimpleDateFormat("HH:mm")
            val date = Date()
            return formatter.format(date)
        }

        open fun errorToast(mContext: Context,text:String){
            Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show()
        }
    }


    private fun getSharedPref(): SharedPreferences {
        if(this.prefs != null) {
            return prefs!!
        }
        prefs = getSharedPreferences(packageName, Context.MODE_PRIVATE);
        return prefs!!
    }

    fun saveStorageBoolean(tag: String, data: Boolean) {
        prefs = getSharedPref()
        val prefEditor = prefs?.edit()
        prefEditor?.putBoolean(tag, data)
        prefEditor?.apply()
    }
    fun getStorageBoolean(tag: String): Boolean {
        prefs = getSharedPref()
        return prefs?.getBoolean(tag, false)!!
    }

//    fun isNetworkConnected(): Boolean {
//        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
//        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
//    }

    fun isNetworkConnected(): Boolean {
        // 0 means no internet,
        return getConnectionType() != 0
    }

    //    @IntRange(from = 0, to = 3)
    fun getConnectionType(): Int {
        var result = 0 // Returns connection type. 0: none; 1: mobile data; 2: wifi; 3: vpn
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        result = 2
                    } else if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        result = 1
                    } else if (hasTransport(NetworkCapabilities.TRANSPORT_VPN)){
                        result = 3
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = 2
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = 1
                    } else if(type == ConnectivityManager.TYPE_VPN) {
                        result = 3
                    }
                }
            }
        }
        return result
    }


    open fun showProgressDialog() {
        progressDialog = ProgressDialog(mContext)
        progressDialog!!.setMessage("Loading....")
        progressDialog!!.setCancelable(false)
        //    progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog!!.show()
    }

    open fun hideProgressDialog() {
        progressDialog?.dismiss()
    }


    fun saveStorageString(tag: String, data: String) {
        prefs = getSharedPref()
        val prefEditor = prefs?.edit()
        prefEditor?.putString(tag, data)
        prefEditor?.apply()
    }
    fun getStorageString(tag: String): String {
        prefs = getSharedPref()
        return prefs?.getString(tag, "")!!
    }

    fun clearStorage() {
        prefs = getSharedPref()
        val prefEditor = prefs?.edit()
        prefEditor?.clear()
        prefEditor?.apply()
    }

    open fun getSqlDate(): String? {
        dateFormat = SimpleDateFormat("yyyy-MM-dd")
        dateFormat.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
        return dateFormat.format(Date())
    }


    open fun getViewDate(): String? {
        dateFormat = SimpleDateFormat("dd-MM-yyyy")
        dateFormat.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
        return dateFormat.format(Date())
    }

    open fun playSound() {
        try {
            val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val r = RingtoneManager.getRingtone(applicationContext, notification)
            r.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun checkStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }
    // Requesting gallery permission
    private fun requestStoragePermission() {
        requestPermissions(storagePermission, BaseActivity.STORAGE_REQUEST)
    }
    private fun ifPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }
    private fun checkCameraPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            return ifPermissionGranted(Manifest.permission.READ_MEDIA_IMAGES)
                    && ifPermissionGranted(Manifest.permission.READ_MEDIA_VIDEO)
                    && ifPermissionGranted(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
                    && ifPermissionGranted(Manifest.permission.CAMERA)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ifPermissionGranted(Manifest.permission.READ_MEDIA_IMAGES)
                    && ifPermissionGranted(Manifest.permission.READ_MEDIA_VIDEO)
                    && ifPermissionGranted(Manifest.permission.CAMERA)
        } else {
            return ifPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)
                    && ifPermissionGranted(Manifest.permission.CAMERA)
        }

    }

    private fun pickFromGallery() {
        galleryLauncher.launch("image/*")
    }
    private fun pickFromCamera() {

        val imageFileName = "file" + System.currentTimeMillis().toString() + ".jpg"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        file = File(storageDir, imageFileName)
        uri = FileProvider.getUriForFile(this, "${packageName}.fileprovider", file)
        camIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        camIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        camIntent.putExtra("return-Data", true)
//        camIntent.putExtra(ENV.IMAGE_PREF_SHARE,true)
        cameraLauncher.launch(camIntent)
    }

    private fun convertImageUriToBase64(contentResolver: ContentResolver, imageUri: Uri): String? {
        return try {
            val inputStream: InputStream? = contentResolver.openInputStream(imageUri)
            val buffer = ByteArrayOutputStream()
            val bufferSize = 1024
            val data = ByteArray(bufferSize)
            var bytesRead= 0
            while (inputStream?.read(data, 0, bufferSize).also {
                    if (it != null) {
                        bytesRead = it
                    }
                } != -1) {
                buffer.write(data, 0, bytesRead)
            }
            val byteArray: ByteArray = buffer.toByteArray()
            Base64.encodeToString(byteArray, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getBitmapFromBase64(base64: String): Bitmap? {
        try {
            val decodedBytes: ByteArray = Base64.decode(base64, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (ex: Exception) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
        return null
    }
    fun viewImageFullScreen(){
        startActivity(Intent
            (Intent.ACTION_VIEW, Uri.parse(imageBase64List.elementAt(0)))
        )
    }


}