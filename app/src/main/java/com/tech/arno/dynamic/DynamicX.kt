package com.tech.arno.dynamic

import com.tech.arno.dynamic.config.DynamicType

/**
 * 下一个类型 循环
 *
 * @return
 */
fun DynamicType.nextType(): DynamicType = when (this) {
    DynamicType.Line -> DynamicType.Card
    DynamicType.Card -> DynamicType.Big
    DynamicType.Big -> DynamicType.Line
}