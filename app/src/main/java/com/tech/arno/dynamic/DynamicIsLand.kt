package com.tech.arno.dynamic

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * 混合类型岛屿🏝️
 *
 * @param type [DynamicConst.TYPE_LINE] [DynamicConst.TYPE_CARD] [DynamicConst.TYPE_BIG]
 * @param isExpanded [Boolean] 是否展开
 * @param duration [Long] 展开动画时长
 * @param onIslandClick [() -> Unit] 点击岛屿回调
 * @param content [@Composable] 岛屿内容
 */
@Composable
fun AutoDynamicIsland(
    type: DynamicConst.DynamicType,
    isExpanded: Boolean,
    duration: Long,
    onIslandClick: () -> Unit,
    content: @Composable () -> Unit
) {
    when (type) {
        DynamicConst.DynamicType.Line -> AutoLineRoundIsland(
            isExpanded = isExpanded,
            duration = duration,
            onIslandClick = onIslandClick,
            content = content
        )
        DynamicConst.DynamicType.Card -> AutoCardRoundIsland(
            isExpanded = isExpanded,
            duration = duration,
            onIslandClick = onIslandClick,
            content = content
        )
        DynamicConst.DynamicType.Big -> AutoBigRoundIsland(
            isExpanded = isExpanded,
            duration = duration,
            onIslandClick = onIslandClick,
            content = content
        )
    }
}

@Composable
fun DynamicIsland(
    type: DynamicConst.DynamicType,
    isExpanded: Boolean,
    duration: Long,
    onIslandClick: () -> Unit,
    content: @Composable () -> Unit
) {
    when (type) {
        DynamicConst.DynamicType.Line -> LineRoundIsland(
            isExpanded = isExpanded,
            onIslandClick = onIslandClick,
            content = content
        )
        DynamicConst.DynamicType.Card -> CardRoundIsland(
            isExpanded = isExpanded,
            onIslandClick = onIslandClick,
            content = content
        )
        DynamicConst.DynamicType.Big -> BigRoundIsland(
            isExpanded = isExpanded,
            onIslandClick = onIslandClick,
            content = content
        )
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
    onIslandClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    var isClickable by remember { mutableStateOf(true) }
    LineRoundIsland(isExpanded = isExpanded, isClickable = isClickable, onIslandClick = {
        isClickable = false
        onIslandClick.invoke()
        scope.launch {
            delay(duration)
            onIslandClick.invoke()
            isClickable = true
        }
    }, content = content)
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
    onIslandClick: () -> Unit,
    content: @Composable () -> Unit
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
        onIslandClick = onIslandClick,
        content = content
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
    onIslandClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    var isClickable by remember { mutableStateOf(true) }
    CardRoundIsland(isExpanded = isExpanded, isClickable = isClickable, onIslandClick = {
        isClickable = false
        onIslandClick.invoke()
        scope.launch {
            delay(duration)
            onIslandClick.invoke()
            isClickable = true
        }
    }, content = content)
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
    onIslandClick: () -> Unit,
    content: @Composable () -> Unit
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
        onIslandClick = onIslandClick,
        content = content
    )
}

/**
 * 带有自动回滚的大卡片的动态岛🏝️
 *
 * @param isExpanded
 * @param duration
 * @param onIslandClick
 */
@Composable
fun AutoBigRoundIsland(
    isExpanded: Boolean,
    duration: Long,
    onIslandClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    var isClickable by remember { mutableStateOf(true) }
    BigRoundIsland(
        isExpanded = isExpanded,
        isClickable = isClickable,
        onIslandClick = {
            isClickable = false
            onIslandClick.invoke()
            scope.launch {
                delay(duration)
                onIslandClick.invoke()
                isClickable = true
            }
        }, content = content
    )
}

/**
 * 大的动态岛🏝️
 *
 * @param isExpanded
 * @param isClickable
 * @param onIslandClick
 */
@Composable
fun BigRoundIsland(
    isExpanded: Boolean,
    isClickable: Boolean = true,
    onIslandClick: () -> Unit,
    content: @Composable () -> Unit
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
            height = DynamicConst.BIG_HEIGHT,
            width = LocalConfiguration.current.screenWidthDp.dp - DynamicConst.BIG_WIDTH_MARGIN,
            corner = DynamicConst.BIG_CORNER,
        ),
        onIslandClick = onIslandClick,
        content = content
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
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    isClickable: Boolean = true,
    default: DynamicConst.DynamicSize,
    targetSize: DynamicConst.DynamicSize,
    onIslandClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    //region 状态转移型动画
    val springSpec = Spring.DampingRatioLowBouncy
    //animateXXXAsState
    val widthState by animateDpAsState(
        if (isExpanded) targetSize.width else default.width,
        animationSpec = spring(springSpec)
    )
    val heightState by animateDpAsState(
        if (isExpanded) targetSize.height else default.height,
        animationSpec = spring(springSpec)
    )
    val cornerState by animateDpAsState(
        if (isExpanded) targetSize.corner else default.corner,
        animationSpec = spring(springSpec)
    )

    Card(
        modifier = Modifier
            .height(heightState)
            .width(widthState)
            .clickable(enabled = isClickable) {
                onIslandClick.invoke()
            },
        shape = RoundedCornerShape(cornerState),
        elevation = 8.dp,
        backgroundColor = Color.Black

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isExpanded) {
                content.invoke()
            }
        }
    }
    //endregion

////region 流程定制型
//    val width = remember(isExpanded) { if (isExpanded) targetSize.width else default.width }
//    val height = remember(isExpanded) { if (isExpanded) targetSize.height else default.height }
//    val corner = remember(isExpanded) { if (isExpanded) targetSize.corner else default.corner }
//
//    val animWidth = remember { Animatable(width, Dp.VectorConverter) }
//    val animHeight = remember { Animatable(height, Dp.VectorConverter) }
//    val animCorner = remember { Animatable(corner, Dp.VectorConverter) }
//
//    LaunchedEffect(isExpanded) {
//        if (isExpanded) {
//            animCorner.animateTo(corner)
//            animHeight.animateTo(height, spring(springSpec))
//            animWidth.animateTo(width, spring(springSpec))
//
//        } else {
//            animHeight.animateTo(height, spring(springSpec))
//            animWidth.animateTo(width, spring(springSpec))
//            animCorner.animateTo(corner)
//        }
//
//    }
//    Card(
//        modifier = Modifier
//            .height(animHeight.value)
//            .width(animWidth.value)
//            .clickable(enabled = isClickable) {
//                onIslandClick.invoke()
//            },
//        shape = RoundedCornerShape(animCorner.value),
//        elevation = 8.dp,
//        backgroundColor = Color.Black
//
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(8.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            if (isExpanded) {
//                content.invoke()
//            }
//        }
//    }
////endregion
}