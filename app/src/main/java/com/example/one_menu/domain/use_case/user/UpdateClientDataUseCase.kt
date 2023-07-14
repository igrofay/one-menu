package com.example.one_menu.domain.use_case.user

import com.example.one_menu.domain.model.profile.UpdateDataProfileModel
import com.example.one_menu.domain.repos.ClientRepos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateClientDataUseCase @Inject constructor(
    private val clientRepos: ClientRepos,
) {
    suspend fun execute(image: String?, updateDataProfileModel: UpdateDataProfileModel) = runCatching {
        withContext(Dispatchers.IO){
            launch { clientRepos.changeDataProfile(updateDataProfileModel) }.start()
            image?.let {
                launch { clientRepos.uploadImage(it) }.start()
            }
        }
    }
}