package com.tech.arno.dynamic.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.blankj.utilcode.util.ToastUtils
import com.tech.arno.dynamic.service.DynamicAccessibilityService
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlin.jvm.internal.Intrinsics

class DynamicConfigActivity : ComponentActivity() {
    private val viewModel: DynamicActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                val checkAccessibility = viewModel.checkAccessibility.collectAsState()
                val checkOverlayPermission = viewModel.checkOverlayPermission.collectAsState()

                if (checkAccessibility.value) {
                    checkAccessibilityPermission()
                    viewModel.checkAccessibility.update { false }
                }
                if (checkOverlayPermission.value) {
                    checkOverlayPermission()
                    viewModel.checkOverlayPermission.update { false }
                }
                DynamicSettingScreen(viewModel)
            }
        }
        viewModel.injectFloatViewModel()
    }

    private fun initObserver() {

    }

    // method to ask user to grant the Overlay permission
    private fun checkOverlayPermission() {
        if (!Settings.canDrawOverlays(this)) {
            // send user to the device settings
            startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION))
        } else {
            ToastUtils.showShort("已经允许覆盖界面")
        }
    }

    private fun checkAccessibilityPermission() {
        if (!DynamicAccessibilityService.isReady) {
            jumpAccessibilityServiceSettings(DynamicAccessibilityService::class.java)
        } else {
            ToastUtils.showShort("已经建立无障碍")
        }
    }

    private fun jumpAccessibilityServiceSettings(cls: Class<*>) {
        Intrinsics.checkNotNullParameter(cls, "cls")
        val intent = Intent("android.settings.ACCESSIBILITY_SETTINGS")
        startActivity(intent)
    }
}

