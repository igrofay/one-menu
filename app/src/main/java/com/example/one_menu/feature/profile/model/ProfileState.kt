package com.example.one_menu.feature.profile.model

import com.example.one_menu.domain.model.profile.ProfileClientModel
import com.example.one_menu.domain.model.profile.UpdateDataProfileModel
import com.example.one_menu.feature.common.model.mvi.UIState

data class ProfileState(
    override val id: Int = -1,
    override val image: String? = null,
    override val name: String = "",
    override val surname: String = "",
    override val country: String = "",
    override val city: String = "",
    override val birthday: String? = null,
    val isEdit: Boolean = false,
    val editImage: String? = image,
    val editName: String = name,
    val isErrorName: Boolean = false,
    val editSurname: String = surname,
    val isErrorSurname: Boolean = false,
    val editBirthday: String? = birthday,
    val editCountry: String = country,
    val isErrorCountry: Boolean = false,
    val editCity: String = city,
    val isErrorCity: Boolean = false,
) : UIState(), ProfileClientModel{
    companion object{
        fun ProfileClientModel.fromModelToProfileState() = ProfileState(
            id, image, name, surname, country, city, birthday
        )
    }
    fun updateDataProfile() = object : UpdateDataProfileModel{
        override val name: String = editName.trim()
        override val surname: String = editSurname.trim()
        override val birthday: String? = editBirthday?.trim()
        override val country: String = editCountry.trim()
        override val city: String = editCity.trim()
    }
}