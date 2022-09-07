package com.tech.arno.traditional

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

class CompareCommand {
    fun textByTraditional(context: Context) {
        val textView = TextView(context)
        textView.text = "123"
        textView.setTextColor(Color.RED)
    }

    @Composable
    fun textByCompose() {
        Text(text = "123", color = androidx.compose.ui.graphics.Color.Red)
    }
}