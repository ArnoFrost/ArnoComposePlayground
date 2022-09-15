package com.tech.arno.dynamic

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun PreviewDemo() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val aniDuration = 3000L
        val autoCloseInterval = 3000L
        val direction = DynamicDirection.Center
        var isExpanded by remember { mutableStateOf(false) }
        var islandType by remember { mutableStateOf<DynamicType>(DynamicType.Line) }
        val triggerDynamic = { isExpanded = !isExpanded }
        Text("类型: ${islandType.javaClass.simpleName}")
        Spacer(Modifier.height(16.dp))

        //region 配置属性
        var dynamicLocation by remember { mutableStateOf(Pair(0F, 0F)) }
        var dynamicDefaultWidth by remember { mutableStateOf(24F) }
        var dynamicDefaultHeight by remember { mutableStateOf(24F) }
        var dynamicDefaultCorner by remember { mutableStateOf(24F) }
        val defaultSize = remember {
            derivedStateOf {
                DynamicConst.DynamicSize(
                    dynamicDefaultHeight.dp,
                    dynamicDefaultWidth.dp,
                    dynamicDefaultCorner.dp
                )
            }
        }
        //endregion

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
            DynamicScreen(islandType)
        }
        Spacer(Modifier.height(40.dp))
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
        Column(verticalArrangement = Arrangement.SpaceAround) {
            Row(
                Modifier
                    .wrapContentSize()
                    .padding(6.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "横坐标X：")
                Slider(valueRange = 0f..1f, value = 0.5f, onValueChange = { x ->
                    dynamicLocation = Pair(x, dynamicLocation.second)
                })
            }
            Row(
                Modifier
                    .wrapContentSize()
                    .padding(6.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "纵坐标Y：")
                Slider(valueRange = 0f..1f, value = 0.5f, onValueChange = { y ->
                    dynamicLocation = Pair(dynamicLocation.first, y)
                })
            }
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
                    DynamicConst.DEFAULT_WIDTH = dynamicDefaultWidth.dp
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
                    DynamicConst.DEFAULT_HEIGHT = dynamicDefaultHeight.dp
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
                        DynamicConst.DEFAULT_HEIGHT = dynamicDefaultCorner.dp
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
            DynamicScreen(islandType)
        }
    }
}

/**
 * 演示用动态切换效果
 *
 * @param type
 */
@Composable
private fun DynamicScreen(type: DynamicType) {
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