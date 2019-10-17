package pl.kfert.movie.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * A {@see RequestInterceptor} that adds an auth token to requests
 */
class AuthInterceptor(private val apiKey: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .url(request.url().newBuilder()
                .addQueryParameter(API_KEY, apiKey)
                .build())
            .method(request.method(), request.body())
            .build()

        return chain.proceed(newRequest)
    }

    companion object
    {
        const val API_KEY = "api_key"
    }
}
