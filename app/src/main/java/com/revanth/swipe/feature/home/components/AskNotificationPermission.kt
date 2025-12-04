package com.revanth.swipe.feature.home.components

import android.Manifest
import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat

@Composable
fun AskNotificationPermission() {
    val context = LocalContext.current
    val activity = context as Activity

    var shouldShowPermission by remember { mutableStateOf(true) }
    var launchRequest by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            // Check if user blocked ("Don't ask again")
            val permanentlyDenied = !ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.POST_NOTIFICATIONS
            )

            if (permanentlyDenied) {
                Toast.makeText(
                    context,
                    "You have blocked notification permission. You won't receive notifications.",
                    Toast.LENGTH_LONG
                ).show()

                shouldShowPermission = false // stop asking
            } else {
                // user denied normally → ask again
                launchRequest = true
            }
        } else {
            shouldShowPermission = false // permission granted → stop asking
        }
    }

    // Trigger permission dialog
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && shouldShowPermission) {
        LaunchedEffect(launchRequest) {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}

