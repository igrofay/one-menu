package com.example.one_menu.domain.repos

import com.example.one_menu.domain.model.profile.ProfileClientModel
import com.example.one_menu.domain.model.profile.UpdateDataProfileModel

interface ClientRepos {
    suspend fun uploadImage(imageUrl: String)
    suspend fun getProfile() : ProfileClientModel
    suspend fun changeDataProfile(updateDataProfileModel: UpdateDataProfileModel)
}