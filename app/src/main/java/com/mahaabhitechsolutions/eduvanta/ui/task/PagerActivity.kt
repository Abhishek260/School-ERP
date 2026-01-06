package com.mahaabhitechsolutions.eduvanta.ui.task

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mahaabhitechsolutions.eduvanta.base.BaseActivity.Companion.errorToast
import com.mahaabhitechsolutions.eduvanta.databinding.ActivityTaskBinding
import com.mahaabhitechsolutions.eduvanta.ui.task.adapter.PlanPagerAdapter
import com.mahaabhitechsolutions.eduvanta.ui.task.model.Data
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagerActivity : AppCompatActivity() {
    private val viewModel: TaskViewModel by viewModels()
    private var planData: List<Data> = ArrayList()
    private lateinit var activityBinding: ActivityTaskBinding
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityBinding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)
        setStatusBar()
        getPlans()
        setObservers()

        }

    private fun getPlans() {
        viewModel.getPlans()
        Log.d("API","getPlans")
    }

    private fun setObservers() {
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
        viewModel.taskLiveData.observe(this) { planData ->
            this.planData = planData
            val adapter = PlanPagerAdapter(planData,activityBinding.viewPagerPlans)
            activityBinding.viewPagerPlans.adapter = adapter
//            activityBinding.dotsIndicator.attachTo(activityBinding.viewPagerPlans)

            Log.d("Task_Data","$planData")
        }
    }


    private fun setStatusBar(){
            ViewCompat.setOnApplyWindowInsetsListener(activityBinding.root) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

    }

    private fun showProgressDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Loading....")
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
    }

    private fun hideProgressDialog() {
        progressDialog?.dismiss()
    }
}