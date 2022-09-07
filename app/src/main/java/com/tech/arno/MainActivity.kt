package com.tech.arno

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tech.arno.demo.SimpleAnimationSection
import com.tech.arno.ui.theme.ArnoComposePlaygroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArnoComposePlaygroundTheme {
                SimpleAnimationSection()
            }
        }
    }
}

