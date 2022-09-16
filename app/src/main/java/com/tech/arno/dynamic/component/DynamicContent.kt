package com.tech.arno.dynamic.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tech.arno.dynamic.config.DynamicMessage
import com.tech.arno.dynamic.config.DynamicType

/**
 * 演示用动态切换效果
 *
 * @param type
 */
@Composable
fun DynamicContentScreen(type: DynamicType, message: DynamicMessage) {
    val dynamicContent by remember { mutableStateOf(message) }
    val dynamicType by remember { mutableStateOf(type) }
    when (dynamicType) {
        DynamicType.Line -> LineContent(dynamicContent)
        DynamicType.Card -> CardContent(dynamicContent)
        DynamicType.Big -> BigContent(dynamicContent)
        DynamicType.Battery -> PowerContent(dynamicContent)
    }
}

@Composable
fun PowerContent(message: DynamicMessage) {
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            "正在充电", fontSize = 16.sp, color = Color.White
        )
        Icon(Icons.Filled.Menu, contentDescription = "充电图标")
    }
}

@Composable
fun BigContent(message: DynamicMessage) {
    val cacheMessage by remember { mutableStateOf(message) }
    Column(verticalArrangement = Arrangement.Center) {
        Text(
            "🏝️ ${cacheMessage.title}", fontSize = 16.sp, color = Color.White
        )
        Spacer(Modifier.height(8.dp))
        Text(
            cacheMessage.content, fontSize = 14.sp, color = Color.White
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "🏝️ ${cacheMessage.status}", fontSize = 14.sp, color = Color.White
        )
    }
}

@Composable
fun CardContent(message: DynamicMessage) {
    val cacheMessage by remember { mutableStateOf(message) }
    Column(verticalArrangement = Arrangement.Center) {
        Text(
            "🏝️ ${cacheMessage.title}", fontSize = 16.sp, color = Color.White
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "🏝️ ${cacheMessage.content}", fontSize = 14.sp, color = Color.White
        )
    }
}

@Composable
fun LineContent(message: DynamicMessage) {
    val cacheMessage by remember { mutableStateOf(message) }
    Text(
        "🏝️ ${cacheMessage.title}", fontSize = 14.sp, color = Color.White
    )
}

@Composable
fun EmptyContent() {

}