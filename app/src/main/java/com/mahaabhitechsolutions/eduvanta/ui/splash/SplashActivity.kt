package com.mahaabhitechsolutions.eduvanta.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mahaabhitechsolutions.eduvanta.MainActivity
import com.mahaabhitechsolutions.eduvanta.R
import com.mahaabhitechsolutions.eduvanta.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySplashBinding
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setStatusBar()
        animate()
    }

    private fun setStatusBar() {
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun animate() {
        val topAnim = AnimationUtils.loadAnimation(this, R.anim.top_to_center)
        val bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_to_center)

        mBinding.tvTitle.startAnimation(topAnim)
        mBinding.imgBoy.startAnimation(bottomAnim)

        handler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2500)
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null) // memory-safe
        super.onDestroy()
    }
}
