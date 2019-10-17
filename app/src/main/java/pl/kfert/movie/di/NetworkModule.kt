package pl.kfert.movie.di

import android.content.Context
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import pl.kfert.movie.BuildConfig
import pl.kfert.movie.api.ApiService
import pl.kfert.movie.api.AuthInterceptor
import pl.kfert.movie.api.MockApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { createOkHttpCache(get()) }
    single(named("logging")) { createLoggingInterceptor() }
    single(named("header")) { AuthInterceptor(BuildConfig.API_KEY) }
    single { createOkHttpClient(get(named("logging")), get(named("header"))) }
    single { createAppRetrofit(get()) }
    single { createApiService(get(), get(), get()) }
    single { Mock(BuildConfig.MOCK_DATA) }
    single { MockApi() }
}

const val TIMEOUT = 10

fun createOkHttpCache(context: Context): Cache =
    Cache(context.cacheDir, (10 * 1024 * 1024).toLong())

fun createLoggingInterceptor(): Interceptor =
    HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
    }

fun createOkHttpClient(
    logging: Interceptor,
    header: Interceptor
): OkHttpClient =
    OkHttpClient.Builder()
        .connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        .readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        .addInterceptor(header)
        .addInterceptor(logging)
        .build()

fun createAppRetrofit(
    okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpClient)
        .build()


fun createApiService(retrofit: Retrofit, mockApi: MockApi, mock: Mock): ApiService =
    if (mock.isMock) mockApi
    else retrofit.create(ApiService::class.java)

class Mock(val isMock: Boolean)