package com.example.one_menu.feature.common.view_model

import androidx.lifecycle.ViewModel
import com.example.one_menu.feature.common.model.mvi.UIEvent
import com.example.one_menu.feature.common.model.mvi.UISideEffect
import com.example.one_menu.feature.common.model.mvi.UIState
import org.orbitmvi.orbit.ContainerHost

abstract class AppVM  <S: UIState,SF: UISideEffect, E: UIEvent>
    : ViewModel(), ContainerHost<S, SF>, EventBase<E> {
    protected abstract fun onError(error: Throwable)
}