package com.nmb.photoshoto.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.nmb.photoshoto.presentation.screens.AppNavGraph
import com.nmb.photoshoto.presentation.ui.theme.PhotoShotoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            PhotoShotoAppTheme {
                AppNavGraph()
            }
        }
    }
}
