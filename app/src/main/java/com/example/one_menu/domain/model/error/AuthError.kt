package com.example.one_menu.domain.model.error

sealed class AuthError : Error(){
    object InvalidConfirmationCode : AuthError()
    object PhoneNumberRegistered : AuthError()
    object AccountNotFound : AuthError()
    object WrongPassword : AuthError()
    object EmailRegistered: AuthError()
}
