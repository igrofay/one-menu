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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.one_menu.feature.menu.model.ProductInfo
import com.example.one_menu.feature.nav.view.LocalBottomNavBarSetting
import kotlinx.coroutines.launch

val category = listOf("All View", "Pizza", "Burger")

val listPizza = listOf(
    ProductInfo(
        name = "Margarita",
        components = "600 gr dough + tomato sauce + cheese + basil",
        price = "40.00",
        previewImage = R.drawable.im_margarita,
        aboutProduct = "Margherita pizza is a classic Italian dish that features a thin crust topped with grated ripe tomato, fresh mozzarella cheese, and fragrant basil. It delights food enthusiasts worldwide with its delicate flavor. The thin crust becomes crispy during baking, complementing the tangy tomato sauce that adds a hint of zest. The melted mozzarella cheese creates a creamy texture, while the fresh basil leaves contribute aroma and freshness. All the ingredients come together in a harmonious composition, creating a perfect blend of simple yet exquisite flavors. Margherita pizza is the epitome of Italian gastronomy, enchanting palates with its tender and classic taste.",
        category = "Pizza",
        mainImage = R.drawable.im_margarita_pizza_main,
    )
)
val listBurger = listOf(
    ProductInfo(
        name = "Chicken burger",
        components = "200 gr chicken + cheese  Lettuce + tomato",
        price = "22.00",
        previewImage = R.drawable.im_chicken_burger,
        aboutProduct = "Chicken Burger - It's a flavorful burger consisting of a juicy chicken patty, creamy cheese, fresh lettuce leaves, a slice of tomato, all nestled between a soft bun. The chicken patty adds succulence and a tender taste, while the melted cheese provides a smooth texture. The crisp lettuce leaves and the slice of tomato bring freshness and a satisfying crunch. The combination of these ingredients creates a unique flavor profile that is sure to delight your taste buds.",
        category = "Burger",
        mainImage = R.drawable.im_chicken_burger_main
    )
)

val all = listBurger + listPizza

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MenuInstitutionScreen(
    back: () -> Unit,
    openProduct: (id:String)->Unit
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
                text = "Plov Lounge",
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
            itemsIndexed(category) { index, item ->
                val animColorBack by animateColorAsState(
                    targetValue = if (index == pagerState.currentPage)
                        MaterialTheme.colors.primary
                    else Color.Transparent
                )
                Text(
                    text = item,
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
            pageCount = category.size,
            state = pagerState,
            modifier = Modifier.padding(
                bottom = bottomNavBarSetting?.bottomPaddingValues ?: 0.dp
            )
        ) {
            when(it){
                0-> ListProduct(products = all, open = openProduct)
                1-> ListProduct(products = listPizza, open = openProduct)
                2-> ListProduct(products = listBurger, open = openProduct)
            }
        }
    }
}

@Composable
private fun ListProduct(products: List<ProductInfo>, open: (id: String)->Unit) {
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
                    .clickable{
                        open(product.id)
                    }
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 8.dp, top = 27.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(product.previewImage),
                    contentDescription = null,
                    modifier = Modifier
                        .height(70.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillHeight
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
                val splitPrice = remember(product.price){
                    product.price.split('.')
                }
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)){
                            append("$ ${splitPrice.first()}.")
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