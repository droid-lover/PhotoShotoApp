package com.nmb.photoshoto.presentation.screens.onboarding


import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale
import com.nmb.photoshoto.R
import com.nmb.utilities.logging.AppLogger

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun OnboardingScreen(
    onPermissionGranted: () -> Unit,
    viewmodel: OnboardingViewModel = hiltViewModel()
)  {

    val context = LocalContext.current

    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                android.Manifest.permission.READ_MEDIA_IMAGES
            } else {
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            },
        )
    )

    val allPermissionsGranted = permissionsState.allPermissionsGranted
    val showRationale = permissionsState.permissions.any {
        !it.status.isGranted && it.status.shouldShowRationale
    }

    LaunchedEffect(permissionsState.allPermissionsGranted, showRationale) {
        if (allPermissionsGranted) {
            AppLogger.d(message = "Permission granted")
            onPermissionGranted()
        } else if (!viewmodel.permissionRequested && !showRationale) {
            viewmodel.showPermissionDialog = true
            viewmodel.permissionRequested = true
        } else if (showRationale) {
            viewmodel.showPermissionDialog = true
        } else {
            viewmodel.showSettingsDialog = true
        }
    }

    if (viewmodel.showPermissionDialog) {
        PermissionDialog(
            onDismiss = { viewmodel.showPermissionDialog = false },
            onConfirm = {
                viewmodel.showPermissionDialog = false
                permissionsState.launchMultiplePermissionRequest()
                viewmodel.showSettingsDialog = true
            }
        )
    }

    if (viewmodel.showSettingsDialog) {
        GoToSettingsDialog(
            context = context,
            onDismiss = { viewmodel.showSettingsDialog = false }
        )
    }


}


@Composable
fun PermissionDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.permission_required)) },
        text = { Text(stringResource(R.string.this_app_needs_permission_to_access_your_photos)) },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(stringResource(R.string.grant_permission))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}


@Composable
fun GoToSettingsDialog(
    context: Context,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(R.string.permission_required))
        },
        text = {
            Text(text = stringResource(R.string.the_following_permission_s_are_permanently_denied_please_enable_them_in_the_app_settings_to_use_this_feature))
        },
        confirmButton = {
            TextButton(onClick = {
                onDismiss()
                context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = android.net.Uri.fromParts("package", context.packageName, null)
                })
            }) {
                Text("Open Settings")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}
