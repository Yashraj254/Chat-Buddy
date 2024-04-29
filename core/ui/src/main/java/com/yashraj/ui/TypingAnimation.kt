package com.yashraj.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.delay

@Composable
fun TypingAnimation(
    text: String, fontSize:TextUnit, color: Color, duration: Long
) {
    var textToDisplay by remember { mutableStateOf("") }
    LaunchedEffect(
        key1 = true,
    ) {
        while (true) {
            text.forEachIndexed { charIndex, _ ->
                textToDisplay = text
                    .substring(
                        startIndex = 0,
                        endIndex = charIndex + 1,
                    )
                delay(duration)
            }
            delay(500)
        }
    }

    Text(
        text = textToDisplay,
        fontSize = fontSize,
        color = color,
        fontWeight = FontWeight.Bold
    )
}