package com.example.one_menu.feature.nav.view

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsIgnoringVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsIgnoringVisibility
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goodsaccounting.common.view.click.alphaClick
import com.example.goodsaccounting.common.view.click.scaleClick
import com.example.one_menu.R
import com.example.one_menu.feature.nav.model.MainRouting

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainBottomNavBar(
    routes: List<MainRouting>,
    onClickItem: (route: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val animationPaddingValues by animateDpAsState(
        targetValue = WindowInsets.systemBarsIgnoringVisibility.asPaddingValues().calculateBottomPadding()
    )
    Row(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .padding(bottom = 20.dp + animationPaddingValues)
            .padding()
            .fillMaxWidth()
            .clip(RoundedCornerShape(25.dp))
            .background(Color(0xFF333333), RoundedCornerShape(25.dp))
            .padding(vertical = 10.dp, horizontal = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        for (route in routes) {
            when (route) {
                MainRouting.Menu -> ItemMenu {
                    onClickItem(route.route)
                }
                MainRouting.Profile -> ItemDefault(
                    icon = R.drawable.ic_profile,
                    text = R.string.my_profile
                ) {
                    onClickItem(route.route)
                }
                MainRouting.Support -> ItemDefault(
                    icon = R.drawable.ic_support,
                    text = R.string.support
                ) {
                    onClickItem(route.route)
                }
            }
        }
    }
}

@Composable
fun ItemMenu(
    onClick: () -> Unit,
) {
    Text(
        text = stringResource(R.string.menu).uppercase(),
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = Modifier
            .clip(CircleShape)
            .scaleClick(onClick = onClick)
            .background(MaterialTheme.colors.primary, CircleShape)
            .padding(vertical = 15.dp, horizontal = 12.dp)
    )
}

@Composable
private fun ItemDefault(
    @DrawableRes icon: Int,
    @StringRes text: Int,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier
            .alphaClick(onClick = onClick)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(30.dp),
            tint = Color(0xFFD9D9D9)
        )
        Text(
            text = stringResource(text),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFD9D9D9)
        )
    }
}