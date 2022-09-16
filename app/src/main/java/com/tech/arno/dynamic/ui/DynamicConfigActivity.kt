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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tech.arno.dynamic.service.DynamicFloatService

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
                DynamicSettingScreen(viewModel)
//                val x = DynamicWindow.floatingViewModel.offsetX.collectAsState(initial = 0F)
//                val y = DynamicWindow.floatingViewModel.offsetY.collectAsState(initial = 0F)
//                Box(
//                    Modifier
//                        .offset(x.value.dp, y.value.dp)
//                        .fillMaxHeight()) {
//                    DynamicFloatScreen(viewModel)
//                }
            }
        }
        viewModel.injectFloatViewModel()
        checkOverlayPermission()
        startService()
    }

    // method to ask user to grant the Overlay permission
    private fun checkOverlayPermission() {
        if (!Settings.canDrawOverlays(this)) {
            // send user to the device settings
            startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION))
        }
    }

    private fun startService() {
        // check if the user has already granted
        // the Draw over other apps permission
        if (Settings.canDrawOverlays(this)) {
            // start the service based on the android version
            startForegroundService(Intent(this, DynamicFloatService::class.java))
        }
    }
}

