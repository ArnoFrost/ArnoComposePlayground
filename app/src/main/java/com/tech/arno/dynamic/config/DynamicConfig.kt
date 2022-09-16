package com.tech.arno.dynamic.config

import androidx.compose.ui.unit.Dp
import org.json.JSONObject


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
) {
    fun toJson(): String {
        val jsonObject = JSONObject()
        jsonObject.apply {
            put("height", height.value)
            put("width", width.value)
            put("corner", corner.value)
            put("offsetX", offsetX.value)
            put("offsetY", offsetY.value)
        }
        return jsonObject.toString()
    }

    companion object {
        fun fromJson(json: String): DynamicConfig {
            val jsonObject = JSONObject(json)
            return DynamicConfig(
                height = Dp(jsonObject.getDouble("height").toFloat()),
                width = Dp(jsonObject.getDouble("width").toFloat()),
                corner = Dp(jsonObject.getDouble("corner").toFloat()),
                offsetX = Dp(jsonObject.getDouble("offsetX").toFloat()),
                offsetY = Dp(jsonObject.getDouble("offsetY").toFloat()),
            )
        }
    }
}