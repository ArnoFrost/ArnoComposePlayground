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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * 混合类型岛屿🏝️
 *
 * @param type [DynamicConst.DynamicType.Line] [DynamicConst.DynamicType.Card] [DynamicConst.DynamicType.Big]
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
    autoClose: Boolean = false,
    finishListener: (() -> Unit)? = null,
    onIslandClick: () -> Unit,
    content: @Composable () -> Unit
) {
    when (type) {
        DynamicConst.DynamicType.Line -> AutoLineRoundIsland(
            isExpanded = isExpanded,
            duration = duration,
            autoClose = autoClose,
            finishListener = finishListener,
            onIslandClick = onIslandClick,
            content = content
        )
        DynamicConst.DynamicType.Card -> AutoCardRoundIsland(
            isExpanded = isExpanded,
            duration = duration,
            autoClose = autoClose,
            finishListener = finishListener,
            onIslandClick = onIslandClick,
            content = content
        )
        DynamicConst.DynamicType.Big -> AutoBigRoundIsland(
            isExpanded = isExpanded,
            duration = duration,
            autoClose = autoClose,
            finishListener = finishListener,
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
 * @param autoClose
 * @param onIslandClick
 */
@Composable
fun AutoLineRoundIsland(
    isExpanded: Boolean,
    duration: Long,
    finishListener: (() -> Unit)? = null,
    onIslandClick: () -> Unit,
    autoClose: Boolean = false,
    content: @Composable () -> Unit
) {
    BasicAutoDynamicIsland(
        isExpanded = isExpanded,
        duration = duration,
        autoClose = autoClose,
        finishListener = finishListener,
        defaultSize = DynamicConst.DynamicSize(
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
 * @param autoClose
 * @param onIslandClick
 */
@Composable
fun AutoCardRoundIsland(
    isExpanded: Boolean,
    duration: Long,
    autoClose: Boolean,
    finishListener: (() -> Unit)? = null,
    onIslandClick: () -> Unit,
    content: @Composable () -> Unit
) {
    BasicAutoDynamicIsland(
        isExpanded = isExpanded,
        duration = duration,
        autoClose = autoClose,
        finishListener = finishListener,
        defaultSize = DynamicConst.DynamicSize(
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
 * @param autoClose
 * @param onIslandClick
 */
@Composable
fun AutoBigRoundIsland(
    isExpanded: Boolean,
    duration: Long,
    autoClose: Boolean,
    finishListener: (() -> Unit)? = null,
    onIslandClick: () -> Unit,
    content: @Composable () -> Unit
) {
    BasicAutoDynamicIsland(
        isExpanded = isExpanded,
        duration = duration,
        autoClose = autoClose,
        finishListener = finishListener,
        defaultSize = DynamicConst.DynamicSize(
            height = DynamicConst.DEFAULT_HEIGHT,
            width = DynamicConst.DEFAULT_WIDTH,
            corner = DynamicConst.DEFAULT_CORNER,
        ),
        targetSize = DynamicConst.DynamicSize(
            height = DynamicConst.BIG_HEIGHT,
            width = LocalConfiguration.current.screenWidthDp.dp - DynamicConst.BIG_WIDTH_MARGIN,
            corner = DynamicConst.BIG_CORNER,
        ),
        onIslandClick = onIslandClick, content = content
    )
}

@Composable
fun BasicAutoDynamicIsland(
    isExpanded: Boolean,
    duration: Long,
    autoClose: Boolean,
    defaultSize: DynamicConst.DynamicSize,
    targetSize: DynamicConst.DynamicSize,
    finishListener: (() -> Unit)? = null,
    onIslandClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    var isClickable by remember { mutableStateOf(true) }
    //自动关闭
    LaunchedEffect(isExpanded) {
        if (autoClose && isExpanded) {
            scope.launch {
                delay(1500L)
                onIslandClick.invoke()
                isClickable = true
            }
        }
    }
    BasicDynamicIsland(
        isExpanded = isExpanded,
        duration = duration,
        isClickable = isClickable,
        defaultSize = defaultSize,
        targetSize = targetSize,
        finishListener = finishListener,
        onIslandClick = {
            isClickable = false
            onIslandClick.invoke()
        }, content = content
    )
}


/**
 * 基础岛🏝️
 *
 * @param isExpanded 是否展开
 * @param duration 动画时长
 * @param isClickable 是否可点击
 * @param defaultSize 默认大小
 * @param targetSize 目标大小
 * @param finishListener 动画结束监听
 * @param onIslandClick 点击事件
 * @param content 内容
 */
@Composable
fun BasicDynamicIsland(
    isExpanded: Boolean,
    duration: Long = DynamicConst.DEFAULT_ANIMATION_DURATION, //TODO
    isClickable: Boolean = true,
    defaultSize: DynamicConst.DynamicSize,
    targetSize: DynamicConst.DynamicSize,
    finishListener: (() -> Unit)? = null,
    onIslandClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    //region 状态转移型动画
    val springSpec = Spring.DampingRatioLowBouncy
    //animateXXXAsState
    val widthState by animateDpAsState(
        if (isExpanded) targetSize.width else defaultSize.width,
        animationSpec = spring(springSpec),
        finishedListener = { dp: Dp ->
            finishListener?.invoke()
        }
    )
    val heightState by animateDpAsState(
        if (isExpanded) targetSize.height else defaultSize.height,
        animationSpec = spring(springSpec)
    )
    val cornerState by animateDpAsState(
        if (isExpanded) targetSize.corner else defaultSize.corner,
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
//    val width = remember(isExpanded) { if (isExpanded) targetSize.width else defaultSize.width }
//    val height = remember(isExpanded) { if (isExpanded) targetSize.height else defaultSize.height }
//    val corner = remember(isExpanded) { if (isExpanded) targetSize.corner else defaultSize.corner }
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