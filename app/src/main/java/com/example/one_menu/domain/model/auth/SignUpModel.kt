package com.example.one_menu.domain.model.auth

interface SignUpModel {
    val name: String
    val surname: String
    val email: String
    val phoneNumber : String
    val password: String
    val country: String
    val city: String
}