package com.task.data.util

import com.task.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.annotation.Single

@Single
class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header("Authorization", "Bearer ${BuildConfig.AUTH_TOKEN}")

        val request = requestBuilder.build()
        return chain.proceed(request)

    }
}
