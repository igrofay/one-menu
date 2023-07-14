package com.example.one_menu.feature.common.view.text_field

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.one_menu.feature.common.view.theme.textHintColor
import com.example.one_menu.feature.common.view.theme.textSecondaryColor
import kotlinx.coroutines.flow.collect

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String)->Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        color = textSecondaryColor,
        fontWeight = FontWeight.Bold,
    ),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    label: @Composable (() -> Unit)? = null,
    leftIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    readOnly: Boolean = false,
    enabled: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var isFocus by remember {
        mutableStateOf(false)
    }
    val animColor by animateColorAsState(
        targetValue =  if (isError) MaterialTheme.colors.error else
            if (isFocus) MaterialTheme.colors.primary else Color(0xFFEEEEEE),
        animationSpec = tween(
            durationMillis = 300,
        )
    )
    val animBorder by animateDpAsState(
        targetValue = if (isFocus) 2.dp else 1.dp,
        animationSpec = tween(
            durationMillis = 300,
        )
    )
    LaunchedEffect(interactionSource){
        interactionSource.interactions.collect{
            when(it){
                is FocusInteraction.Focus ->{
                    isFocus = true
                }
                is FocusInteraction.Unfocus ->{
                    isFocus = false
                }
            }
        }
    }
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        interactionSource = interactionSource,
        textStyle = textStyle,
        modifier = modifier
            .fillMaxWidth()
            .border(animBorder, animColor, MaterialTheme.shapes.small)
            .padding(vertical = 18.dp, horizontal = 16.dp),
        cursorBrush = SolidColor(MaterialTheme.colors.primary),
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        readOnly= readOnly,
        enabled = enabled,
    ){ innerTextField ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier.weight(1f)
            ){
                if (label != null && value.isBlank()){
                    label()
                }
                innerTextField()
            }
            leftIcon?.let {innerLeftIcon ->
                Spacer(modifier = Modifier.width(4.dp))
                innerLeftIcon()
            }
        }

    }
}