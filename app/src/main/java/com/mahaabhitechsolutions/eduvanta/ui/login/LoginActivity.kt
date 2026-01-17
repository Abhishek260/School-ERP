package com.mahaabhitechsolutions.eduvanta.ui.login

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mahaabhitechsolutions.eduvanta.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setStatusBar()
        setOnClicks()
    }
    private fun setStatusBar() {
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun setOnClicks(){
        mBinding.hidePassImg.setOnClickListener {
            mBinding.etPassword.transformationMethod =
                PasswordTransformationMethod.getInstance()
            mBinding.showPassImg.visibility = View.VISIBLE
            mBinding.hidePassImg.visibility = View.GONE
            mBinding.etPassword.setSelection(mBinding.etPassword.text!!.length)
        }
        mBinding.showPassImg.setOnClickListener {
            mBinding.etPassword.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
            mBinding.showPassImg.visibility = View.GONE
            mBinding.hidePassImg.visibility = View.VISIBLE
            mBinding.etPassword.setSelection(mBinding.etPassword.text!!.length)
        }
    }
}