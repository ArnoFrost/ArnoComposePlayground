package com.tech.arno.dynamic

import androidx.compose.runtime.Recomposer
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.compose.ui.platform.compositionContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import androidx.savedstate.R
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.tech.arno.dynamic.config.DynamicType
import com.tech.arno.dynamic.service.ComposeViewLifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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

// Trick The ComposeView into thinking we are tracking lifecycle
fun AbstractComposeView.addToLifecycle() {
    val viewModelStore = ViewModelStore()
    val lifecycleOwner = ComposeViewLifecycleOwner()
    lifecycleOwner.performRestore(null)
    lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    ViewTreeLifecycleOwner.set(this, lifecycleOwner)
    ViewTreeViewModelStoreOwner.set(this) { viewModelStore }
    // OLD.
//    ViewTreeSavedStateRegistryOwner.set(this, lifecycleOwner)
//    this.setTag(R.id.view_tree_saved_state_registry_owner, lifecycleOwner)
    setViewTreeSavedStateRegistryOwner(lifecycleOwner)
    //这里是触发重组的关键
    val coroutineContext = AndroidUiDispatcher.CurrentThread
    val runRecomposeScope = CoroutineScope(coroutineContext)
    val reComposer = Recomposer(coroutineContext)
    this.compositionContext = reComposer
    runRecomposeScope.launch {
        reComposer.runRecomposeAndApplyChanges()
    }
}