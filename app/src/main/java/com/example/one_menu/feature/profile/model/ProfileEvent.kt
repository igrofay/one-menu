package com.example.one_menu.feature.profile.model

import com.example.one_menu.feature.common.model.mvi.UIEvent
import java.time.LocalDate

sealed class ProfileEvent : UIEvent() {
    class SelectImage(val image: String) : ProfileEvent()
    class InputName(val name: String) : ProfileEvent()
    class InputSurname(val surname: String) : ProfileEvent()
    class InputCountry(val country: String) : ProfileEvent()
    class InputCity(val city: String) : ProfileEvent()
    class SelectBirthday(val date: LocalDate): ProfileEvent()
    object Save : ProfileEvent()
}
