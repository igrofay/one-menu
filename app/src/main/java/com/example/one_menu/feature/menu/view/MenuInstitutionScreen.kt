package com.example.one_menu.feature.menu.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goodsaccounting.common.view.click.alphaClick
import com.example.one_menu.R
import com.example.one_menu.domain.model.menu.MenuCategoryModel
import com.example.one_menu.domain.model.menu.MenuDishModel
import com.example.one_menu.domain.model.menu.RestaurantModel
import com.example.one_menu.feature.nav.view.LocalBottomNavBarSetting
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch
import java.util.Currency


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MenuInstitutionScreen(
    restaurantModel : RestaurantModel,
    listCategory: List<MenuCategoryModel>,
    dishes: Map<Int, List<MenuDishModel>>,
    back: () -> Unit,
    openProduct: (MenuDishModel)->Unit
) {
    val bottomNavBarSetting = LocalBottomNavBarSetting.current
    LaunchedEffect(Unit) {
        bottomNavBarSetting?.offPadding()
    }
    Column {
        Box(
            modifier = Modifier
                .padding(top = 38.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 25.dp)
                    .alphaClick(onClick = back)
                    .size(45.dp)
                    .border(2.dp, MaterialTheme.colors.primary, MaterialTheme.shapes.large),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp),
                    tint = Color.Black
                )
            }
            Text(
                text = restaurantModel.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Text(
            text = stringResource(R.string.find_by_category),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 20.dp, top = 26.dp, bottom = 20.dp)
        )
        val pagerState = rememberPagerState()
        val localScope = rememberCoroutineScope()
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(listCategory) { index, item ->
                val animColorBack by animateColorAsState(
                    targetValue = if (index == pagerState.currentPage)
                        MaterialTheme.colors.primary
                    else Color.Transparent
                )
                Text(
                    text = item.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .alphaClick {
                            localScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                        .background(animColorBack, MaterialTheme.shapes.medium)
                        .border(2.dp, MaterialTheme.colors.primary, MaterialTheme.shapes.medium)
                        .padding(horizontal = 15.dp, vertical = 8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalPager(
            pageCount = listCategory.size,
            state = pagerState,
            modifier = Modifier.padding(
                bottom = bottomNavBarSetting?.bottomPaddingValues ?: 0.dp
            )
        ) {
            val category = listCategory[it]
            ListProduct(
                products = dishes[category.id] ?: listOf(),
                open = openProduct
            )
        }
    }
}

@Composable
private fun ListProduct(products: List<MenuDishModel>, open: (MenuDishModel)->Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(products){ product ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colors.background, MaterialTheme.shapes.medium)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable {
                        open(product)
                    }
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 8.dp, top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 15.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    for (i in 0 until product.spicinessLevel) {
                        Image(
                            painter = painterResource(R.drawable.im_hot),
                            contentDescription = null,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
                GlideImage(
                    imageModel = { product.image },
                    modifier = Modifier
                        .height(70.dp)
                        .fillMaxWidth(),
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.FillHeight,
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = product.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = product.components,
                    fontSize = 12.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                val splitPrice = remember(product.price) {
                    product.price.toString().split('.')
                }
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)){
                            append("${Currency.getInstance(product.currency).symbol} ${splitPrice.first()}.")
                        }
                        withStyle(SpanStyle(fontSize = 10.sp, fontWeight = FontWeight.Medium)){
                            append(splitPrice.last())
                        }
                    },
                    color = Color(0xFFFE8C00)
                )
            }
        }
    }
}