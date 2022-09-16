package com.tech.arno.dynamic.config

/**
 * 岛屿类型定义
 *
 */
sealed class DynamicType {
    object Line : DynamicType()
    object Card : DynamicType()
    object Big : DynamicType()
}