package com.xanroid.mybubblescandit

import android.Manifest.permission.CAMERA
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat.checkSelfPermission


abstract class CameraPermission: Fragment() {

    private val requestCameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it && isResumed){
            onCameraPermissionGranted()
        }
    }
    fun requestCameraPermission(){
        if (checkSelfPermission(requireContext(), CAMERA) == PERMISSION_GRANTED){
            onCameraPermissionGranted()
        } else {
            requestCameraPermission.launch(CAMERA)
        }
    }

    abstract fun onCameraPermissionGranted()

}