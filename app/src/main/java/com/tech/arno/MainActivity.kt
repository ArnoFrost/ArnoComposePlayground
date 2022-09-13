package com.tech.arno

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.tech.arno.demo.SimpleAnimationSection
import com.tech.arno.ui.theme.ArnoComposePlaygroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowTest()
        }
    }
}

@Composable
fun ShowTest() {
    Column {
        repeat(10) {
            Text(text = "Hello world $it")
        }
    }

}

