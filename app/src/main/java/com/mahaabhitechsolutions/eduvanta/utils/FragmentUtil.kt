package com.mahaabhitechsolutions.eduvanta.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.mahaabhitechsolutions.eduvanta.R
import com.mahaabhitechsolutions.eduvanta.utils.FragmentTransition.Companion.DOWN_TO_TOP
import com.mahaabhitechsolutions.eduvanta.utils.FragmentTransition.Companion.LEFT_TO_RIGHT
import com.mahaabhitechsolutions.eduvanta.utils.FragmentTransition.Companion.NEITHER_LEFT_NOR_RIGHT
import com.mahaabhitechsolutions.eduvanta.utils.FragmentTransition.Companion.RIGHT_TO_LEFT
import com.mahaabhitechsolutions.eduvanta.utils.FragmentTransition.Companion.TOP_TO_DOWN

class FragmentUtil
{

    @Synchronized
    fun replaceFragment(
        context: Context,
        fragment: Fragment,
        frameLayoutId: Int,
        removeStack: Boolean,
        animConstant: Int
    ) {
        try {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
//            transaction.commitAllowingStateLoss()
            when (animConstant) {
                LEFT_TO_RIGHT -> transaction.setCustomAnimations(
                    R.anim.frag_exit_right,
                    R.anim.frag_enter_left
                )
                RIGHT_TO_LEFT -> transaction.setCustomAnimations(
                    R.anim.frag_enter_right,
                    R.anim.frag_exit_left
                )
                NEITHER_LEFT_NOR_RIGHT -> {}
                TOP_TO_DOWN -> transaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up)
                DOWN_TO_TOP -> transaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down)
                else -> {}
            }

            transaction.replace(frameLayoutId, fragment)
            transaction.addToBackStack(fragment.javaClass.name)
            transaction.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Synchronized
    fun removeFragment(context: Context, animConstant: Int, fragment: Fragment?) {
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        when (animConstant) {
            LEFT_TO_RIGHT -> transaction.setCustomAnimations(
                R.anim.frag_exit_right,
                R.anim.frag_enter_left
            )
            RIGHT_TO_LEFT -> transaction.setCustomAnimations(
                R.anim.frag_enter_right,
                R.anim.frag_exit_left
            )
            NEITHER_LEFT_NOR_RIGHT -> {}
            TOP_TO_DOWN -> transaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up)
            DOWN_TO_TOP -> transaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down)
            else -> {}
        }
        transaction.remove(fragment!!)
            .addToBackStack(null)
            .commit()
    }

    @Synchronized
    fun addFragment(context: Context, fragment: Fragment?, frameLayoutId: Int, animConstant: Int) {
        try {
            val transaction =
                (context as AppCompatActivity).supportFragmentManager.beginTransaction()
            when (animConstant) {
                LEFT_TO_RIGHT -> transaction.setCustomAnimations(
                    R.anim.frag_exit_right,
                    R.anim.frag_enter_left
                )
                RIGHT_TO_LEFT -> transaction.setCustomAnimations(
                    R.anim.frag_enter_right,
                    R.anim.frag_exit_left
                )
                NEITHER_LEFT_NOR_RIGHT -> {}
                TOP_TO_DOWN -> transaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up)
                DOWN_TO_TOP -> transaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down)
                else -> {}
            }
            transaction.add(frameLayoutId, fragment!!)
            //transaction.addToBackStack(fragment.getClass().getName());
            transaction.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun setFragment(
        fragment: Fragment?,
        removeStack: Boolean,
        activity: FragmentActivity,
        mContainer: Int,
        tag: String?,
        animConstant: Int
    ) {
        val fragmentManager = activity.supportFragmentManager
        val ftTransaction = fragmentManager.beginTransaction()
        when (animConstant) {
            LEFT_TO_RIGHT -> ftTransaction.setCustomAnimations(
                R.anim.frag_exit_right,
                R.anim.frag_enter_left
            )
            RIGHT_TO_LEFT -> ftTransaction.setCustomAnimations(
                R.anim.frag_enter_right,
                R.anim.frag_exit_left
            )
            NEITHER_LEFT_NOR_RIGHT -> {}
            TOP_TO_DOWN -> ftTransaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up)
            DOWN_TO_TOP -> ftTransaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down)
            else -> {}
        }
        if (removeStack) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            if (tag != null) ftTransaction.replace(
                mContainer,
                fragment!!,
                tag
            ) else ftTransaction.replace(
                mContainer,
                fragment!!
            )
        } else {
            if (tag != null) ftTransaction.replace(
                mContainer,
                fragment!!,
                tag
            ) else ftTransaction.replace(
                mContainer,
                fragment!!
            )
            ftTransaction.addToBackStack(null)
        }
        ftTransaction.commit()
    }

}