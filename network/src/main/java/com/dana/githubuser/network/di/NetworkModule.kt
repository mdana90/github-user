package com.dana.githubuser.network.di

import android.content.Context
import com.dana.githubuser.network.BuildConfig
import com.dana.githubuser.network.UrlConstants
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val CONNECTION_TIMEOUT = 10L

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json { ignoreUnknownKeys = true }
    }

    @Provides
    @Singleton
    fun providesRetrofit(json: Json, httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(UrlConstants.BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesHttpClient(flipperInterceptor: FlipperOkhttpInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .apply { if (BuildConfig.DEBUG) addNetworkInterceptor(flipperInterceptor) }
            .build()
    }

    @Provides
    @Singleton
    fun provideFlipperInterceptor(@ApplicationContext context: Context): FlipperOkhttpInterceptor {
        val plugin = AndroidFlipperClient
            .getInstance(context)
            .getPluginByClass(NetworkFlipperPlugin::class.java)!!
        return FlipperOkhttpInterceptor(plugin)
    }
}
