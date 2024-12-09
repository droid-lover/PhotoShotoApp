package com.nmb.photoshoto.presentation.screens.onboarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(): ViewModel() {


    var showPermissionDialog by  mutableStateOf(false)
    var showSettingsDialog by mutableStateOf(false)
    var permissionRequested by mutableStateOf(false)




}