package com.example.one_menu.di

import android.util.Log
import com.example.one_menu.domain.repos.AppRepos
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.plugin
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthorizedClient

@Module
@InstallIn(SingletonComponent::class)
object KtorModule {
    private const val urlServer = "http://192.168.1.37:5088/"

    @Provides
    @Singleton
    @BaseClient
    fun provideBaseClient(): HttpClient{
        val client = HttpClient(CIO){
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true

                })
            }
            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 5)
                exponentialDelay()
            }
            install(HttpCache)
            expectSuccess = true
            defaultRequest{
                url(urlServer)
            }
        }
        client.plugin(HttpSend).intercept { request ->
            Log.e("BaseClient::intercept::request",request.url.toString())
            execute(request).apply {
                Log.e("BaseClient::intercept::answer",this.request.url.toString() +" "+  this.response.status.value.toString())
            }
        }
        return client
    }
    @Provides
    @Singleton
    @AuthorizedClient
    fun provideAuthorizedClient(appRepos: AppRepos): HttpClient{
        val client = HttpClient(CIO){
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true

                })
            }
            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 5)
                exponentialDelay()
            }
            install(HttpCache)
            expectSuccess = true
            defaultRequest{
                url(urlServer)
            }
        }
        client.plugin(HttpSend).intercept { request ->
            Log.e("AuthorizedClient::intercept::request",request.url.host)
            request.header(HttpHeaders.Authorization, appRepos.getToken())
            execute(request).apply {
                Log.e("AuthorizedClient::intercept::answer",this.request.url.toString() +" "+  this.response.status.value.toString())
            }
        }
        return client
    }
}