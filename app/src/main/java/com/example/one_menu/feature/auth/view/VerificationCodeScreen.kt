package com.example.one_menu.feature.auth.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goodsaccounting.common.view.click.scaleClick
import com.example.one_menu.R
import com.example.one_menu.feature.common.view.button.CustomButton
import com.example.one_menu.feature.common.view.theme.textHintColor2

@Composable
fun VerificationCodeScreen(
    openContent: () -> Unit,
    back: ()->Unit,
) {
    Column(
        modifier = Modifier
            .padding(top = 50.dp)
            .padding(horizontal = 24.dp)
            .imePadding()
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .scaleClick(onClick = back)
                .size(46.dp)
                .border(1.dp, Color(0xFFF3F3F3), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_left_arrow),
                contentDescription = null,
                modifier = Modifier
                    .size(22.dp),
                tint = Color(0xFF191D31)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.verification_code),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        color = Color(0xFFA7A9B7),
                    )
                ) {
                    append(stringResource(R.string.we_have_sent_code_verification_to_your_number))
                }
                withStyle(
                    SpanStyle(
                        color = Color(0xFF1D272F),
                        fontWeight = FontWeight.Medium
                    )
                ) {
                    append(" +01 65841542265")
                }
            },
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.height(30.dp))
        val items = remember {
            mutableStateListOf("", "", "", "")
        }
        val focus = remember {
            listOf(FocusRequester(), FocusRequester(), FocusRequester(), FocusRequester())
        }
        LaunchedEffect(Unit){
            focus.firstOrNull()?.requestFocus()
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            for (index in items.indices) {
                var isFocus by remember {
                    mutableStateOf(false)
                }
                val interactionSource = remember { MutableInteractionSource() }
                val animColor by animateColorAsState(
                    targetValue = if (isFocus) Color(0xFF1D272F) else Color.Transparent
                )
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect {
                        when (it) {
                            is FocusInteraction.Focus -> {
                                isFocus = true
                            }

                            is FocusInteraction.Unfocus -> {
                                isFocus = false
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .height(59.dp)
                        .width(66.dp)
                        .border(1.dp, animColor, MaterialTheme.shapes.medium)
                        .background(Color(0xFFF8F9FB), MaterialTheme.shapes.medium),
                    contentAlignment = Alignment.Center
                ) {
                    BasicTextField(
                        value = items[index],
                        onValueChange = {
                            items[index] = it.lastOrNull()?.toString()?.also {
                                focus.getOrNull(index + 1)?.requestFocus()
                            } ?: "".also {
                                focus.getOrNull(index - 1)?.requestFocus()
                            }

                        },
                        interactionSource = interactionSource,
                        modifier = Modifier.focusRequester(focus[index]),
                        textStyle = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword
                        )
                    )
                }

            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "2:39",
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
            color = Color(0xFF191D31),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.weight(1f))
        CustomButton(
            label = stringResource(R.string.submit),
            shape = CircleShape,
            onClick = openContent
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        color = Color(0xFFA7A9B7)
                    )
                ) {
                    append("${stringResource(R.string.didnt_receive_code)}? ")
                }
                withStyle(
                    SpanStyle(
                        color = Color(0xFF1D272F),
                        fontWeight = FontWeight.Medium,
                    )
                ) {
                    append(stringResource(R.string.resend))
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(15.dp))
    }
}