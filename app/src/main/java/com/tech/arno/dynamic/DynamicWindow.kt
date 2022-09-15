package com.tech.arno.dynamic

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.os.Handler
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
//import androidx.savedstate.ViewTreeSavedStateRegistryOwner
import com.tech.arno.dynamic.service.FloatWindowLifecycleOwner

class DynamicWindow(var context: Context) {
    private lateinit var windowManager: WindowManager
    private lateinit var layoutParams: WindowManager.LayoutParams
    private lateinit var handler: Handler

    private var receiver: MyReceiver? = null
    private var floatingView: ComposeView? = null

    private var x = 0
    private var y = 0

    // 用来判断floatingView是否attached 到 window manager，防止二次removeView导致崩溃
    private var attached = false
    fun init() {
        // 注册广播
        receiver = MyReceiver()
        val filter = IntentFilter()
        filter.addAction("android.intent.action.MyReceiver")
        context.registerReceiver(receiver, filter);

        // 获取windowManager并设置layoutParams
        windowManager = context.getSystemService(Service.WINDOW_SERVICE) as WindowManager
        layoutParams = WindowManager.LayoutParams().apply {
            type =
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            format = PixelFormat.RGBA_8888
//            format = PixelFormat.TRANSPARENT
            gravity = Gravity.START or Gravity.TOP
            flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            x = DynamicConst.DEFAULT_OFFSET_X.value.toInt()
            y = DynamicConst.DEFAULT_OFFSET_Y.value.toInt()
        }

        // region init ComposeView
        floatingView = ComposeView(context).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                DynamicScreen()
            }
        }
        floatingView?.let { composeView ->
            initLifecycleOwner(composeView)
        }
        //endregion
    }

    fun showWindow() {
        if (Settings.canDrawOverlays(context)) {
            windowManager.addView(floatingView, layoutParams)
            attached = true
        }
    }

    @Composable
    private fun DynamicScreen() {
        //region 配置属性
        val aniDuration = 3000L
        val autoCloseInterval = 3000L
        val direction = DynamicDirection.Center
        var isExpanded by remember { mutableStateOf(false) }
        var islandType by remember { mutableStateOf<DynamicType>(DynamicType.Line) }
        val triggerDynamic = { isExpanded = !isExpanded }
        val screenWith = LocalConfiguration.current.screenWidthDp
        val scrollState = rememberScrollState()

        var dynamicDefaultOffSetX by remember { mutableStateOf(0F) }
        var dynamicDefaultOffSetY by remember { mutableStateOf(0F) }
        var dynamicDefaultWidth by remember { mutableStateOf(24F) }
        var dynamicDefaultHeight by remember { mutableStateOf(24F) }
        var dynamicDefaultCorner by remember { mutableStateOf(24F) }
        val defaultSize = remember {
            derivedStateOf {
                DynamicConst.DynamicSize(
                    dynamicDefaultHeight.dp,
                    dynamicDefaultWidth.dp,
                    dynamicDefaultCorner.dp,
                    dynamicDefaultOffSetX.dp,
                    dynamicDefaultOffSetY.dp
                )
            }
        }
        //endregion

        //region UI
        AutoDynamicIsland(
            type = islandType,
            isExpanded = isExpanded,
            direction = direction,
            aniDuration = aniDuration,
            autoClose = true,
            autoCloseInterval = autoCloseInterval,
            defaultSize = defaultSize.value,
            onIslandClick = triggerDynamic
        ) {
            DynamicContentScreen(islandType)
        }
        //endregion
    }

    fun destroy() {
        floatingView?.let { composeView ->
            ViewTreeLifecycleOwner.set(composeView, null)
        }
        // 注销广播并删除浮窗
        context.unregisterReceiver(receiver)
        receiver = null
        if (attached) {
            windowManager.removeViewImmediate(floatingView)
        }
    }

    inner class MyReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
//            val content = intent.getStringExtra("content") ?: ""
//            val message = Message.obtain()
//            message.what = 0
//            message.obj = stringBuilder.toString()
//            handler.sendMessage(message)
        }
    }

    // Trick The ComposeView into thinking we are tracking lifecycle
    private fun initLifecycleOwner(view: View) {
        val viewModelStore = ViewModelStore()
        val lifecycleOwner = FloatWindowLifecycleOwner().apply {
            performRestore(null)
            handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        }

        ViewTreeLifecycleOwner.set(view, lifecycleOwner)
        ViewTreeViewModelStoreOwner.set(view) { viewModelStore }
        //FIXME 引用不到？
//        ViewTreeSavedStateRegistryOwner.set(view, lifecycleOwner)
    }
}