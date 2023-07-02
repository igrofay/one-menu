package com.example.one_menu.feature.menu.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goodsaccounting.common.view.click.alphaClick
import com.example.one_menu.R
import com.example.one_menu.feature.common.view.button.CustomButton
import com.example.one_menu.feature.nav.view.LocalBottomNavBarSetting

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InformationAboutInstitutionScreen(
    next: ()->Unit,
    back: () -> Unit,
) {
    val bottomNavBarSetting = LocalBottomNavBarSetting.current
    LaunchedEffect(Unit) {
        bottomNavBarSetting?.onPadding()
    }
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val maxHeight = this.maxHeight
        var heightInfo by remember {
            mutableStateOf(maxHeight/2)
        }
        val density = LocalDensity.current
        BottomSheetScaffold(
            sheetContent = {
                Column(
                    modifier = Modifier
                        .onGloballyPositioned {
                            heightInfo = with(density){ it.size.height.toDp() }
                        }
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Spacer(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 15.dp)
                            .height(5.dp)
                            .width(58.dp)
                            .background(Color.Black.copy(0.1f), CircleShape)
                    )
                    Spacer(modifier = Modifier.height(38.dp))
                    Text(
                        text = "Plov Lounge",
                        fontSize = 27.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Most whole Alaskan Red King Crabs get broken down into legs, claws, and lump meat. We offer all of these options as well in our online shop, but there is nothing like getting the whole . . . .",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    Spacer(modifier = Modifier.height(38.dp))
                    CustomButton(label = stringResource(R.string.view_menu), onClick = next)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            },
            sheetPeekHeight = heightInfo,
            sheetBackgroundColor = MaterialTheme.colors.background,
            sheetShape = RoundedCornerShape(topEnd = 25.dp, topStart = 25.dp),
        ) {
            Box {
                Image(
                    painter = painterResource(R.drawable.im_plov_lounge),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .height(maxHeight / 1.5f),
                    contentScale = ContentScale.Crop,
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 25.dp, top = 38.dp)
                        .alphaClick(onClick = back)
                        .size(45.dp)
                        .background(Color.White.copy(0.3f), MaterialTheme.shapes.large),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp),
                        tint = Color.White
                    )
                }
            }
        }


    }
}