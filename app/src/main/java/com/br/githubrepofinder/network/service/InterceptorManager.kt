package com.br.githubrepofinder.network.service

import android.util.Log
import com.br.githubrepofinder.network.service.Constants.MEDIA_TYPE
import okhttp3.Interceptor
import okhttp3.Request

abstract class InterceptorManager {

    fun interceptor(): Interceptor =
        Interceptor { chain ->
            val reqBuilder = chain.request().newBuilder()
            setupBuilder(reqBuilder)

            val request = reqBuilder.build()
            val response = chain.proceed(request)
            checkResponseCode(response.code)
            return@Interceptor response
        }

    private fun checkResponseCode(code: Int) {
        if (code != 200) Log.e("ResponseCode", "$code")
    }

    private fun setupBuilder(builder: Request.Builder) =
        builder.header("Content-Type", MEDIA_TYPE)
}