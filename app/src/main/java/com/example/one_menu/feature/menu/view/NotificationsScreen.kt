package com.example.one_menu.feature.menu.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goodsaccounting.common.view.click.scaleClick
import com.example.one_menu.R
import com.example.one_menu.feature.common.view.theme.textHintColor
import com.example.one_menu.feature.common.view.theme.textPrimaryColor
import com.example.one_menu.feature.nav.view.LocalBottomNavBarSetting

@Composable
fun NotificationsScreen(
    back: () -> Unit,
) {
    val bottomNavBarSetting = LocalBottomNavBarSetting.current
    val bottomPadding = bottomNavBarSetting?.bottomPaddingValues?.div(2) ?: 0.dp
    LaunchedEffect(Unit) {
        bottomNavBarSetting?.offPadding()
    }
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp,)
        ) {
            Text(
                text = stringResource(R.string.notifications),
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
                .fillMaxWidth()
                .padding( bottom = bottomPadding)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                bottom = bottomPadding + 12.dp,
                top = 12.dp
            )
        ) {
            items(0) {
                ItemNotification(item = it + 1)
            }
        }
    }
}

@Composable
private fun ItemNotification(item: Int) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, MaterialTheme.shapes.small)
            .background(MaterialTheme.colors.background, MaterialTheme.shapes.small)
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp, bottom = 20.dp)
    ) {
        Text(
            text = "Title text $item",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = textPrimaryColor,
        )
        Text(
            text = "Here will be the text. Here will be the text. Here will be the text. Here will be the text. Here will be the text. Here will be the text. Here will be the text.",
            fontSize = 14.sp,
            color = textHintColor.copy(0.6f)
        )
        Text(
            text = "6 Jun. 2023 в 15:32",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = textHintColor.copy(0.8f),
            modifier = Modifier.align(Alignment.End)
        )
    }
}