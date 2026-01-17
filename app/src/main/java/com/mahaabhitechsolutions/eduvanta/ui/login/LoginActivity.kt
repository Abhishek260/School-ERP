package com.mahaabhitechsolutions.eduvanta.ui.login

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mahaabhitechsolutions.eduvanta.R
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
        animateBubble(findViewById(R.id.bubble1), 80f)
        animateBubble(findViewById(R.id.bubble2), -100f)
        val screenWidth = resources.displayMetrics.widthPixels
        animateBubbleLeftRight(findViewById(R.id.bubble3), screenWidth)


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


}