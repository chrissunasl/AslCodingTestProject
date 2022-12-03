package com.autotoll.ffts.di.module

import android.content.Context
import com.autotoll.ffts.model.constant.IConstants
import com.example.aslcodingtestproject.model.converter.EnumConverterFactory
import com.example.aslcodingtestproject.model.database.room.AppDatabase
import com.example.aslcodingtestproject.model.remote.interceptor.MyOkHttpClient
import com.example.aslcodingtestproject.model.remote.service.NonTokenService
import com.google.gson.GsonBuilder

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


/**
 * API after login
 * @Named ApiTokenRetrofit, HTTP client without login, with Access Token
 * @Named NormalRetrofit, HTTP client with SignIn, with Session Access Token
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /*  Okhttp non Token  */
    @Singleton
    @Provides
    @Named("NonTokenRetrofit")
    fun provideNonTokenRetrofit(
        @ApplicationContext appContext: Context,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(IConstants.DomainName.API_DOMAIN_JSONPLACEHOLDER)
        .client(MyOkHttpClient.getOkHttpClient(appContext, null, isNeedApiAccessToken = false))
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .disableHtmlEscaping()
                    .create()
            )
        )
        .addConverterFactory(EnumConverterFactory())
        .build()

    /*  Okhttp Asymmetric Key Token  */
//    @Singleton
//    @Provides
//    @Named("AsymmetricKeyRetrofit")
//    fun provideAsymmetricKeyRetrofit(
//        @ApplicationContext appContext: Context,
//        repository: AsymmetricKeyRepository,
//    ): Retrofit = Retrofit.Builder()
//        .baseUrl(IConstants.DomainName.getApiDomain())
//        .client(
//            MyOkHttpClient.getOkHttpClient(
//                appContext,
//                AsymmetricKeyAuthenticator(repository), isNeedApiAccessToken = false
//            )
//        )
//        .addConverterFactory(
//            GsonConverterFactory.create(
//                GsonBuilder()
//                    .disableHtmlEscaping()
//                    .create()
//            )
//        )
//        .addConverterFactory(EnumConverterFactory())
//        .build()

    /*  Okhttp API TOKEN  */
//    @Singleton
//    @Provides
//    @Named("ApiTokenRetrofit")
//    fun provideApiTokenRetrofit(
//        @ApplicationContext appContext: Context,
//        authenticationRepository: AuthenticationRepository,
//        asymmetricKeyRepository: AsymmetricKeyRepository,
//    ): Retrofit = Retrofit.Builder()
//        .baseUrl(IConstants.DomainName.getApiDomain())
//        .client(
//            MyOkHttpClient.getOkHttpClient(
//                appContext,
//                ApiTokenAuthenticator(authenticationRepository, asymmetricKeyRepository),
//                isNeedApiAccessToken = true
//            )
//        )
//        .addConverterFactory(
//            GsonConverterFactory.create(
//                GsonBuilder()
//                    .serializeNulls()
//                    .disableHtmlEscaping()
//                    .create()
//            )
//        )
//        .addConverterFactory(EnumConverterFactory())
//        .build()
//
//    @Singleton
//    @Provides
//    @Named("RefreshTokenRetrofit")
//    fun provideRefreshTokenRetrofit(
//        @ApplicationContext appContext: Context,
//        authenticationRepository: AuthenticationRepository,
//        asymmetricKeyRepository: AsymmetricKeyRepository,
//    ): Retrofit = Retrofit.Builder()
//        .baseUrl(IConstants.DomainName.getApiDomain())
//        .client(
//            MyOkHttpClient.getHttpClientAfterLogin(
//                appContext,
//                ApiTokenAuthenticator(authenticationRepository, asymmetricKeyRepository)
//            )
//        )
//        .addConverterFactory(
//            GsonConverterFactory.create(
//                GsonBuilder()
//                    .disableHtmlEscaping()
//                    .create()
//            )
//        )
//        .addConverterFactory(EnumConverterFactory())
//        .build()

    /*  Okhttp with session access key  */
//    @Singleton
//    @Provides
//    @Named("NormalRetrofit")
//    fun provideRetrofit(
//        @ApplicationContext appContext: Context,
//        service: RefreshSessionTokenService,
//    ): Retrofit = Retrofit.Builder()
//        .baseUrl(IConstants.DomainName.getApiDomain())
//        .client(
//            MyOkHttpClient.getHttpClientAfterLogin(
//                appContext,
//                SessionTokenAuthenticator(appContext, service)
//            )
//        )
//        .addConverterFactory(
//            GsonConverterFactory.create(
//                GsonBuilder()
//                    .serializeNulls()
////                    .disableHtmlEscaping()
//                    .create()
//            )
//        )
//        .addConverterFactory(EnumConverterFactory())
//        .build()


    /*  Okhttp non Token  */
    @Singleton
    @Provides
    @Named("RecaptchaRetrofit")
    fun recaptchaRetrofit(
        @ApplicationContext appContext: Context,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(IConstants.DomainName.API_DOMAIN_ReCaptcha)
        .client(MyOkHttpClient.getOkHttpClient(appContext, null, isNeedApiAccessToken = false))
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .disableHtmlEscaping()
                    .create()
            )
        )
        .addConverterFactory(EnumConverterFactory())
        .build()


    // Service Before Login
    @Singleton
    @Provides
    fun getNonTokenService(@Named("NonTokenRetrofit") retrofit: Retrofit): NonTokenService =
        retrofit.create(NonTokenService::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun PhotoDao(db: AppDatabase) = db.getVehicleDao()
}