package com.yashraj.chat_presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yashraj.chat_domain.model.common.Message
import com.yashraj.utils.LightGrey
import com.yashraj.utils.TextColor


@Composable
fun ChatItem(response: Message) {
        val modifier = if (response.role == "user") Modifier.fillMaxWidth().padding(8.dp).padding(start=64.dp) else Modifier.padding(8.dp).padding(end=64.dp)
        Row(modifier = modifier,
            horizontalArrangement = if(response.role == "user") Arrangement.End else Arrangement.Start) {
            Card {
                Text(text = response.content, color = if (response.role == "user") Color.White else TextColor,
                    modifier = Modifier
                    .background(color = if (response.role == "user") Blue else LightGrey)
                    .padding(horizontal = 8.dp, vertical = 4.dp))
            }


    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, backgroundColor = 0xFFF)
@Composable
fun ChatItemPreview() {
    ChatItem(response = Message("hi","user"))
}
