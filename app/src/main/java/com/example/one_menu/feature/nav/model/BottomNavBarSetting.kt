package com.example.one_menu.feature.nav.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
class BottomNavBarSetting private constructor(
    visibleBottomBar: Boolean = true,
    useSafePadding: Boolean = false,
    bottomPaddingValues: Dp = 0.dp
){
    private var _visibleBottomBar by mutableStateOf(visibleBottomBar)
    private var _useSafePadding by mutableStateOf(useSafePadding)
    private var _bottomPaddingValues by mutableStateOf(bottomPaddingValues)

    val visibleBottomBar get() = _visibleBottomBar

    val useSafePadding get() = _useSafePadding

    val bottomPaddingValues get() = _bottomPaddingValues

    fun show(){
        _visibleBottomBar = true
    }
    fun hide(){
        _visibleBottomBar = false
    }

    fun onPadding() {
        _useSafePadding = true
    }

    fun offPadding(){
        _useSafePadding = false
    }

    fun offAll(){
        hide()
        offPadding()
    }
    fun onAll(){
        show()
        onPadding()
    }

    fun messageBottomPadding(padding: Dp){
        _bottomPaddingValues = padding
    }
    companion object{
        @Composable
        fun rememberBottomNavBarSetting() : BottomNavBarSetting{
            return rememberSaveable(saver = Saver) {
                BottomNavBarSetting()
            }
        }
        private val Saver: Saver<BottomNavBarSetting, *> = listSaver(
            save = { listOf(it._visibleBottomBar, it._useSafePadding, it._bottomPaddingValues.value) },
            restore = {
                BottomNavBarSetting(
                    visibleBottomBar = it[0] as Boolean,
                    useSafePadding = it[1] as Boolean,
                    bottomPaddingValues = Dp(it[2] as Float),
                )
            }
        )
    }
}