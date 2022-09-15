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


//region 1. 主要功能暴露
/**
 * 混合类型岛屿🏝️
 *
 * @param type [DynamicType.Line] [DynamicType.Card] [DynamicType.Big]
 * @param isExpanded [Boolean] 是否展开
 * @param aniDuration [Long] 展开动画时长
 * @param direction [DynamicDirection] 展开方向
 * @param autoClose [Boolean] 是否自动关闭
 * @param autoCloseInterval [Long] 自动关闭间隔
 * @param defaultSize [Dp] 默认大小
 * @param finishListener [Long] 展开动画时长
 * @param onIslandClick [() -> Unit] 点击岛屿回调
 * @param content [@Composable] 岛屿内容
 */
@Composable
fun AutoDynamicIsland(
    type: DynamicType,
    isExpanded: Boolean,
    aniDuration: Long,
    direction: DynamicDirection = DynamicDirection.Center,
    autoClose: Boolean = false,
    autoCloseInterval: Long = 1500L,
    defaultSize: DynamicConst.DynamicSize = DynamicConst.DynamicSize(
        height = DynamicConst.DEFAULT_HEIGHT,
        width = DynamicConst.DEFAULT_WIDTH,
        corner = DynamicConst.DEFAULT_CORNER
    ),
    finishListener: (() -> Unit)? = null,
    onIslandClick: () -> Unit,
    content: @Composable () -> Unit
) {
    //region 初始设定
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val targetSize by remember(type) { mutableStateOf(getTargetSizeByType(type, screenWidth)) }
    //endregion

    BasicAutoDynamicIsland(
        isExpanded = isExpanded,
        aniDuration = aniDuration,
        direction = direction,
        autoClose = autoClose,
        autoCloseInterval = autoCloseInterval,
        finishListener = finishListener,
        defaultSize = defaultSize,
        targetSize = targetSize,
        onIslandClick = onIslandClick,
        content = content
    )
}


/**
 * 基础的自动动态岛🏝️
 *
 * @param isExpanded [Boolean] 是否展开
 * @param aniDuration [Long] 展开动画时长
 * @param direction [DynamicDirection] 布局方向
 * @param autoClose [Boolean] 是否自动关闭
 * @param autoCloseInterval [Long] 自动关闭间隔
 * @param defaultSize
 * @param targetSize
 * @param finishListener
 * @param onIslandClick
 * @param content
 */
@Composable
inline fun BasicAutoDynamicIsland(
    isExpanded: Boolean,
    aniDuration: Long,
    direction: DynamicDirection = DynamicDirection.Center,
    autoClose: Boolean = false,
    autoCloseInterval: Long = 1500L,
    defaultSize: DynamicConst.DynamicSize,
    targetSize: DynamicConst.DynamicSize,
    noinline finishListener: (() -> Unit)? = null,
    crossinline onIslandClick: () -> Unit,
    crossinline content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    var isClickable by remember { mutableStateOf(true) }
    //自动关闭
    LaunchedEffect(isExpanded) {
        if (autoClose && isExpanded) {
            scope.launch {
                delay(autoCloseInterval)
                onIslandClick.invoke()
                isClickable = true
            }
        }
    }
    BasicDynamicIsland(
        isExpanded = isExpanded,
        direction = direction,
        aniDuration = aniDuration,
        isClickable = isClickable,
        defaultSize = defaultSize,
        targetSize = targetSize,
        finishListener = finishListener,
        onIslandClick = {
            isClickable = false
            onIslandClick.invoke()
        }, content = { content.invoke() }
    )
}


/**
 * 基础岛🏝️
 *
 * @param isExpanded 是否展开
 * @param direction [DynamicDirection] 展开方向
 * @param aniDuration 动画时长
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
    direction: DynamicDirection = DynamicDirection.Center,
    aniDuration: Long = DynamicConst.DEFAULT_ANIMATION_DURATION, //TODO
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
    val alignment by remember(direction) { mutableStateOf(getAlignmentByDirection(direction)) }

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
                .fillMaxSize(),
            contentAlignment = alignment
        ) {
            if (isExpanded) {
                content.invoke()
            }
        }
    }
    //endregion
}
//endregion

//region 2. 次要样式暴露
/**
 * 带有自动回滚的横线变化岛🏝️
 *
 * @param isExpanded
 * @param aniDuration
 * @param autoClose
 * @param onIslandClick
 */
@JvmOverloads
@Composable
inline fun AutoLineRoundIsland(
    isExpanded: Boolean,
    aniDuration: Long,
    autoClose: Boolean = false,
    noinline finishListener: (() -> Unit)? = null,
    crossinline onIslandClick: () -> Unit,
    crossinline content: @Composable () -> Unit
) {
    BasicAutoDynamicIsland(
        isExpanded = isExpanded,
        aniDuration = aniDuration,
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
 * @param aniDuration
 * @param autoClose
 * @param onIslandClick
 */
@Composable
inline fun AutoCardRoundIsland(
    isExpanded: Boolean,
    aniDuration: Long,
    autoClose: Boolean,
    noinline finishListener: (() -> Unit)? = null,
    crossinline onIslandClick: () -> Unit,
    crossinline content: @Composable () -> Unit
) {
    BasicAutoDynamicIsland(
        isExpanded = isExpanded,
        aniDuration = aniDuration,
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
 * @param aniDuration
 * @param autoClose
 * @param onIslandClick
 */
@Composable
inline fun AutoBigRoundIsland(
    isExpanded: Boolean,
    aniDuration: Long,
    autoClose: Boolean,
    noinline finishListener: (() -> Unit)? = null,
    crossinline onIslandClick: () -> Unit,
    crossinline content: @Composable () -> Unit
) {
    BasicAutoDynamicIsland(
        isExpanded = isExpanded,
        aniDuration = aniDuration,
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
//endregion

//region 3. 私有方法
/**
 * 根据类型生成目标尺寸
 *
 * @param type
 * @param screenWidthDp
 * @return
 */
private fun getTargetSizeByType(
    type: DynamicType,
    screenWidthDp: Dp
): DynamicConst.DynamicSize {
    return when (type) {
        DynamicType.Line -> {
            DynamicConst.DynamicSize(
                height = DynamicConst.LINE_HEIGHT,
                width = DynamicConst.LINE_WIDTH,
                corner = DynamicConst.LINE_CORNER,
            )
        }
        DynamicType.Card -> {
            DynamicConst.DynamicSize(
                height = DynamicConst.CARD_HEIGHT,
                width = DynamicConst.CARD_WIDTH,
                corner = DynamicConst.CARD_CORNER,
            )
        }
        DynamicType.Big -> {
            DynamicConst.DynamicSize(
                height = DynamicConst.BIG_HEIGHT,
                width = screenWidthDp - DynamicConst.BIG_WIDTH_MARGIN,
                corner = DynamicConst.BIG_CORNER,
            )
        }
    }
}

/**
 * 基于方向控制对齐方式
 *
 * @param direction
 * @return
 */
private fun getAlignmentByDirection(direction: DynamicDirection): Alignment {
    return when (direction) {
        DynamicDirection.Left -> Alignment.CenterStart
        DynamicDirection.Right -> Alignment.CenterEnd
        DynamicDirection.Center -> Alignment.Center
    }
}
//endregion