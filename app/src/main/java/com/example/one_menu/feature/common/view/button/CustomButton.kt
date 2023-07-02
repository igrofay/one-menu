package com.example.one_menu.feature.common.view.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goodsaccounting.common.view.click.scaleClick
import com.example.one_menu.feature.common.view.theme.textPrimaryColor
import com.example.one_menu.feature.common.view.theme.textSecondaryColor

@Composable
fun CustomButton(
    label: String,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(vertical = 18.dp),
    style: TextStyle = TextStyle(
        color = textSecondaryColor,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
    ),
    shape: Shape = MaterialTheme.shapes.large,
    onClick:()->Unit,
) {
    Box(
        modifier = modifier
            .scaleClick(onClick = onClick)
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary, shape )
            .padding(paddingValues),
        contentAlignment = Alignment.Center,
    ){
        Text(text = label, style = style)
    }
}