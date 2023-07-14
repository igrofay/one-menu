package com.example.one_menu.feature.menu.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goodsaccounting.common.view.click.alphaClick
import com.example.one_menu.R
import com.example.one_menu.domain.model.menu.MenuDishModel
import com.example.one_menu.feature.nav.view.LocalBottomNavBarSetting
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InformationAboutProductScreen(
    product : MenuDishModel,
    back: ()->Unit
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
        BottomSheetScaffold(
            sheetContent = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Spacer(modifier = Modifier.height(38.dp))
                    Row{
                        Text(
                            text = product.name,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.weight(1f)
                        )
                        if (product.spicinessLevel != 0)
                            Row(
                                horizontalArrangement = Arrangement.End
                            ) {
                                for (i in 0 until product.spicinessLevel) {
                                    Image(
                                        painter = painterResource(R.drawable.im_hot),
                                        contentDescription = null,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    val splitPrice = remember(product.price){
                        product.price.toString().split('.')
                    }
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold)){
                                append("$ ${splitPrice.first()}.")
                            }
                            withStyle(SpanStyle(fontSize = 24.sp, fontWeight = FontWeight.Medium)){
                                append(splitPrice.last())
                            }
                        },
                        color = Color(0xFFFE8C00),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.about),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = product.description, fontSize = 14.sp,)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            },
            sheetPeekHeight = maxHeight / 1.8f,
            sheetBackgroundColor = MaterialTheme.colors.background,
            sheetShape = RoundedCornerShape(topEnd = 25.dp, topStart = 25.dp),
        ){
            Box {
                GlideImage(
                    imageModel = {product.image},
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .height(maxHeight / 2f),
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop
                    )
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