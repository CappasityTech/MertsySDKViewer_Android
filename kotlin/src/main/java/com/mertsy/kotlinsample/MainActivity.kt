package com.mertsy.kotlinsample

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.mertsy.common.TokenStatus
import com.mertsy.sdk.MertsySDK

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val Context.hasStoragePermission: Boolean
        get() = isPermissionGranted(
            targetStoragePermission
        )
    private val Context.hasCameraPermission get() = isPermissionGranted(Manifest.permission.CAMERA)

    private val targetStoragePermission
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else Manifest.permission.WRITE_EXTERNAL_STORAGE

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Required for capturing
        requestCameraAndStoragePermissions()

        findViewById<AppCompatTextView>(R.id.tvTokenStatus).text = "Token status: Checking"

        MertsySDK.onTokenStatusChangedCallback = { status: TokenStatus ->
            val prefix = "Token status: "
            findViewById<AppCompatTextView>(R.id.tvTokenStatus).text = when (status) {
                TokenStatus.SUCCESS -> prefix + "Success"
                else -> prefix + "Error"
            }
        }

        findViewById<AppCompatTextView>(R.id.tvFrameworkVersion).text =
            "SDK Version: ${MertsySDK.getVersion()}"

        findViewById<AppCompatButton>(R.id.btnView).setOnClickListener {
            checkTokenAndExecute {
                startActivity(Intent(this, ModelViewActivity::class.java))
            }
        }
    }

    private fun checkTokenAndExecute(action: () -> Unit) {
        when (MertsySDK.tokenStatus) {
            TokenStatus.CHECKING -> {
                val msg = "Token validation. Try again later"
                Log.e(TAG, msg)
                showToast(msg)
            }

            TokenStatus.ERROR -> {
                val msg = "Wrong token"
                Log.e(TAG, msg)
                showToast(msg)
            }

            TokenStatus.SUCCESS -> action.invoke()
        }
    }

    private fun requestCameraAndStoragePermissions() {
        val permissions = arrayOf(
            targetStoragePermission,
            Manifest.permission.CAMERA
        )
        if (!hasStoragePermission || !hasCameraPermission)
            requestPermissions(permissions, 123)
    }

    private fun Context.isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
}