package com.benrostudios.syncure.data.network


import com.benrostudios.syncure.data.network.response.GenericResponse
import com.benrostudios.syncure.utils.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface AuthService {

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<GenericResponse>

    @FormUrlEncoded
    @POST("user/register")
    suspend fun userRegister(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("name") name: String?
    ): Response<GenericResponse>

    @FormUrlEncoded
    @POST("user/verify/{username}")
    suspend fun twoFactorRegister(
        @Path(value = "username", encoded = true) username: String,
        @Field("totp") totp: String,
        @Field("password") password: String
    ): Response<GenericResponse>

    @FormUrlEncoded
    @POST("verify/{username}}")
    suspend fun twoFactorLogin(
        @Path(value = "username", encoded = true) username: String,
        @Field("totp") totp: String
    ): Response<GenericResponse>

    @FormUrlEncoded
    @GET("/user/mail/{username}")
    suspend fun resendTwoFactor(
        @Path(value = "username", encoded = true) username: String
    )

    @FormUrlEncoded
    @POST("user/forgotPassword")
    suspend fun forgotPassword(
        @Field("username") username: String
    ): Response<GenericResponse>


    companion object {
        operator fun invoke(): AuthService {
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AuthService::class.java)
        }
    }
}