package com.tech.arno.dynamic.demo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tech.arno.dynamic.component.*
import com.tech.arno.dynamic.config.DynamicConfig
import com.tech.arno.dynamic.config.DynamicDirection
import com.tech.arno.dynamic.config.DynamicMessage
import com.tech.arno.dynamic.config.DynamicType

@Preview(showBackground = true)
@Composable
fun PreviewDemo() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        //region 配置属性
        val aniDuration = 3000L
        val autoCloseInterval = 3000L
        val direction = DynamicDirection.Center
        var isExpanded by remember { mutableStateOf(false) }
        var islandType by remember { mutableStateOf<DynamicType>(DynamicType.Line) }
        val triggerDynamic = { isExpanded = !isExpanded }
        val message by remember { mutableStateOf(DynamicMessage("测试消息", "这是测试长文本", 1)) }
        val screenWith = LocalConfiguration.current.screenWidthDp
        val scrollState = rememberScrollState()

        var dynamicDefaultOffSetX by remember { mutableStateOf(0F) }
        var dynamicDefaultOffSetY by remember { mutableStateOf(0F) }
        var dynamicDefaultWidth by remember { mutableStateOf(24F) }
        var dynamicDefaultHeight by remember { mutableStateOf(24F) }
        var dynamicDefaultCorner by remember { mutableStateOf(24F) }
        val defaultConfig = remember {
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
        Text("类型: ${islandType.javaClass.simpleName}")
        Spacer(Modifier.height(16.dp))
        AutoDynamicIsland(
            type = islandType,
            isExpanded = isExpanded,
            direction = direction,
            aniDuration = aniDuration,
            autoClose = true,
            autoCloseInterval = autoCloseInterval,
            defaultConfig = defaultConfig.value,
            onIslandClick = triggerDynamic
        ) {
            DynamicContentScreen(islandType, message)
        }
        Spacer(Modifier.height(40.dp))
        //测试按钮
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
            Button(
                onClick = {
                    islandType = DynamicType.Battery
                    triggerDynamic.invoke()
                }) {
                Text("电量")
            }
        }
        Spacer(Modifier.height(16.dp))
        //配置信息
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

    }
}


@Preview(showBackground = true)
@Composable
fun PreviewDynamicIsland() {
    val aniDuration = 1500L
    var isExpanded by remember { mutableStateOf(true) }
    var islandType by remember {
        mutableStateOf<DynamicType>(
            DynamicType.Big
        )
    }
    val message by remember { mutableStateOf(DynamicMessage("测试消息", "这是测试长文本", 1)) }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("类型: ${islandType.javaClass.simpleName}")
        Spacer(modifier = Modifier.height(16.dp))
        AutoDynamicIsland(type = islandType,
            aniDuration = aniDuration,
            isExpanded = isExpanded,
            autoClose = true,
            onIslandClick = { isExpanded = !isExpanded },
            finishListener = {
                if (!isExpanded) {
                    islandType = islandType.nextType()
                }
            }) {
            DynamicContentScreen(islandType, message)
        }
    }
}

