package com.example.one_menu.data.model.profile

import com.example.one_menu.domain.model.profile.UpdateDataProfileModel
import kotlinx.serialization.Serializable

@Serializable
data class UpdateDataProfileBody(
    override val name: String,
    override val surname: String,
    override val birthday: String?,
    override val country: String,
    override val city: String
) : UpdateDataProfileModel{
    companion object{
        fun UpdateDataProfileModel.fromModelToUpdateDataProfileBody() = UpdateDataProfileBody(
            name, surname, birthday, country, city
        )
    }
}