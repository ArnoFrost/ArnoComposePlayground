package com.tech.arno.demo

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TestAnimateState(isBig: Boolean, onBoxClick: () -> Unit) {
    val size by animateDpAsState(if (isBig) 196.dp else 96.dp) //animateXXXAsState
    Box(modifier = Modifier
        .size(size)
        .background(Color.Cyan)
        .clickable { onBoxClick.invoke() })
}

@Composable
fun LineRoundIsland(isBig: Boolean, onBoxClick: () -> Unit) {
    val size by animateDpAsState(if (isBig) 196.dp else 24.dp) //animateXXXAsState
    Card(shape = RoundedCornerShape(24.dp)) {
        Box(modifier = Modifier
            .height(24.dp)
            .width(size)
            .background(Color.Black)
            .clickable { onBoxClick.invoke() })
    }
}

@Composable
fun BigRoundIsland(isBig: Boolean, onBoxClick: () -> Unit) {
    val size by animateDpAsState(if (isBig) 196.dp else 24.dp) //animateXXXAsState
    Card(shape = RoundedCornerShape(24.dp)) {
        Box(modifier = Modifier
            .size(size)
            .background(Color.Black)
            .clickable { onBoxClick.invoke() })
    }
}