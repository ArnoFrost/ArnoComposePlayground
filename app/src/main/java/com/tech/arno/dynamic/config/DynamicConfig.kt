package com.tech.arno.dynamic.config

import androidx.compose.ui.unit.Dp


/**
 * 配置尺寸
 *
 * @property height
 * @property width
 * @property corner
 */
data class DynamicConfig(
    val height: Dp = DynamicConst.DEFAULT_HEIGHT,
    val width: Dp = DynamicConst.DEFAULT_WIDTH,
    val corner: Dp = DynamicConst.DEFAULT_CORNER,
    val offsetX: Dp = DynamicConst.DEFAULT_OFFSET_X,
    val offsetY: Dp = DynamicConst.DEFAULT_OFFSET_Y,
)