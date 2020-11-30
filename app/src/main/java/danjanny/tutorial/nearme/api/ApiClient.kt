package danjanny.tutorial.nearme.api

import com.facebook.stetho.okhttp3.StethoInterceptor
import danjanny.tutorial.nearme.ApiEndpoint
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    fun getRetrofitInstance(): Retrofit {
        val okHttpClient: OkHttpClient = OkHttpClient
            .Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        return Retrofit.Builder().baseUrl(ApiEndpoint.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService() = getRetrofitInstance().create(ApiService::class.java)
}