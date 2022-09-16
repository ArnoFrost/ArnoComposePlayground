package com.tech.arno.dynamic.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.tech.arno.dynamic.DynamicConst
import com.tech.arno.dynamic.config.DynamicConfig
import com.tech.arno.dynamic.config.DynamicDirection
import com.tech.arno.dynamic.config.DynamicType
import com.tech.arno.dynamic.service.DynamicFloatService

class DynamicConfigActivity : ComponentActivity() {
    private val viewModel: DynamicConfigViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DynamicSettingScreen(viewModel)
                checkOverlayPermission()
                startService()
            }
        }
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

    @Composable
    fun DynamicSettingScreen(viewModel: DynamicConfigViewModelInterface) {
        //region 配置属性
        val aniDuration = 3000L
        val autoCloseInterval = 3000L
        val direction = DynamicDirection.Center
        var isExpanded by remember { mutableStateOf(false) }
        var islandType by remember { mutableStateOf<DynamicType>(DynamicType.Line) }
        val triggerDynamic = { isExpanded = !isExpanded }
        val screenWith = LocalConfiguration.current.screenWidthDp
        val scrollState = rememberScrollState()

        var dynamicDefaultOffSetX by remember { mutableStateOf(0F) }
        var dynamicDefaultOffSetY by remember { mutableStateOf(0F) }
        var dynamicDefaultWidth by remember { mutableStateOf(24F) }
        var dynamicDefaultHeight by remember { mutableStateOf(24F) }
        var dynamicDefaultCorner by remember { mutableStateOf(24F) }
        val defaultSize = remember {
            derivedStateOf {
                DynamicConfig(
                    dynamicDefaultHeight.dp,
                    dynamicDefaultWidth.dp,
                    dynamicDefaultCorner.dp,
                    dynamicDefaultOffSetX.dp,
                    dynamicDefaultOffSetY.dp
                )
            }
        }
        //endregion

        // region 测试按钮
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Button(
                onClick = {
                    islandType = DynamicType.Line
                    triggerDynamic.invoke()
                }) {
                Text("简短")
            }
            Button(
                onClick = {
                    islandType = DynamicType.Card
                    triggerDynamic.invoke()
                }) {
                Text("卡片")
            }
            Button(
                onClick = {
                    islandType = DynamicType.Big
                    triggerDynamic.invoke()
                }) {
                Text("扩展")
            }
        }
        //endregion
        Spacer(Modifier.height(16.dp))
        //region 配置组件
        Column(
            Modifier.verticalScroll(scrollState),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Row(
                Modifier
                    .wrapContentSize()
                    .padding(6.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "横坐标X：")
                Slider(
                    valueRange = 0f..screenWith.toFloat(),
                    value = dynamicDefaultOffSetX,
                    onValueChange = { x ->
                        dynamicDefaultOffSetX = x
                    })
            }
            OutlinedTextField(
                value = dynamicDefaultOffSetX.toString(),
                onValueChange = { value ->
                    dynamicDefaultOffSetX = if (value.toFloatOrNull() != null) {
                        value.toFloat()
                    } else {
                        0F
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("横坐标X") },
            )
            Row(
                Modifier
                    .wrapContentSize()
                    .padding(6.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "纵坐标Y：")
                Slider(valueRange = 0f..100f, value = dynamicDefaultOffSetY, onValueChange = { y ->
                    dynamicDefaultOffSetY = y
                })
            }
            OutlinedTextField(
                value = dynamicDefaultOffSetY.toString(),
                onValueChange = { value ->
                    dynamicDefaultOffSetY = if (value.toFloatOrNull() != null) {
                        value.toFloat()
                    } else {
                        0F
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("纵坐标Y") },
            )
            Row(
                Modifier
                    .wrapContentSize()
                    .padding(6.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "宽度：")
                Slider(valueRange = 0f..200f, value = dynamicDefaultWidth, onValueChange = { w ->
                    dynamicDefaultWidth = w
                })
            }

            Row(
                Modifier
                    .wrapContentSize()
                    .padding(6.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "高度：")
                Slider(valueRange = 0f..50f, value = dynamicDefaultHeight, onValueChange = { h ->
                    dynamicDefaultHeight = h
                })
            }

            Row(
                Modifier
                    .wrapContentSize()
                    .padding(6.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "圆角：")
                Slider(
                    valueRange = 0f..40f,
                    value = dynamicDefaultCorner,
                    onValueChange = { corner ->
                        dynamicDefaultCorner = corner
                    })
            }
        }
        //endregion
    }
}

