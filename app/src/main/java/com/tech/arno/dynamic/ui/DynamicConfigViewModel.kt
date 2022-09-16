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

    val autoClose: Flow<Boolean>

    val direction: Flow<DynamicDirection>

    val isExpanded: Flow<Boolean>

    val isLandType: Flow<DynamicType>

    //region config
    val offsetX: Flow<Float>
    val offsetY: Flow<Float>
    val width: Flow<Float>
    val height: Flow<Float>
    val corner: Flow<Float>
    //endregion

    /**
     * 触发展示
     *
     */
    fun triggerDynamic()
    fun onOffSetXChange(value: Float)
    fun onOffSetYChange(value: Float)
    fun onWidthChange(value: Float)
    fun onHeightChange(value: Float)
    fun onCornerChange(value: Float)

    fun sendLineType()
    fun sendCardType()
    fun sendBigType()
}

open class DynamicFloatViewModel : ViewModel(), DynamicConfigViewModelInterface {
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

    private val _autoClose = MutableStateFlow(false)
    override val autoClose: Flow<Boolean>
        get() = _autoClose.asStateFlow()

    private val _direction: MutableStateFlow<DynamicDirection> =
        MutableStateFlow(DynamicDirection.Center)
    override val direction: Flow<DynamicDirection>
        get() = _direction.asStateFlow()

    private val _isExpanded = MutableStateFlow(false)
    override val isExpanded: Flow<Boolean>
        get() = _isExpanded.asStateFlow()

    private val _isLandType: MutableStateFlow<DynamicType> = MutableStateFlow(DynamicType.Line)
    override val isLandType: Flow<DynamicType>
        get() = _isLandType.asStateFlow()


    private val _offsetX = MutableStateFlow(0F)
    override val offsetX: Flow<Float>
        get() = _offsetX.asStateFlow()

    private val _offsetY = MutableStateFlow(0F)
    override val offsetY: Flow<Float>
        get() = _offsetY.asStateFlow()

    private val _width = MutableStateFlow(24F)
    override val width: Flow<Float>
        get() = _width.asStateFlow()

    private val _height = MutableStateFlow(24F)
    override val height: Flow<Float>
        get() = _height.asStateFlow()

    private val _corner = MutableStateFlow(24F)
    override val corner: Flow<Float>
        get() = _corner.asStateFlow()

    override fun triggerDynamic() {
        //相反触发 TODO 队列变更待处理
        _isExpanded.update {
            !_isExpanded.value
        }
    }

    override fun onOffSetXChange(value: Float) {
        _offsetX.update { value }
    }

    override fun onOffSetYChange(value: Float) {
        _offsetY.update { value }
    }

    override fun onWidthChange(value: Float) {
        _width.update { value }
    }

    override fun onHeightChange(value: Float) {
        _height.update { value }
    }

    override fun onCornerChange(value: Float) {
        _corner.update { value }
    }

    override fun sendLineType() {
        _isLandType.update { DynamicType.Line }
        triggerDynamic()
    }

    override fun sendCardType() {
        _isLandType.update { DynamicType.Card }
        triggerDynamic()
    }

    override fun sendBigType() {
        _isLandType.update { DynamicType.Big }
        triggerDynamic()
    }
}
class DynamicActivityViewModel :DynamicFloatViewModel() {
    private val _aniDuration = MutableStateFlow(3000L)
    override val aniDuration: Flow<Long>
        get() = _aniDuration.asStateFlow()

    private val _autoCloseInterval = MutableStateFlow(3000L)
    override val autoCloseInterval: Flow<Long>
        get() = _autoCloseInterval.asStateFlow()

    private val _autoClose = MutableStateFlow(false)
    override val autoClose: Flow<Boolean>
        get() = _autoClose.asStateFlow()

    private val _direction: MutableStateFlow<DynamicDirection> =
        MutableStateFlow(DynamicDirection.Center)
    override val direction: Flow<DynamicDirection>
        get() = _direction.asStateFlow()

    private val _isExpanded = MutableStateFlow(false)
    override val isExpanded: Flow<Boolean>
        get() = _isExpanded.asStateFlow()

    private val _isLandType: MutableStateFlow<DynamicType> = MutableStateFlow(DynamicType.Line)
    override val isLandType: Flow<DynamicType>
        get() = _isLandType.asStateFlow()


    private val _offsetX = MutableStateFlow(0F)
    override val offsetX: Flow<Float>
        get() = _offsetX.asStateFlow()

    private val _offsetY = MutableStateFlow(0F)
    override val offsetY: Flow<Float>
        get() = _offsetY.asStateFlow()

    private val _width = MutableStateFlow(24F)
    override val width: Flow<Float>
        get() = _width.asStateFlow()

    private val _height = MutableStateFlow(24F)
    override val height: Flow<Float>
        get() = _height.asStateFlow()

    private val _corner = MutableStateFlow(24F)
    override val corner: Flow<Float>
        get() = _corner.asStateFlow()

    override fun triggerDynamic() {
        //相反触发 TODO 队列变更待处理
        _isExpanded.update {
            !_isExpanded.value
        }
    }

    override fun onOffSetXChange(value: Float) {
        _offsetX.update { value }
    }

    override fun onOffSetYChange(value: Float) {
        _offsetY.update { value }
    }

    override fun onWidthChange(value: Float) {
        _width.update { value }
    }

    override fun onHeightChange(value: Float) {
        _height.update { value }
    }

    override fun onCornerChange(value: Float) {
        _corner.update { value }
    }

    override fun sendLineType() {
        _isLandType.update { DynamicType.Line }
        triggerDynamic()
    }

    override fun sendCardType() {
        _isLandType.update { DynamicType.Card }
        triggerDynamic()
    }

    override fun sendBigType() {
        _isLandType.update { DynamicType.Big }
        triggerDynamic()
    }
}

class DynamicConfigViewModel(floatViewModel: DynamicConfigViewModelInterface) :
    DynamicConfigViewModelInterface by floatViewModel {
}