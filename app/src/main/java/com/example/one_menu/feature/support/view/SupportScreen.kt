package com.example.one_menu.feature.support.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goodsaccounting.common.view.click.scaleClick
import com.example.one_menu.R
import com.example.one_menu.feature.common.view.theme.textHintColor
import com.example.one_menu.feature.common.view.theme.textPrimaryColor
import com.example.one_menu.feature.nav.view.LocalBottomNavBarSetting
import com.example.one_menu.feature.support.model.MessageInfo

val messages = mutableStateListOf<MessageInfo>()
@Composable
fun SupportScreen(back: () -> Unit) {
    val bottomNavBarSetting = LocalBottomNavBarSetting.current
    LaunchedEffect(Unit) {
        bottomNavBarSetting?.offAll()
    }
    DisposableEffect(Unit) {
        onDispose {
            bottomNavBarSetting?.show()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .navigationBarsPadding()
            .padding(top = 40.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.support),
                color = textPrimaryColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Center)
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 14.dp)
                    .align(Alignment.CenterStart)
                    .scaleClick(onClick = back)
                    .size(32.dp)
            )
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .rotate(180f),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
        ) {
            items(messages){
                Row(
                    modifier = Modifier.rotate(180f),
                    horizontalArrangement = Arrangement.End
                ) {
                    MessageItem(it)
                }
            }
        }
        var message by remember {
            mutableStateOf("")
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 12.dp, bottom = 24.dp)
                .heightIn(max = 60.dp)
                .fillMaxWidth(),
        ) {
            BasicTextField(
                value = message,
                onValueChange = { message = it },
                modifier = Modifier
                    .weight(1f)
                    .clip(CircleShape)
                    .background(Color(0xFFF4F4F4), CircleShape)
                    .border(1.dp, Color(0xFFEEEEEE), CircleShape)
                    .padding(vertical = 8.dp, horizontal = 12.dp),
                textStyle = TextStyle(
                    color = textHintColor,
                    fontSize = 17.sp,
                )
            ) { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (message.isBlank())
                        Text(
                            text = "${stringResource(R.string.message)}...",
                            fontSize = 17.sp,
                            color = textHintColor.copy(0.4f)
                        )
                    innerTextField()
                }
            }
            AnimatedVisibility(
                visible = message.isNotBlank(),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .scaleClick {
                            messages.add(0,MessageInfo(message.trim()))
                            message = ""
                        }
                        .clip(CircleShape)
                        .background(MaterialTheme.colors.primary, CircleShape)
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_top_arrow),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MessageItem(messageInfo: MessageInfo) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomStart = 12.dp))
            .background(
                MaterialTheme.colors.primary,
                RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomStart = 12.dp)
            )
            .padding(6.dp)
    ){
        Text(
            text = messageInfo.message,
            color = textHintColor,
            fontSize = 14.sp,
            modifier = Modifier
                .widthIn(min = 160.dp, max = 260.dp)
                .padding(horizontal = 6.dp,)
                .padding(top = 2.dp)
        )
        Text(
            text = messageInfo.time,
            fontSize = 10.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.End)
        )
    }
}