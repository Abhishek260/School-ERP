package com.mahaabhitechsolutions.eduvanta.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mahaabhitechsolutions.eduvanta.MainActivity
import com.mahaabhitechsolutions.eduvanta.R
import com.mahaabhitechsolutions.eduvanta.databinding.ActivitySplashBinding
import com.mahaabhitechsolutions.eduvanta.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySplashBinding
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setStatusBar()
        animateSplash()
    }

    private fun setStatusBar() {
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun animateSplash() {

        val edAnim = AnimationUtils.loadAnimation(this, R.anim.left_to_center)
        val vantaAnim = AnimationUtils.loadAnimation(this, R.anim.right_to_center)
        val uAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_to_center)
        val penDrop = AnimationUtils.loadAnimation(this, R.anim.pen_drop)

        mBinding.tvEd.startAnimation(edAnim)
        mBinding.tvVanta.startAnimation(vantaAnim)

        handler.postDelayed({
            mBinding.imgU.visibility = View.VISIBLE
            mBinding.imgU.startAnimation(uAnim)
        }, 700)

        handler.postDelayed({
            mBinding.imgPens.visibility = View.VISIBLE
            mBinding.imgPens.startAnimation(penDrop)
        }, 1400)


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 2500)
    }


    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}
