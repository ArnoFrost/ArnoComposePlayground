package com.tech.arno.const

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class DynamicConst {
    companion object {
        //默认尺寸
        val DEFAULT_WIDTH = 24.dp
        val DEFAULT_HEIGHT = 24.dp
        val DEFAULT_CORNER = DEFAULT_HEIGHT

        //横线尺寸
        val LINE_WIDTH = 196.dp
        val LINE_HEIGHT = 50.dp
        val LINE_CORNER = LINE_HEIGHT

        //卡片尺寸
        val CARD_WIDTH = 196.dp
        val CARD_HEIGHT = CARD_WIDTH
        val CARD_CORNER = 30.dp

        //大卡片尺寸
        val BIG_WIDTH_MARGIN = 30.dp
        val BIG_HEIGHT = 150.dp
        val BIG_CORNER = 24.dp

    }

    /**
     * 配置尺寸
     *
     * @property height
     * @property width
     * @property corner
     */
    data class DynamicSize(val height: Dp, val width: Dp, val corner: Dp)
}