package com.sultanayubi.waStatussaver.Utils

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.sultanayubi.waStatussaver.R

fun Activity.replaceFragment(fragment: Fragment, args: Bundle? = null, addToBackStack: Boolean = true) {
    val fragmentActivity = this as FragmentActivity
    val transaction = fragmentActivity.supportFragmentManager.beginTransaction()

    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
    args?.let {
        fragment.arguments = it
    }
    transaction.replace(R.id.frameLayout, fragment)
    if (addToBackStack) {
        transaction.addToBackStack(null)
    }
    transaction.commit()
}

