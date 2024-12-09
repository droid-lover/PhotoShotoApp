package com.nmb.utilities.permission


import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.nmb.utilities.logging.AppLogger

class PermissionManager(private val activity: ComponentActivity) {
    fun requestPhotoPermissions(
        onGranted: () -> Unit,
        onDenied: () -> Unit,
        onShowSettings: () -> Unit
    ) {
        val launcher = activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val readMediaImagesGranted =
                permissions[android.Manifest.permission.READ_MEDIA_IMAGES] ?: false
            val readExternalStorageGranted =
                permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE] ?: false

            if (readMediaImagesGranted || readExternalStorageGranted) {
                AppLogger.d(message = "Permission granted")
                onGranted()
            } else {
                AppLogger.d(message = "Permission denied")
                val permanentlyDenied = permissions.filter { !it.value && !activity.shouldShowRequestPermissionRationale(it.key) }

                if (permanentlyDenied.isNotEmpty()) {
                    onShowSettings()
                } else {
                    onDenied()
                }
            }
        }

        val permissionsToRequest = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES)
            } else -> {
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
        launcher.launch(permissionsToRequest)
    }
}

//@Composable
//fun GoToSettingsDialog(
//    context: Context,
//    onDismiss: () -> Unit
//) {
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        title = {
//            Text(text = "Permission Required")
//        },
//        text = {
//            Text(text = "The app needs permission to access your photos. Please enable them in the app settings to use this feature.")
//        },
//        confirmButton = {
//            TextButton(onClick = {
//                onDismiss()
//                context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
//                    data = android.net.Uri.fromParts("package", context.packageName, null)
//                })
//            }) {
//                Text("Open Settings")
//            }
//        },
//        dismissButton = {
//            TextButton(onClick = onDismiss) {
//                Text("Cancel")
//            }
//        }
//    )
//}
