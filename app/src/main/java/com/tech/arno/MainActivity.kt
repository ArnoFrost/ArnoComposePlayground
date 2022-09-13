package com.tech.arno

import android.os.Bundle
import android.widget.CheckBox
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tech.arno.demo.BigRoundIsland
import com.tech.arno.demo.LineRoundIsland
import kotlinx.coroutines.coroutineScope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var isLineRoundIslandBig by mutableStateOf(false)
        var isLineRoundIsLandAuto by mutableStateOf(false)

        var isBigRoundIslandBig by mutableStateOf(false)
        var isBigRoundIslandAuto by mutableStateOf(false)

        setContent {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("条幅通知")
                    Spacer(Modifier.width(16.dp))
                    Text("自动：")
                    Checkbox(checked = isLineRoundIsLandAuto, onCheckedChange = {
                        isLineRoundIsLandAuto = !isLineRoundIsLandAuto
                    })
                }
                Spacer(Modifier.height(16.dp))
                LineRoundIsland(isBig = isLineRoundIslandBig, isAuto = isLineRoundIsLandAuto) {
                    isLineRoundIslandBig = !isLineRoundIslandBig
                }
                Spacer(Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("卡片通知")
                    Spacer(Modifier.width(16.dp))
                    Text("自动：")
                    Checkbox(checked = isBigRoundIslandAuto, onCheckedChange = {
                        isBigRoundIslandAuto = !isBigRoundIslandAuto
                    })
                }
                Spacer(Modifier.height(16.dp))
                BigRoundIsland(isBigRoundIslandBig, isAuto = isBigRoundIslandAuto) {
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

