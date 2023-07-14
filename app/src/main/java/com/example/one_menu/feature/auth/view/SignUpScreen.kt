package com.example.one_menu.feature.auth.view

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.goodsaccounting.common.view.click.alphaClick
import com.example.one_menu.R
import com.example.one_menu.feature.auth.model.sign_up.SignUpEvent
import com.example.one_menu.feature.auth.model.sign_up.SignUpSideEffect
import com.example.one_menu.feature.auth.view_model.SignUpViewModel
import com.example.one_menu.feature.common.view.button.CustomButton
import com.example.one_menu.feature.common.view.text_field.CustomTextField
import com.example.one_menu.feature.common.view.theme.textHintColor
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    openVerificationCode: () -> Unit,
) {
    val content = LocalContext.current
    val state by signUpViewModel.collectAsState()
    signUpViewModel.collectSideEffect{sideEffect->
        when(sideEffect){
            is SignUpSideEffect.Message -> Toast
                .makeText(content, sideEffect.text, Toast.LENGTH_SHORT)
                .show()
            SignUpSideEffect.VerificationIDCreated -> openVerificationCode()
        }
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let { uriIsNotNull ->
                signUpViewModel.onEvent(SignUpEvent.SelectedImage(uriIsNotNull.toString()))
            }
        }
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .imePadding()
            .padding(top = 40.dp)
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.registration),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .alphaClick { launcher.launch("image/*") }
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .background(Color(0xFFF8F8F8), MaterialTheme.shapes.medium)
                .padding(16.dp)
        ) {
            GlideImage(
                imageModel = { state.image ?: R.drawable.im_profile_empty },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(52.dp)
                    .clip(
                        if (state.image == null) RoundedCornerShape(0.dp) else MaterialTheme.shapes.large
                    ),
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                )
            )
            Text(
                text = stringResource(R.string.choose_photo),
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Center),
                color = textHintColor.copy(0.6f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomTextField(
                value = state.name,
                onValueChange = {signUpViewModel.onEvent(SignUpEvent.InputName(it))},
                label = {
                    Text(
                        text = stringResource(R.string.name),
                        fontSize = 16.sp,
                        color = textHintColor.copy(0.4f)
                    )
                },
                isError = state.isErrorName,
            )
            CustomTextField(
                value = state.surname,
                onValueChange = {signUpViewModel.onEvent(SignUpEvent.InputSurname(it))},
                label = {
                    Text(
                        text = stringResource(R.string.surname),
                        fontSize = 16.sp,
                        color = textHintColor.copy(0.4f)
                    )
                },
                isError = state.isErrorSurname
            )
            CustomTextField(
                value = state.phoneNumber,
                onValueChange = {signUpViewModel.onEvent(SignUpEvent.InputPhone(it))},
                label = {
                    Text(
                        text = stringResource(R.string.phone_number),
                        fontSize = 16.sp,
                        color = textHintColor.copy(0.4f)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                ),
                isError = state.isErrorPhone,
            )
            CustomTextField(
                value = state.email,
                onValueChange = {signUpViewModel.onEvent(SignUpEvent.InputEmail(it))},
                label = {
                    Text(
                        text = stringResource(R.string.email),
                        fontSize = 16.sp,
                        color = textHintColor.copy(0.4f)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                ),
                isError = state.isErrorEmail,
            )
            CustomTextField(
                value = state.password,
                onValueChange = {signUpViewModel.onEvent(SignUpEvent.InputPassword(it))},
                label = {
                    Text(
                        text = stringResource(R.string.password),
                        fontSize = 16.sp,
                        color = textHintColor.copy(0.4f)
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                isError = state.isErrorPassword,
            )
            CustomTextField(
                value = state.country,
                onValueChange = {signUpViewModel.onEvent(SignUpEvent.InputCountry(it))},
                label = {
                    Text(
                        text = stringResource(R.string.country),
                        fontSize = 16.sp,
                        color = textHintColor.copy(0.4f)
                    )
                },
                isError = state.isErrorCountry,
            )
            CustomTextField(
                value = state.city,
                onValueChange = {signUpViewModel.onEvent(SignUpEvent.InputCity(it))},
                label = {
                    Text(
                        text = stringResource(R.string.city),
                        fontSize = 16.sp,
                        color = textHintColor.copy(0.4f)
                    )
                },
                isError = state.isErrorCity,
            )
        }
        CustomButton(
            label = stringResource(R.string.continue1),
            modifier = Modifier.padding(vertical = 20.dp),
            onClick = {
                signUpViewModel.onEvent(SignUpEvent.CreateAccount)
            }
        )
    }
}