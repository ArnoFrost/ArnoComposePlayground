package com.tech.arno.demo

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
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
fun PreviewDynamicIsland() {
    var isBig by remember { mutableStateOf(false) }
    val duration = 1500L
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("条幅通知")
            Spacer(Modifier.width(16.dp))
        }
        Spacer(Modifier.height(16.dp))
        AutoRoundLineIsland(isBig = isBig, duration) {
            isBig = !isBig
        }
    }
}

@Composable
fun AutoRoundLineIsland(
    isBig: Boolean,
    duration: Long,
    onBoxClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var isClickable by remember { mutableStateOf(true) }
    LineRoundIsland(isBig = isBig, isClickable = isClickable) {
        isClickable = false
        scope.launch {
            delay(duration)
            onBoxClick.invoke()
            isClickable = true
        }
    }
}

@Composable
fun LineRoundIsland(isBig: Boolean, isClickable: Boolean = true, onBoxClick: () -> Unit) {
    val size by animateDpAsState(if (isBig) 196.dp else 24.dp) //animateXXXAsState
    Card(shape = RoundedCornerShape(24.dp)) {
        Box(modifier = Modifier
            .height(24.dp)
            .width(size)
            .background(Color.Black)
            .clickable(enabled = isClickable) {
                onBoxClick.invoke()
            })
    }
}