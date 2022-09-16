package com.tech.arno.dynamic.config

/**
 * 扩展方向定义
 *
 */
sealed class DynamicDirection {
    object Left : DynamicDirection()
    object Center : DynamicDirection()
    object Right : DynamicDirection()
}