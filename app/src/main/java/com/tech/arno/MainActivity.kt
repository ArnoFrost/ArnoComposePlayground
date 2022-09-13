package com.tech.arno

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tech.arno.demo.BigRoundIsland
import com.tech.arno.demo.LineRoundIsland

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var isBig by mutableStateOf(false)
        var isRoundIslandBig by mutableStateOf(false)
        var isBigRoundIslandBig by mutableStateOf(false)
        setContent {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                TestAnimateState(isBig = isBig) {
//                    isBig = !isBig
//                }
//                Spacer(Modifier.height(16.dp))
                LineRoundIsland(isBig = isRoundIslandBig) {
                    isRoundIslandBig = !isRoundIslandBig
                }
                Spacer(Modifier.height(16.dp))
                BigRoundIsland(isBigRoundIslandBig) {
                    isBigRoundIslandBig = !isBigRoundIslandBig
                }
            }
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

