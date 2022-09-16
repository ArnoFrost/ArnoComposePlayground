package com.tech.arno.dynamic.ui

import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.SPStaticUtils
import com.tech.arno.dynamic.component.DynamicWindow
import com.tech.arno.dynamic.component.fromJson
import com.tech.arno.dynamic.component.save
import com.tech.arno.dynamic.config.DynamicConfig
import com.tech.arno.dynamic.config.DynamicDirection
import com.tech.arno.dynamic.config.DynamicMessage
import com.tech.arno.dynamic.config.DynamicType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface DynamicConfigViewModelInterface {

    val defaultConfig: DynamicConfig

    //动画时长
    val aniDuration: Flow<Long>

    val autoCloseInterval: Flow<Long>

    val autoClose: Flow<Boolean>

    val direction: Flow<DynamicDirection>

    val isExpanded: Flow<Boolean>

    val isLandType: Flow<DynamicType>
    val isLandMessage: Flow<DynamicMessage>

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
    fun sendBattery(value: Int)
}

open class DynamicFloatViewModel : ViewModel(), DynamicConfigViewModelInterface {
    protected val _aniDuration = MutableStateFlow(3000L)
    final override val defaultConfig: DynamicConfig
        get() = SPStaticUtils.getString("config", "").fromJson()
    override val aniDuration: Flow<Long>
        get() = _aniDuration.asStateFlow()

    protected val _autoCloseInterval = MutableStateFlow(3000L)
    override val autoCloseInterval: Flow<Long>
        get() = _autoCloseInterval.asStateFlow()

    protected val _autoClose = MutableStateFlow(true)
    override val autoClose: Flow<Boolean>
        get() = _autoClose.asStateFlow()

    protected val _direction: MutableStateFlow<DynamicDirection> =
        MutableStateFlow(DynamicDirection.Center)
    override val direction: Flow<DynamicDirection>
        get() = _direction.asStateFlow()

    protected val _isExpanded = MutableStateFlow(false)
    override val isExpanded: Flow<Boolean>
        get() = _isExpanded.asStateFlow()

    protected val _isLandType: MutableStateFlow<DynamicType> = MutableStateFlow(DynamicType.Line)
    override val isLandType: Flow<DynamicType>
        get() = _isLandType.asStateFlow()

    protected val _isLandMessage: MutableStateFlow<DynamicMessage> =
        MutableStateFlow(DynamicMessage("测试标题", "测试文本", 2))
    override val isLandMessage: Flow<DynamicMessage>
        get() = _isLandMessage.asStateFlow()


    protected val _offsetX = MutableStateFlow(defaultConfig.offsetX.value)
    override val offsetX: Flow<Float>
        get() = _offsetX.asStateFlow()

    protected val _offsetY = MutableStateFlow(defaultConfig.offsetY.value)
    override val offsetY: Flow<Float>
        get() = _offsetY.asStateFlow()

    protected val _width = MutableStateFlow(defaultConfig.width.value)
    override val width: Flow<Float>
        get() = _width.asStateFlow()

    protected val _height = MutableStateFlow(defaultConfig.height.value)
    override val height: Flow<Float>
        get() = _height.asStateFlow()

    protected val _corner = MutableStateFlow(defaultConfig.corner.value)
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

    override fun sendBattery(value: Int) {
        _isLandType.update { DynamicType.Battery }
        triggerDynamic()
    }
}

class DynamicActivityViewModel : DynamicFloatViewModel() {
    private var delegateViewModel: DynamicViewModelDelegate? = null

    var checkOverlayPermission = MutableStateFlow(false)
    var checkAccessibility = MutableStateFlow(false)
    override fun triggerDynamic() {
        //相反触发 TODO 队列变更待处理
        _isExpanded.update {
            !_isExpanded.value
        }
        delegateViewModel?.triggerDynamic()
    }

    override fun onOffSetXChange(value: Float) {
        _offsetX.update { value }
        delegateViewModel?.onOffSetXChange(value)
    }

    override fun onOffSetYChange(value: Float) {
        _offsetY.update { value }
        delegateViewModel?.onOffSetYChange(value)
    }

    override fun onWidthChange(value: Float) {
        _width.update { value }
        delegateViewModel?.onWidthChange(value)
    }

    override fun onHeightChange(value: Float) {
        _height.update { value }
        delegateViewModel?.onHeightChange(value)
    }

    override fun onCornerChange(value: Float) {
        _corner.update { value }
        delegateViewModel?.onCornerChange(value)
    }

    override fun sendLineType() {
        delegateViewModel?.sendLineType()
    }

    override fun sendCardType() {
        delegateViewModel?.sendCardType()
    }

    override fun sendBigType() {
        delegateViewModel?.sendBigType()
    }

    override fun sendBattery(value: Int) {
        delegateViewModel?.sendBattery(value)
    }

    fun injectFloatViewModel() {
        delegateViewModel =
            DynamicViewModelDelegate((DynamicWindow.viewModel) as DynamicFloatViewModel)

    }

    fun clickAccessibility() {
        checkAccessibility.update { true }
    }

    fun showOverlay() {
        checkOverlayPermission.update { true }
    }

    fun saveConfig() {
        val defaultConfig = DynamicConfig(
            offsetX = _offsetX.value.dp,
            offsetY = _offsetY.value.dp,
            width = _width.value.dp,
            height = _height.value.dp,
            corner = _corner.value.dp
        )
        viewModelScope.launch(Dispatchers.IO) {
            defaultConfig.save()
        }
    }

}

class DynamicViewModelDelegate(floatViewModel: DynamicConfigViewModelInterface) :
    DynamicConfigViewModelInterface by floatViewModel {
}