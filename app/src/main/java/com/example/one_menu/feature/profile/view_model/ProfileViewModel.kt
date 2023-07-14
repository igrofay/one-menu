package com.example.one_menu.feature.profile.view_model

import androidx.lifecycle.viewModelScope
import com.example.one_menu.domain.repos.ClientRepos
import com.example.one_menu.domain.use_case.user.UpdateClientDataUseCase
import com.example.one_menu.feature.common.view_model.AppVM
import com.example.one_menu.feature.profile.model.ProfileEvent
import com.example.one_menu.feature.profile.model.ProfileSideEffect
import com.example.one_menu.feature.profile.model.ProfileState
import com.example.one_menu.feature.profile.model.ProfileState.Companion.fromModelToProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import java.time.LocalDate
import javax.inject.Inject

@OptIn(OrbitExperimental::class)
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val clientRepos: ClientRepos,
    private val updateClientDataUseCase: UpdateClientDataUseCase,
) : AppVM<ProfileState, ProfileSideEffect, ProfileEvent>() {


    override val container: Container<ProfileState, ProfileSideEffect> =
        viewModelScope.container(ProfileState()) {
            loadProfile()
        }

    override fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.InputCity -> changeCity(event.city)
            is ProfileEvent.InputCountry -> changeCountry(event.country)
            is ProfileEvent.InputName -> changeName(event.name)
            is ProfileEvent.InputSurname -> changeSurname(event.surname)
            ProfileEvent.Save -> saveChanges()
            is ProfileEvent.SelectBirthday -> changeBirthday(event.date)
            is ProfileEvent.SelectImage -> changeImage(event.image)
        }
    }

    private fun loadProfile() = intent {
        runCatching { clientRepos.getProfile() }
            .onSuccess { profile ->
                reduce { profile.fromModelToProfileState() }
            }
    }

    private fun changeImage(image: String) = intent {
        reduce {
            state.copy(
                editImage = image,
                isEdit = true
            )
        }
    }

    private fun changeName(name: String) = blockingIntent {
        if (name.length <= 50)
            reduce {
                state.copy(
                    editName = name,
                    isEdit = true
                )
            }
    }

    private fun changeSurname(surname: String) = blockingIntent {
        if (surname.length <= 50)
            reduce {
                state.copy(
                    editSurname = surname,
                    isEdit = true
                )
            }
    }

    private fun changeCountry(country: String) = blockingIntent {
        if (country.length <= 50)
            reduce {
                state.copy(
                    editCountry = country,
                    isEdit = true
                )
            }
    }

    private fun changeCity(city: String) = blockingIntent {
        if (city.length <= 80)
            reduce {
                state.copy(
                    editCity = city,
                    isEdit = true
                )
            }
    }

    private fun changeBirthday(birthday: LocalDate) = intent {
        reduce {
            state.copy(
                editBirthday = birthday.toString(),
                isEdit = true,
            )
        }
    }

    private fun saveChanges() = intent {
        val isErrorName = state.editName.isBlank()
        val isErrorSurname = state.editSurname.isBlank()
        val isErrorCountry = state.editCountry.isBlank()
        val isErrorCity = state.editCity.isBlank()
        if (
            isErrorName || isErrorSurname || isErrorCountry || isErrorCity
        ){
            reduce {
                state.copy(
                    isErrorName = isErrorName,
                    isErrorSurname = isErrorSurname,
                    isErrorCountry = isErrorCountry,
                    isErrorCity = isErrorCity,
                )
            }
        }else{
            updateClientDataUseCase.execute(
                image = if (state.editImage == state.image) null else state.editImage,
                updateDataProfileModel = state.updateDataProfile()
            ).onSuccess {
                loadProfile()
                postSideEffect(ProfileSideEffect.Message("Сохранено"))
            }.onFailure(::onError)
        }
    }


    override fun onError(error: Throwable) {
    }
}