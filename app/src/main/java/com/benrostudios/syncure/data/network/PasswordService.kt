package com.benrostudios.syncure.data.network

import android.content.Context
import com.benrostudios.syncure.data.network.response.GenericResponse
import com.benrostudios.syncure.utils.Constants
import com.benrostudios.syncure.utils.SharedPrefManager
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PasswordService {

    @POST("article/findAllPasswords")
    suspend fun getAllPasswords(): Response<GenericResponse>

    @FormUrlEncoded
    @POST("article/addPassword")
    suspend fun addPassword(
        @Field("passwordCode") passwordCode: String,
        @Field("passwordTitle") passwordTitle: String
    ): Response<GenericResponse>

    @FormUrlEncoded
    @POST("article/removePassword")
    suspend fun removePassword(@Field("id") id: String): Response<GenericResponse>


    companion object {
        operator fun invoke(
            context: Context,
            sharedPrefManager: SharedPrefManager
        ): PasswordService {
            val auth_key = sharedPrefManager.jwtStored
            val requestInterceptor = Interceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .header("Authorization:", auth_key)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(ChuckInterceptor(context))
                .build()
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(PasswordService::class.java)
        }
    }
}