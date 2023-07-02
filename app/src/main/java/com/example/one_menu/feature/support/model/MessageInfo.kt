package com.example.one_menu.feature.support.model

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.UUID

val userId = UUID.randomUUID().toString()
data class MessageInfo(
    val message: String,
    val time: String = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
    val id: String = userId,
)