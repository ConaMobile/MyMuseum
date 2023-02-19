package com.museumonline.museummm.DI

import android.content.Context
import com.google.gson.GsonBuilder
import com.museumonline.museummm.network.ApiService
import com.museumonline.museummm.network.Contents
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.michaelrocks.paranoid.Obfuscate
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Obfuscate
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    var gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    fun provideBaseUrl() = Contents.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        BASE_URL: String,
        @ApplicationContext context: Context
    ): ApiService {

//        val interceptor = HttpLoggingInterceptor()
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val builder = OkHttpClient.Builder()

        builder.connectTimeout(20, TimeUnit.SECONDS)
//        builder.addInterceptor(interceptor)

//        val chuckerInterceptor = ChuckerInterceptor.Builder(context)
//            .maxContentLength(500_000L)
//            .redactHeaders("Auth-Token", "Bearer")
//            .alwaysReadResponseBody(true)
//            .build()

//        if (BuildConfig.DEBUG) {
//            builder.addInterceptor(interceptor)
//            builder.addInterceptor(chuckerInterceptor)
//        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(builder.build())
            .build()
            .create(ApiService::class.java)
    }
}