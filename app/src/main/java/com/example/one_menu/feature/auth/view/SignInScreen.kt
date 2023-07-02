package com.example.one_menu.feature.auth.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goodsaccounting.common.view.click.alphaClick
import com.example.one_menu.R
import com.example.one_menu.feature.common.view.button.CustomButton
import com.example.one_menu.feature.common.view.text_field.CustomTextField
import com.example.one_menu.feature.common.view.theme.textHintColor
import com.example.one_menu.feature.common.view.theme.textHintColor2
import com.example.one_menu.feature.common.view.theme.textSecondaryColor



@Composable
fun SignInScreen(
    openContent: ()->Unit,
    openRegistration: () -> Unit,
) {
    var emailOrPhone by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
            .padding(top = 60.dp, bottom = 45.dp)
    ) {
        Text(
            text = "${stringResource(R.string.welcome)}!",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = textSecondaryColor,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.lets_sign_you_in),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = textHintColor2
        )
        Spacer(modifier = Modifier.weight(1f))
        val keyboardOptionsEmailOrPassword = remember(emailOrPhone.isBlank()) {
            if (emailOrPhone.contains(Regex("^[0-9+]+\$"))) {
                KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                )
            } else KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        }
        CustomTextField(
            value = emailOrPhone,
            onValueChange = { emailOrPhone = it },
            label = {
                Text(
                    text = stringResource(R.string.email_or_phone),
                    fontSize = 16.sp,
                    color = textHintColor.copy(0.4f)
                )
            },
            keyboardOptions = keyboardOptionsEmailOrPassword
        )
        Spacer(modifier = Modifier.height(16.dp))
        var visiblePassword by remember {
            mutableStateOf(true)
        }
        val keyboardOptionsPassword = remember(visiblePassword) {
            if (visiblePassword)
                PasswordVisualTransformation()
            else
                VisualTransformation.None
        }
        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(
                    text = stringResource(R.string.type_your_password),
                    fontSize = 16.sp,
                    color = textHintColor.copy(0.4f)
                )
            },
            visualTransformation = keyboardOptionsPassword,
            leftIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_tabler_eye_off),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .alphaClick {
                            visiblePassword = !visiblePassword
                        },
                    tint = Color(0xFF8C919D),
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${stringResource(R.string.forgot_your_password)}?",
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier
                .alphaClick { }
        )
        Spacer(modifier = Modifier.height(37.dp))
        CustomButton(label = stringResource(R.string.login), onClick = openContent)
        Spacer(modifier = Modifier.height(16.dp))
        GoogleButton()
        Spacer(modifier = Modifier.height(40.dp))
        val annotatedText =buildAnnotatedString {
            withStyle(SpanStyle(color = textHintColor2)) {
                append("${stringResource(R.string.dont_have_account)}? ")
            }
            pushStringAnnotation(
                tag = "SignUp", annotation = ""
            )
            withStyle(SpanStyle(color = MaterialTheme.colors.primary)) {
                append(stringResource(R.string.sign_up))
            }
            pop()
        }
        ClickableText(
            text = annotatedText,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier.fillMaxWidth(),
            onClick = {offset->
                annotatedText.getStringAnnotations(
                    tag = "SignUp", start = offset, end = offset
                ).firstOrNull()?.let {
                    openRegistration()
                }
            }
        )
    }
}