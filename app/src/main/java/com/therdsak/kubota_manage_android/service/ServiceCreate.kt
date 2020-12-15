package com.therdsak.kubota_manage_android.service

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceCreate {
    companion object {
        var serviceKubota: ServiceKubota? = null


        fun createKubotaService() {

            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(30, TimeUnit.MINUTES)
                .connectTimeout(30, TimeUnit.MINUTES)
                .build()


            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.spqueuing.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            serviceKubota = retrofit.create(ServiceKubota::class.java)
        }
    }
}