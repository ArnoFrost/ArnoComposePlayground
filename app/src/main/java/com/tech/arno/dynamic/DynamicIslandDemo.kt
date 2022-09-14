package com.tech.arno.demo

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tech.arno.dynamic.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun PreviewDynamicIsland() {
    val duration = 1500L
    var isExpanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var islandType by remember {
        mutableStateOf<DynamicConst.DynamicType>(
            DynamicConst.DynamicType.Line
        )
    }
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("类型: ${islandType.javaClass.simpleName}")
        Spacer(modifier = Modifier.height(16.dp))
        AutoDynamicIsland(
            type = islandType,
            isExpanded = isExpanded,
            duration = duration,
            onIslandClick = {
                isExpanded = !isExpanded
                scope.launch {
                    delay(duration + 1000L)
                    if (!isExpanded) {
                        islandType = islandType.nextType()
                    }
                }

            }) {
            when (islandType) {
                DynamicConst.DynamicType.Line -> {
                    Text(
                        "简短通知🏝️",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
                DynamicConst.DynamicType.Card -> {
                    Column(verticalArrangement = Arrangement.Center) {
                        Text(
                            "方形通知🏝️️",
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
                DynamicConst.DynamicType.Big -> {
                    Column(verticalArrangement = Arrangement.Center) {
                        Text(
                            "扩展通知🏝️",
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
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
    val duration = 1500L
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
            duration = duration,
            onIslandClick = { isLineExpanded = !isLineExpanded }) {
            Text(
                text = "条幅岛🏝️",
                color = Color.White,
                fontSize = 14.sp
            )
        }

        Spacer(Modifier.height(16.dp))

        Text("卡片通知")
        Spacer(Modifier.height(16.dp))
        AutoCardRoundIsland(
            isExpanded = isCardExpanded,
            duration = duration,
            onIslandClick = { isCardExpanded = !isCardExpanded }) {
            Text(
                text = "卡片岛🏝️",
                color = Color.White,
                fontSize = 16.sp
            )
        }

        Spacer(Modifier.height(16.dp))

        Text("扩展通知")
        Spacer(Modifier.height(16.dp))
        AutoBigRoundIsland(
            isExpanded = isBigExpanded,
            duration = duration,
            onIslandClick = { isBigExpanded = !isBigExpanded }) {
            Text(
                text = "扩展岛🏝️",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}