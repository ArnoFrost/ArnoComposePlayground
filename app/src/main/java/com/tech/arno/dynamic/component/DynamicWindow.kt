package com.tech.arno.dynamic.component

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.provider.Settings
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.tech.arno.dynamic.config.DynamicConst
import com.tech.arno.dynamic.ui.DynamicConfigViewModelInterface
import com.tech.arno.dynamic.ui.DynamicFloatScreen
import com.tech.arno.dynamic.ui.DynamicFloatViewModel

class DynamicWindow(var context: Context) {
    companion object {
        val viewModel: DynamicConfigViewModelInterface = DynamicFloatViewModel()
    }

    private lateinit var handler: Handler
    private lateinit var windowManager: WindowManager
    private var receiver: MyReceiver? = null
    private val windowLayoutParams: WindowManager.LayoutParams by lazy {
        WindowManager.LayoutParams().apply {
            type =
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            format = PixelFormat.RGBA_8888
//            format = PixelFormat.TRANSPARENT
            gravity = Gravity.START or Gravity.TOP
            flags =
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS
            }
            x = DynamicConst.DEFAULT_OFFSET_X.value.toInt()
            y = DynamicConst.DEFAULT_OFFSET_Y.value.toInt()
        }
    }
    private val floatingView: ComposeView by lazy {
        ComposeView(context).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
//            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                //位置偏移
                val x = viewModel.offsetX.collectAsState(initial = 0F)
                val y = viewModel.offsetY.collectAsState(initial = 0F)
                windowLayoutParams.apply {
                    this.x = x.value.toInt()
                    this.y = y.value.toInt()
                }
                windowManager.updateViewLayout(floatingView, windowLayoutParams)
                DynamicFloatScreen(viewModel)
            }
        }
    }


    // 用来判断floatingView是否attached 到 window manager，防止二次removeView导致崩溃
    private var attached = false
    fun init() {
        // 注册广播
        receiver = MyReceiver()
        val filter = IntentFilter()
        filter.addAction(DynamicConst.ACTION_DEMO_LINE)
        filter.addAction(DynamicConst.ACTION_DEMO_CARD)
        filter.addAction(DynamicConst.ACTION_DEMO_BIG)

        filter.addAction(Intent.ACTION_BATTERY_CHANGED)
        filter.addAction(DynamicConst.ACTION_BATTERY)

        context.registerReceiver(receiver, filter);

        // 获取windowManager并设置layoutParams
        windowManager = context.getSystemService(Service.WINDOW_SERVICE) as WindowManager
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
            when (intent.action) {
                DynamicConst.ACTION_BATTERY -> {
                    val level = intent.getIntExtra("level", 66)
                    viewModel.sendBattery(level)
                }
                Intent.ACTION_BATTERY_CHANGED -> {
                    val level = intent.getIntExtra("level", 0)
                    viewModel.sendBattery(level)
                }
            }
        }
    }
}