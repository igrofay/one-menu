package com.example.one_menu.data.model.profile

import com.example.one_menu.domain.model.profile.ProfileClientModel
import kotlinx.serialization.Serializable

@Serializable
data class ProfileClientBody(
    override val id: Int,
    override val image: String?,
    override val name: String,
    override val surname: String,
    override val country: String,
    override val city: String,
    override val birthday: String?
) : ProfileClientModel
