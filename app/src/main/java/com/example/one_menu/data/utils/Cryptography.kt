package com.example.one_menu.data.utils

import java.security.MessageDigest

internal fun String.toSha256(): String {
    val md = MessageDigest.getInstance("SHA-256")
    val byteArray = md.digest(this.toByteArray())
    return byteArray.joinToString("") { "%02x".format(it) }
}