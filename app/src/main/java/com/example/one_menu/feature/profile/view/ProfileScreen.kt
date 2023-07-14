package com.example.one_menu.feature.profile.view

import android.net.Uri
import android.util.Log
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.goodsaccounting.common.view.click.alphaClick
import com.example.one_menu.R
import com.example.one_menu.feature.common.view.button.CustomButton
import com.example.one_menu.feature.common.view.text_field.CustomTextField
import com.example.one_menu.feature.common.view.theme.textHintColor
import com.example.one_menu.feature.common.view.theme.textPrimaryColor
import com.example.one_menu.feature.nav.view.LocalBottomNavBarSetting
import com.example.one_menu.feature.profile.model.ProfileEvent
import com.example.one_menu.feature.profile.model.ProfileSideEffect
import com.example.one_menu.feature.profile.view_model.ProfileViewModel
import com.skydoves.landscapist.glide.GlideImage
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect


@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    val bottomNavBarSetting = LocalBottomNavBarSetting.current
    LaunchedEffect(Unit) {
        bottomNavBarSetting?.onPadding()
    }
    val context = LocalContext.current
    val state by profileViewModel.collectAsState()
    profileViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ProfileSideEffect.Message -> Toast
                .makeText(context, sideEffect.text, Toast.LENGTH_SHORT)
                .show()
        }
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let { uriIsNotNull ->
                profileViewModel.onEvent(ProfileEvent.SelectImage(uriIsNotNull.toString()))
            }
        }
    )
    val dialogState = rememberMaterialDialogState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
//            .imePadding()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.my_profile),
            color = textPrimaryColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(22.dp))
        Box(
            modifier = Modifier
                .alphaClick { launcher.launch("image/*") }
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .background(Color(0xFFF8F8F8), MaterialTheme.shapes.medium)
                .padding(16.dp)
        ) {
            GlideImage(
                imageModel = {
                    (if (state.isEdit) state.editImage else state.image) ?: R.drawable.im_profile_empty
                },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(52.dp)
                    .clip(
                        if ((if (state.isEdit) state.editImage else state.image) == null)
                            RoundedCornerShape(0.dp)
                        else MaterialTheme.shapes.large
                    ),
            )
            Text(
                text = stringResource(R.string.edit_photo),
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Center),
                color = textHintColor.copy(0.6f)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            CustomTextField(
                value = if (state.isEdit) state.editName else state.name,
                onValueChange = {
                    profileViewModel.onEvent(ProfileEvent.InputName(it))
                },
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
                value = if (state.isEdit) state.editSurname else state.surname,
                onValueChange = {
                    profileViewModel.onEvent(ProfileEvent.InputSurname(it))
                },
                label = {
                    Text(
                        text = stringResource(R.string.surname),
                        fontSize = 16.sp,
                        color = textHintColor.copy(0.4f)
                    )
                },
                isError = state.isErrorSurname,
            )
            CustomTextField(
                value = if (state.isEdit) state.editCountry else state.country,
                onValueChange = {
                    profileViewModel.onEvent(ProfileEvent.InputCountry(it))
                },
                label = {
                    Text(
                        text = stringResource(R.string.country),
                        fontSize = 16.sp,
                        color = textHintColor.copy(0.4f)
                    )
                }
            )
            CustomTextField(
                value = if (state.isEdit) state.editCity else state.city,
                onValueChange = {
                    profileViewModel.onEvent(ProfileEvent.InputCity(it))
                },
                label = {
                    Text(
                        text = stringResource(R.string.city),
                        fontSize = 16.sp,
                        color = textHintColor.copy(0.4f)
                    )
                }
            )
            CustomTextField(
                value = (if (state.isEdit) state.editBirthday else state.birthday) ?: "",
                onValueChange = {},
                label = {
                    Text(
                        text = stringResource(R.string.date_of_birth),
                        fontSize = 16.sp,
                        color = textHintColor.copy(0.4f)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                ),
                modifier = Modifier.alphaClick {
                    dialogState.show()
                },
                readOnly = true,
                enabled = false
            )
        }
        Spacer(
            modifier = Modifier
                .heightIn(min = 20.dp)
        )
        CustomButton(label = stringResource(R.string.save), enabled = state.isEdit ) {
            profileViewModel.onEvent(ProfileEvent.Save)
        }
        Spacer(modifier = Modifier.height(20.dp))
        MaterialDialog(
            dialogState = dialogState,
            buttons = {
                positiveButton("Ok")
                negativeButton("Cancel")
            },
        ) {
            datepicker(
                yearRange = IntRange(1971, 2100),
            ) { date ->
                profileViewModel.onEvent(ProfileEvent.SelectBirthday(date))
            }
        }
    }
}