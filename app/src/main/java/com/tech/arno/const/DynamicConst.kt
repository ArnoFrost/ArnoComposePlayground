package com.tech.arno.const

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class DynamicConst {
    companion object {
        //默认尺寸
        val DEFAULT_WIDTH = 24.dp
        val DEFAULT_HEIGHT = 24.dp
        val DEFAULT_CORNER = 24.dp

        //横线尺寸
        val LINE_WIDTH = 196.dp
        val LINE_HEIGHT = 30.dp
        val LINE_CORNER = DEFAULT_CORNER

        //
    }

    /**
     * 配置尺寸
     *
     * @property height
     * @property width
     */
    data class DynamicSize(val height: Dp, val width: Dp)
}