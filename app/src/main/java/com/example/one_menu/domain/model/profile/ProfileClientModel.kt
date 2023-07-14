package com.example.one_menu.domain.model.profile

interface ProfileClientModel {
    val id: Int
    val image: String?
    val name: String
    val surname: String
    val country: String
    val city: String
    val birthday: String?
}