package com.example.githubusernavigationdanapi.data.remote.retrofit
import com.example.githubusernavigationdanapi.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    private const val BASE_URL = BuildConfig.BASE_URL

    fun getApiService(): ApiService {
        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val apiKey = BuildConfig.API_KEY
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", apiKey)
                .build()
            chain.proceed(requestHeaders)
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}