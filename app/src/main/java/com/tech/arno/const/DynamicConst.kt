package com.tech.arno.const

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class DynamicConst {
    companion object {
        val DEFAULT_WIDTH = 24.dp
        val DEFAULT_HEIGHT = 24.dp

        val SMALL_WIDTH = 24.dp
        val SMALL_HEIGHT = 24.dp

        val BIG_HEIGHT = 196.dp
        val BIG_WIDTH = 196.dp
    }

    /**
     * 配置尺寸
     *
     * @property height
     * @property width
     */
    data class DynamicSize(val height: Dp, val width: Dp)
}