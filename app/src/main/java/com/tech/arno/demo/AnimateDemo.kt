package com.tech.arno.demo

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun PreviewLine() {
    var isBig by remember { mutableStateOf(false) }
    val isAuto = true
    val duration = 1500L
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Text("条幅通知")
//            Spacer(Modifier.width(16.dp))
//            Text("自动：")
//            Checkbox(checked = isAuto, onCheckedChange = {
//                isAuto = !isAuto
//            })
//        }
//        Spacer(Modifier.height(16.dp))
        LineRoundIsland(isBig = isBig, isAuto = isAuto, duration) {
            isBig = !isBig
        }
    }

}

@Composable
fun LineRoundIsland(isBig: Boolean, isAuto: Boolean, duration: Long, onBoxClick: () -> Unit) {
    val scope = rememberCoroutineScope()
    val size by animateDpAsState(if (isBig) 196.dp else 24.dp) //animateXXXAsState

    var isClickable by remember { mutableStateOf(true) }
    Card(shape = RoundedCornerShape(24.dp)) {
        Box(modifier = Modifier
            .height(24.dp)
            .width(size)
            .background(Color.Black)
            .clickable(enabled = isClickable) {
                onBoxClick.invoke()
                if (isAuto) {
                    isClickable = false
                    scope.launch {
                        delay(duration)
                        onBoxClick.invoke()
                        isClickable = true
                    }
                }
            })
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBig() {
    var isBig by remember { mutableStateOf(false) }
    var isAuto by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Text("条幅通知")
//            Spacer(Modifier.width(16.dp))
//            Text("自动：")
//            Checkbox(checked = isAuto, onCheckedChange = {
//                isAuto = !isAuto
//            })
//        }
//        Spacer(Modifier.height(16.dp))
        BigRoundIsland(isBig = isBig, isAuto = isAuto) {
            isBig = !isBig
        }
    }
}

@Composable
fun BigRoundIsland(isBig: Boolean, isAuto: Boolean, onBoxClick: () -> Unit) {
    val scope = rememberCoroutineScope()
    val size by animateDpAsState(if (isBig) 196.dp else 24.dp) //animateXXXAsState
    Card(shape = RoundedCornerShape(24.dp)) {
        Box(modifier = Modifier
            .size(size)
            .background(Color.Black)
            .clickable {
                onBoxClick.invoke()
                if (isAuto) {
                    scope.launch {
                        delay(3000)
                        onBoxClick.invoke()
                    }
                }
            })
    }
}