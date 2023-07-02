package com.example.one_menu.feature.auth.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.goodsaccounting.common.view.click.scaleClick
import com.example.one_menu.R

@Composable
fun GoogleButton() {
    Box(
        modifier = Modifier
            .scaleClick { }
            .fillMaxWidth()
            .background(Color(0xFFF9F9F9), MaterialTheme.shapes.small)
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_google),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
        )
    }
}