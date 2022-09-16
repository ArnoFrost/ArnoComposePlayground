package com.tech.arno.dynamic.ui

import androidx.lifecycle.ViewModel
import com.tech.arno.dynamic.config.DynamicDirection
import com.tech.arno.dynamic.config.DynamicType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface DynamicConfigViewModelInterface {
    //动画时长
    val aniDuration: Flow<Long>

    val autoCloseInterval: Flow<Long>

    val direction: Flow<DynamicDirection>

    val isExpanded: Flow<Boolean>

    val isLandType: Flow<DynamicType>

    /**
     * 触发展示
     *
     */
    fun triggerDynamic()
}

class DynamicConfigViewModel : ViewModel(), DynamicConfigViewModelInterface {
//    companion object {
//        val composeViewModel by lazy {
//            object : DynamicConfigViewModelInterface {
//                private val _aniDuration = MutableStateFlow(3000L)
//                override val aniDuration: Flow<Long>
//                    get() = _aniDuration.asStateFlow()
//            }
//        }
//    }

    private val _aniDuration = MutableStateFlow(3000L)
    override val aniDuration: Flow<Long>
        get() = _aniDuration.asStateFlow()
    private val _autoCloseInterval = MutableStateFlow(3000L)
    override val autoCloseInterval: Flow<Long>
        get() = _autoCloseInterval.asStateFlow()

    private val _direction = MutableStateFlow(DynamicDirection.Center)
    override val direction: Flow<DynamicDirection>
        get() = _direction.asStateFlow()

    private val _isExpanded = MutableStateFlow(false)
    override val isExpanded: Flow<Boolean>
        get() = _isExpanded.asStateFlow()

    private val _isLandType = MutableStateFlow(DynamicType.Line)
    override val isLandType: Flow<DynamicType>
        get() = _isLandType.asStateFlow()

    override fun triggerDynamic() {
        //相反触发 TODO 队列变更待处理
        _isExpanded.update {
            !_isExpanded.value
        }
    }
}