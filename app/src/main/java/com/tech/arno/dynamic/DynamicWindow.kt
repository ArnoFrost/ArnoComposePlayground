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
import android.view.WindowManager
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.tech.arno.dynamic.ui.DynamicConfigViewModelInterface
import com.tech.arno.dynamic.ui.DynamicFloatScreen
import com.tech.arno.dynamic.ui.DynamicFloatViewModel

class DynamicWindow(var context: Context) {
    companion object {
        val floatingViewModel: DynamicConfigViewModelInterface = DynamicFloatViewModel()
    }

    private lateinit var windowManager: WindowManager
    private lateinit var windowLayoutParams: WindowManager.LayoutParams
    private lateinit var handler: Handler

    private var receiver: MyReceiver? = null
    private val floatingView: ComposeView by lazy {
        ComposeView(context).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
//            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                //位置偏移
                val x = floatingViewModel.offsetX.collectAsState(initial = 0F)
                val y = floatingViewModel.offsetY.collectAsState(initial = 0F)
                windowLayoutParams.apply {
                    this.x = x.value.toInt()
                    this.y = y.value.toInt()
                }
                windowManager.updateViewLayout(floatingView, windowLayoutParams)
                DynamicFloatScreen(floatingViewModel)
            }
        }
    }


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
        windowLayoutParams = WindowManager.LayoutParams().apply {
            type =
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            format = PixelFormat.RGBA_8888
//            format = PixelFormat.TRANSPARENT
            gravity = Gravity.START or Gravity.TOP
            flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            x = DynamicConst.DEFAULT_OFFSET_X.value.toInt()
            y = DynamicConst.DEFAULT_OFFSET_Y.value.toInt()
        }
    }

    fun showWindow() {
        if (Settings.canDrawOverlays(context)) {
            floatingView.addToLifecycle()
            windowManager.addView(floatingView, windowLayoutParams)
            attached = true
        }
    }

    fun destroy() {
        floatingView.let { composeView ->
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
}