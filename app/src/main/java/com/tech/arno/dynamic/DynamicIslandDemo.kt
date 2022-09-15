package com.tech.arno.dynamic

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
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
        var isExpanded by remember { mutableStateOf(false) }
        var islandType by remember { mutableStateOf<DynamicConst.DynamicType>(DynamicConst.DynamicType.Line) }
        val triggerDynamic = { isExpanded = !isExpanded }
        Text("类型: ${islandType.javaClass.simpleName}")
        Spacer(Modifier.height(16.dp))

        AutoDynamicIsland(
            type = islandType,
            aniDuration = aniDuration,
            isExpanded = isExpanded,
            autoClose = true,
            autoCloseInterval = autoCloseInterval,
            onIslandClick = triggerDynamic
        ) {
            DynamicScreen(islandType)
        }
        Spacer(Modifier.height(40.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Button(
                onClick = {
                    islandType = DynamicConst.DynamicType.Line
                    triggerDynamic.invoke()
                }) {
                Text("简短")
            }
            Button(
                onClick = {
                    islandType = DynamicConst.DynamicType.Card
                    triggerDynamic.invoke()
                }) {
                Text("卡片")
            }
            Button(
                onClick = {
                    islandType = DynamicConst.DynamicType.Big
                    triggerDynamic.invoke()
                }) {
                Text("扩展")
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
        mutableStateOf<DynamicConst.DynamicType>(
            DynamicConst.DynamicType.Line
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
private fun DynamicScreen(type: DynamicConst.DynamicType) {
    when (type) {
        DynamicConst.DynamicType.Line -> {
            Text(
                "简短通知🏝️", fontSize = 14.sp, color = Color.White
            )
        }
        DynamicConst.DynamicType.Card -> {
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    "卡片通知🏝️️", fontSize = 16.sp, color = Color.White
                )
            }
        }
        DynamicConst.DynamicType.Big -> {
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