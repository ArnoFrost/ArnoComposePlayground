package com.tech.arno.dynamic.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.tech.arno.dynamic.AutoDynamicIsland
import com.tech.arno.dynamic.DynamicConst
import com.tech.arno.dynamic.DynamicContentScreen
import com.tech.arno.dynamic.config.DynamicConfig
import com.tech.arno.dynamic.config.DynamicDirection
import com.tech.arno.dynamic.config.DynamicType


@Composable
fun DynamicSettingScreen(viewModel: DynamicConfigViewModelInterface) {
    //region 配置属性
    val dynamicDefaultOffSetX = viewModel.offsetX.collectAsState(initial = 0F)
    val dynamicDefaultOffSetY = viewModel.offsetY.collectAsState(initial = 0F)
    val dynamicDefaultWidth = viewModel.width.collectAsState(initial = 24F)
    val dynamicDefaultHeight = viewModel.height.collectAsState(initial = 24F)
    val dynamicDefaultCorner = viewModel.corner.collectAsState(initial = 24F)
    val screenWith = LocalConfiguration.current.screenWidthDp
    val scrollState = rememberScrollState()
    //endregion

    // region 测试按钮
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        Button(
            onClick = {
                viewModel.sendLineType()
            }) {
            Text("简短")
        }
        Button(
            onClick = {
                viewModel.sendCardType()
            }) {
            Text("卡片")
        }
        Button(
            onClick = {
                viewModel.sendBigType()
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
                value = dynamicDefaultOffSetX.value,
                onValueChange = { x ->
                    viewModel.onOffSetXChange(x)
                })
        }
        OutlinedTextField(
            value = dynamicDefaultOffSetX.value.toString(),
            onValueChange = { value ->
                val result = if (value.toFloatOrNull() != null) {
                    value.toFloat()
                } else {
                    0F
                }
                viewModel.onOffSetXChange(result)
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
            Slider(
                valueRange = 0f..100f,
                value = dynamicDefaultOffSetY.value,
                onValueChange = { y ->
                    viewModel.onOffSetYChange(y)
                })
        }
        OutlinedTextField(
            value = dynamicDefaultOffSetY.value.toString(),
            onValueChange = { value ->
                val result = if (value.toFloatOrNull() != null) {
                    value.toFloat()
                } else {
                    0F
                }
                viewModel.onOffSetYChange(result)
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
            Slider(
                valueRange = 0f..200f,
                value = dynamicDefaultWidth.value,
                onValueChange = { w ->
                    viewModel.onWidthChange(w)
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
            Slider(
                valueRange = 0f..50f,
                value = dynamicDefaultHeight.value,
                onValueChange = { h ->
                    viewModel.onHeightChange(h)
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
                value = dynamicDefaultCorner.value,
                onValueChange = { corner ->
                    viewModel.onCornerChange(corner)
                })
        }
    }
    //endregion
}

@Composable
fun DynamicFloatScreen(
    type: DynamicType,
    isExpanded: Boolean,
    aniDuration: Long,
    direction: DynamicDirection = DynamicDirection.Center,
    autoClose: Boolean = false,
    autoCloseInterval: Long = 1500L,
    defaultConfig: DynamicConfig = DynamicConfig(
        height = DynamicConst.DEFAULT_HEIGHT,
        width = DynamicConst.DEFAULT_WIDTH,
        corner = DynamicConst.DEFAULT_CORNER
    ),
    finishListener: (() -> Unit)? = null,
    onIslandClick: () -> Unit,
    content: @Composable () -> Unit
) {
    AutoDynamicIsland(
        type = type,
        aniDuration = aniDuration,
        isExpanded = isExpanded,
        autoClose = true,
        onIslandClick = onIslandClick,
        finishListener = finishListener
    ) {
        DynamicContentScreen(type)
    }
}

@Composable
fun DynamicFloatScreen(viewModel: DynamicConfigViewModelInterface) {
    //region 配置属性
    val aniDuration =
        viewModel.aniDuration.collectAsState(initial = DynamicConst.DEFAULT_ANIMATION_DURATION)

    val autoCloseInterval =
        viewModel.autoCloseInterval.collectAsState(initial = DynamicConst.DEFAULT_AUTO_CLOSE_INTERVAL)

    val autoClose = viewModel.autoClose.collectAsState(initial = true)

    val direction = viewModel.direction.collectAsState(initial = DynamicDirection.Center)

    val isExpanded = viewModel.isExpanded.collectAsState(initial = false)

    val islandType = viewModel.isLandType.collectAsState(initial = DynamicType.Line)

    val dynamicDefaultOffSetX = viewModel.offsetX.collectAsState(initial = 0F)
    val dynamicDefaultOffSetY = viewModel.offsetY.collectAsState(initial = 0F)
    val dynamicDefaultWidth = viewModel.width.collectAsState(initial = 24F)
    val dynamicDefaultHeight = viewModel.height.collectAsState(initial = 24F)
    val dynamicDefaultCorner = viewModel.corner.collectAsState(initial = 24F)
    val defaultConfig = remember {
        derivedStateOf {
            DynamicConfig(
                dynamicDefaultHeight.value.dp,
                dynamicDefaultWidth.value.dp,
                dynamicDefaultCorner.value.dp,
                dynamicDefaultOffSetX.value.dp,
                dynamicDefaultOffSetY.value.dp
            )
        }
    }
    //endregion

    AutoDynamicIsland(
        type = islandType.value,
        isExpanded = isExpanded.value,
        direction = direction.value,
        aniDuration = aniDuration.value,
        autoClose = autoClose.value,
        autoCloseInterval = autoCloseInterval.value,
        defaultConfig = defaultConfig.value,
        onIslandClick = {
            viewModel.triggerDynamic()
        }
    ) {
        DynamicContentScreen(islandType.value)
    }
}