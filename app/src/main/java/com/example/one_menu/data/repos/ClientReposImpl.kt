package com.example.one_menu.data.repos

import android.content.Context
import android.net.Uri
import com.example.one_menu.data.data_source.ClientApi
import com.example.one_menu.data.model.profile.ProfileClientBody
import com.example.one_menu.data.model.profile.UpdateDataProfileBody.Companion.fromModelToUpdateDataProfileBody
import com.example.one_menu.domain.model.profile.ProfileClientModel
import com.example.one_menu.domain.model.profile.UpdateDataProfileModel
import com.example.one_menu.domain.repos.ClientRepos
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.call.body
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ClientReposImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val clientApi: ClientApi,
): ClientRepos {
    override suspend fun uploadImage(imageUrl: String) {
        withContext(Dispatchers.IO){
            val stream = context.contentResolver.openInputStream(Uri.parse(imageUrl))!!
            clientApi.uploadImage(stream.readBytes())
            stream.close()
        }
    }

    override suspend fun getProfile(): ProfileClientModel {
        return clientApi.getProfile().body<ProfileClientBody>()
    }

    override suspend fun changeDataProfile(updateDataProfileModel: UpdateDataProfileModel) {
        clientApi.updateProfile(updateDataProfileModel.fromModelToUpdateDataProfileBody())
    }
}