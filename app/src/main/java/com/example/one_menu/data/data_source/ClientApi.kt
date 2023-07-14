package com.example.one_menu.data.data_source

import com.example.one_menu.data.model.profile.UpdateDataProfileBody
import com.example.one_menu.di.AuthorizedClient
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import javax.inject.Inject

class ClientApi @Inject constructor(
    @AuthorizedClient
    private val client: HttpClient
) {
    suspend fun uploadImage(byteArray: ByteArray) = client.post("/client/upload_image"){
        setBody(
            MultiPartFormDataContent(formData {
                append("description", "Client Image")
                append("image", byteArray, Headers.build {
                    append(HttpHeaders.ContentType, "image/jpg")
                    append(HttpHeaders.ContentDisposition, "filename=\"restaurant_image.jpg\"")
                })
            })
        )
    }
    suspend fun getProfile() = client.get("/client/profile")
    suspend fun updateProfile(updateDataProfileBody: UpdateDataProfileBody) = client.put("/client/profile"){
        setBody(updateDataProfileBody)
        contentType(ContentType.Application.Json)
    }
}