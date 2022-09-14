package com.tech.arno.demo

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tech.arno.const.DynamicConst
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 预览效果测试
 *
 */
@Preview(showBackground = true)
@Composable
fun PreviewDynamicIsland() {
    var isLineExpanded by remember { mutableStateOf(false) }
    var isCardExpanded by remember { mutableStateOf(false) }
    val duration = 1500L
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("条幅通知")
        Spacer(Modifier.height(16.dp))
        AutoLineRoundIsland(isExpanded = isLineExpanded, duration) {
            isLineExpanded = !isLineExpanded
        }

        Spacer(Modifier.height(16.dp))

        Text("卡片通知")
        Spacer(Modifier.height(16.dp))
        AutoCardRoundIsland(isExpanded = isCardExpanded, duration) {
            isCardExpanded = !isCardExpanded
        }
    }
}

/**
 * 带有自动回滚的横线变化岛🏝️
 *
 * @param isExpanded
 * @param duration
 * @param onIslandClick
 */
@Composable
fun AutoLineRoundIsland(
    isExpanded: Boolean,
    duration: Long,
    onIslandClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var isClickable by remember { mutableStateOf(true) }
    LineRoundIsland(isExpanded = isExpanded, isClickable = isClickable) {
        isClickable = false
        onIslandClick.invoke()
        scope.launch {
            delay(duration)
            onIslandClick.invoke()
            isClickable = true
        }
    }
}

/**
 * 横线变化岛🏝️
 *
 * @param isExpanded
 * @param isClickable
 * @param onIslandClick
 */
@Composable
fun LineRoundIsland(
    isExpanded: Boolean,
    isClickable: Boolean = true,
    onIslandClick: () -> Unit
) {
    BasicDynamicIsland(
        isExpanded = isExpanded,
        isClickable = isClickable,
        default = DynamicConst.DynamicSize(
            height = DynamicConst.DEFAULT_HEIGHT,
            width = DynamicConst.DEFAULT_WIDTH,
            corner = DynamicConst.DEFAULT_CORNER,
        ),
        targetSize = DynamicConst.DynamicSize(
            height = DynamicConst.LINE_HEIGHT,
            width = DynamicConst.LINE_WIDTH,
            corner = DynamicConst.LINE_CORNER,
        ),
        onIslandClick = onIslandClick
    )
}

/**
 * 带有自动回滚的卡片样式的动态岛🏝️
 *
 * @param isExpanded
 * @param duration
 * @param onIslandClick
 */
@Composable
fun AutoCardRoundIsland(
    isExpanded: Boolean,
    duration: Long,
    onIslandClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var isClickable by remember { mutableStateOf(true) }
    CardRoundIsland(isExpanded = isExpanded, isClickable = isClickable) {
        isClickable = false
        onIslandClick.invoke()
        scope.launch {
            delay(duration)
            onIslandClick.invoke()
            isClickable = true
        }
    }
}

/**
 * 卡片样式的动态岛🏝️
 *
 * @param isExpanded
 * @param isClickable
 * @param onIslandClick
 */
@Composable
fun CardRoundIsland(
    isExpanded: Boolean,
    isClickable: Boolean = true,
    onIslandClick: () -> Unit
) {
    BasicDynamicIsland(
        isExpanded = isExpanded,
        isClickable = isClickable,
        default = DynamicConst.DynamicSize(
            height = DynamicConst.DEFAULT_HEIGHT,
            width = DynamicConst.DEFAULT_WIDTH,
            corner = DynamicConst.DEFAULT_CORNER,
        ),
        targetSize = DynamicConst.DynamicSize(
            height = DynamicConst.CARD_HEIGHT,
            width = DynamicConst.CARD_WIDTH,
            corner = DynamicConst.CARD_CORNER,
        ),
        onIslandClick = onIslandClick
    )
}

/**
 * 基础岛🏝️
 *
 * @param isExpanded
 * @param isClickable
 * @param default
 * @param targetSize
 * @param onIslandClick
 */
@Composable
fun BasicDynamicIsland(
    isExpanded: Boolean,
    isClickable: Boolean = true,
    default: DynamicConst.DynamicSize,
    targetSize: DynamicConst.DynamicSize,
    onIslandClick: () -> Unit
) {
    //region 状态转移型动画
    //animateXXXAsState
    val widthState by animateDpAsState(if (isExpanded) targetSize.width else default.width)
    val heightState by animateDpAsState(if (isExpanded) targetSize.height else default.height)
    val cornerState by animateDpAsState(if (isExpanded) targetSize.corner else default.corner)

    Card(shape = RoundedCornerShape(cornerState), elevation = 8.dp) {
        Box(modifier = Modifier
            .height(heightState)
            .width(widthState)
            .background(Color.Black)
            .clickable(enabled = isClickable) {
                onIslandClick.invoke()
            })
    }
    //endregion

//region 流程定制型
    val width = remember(isExpanded) { if (isExpanded) targetSize.width else default.width }
    val height = remember(isExpanded) { if (isExpanded) targetSize.height else default.height }
    val corner = remember(isExpanded) { if (isExpanded) targetSize.corner else default.corner }

    val animWidth = remember { Animatable(width, Dp.VectorConverter) }
    val animHeight = remember { Animatable(height, Dp.VectorConverter) }
    val animCorner = remember { Animatable(corner, Dp.VectorConverter) }

    LaunchedEffect(isExpanded) {
        if (isExpanded) {
            animCorner.animateTo(corner)
            animHeight.animateTo(height)
            animWidth.animateTo(width)
        } else {
            animHeight.animateTo(height)
            animWidth.animateTo(width)
            animCorner.animateTo(corner)
        }

    }
    Card(shape = RoundedCornerShape(animCorner.value), elevation = 8.dp) {
        Box(modifier = Modifier
            .height(animHeight.value)
            .width(animWidth.value)
            .background(Color.Black)
            .clickable(enabled = isClickable) {
                onIslandClick.invoke()
            })
    }
//endregion
}