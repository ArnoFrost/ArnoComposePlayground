package com.tech.arno.dynamic

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
        val screenWith = LocalConfiguration.current.screenWidthDp
        val scrollState = rememberScrollState()

        var dynamicDefaultOffSetX by remember { mutableStateOf(0F) }
        var dynamicDefaultOffSetY by remember { mutableStateOf(0F) }
        var dynamicDefaultWidth by remember { mutableStateOf(24F) }
        var dynamicDefaultHeight by remember { mutableStateOf(24F) }
        var dynamicDefaultCorner by remember { mutableStateOf(24F) }
        val defaultSize = remember {
            derivedStateOf {
                DynamicConst.DynamicSize(
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
            defaultSize = defaultSize.value,
            onIslandClick = triggerDynamic
        ) {
            DynamicContentScreen(islandType)
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
    var isExpanded by remember { mutableStateOf(false) }
    var islandType by remember {
        mutableStateOf<DynamicType>(
            DynamicType.Line
        )
    }
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
            DynamicContentScreen(islandType)
        }
    }
}

/**
 * 演示用动态切换效果
 *
 * @param type
 */
@Composable
fun DynamicContentScreen(type: DynamicType) {
    when (type) {
        DynamicType.Line -> {
            Text(
                "简短通知🏝️", fontSize = 14.sp, color = Color.White
            )
        }
        DynamicType.Card -> {
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    "卡片通知🏝️️", fontSize = 16.sp, color = Color.White
                )
            }
        }
        DynamicType.Big -> {
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    "扩展通知🏝️", fontSize = 16.sp, color = Color.White
                )
            }
        }
    }
}

/**
 * 预览效果测试
 *
 */
@Preview(showBackground = true)
@Composable
fun PreviewSingleDynamicIsland() {

    var isLineExpanded by remember { mutableStateOf(false) }
    var isCardExpanded by remember { mutableStateOf(false) }
    var isBigExpanded by remember { mutableStateOf(false) }
    val aniDuration = 1500L
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("条幅通知")
        Spacer(Modifier.height(16.dp))
        AutoLineRoundIsland(
            isExpanded = isLineExpanded,
            aniDuration = aniDuration,
            autoClose = true,
            onIslandClick = { isLineExpanded = !isLineExpanded }) {
            Text(
                text = "条幅岛🏝️", color = Color.White, fontSize = 14.sp
            )
        }

        Spacer(Modifier.height(16.dp))

        Text("卡片通知")
        Spacer(Modifier.height(16.dp))
        AutoCardRoundIsland(
            isExpanded = isCardExpanded,
            aniDuration = aniDuration,
            autoClose = true,
            onIslandClick = { isCardExpanded = !isCardExpanded }) {
            Text(
                text = "卡片岛🏝️", color = Color.White, fontSize = 16.sp
            )
        }

        Spacer(Modifier.height(16.dp))

        Text("扩展通知")
        Spacer(Modifier.height(16.dp))
        AutoBigRoundIsland(
            isExpanded = isBigExpanded,
            aniDuration = aniDuration,
            autoClose = true,
            onIslandClick = { isBigExpanded = !isBigExpanded }) {
            Text(
                text = "扩展岛🏝️", color = Color.White, fontSize = 16.sp
            )
        }
    }
}