package com.tech.arno.dynamic

/**
 * 下一个类型 循环
 *
 * @return
 */
fun DynamicConst.DynamicType.nextType(): DynamicConst.DynamicType = when (this) {
    DynamicConst.DynamicType.Line -> DynamicConst.DynamicType.Card
    DynamicConst.DynamicType.Card -> DynamicConst.DynamicType.Big
    DynamicConst.DynamicType.Big -> DynamicConst.DynamicType.Line
}