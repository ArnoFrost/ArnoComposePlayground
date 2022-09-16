package com.tech.arno.dynamic.config

import androidx.compose.ui.unit.dp

class DynamicConst {
    companion object {
        const val DEFAULT_ANIMATION_DURATION = 500L
        const val DEFAULT_AUTO_CLOSE_INTERVAL = 3000L

        const val ACTION_BATTERY = "com.arno.tech.dynamic.ACTION_POWER"
        const val ACTION_DEMO_LINE = "com.arno.tech.dynamic.ACTION_DEMO_LINE"
        const val ACTION_DEMO_CARD = "com.arno.tech.dynamic.ACTION_DEMO_CARD"
        const val ACTION_DEMO_BIG = "com.arno.tech.dynamic.ACTION_DEMO_BIG"

        //默认尺寸
        var DEFAULT_WIDTH = 24.dp
        var DEFAULT_HEIGHT = 24.dp
        var DEFAULT_CORNER = 24.dp
        var DEFAULT_OFFSET_X = 0.dp
        var DEFAULT_OFFSET_Y = 0.dp

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

        //电量尺寸
        val POWER_WIDTH_MARGIN = 30.dp
        val POWER_HEIGHT = 100.dp
        val POWER_CORNER = 24.dp
    }
}

