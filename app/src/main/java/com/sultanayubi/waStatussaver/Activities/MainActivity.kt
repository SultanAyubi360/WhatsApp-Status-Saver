package com.sultanayubi.waStatussaver.Activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.sultanayubi.waStatussaver.databinding.ActivityMainBinding
import com.sultanayubi.waStatussaver.Utils.Constants
import com.sultanayubi.waStatussaver.Utils.SharedPrefKeys
import com.sultanayubi.waStatussaver.Utils.SharedPrefUtils
import com.sultanayubi.waStatussaver.Fragments.SettingsFragment
import com.sultanayubi.waStatussaver.Fragments.StatusFragment
import com.sultanayubi.waStatussaver.R
import com.sultanayubi.waStatussaver.Utils.replaceFragment
import com.sultanayubi.waStatussaver.Utils.slideFromStart
import com.sultanayubi.waStatussaver.Utils.slideToEndWithFadeOut

class MainActivity : AppCompatActivity() {
    private val activity = this
    private val PERMISSION_REQUEST_CODE = 50

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        SharedPrefUtils.init(activity)

        binding.apply {
            splashLogic()
            requestPermission()

            if (savedInstanceState != null) {
                val selectedMenuItem = savedInstanceState.getInt("selected_menu_item")
                BottomNavMenu.selectedItemId = selectedMenuItem
            } else {
                val whatsAppStatusFragment = StatusFragment()
                val bundle = Bundle()
                bundle.putString(Constants.FRAGMENT_TYPE_KEY, Constants.TYPE_WHATSAPP_MAIN)
                replaceFragment(whatsAppStatusFragment, bundle)
            }

            BottomNavMenu.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_status -> {
                        val whatsAppStatusFragment = StatusFragment()
                        val bundle = Bundle()
                        bundle.putString(Constants.FRAGMENT_TYPE_KEY, Constants.TYPE_WHATSAPP_MAIN)
                        replaceFragment(whatsAppStatusFragment, bundle)
                    }

                    R.id.menu_business_status -> {
                        val whatsAppStatusFragment = StatusFragment()
                        val bundle = Bundle()
                        bundle.putString(Constants.FRAGMENT_TYPE_KEY, Constants.TYPE_WHATSAPP_BUSINESS)
                        replaceFragment(whatsAppStatusFragment, bundle)
                    }

                    R.id.menu_settings -> {
                        replaceFragment(SettingsFragment(), addToBackStack = false)
                    }
                }
                true
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selected_menu_item", binding.BottomNavMenu.selectedItemId)
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            val isPermissionsGranted = SharedPrefUtils.getPrefBoolean(
                SharedPrefKeys.PREF_KEY_IS_PERMISSIONS_GRANTED,
                false
            )
            if (!isPermissionsGranted) {
                ActivityCompat.requestPermissions(
                    /* activity = */ activity,
                    /* permissions = */ arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    /* requestCode = */ PERMISSION_REQUEST_CODE
                )
                Toast.makeText(activity, "Please Grant Permissions", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            val isGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED
            if (isGranted) {
                SharedPrefUtils.putPrefBoolean(SharedPrefKeys.PREF_KEY_IS_PERMISSIONS_GRANTED, true)
            } else {
                SharedPrefUtils.putPrefBoolean(
                    SharedPrefKeys.PREF_KEY_IS_PERMISSIONS_GRANTED,
                    false
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = supportFragmentManager?.findFragmentById(R.id.frameLayout)
        fragment?.onActivityResult(requestCode, resultCode, data)
    }

    private fun splashLogic() {
        binding.apply {
            splashLayout.cardView.slideFromStart()
            Handler(Looper.myLooper()!!).postDelayed({
                splashScreenHolder.slideToEndWithFadeOut()
                splashScreenHolder.visibility = View.GONE
            }, 3000)
        }
    }
}
