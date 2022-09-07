package com.tech.arno.demo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tech.arno.R

@Preview(showBackground = true)
@Composable
fun SimpleAnimationSection() {
    Card {
        var expanded by remember { mutableStateOf(false) }
        Column(
            Modifier
                .clickable { expanded = !expanded }
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.jetpack_compose_logo),
                contentDescription = null
            )
            AnimatedVisibility(visible = expanded) {
                Text(
                    text = "Jetpack Compose",
                    style = MaterialTheme.typography.h2
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowCompose() {
    var score by remember { mutableStateOf(0) }

//    var scoreList =  remember { mutableStateListOf<Int>() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Score: $score")
        Spacer(Modifier.height(20.dp))
        Button(onClick = { score++ }) {
            Text(text = "Click me")
        }
//        Divider()
//        Spacer(Modifier.height(20.dp))
    }
}