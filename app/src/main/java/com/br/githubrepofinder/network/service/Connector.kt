package com.br.githubrepofinder.network.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Connector : InterceptorManager() {

    lateinit var client: OkHttpClient

    companion object {
        const val timeout: Long = 30
    }

    init {
        client = buildClient()
    }

    private fun buildClient() = OkHttpClient.Builder().let { builder ->
        builder.connectTimeout(timeout, TimeUnit.SECONDS)
        builder.readTimeout(timeout, TimeUnit.SECONDS)
        builder.writeTimeout(timeout, TimeUnit.SECONDS)
        builder.addInterceptor(interceptor())
        return@let builder.build()
    }

    fun <T> request(url: String, service: Class<T>): T{
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(service)
    }

}