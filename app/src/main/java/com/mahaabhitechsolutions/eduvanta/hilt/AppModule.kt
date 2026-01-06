package com.mahaabhitechsolutions.eduvanta.hilt

import com.mahaabhitechsolutions.eduvanta.ENV
import com.mahaabhitechsolutions.eduvanta.api.OtherService
import com.mahaabhitechsolutions.eduvanta.api.ServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })
            val sslContext = SSLContext.getInstance("SSL").apply {
                init(null, trustAllCerts, SecureRandom())
            }
            val sslSocketFactory = sslContext.socketFactory
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return builder.build()
    }

    @Provides
    @Singleton
    @BaseUrl
    fun provideMainRetrofit(client: OkHttpClient): ServiceApi {
        return Retrofit.Builder()
            .baseUrl(ENV.getBaseUrl())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ServiceApi::class.java)
    }

    @Provides
    @Singleton
    @OtherUrl
    fun provideOtherRetrofit(client: OkHttpClient): OtherService {
        return Retrofit.Builder()
            .baseUrl(ENV.getOtherUrl())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OtherService::class.java)
    }
}
