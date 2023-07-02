package com.example.one_menu.feature.auth.view

import android.net.Uri
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goodsaccounting.common.view.click.alphaClick
import com.example.one_menu.R
import com.example.one_menu.feature.common.view.button.CustomButton
import com.example.one_menu.feature.common.view.text_field.CustomTextField
import com.example.one_menu.feature.common.view.theme.textHintColor
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun SignUpScreen(
    openVerificationCode: () -> Unit,
) {
    var image by remember {
        mutableStateOf<Any>(R.drawable.im_profile_empty)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let { uriIsNotNull ->
                image = uriIsNotNull.toString()
            }
        }
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
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
                imageModel = { image },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(52.dp)
                    .clip(
                        if (image == R.drawable.im_profile_empty) RoundedCornerShape(0.dp) else MaterialTheme.shapes.large
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
                value = "",
                onValueChange = {},
                label = {
                    Text(
                        text = stringResource(R.string.name),
                        fontSize = 16.sp,
                        color = textHintColor.copy(0.4f)
                    )
                }
            )
            CustomTextField(
                value = "",
                onValueChange = {},
                label = {
                    Text(
                        text = stringResource(R.string.surname),
                        fontSize = 16.sp,
                        color = textHintColor.copy(0.4f)
                    )
                }
            )
            CustomTextField(
                value = "",
                onValueChange = {},
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
            )
            CustomTextField(
                value = "",
                onValueChange = {},
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
            )
            CustomTextField(
                value = "",
                onValueChange = {},
                label = {
                    Text(
                        text = stringResource(R.string.country),
                        fontSize = 16.sp,
                        color = textHintColor.copy(0.4f)
                    )
                }
            )
            CustomTextField(
                value = "",
                onValueChange = {},
                label = {
                    Text(
                        text = stringResource(R.string.city),
                        fontSize = 16.sp,
                        color = textHintColor.copy(0.4f)
                    )
                }
            )
        }
        CustomButton(
            label = stringResource(R.string.continue1),
            modifier = Modifier.padding(vertical = 20.dp),
            onClick = openVerificationCode
        )
    }
}