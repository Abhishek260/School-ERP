package com.mahaabhitechsolutions.eduvanta.ui.login

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mahaabhitechsolutions.eduvanta.R
import com.mahaabhitechsolutions.eduvanta.base.BaseActivity
import com.mahaabhitechsolutions.eduvanta.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private lateinit var mBinding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setStatusBar()
        animateBubble(findViewById(R.id.bubble1), 80f)
        animateBubble(findViewById(R.id.bubble2), -100f)
        val screenWidth = resources.displayMetrics.widthPixels
        animateBubbleLeftRight(findViewById(R.id.bubble3), screenWidth)
        startAnimations()
        setColor()
        setObservers()
        setOnClicks()
    }
    private fun setStatusBar() {
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun startAnimations(){
        animateBubble(findViewById(R.id.bubble1), 80f)
        animateBubble(findViewById(R.id.bubble2), -100f)
        val screenWidth = resources.displayMetrics.widthPixels
        animateBubbleLeftRight(findViewById(R.id.bubble3), screenWidth)
    }
    private fun setOnClicks(){
    }
    private fun animateBubble(view: View, distance: Float) {
        view.animate()
            .translationYBy(distance)
            .setDuration(6000)
            .setInterpolator(LinearInterpolator())
            .withEndAction {
                view.translationY = 0f
                animateBubble(view, distance)
            }
            .start()
    }
    private fun animateBubbleLeftRight(view: View, screenWidth: Int) {
        view.translationX = -200f

        view.animate()
            .translationX(screenWidth.toFloat() + 200)
            .setDuration(8000)
            .setInterpolator(LinearInterpolator())
            .withEndAction {
                animateBubbleLeftRight(view, screenWidth)
            }
            .start()
    }

    private fun setObservers(){
        viewModel.isError.observe(this) { errMsg ->
            errorToast(this,errMsg)
        }
        viewModel.viewDialogLiveData.observe(this) { show ->
            if (show) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
    }

    private fun setColor(){
        mBinding.tilUsername.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                mBinding.tilUsername.setBoxBackgroundColorResource(android.R.color.white)
                mBinding.tilUsername.hintTextColor =
                    ColorStateList.valueOf(Color.parseColor("#155DFC"))
            } else {
                mBinding.tilUsername.setBoxBackgroundColorResource(R.color.input_bg_default)
                mBinding.tilUsername.hintTextColor =
                    ColorStateList.valueOf(Color.parseColor("#E2E8F0"))
            }
        }
        mBinding.tilPassword.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                mBinding.tilPassword.setBoxBackgroundColorResource(android.R.color.white)
                mBinding.tilPassword.hintTextColor =
                    ColorStateList.valueOf(Color.parseColor("#155DFC"))
            } else {
                mBinding.tilPassword.setBoxBackgroundColorResource(R.color.input_bg_default)
                mBinding.tilPassword.hintTextColor =
                    ColorStateList.valueOf(Color.parseColor("#E2E8F0"))
            }
        }
    }


}