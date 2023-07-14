package com.example.one_menu.feature.common.view_model

import com.example.one_menu.feature.common.model.mvi.UIEvent

interface EventBase<T: UIEvent> {
    fun onEvent(event: T)
}