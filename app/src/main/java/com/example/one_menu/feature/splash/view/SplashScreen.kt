package com.example.one_menu.feature.splash.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.one_menu.R
import com.example.one_menu.feature.common.view.theme.textSecondaryColor
import com.example.one_menu.feature.splash.model.SplashSideEffect
import com.example.one_menu.feature.splash.view_model.SplashViewModel
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = hiltViewModel(),
    goToAuth: ()-> Unit,
    goToMainContent: ()->Unit,
) {
    splashViewModel.collectSideEffect{sideEffect->
        when(sideEffect){
            SplashSideEffect.Authorized -> goToMainContent()
            SplashSideEffect.NeedToSignIn -> goToAuth()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center,
    ){
        Text(
            text = stringResource(R.string.app_name).uppercase(),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = textSecondaryColor,
        )
    }
}