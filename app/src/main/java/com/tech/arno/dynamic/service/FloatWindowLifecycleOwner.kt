package com.tech.arno.dynamic.service

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner

/**
 * Compose自定义LifecycleOwner
 *
 */
class ComposeViewLifecycleOwner : SavedStateRegistryOwner {

    private var lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)
    private var savedStateRegistryController: SavedStateRegistryController =
        SavedStateRegistryController.create(this)

    val isInitialized: Boolean
        get() = true

    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

    fun setCurrentState(state: Lifecycle.State) {
        lifecycleRegistry.currentState = state
    }

    fun handleLifecycleEvent(event: Lifecycle.Event) {
        lifecycleRegistry.handleLifecycleEvent(event)
    }


    fun performRestore(savedState: Bundle?) {
        savedStateRegistryController.performRestore(savedState)
    }

    fun performSave(outBundle: Bundle) {
        savedStateRegistryController.performSave(outBundle)
    }
}