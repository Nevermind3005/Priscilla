package eu.fitped.priscilla.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.fitped.priscilla.BASE_URL
import eu.fitped.priscilla.IJwtTokenManager
import eu.fitped.priscilla.service.IAuthService
import eu.fitped.priscilla.service.ICourseService
import eu.fitped.priscilla.service.ILanguageService
import eu.fitped.priscilla.service.ILeaderboardService
import eu.fitped.priscilla.service.IUserService
import eu.fitped.priscilla.service.websocket.IWebSocketService
import eu.fitped.priscilla.service.websocket.WebSocketService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

val mapper = jacksonObjectMapper()

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) : OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create(mapper))
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideWebSocketService(okHttpClient: OkHttpClient): IWebSocketService {
        return WebSocketService(okHttpClient)
    }

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit) : IAuthService = retrofit.create(IAuthService::class.java)


    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AuthenticatedClient

    @Provides
    fun providesAccessTokenInterceptor(tokenManager: IJwtTokenManager) = AccessTokenInterceptor(tokenManager)

    @Provides
    @Singleton
    @AuthenticatedClient
    fun providesAccessOkHttpClient(
        accessTokenInterceptor: AccessTokenInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(accessTokenInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AuthenticatedRetrofit

    @Singleton
    @Provides
    @AuthenticatedRetrofit
    fun provideAuthRetrofit(@AuthenticatedClient okHttpClient: OkHttpClient) : Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create(mapper))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideUserService(@AuthenticatedRetrofit retrofit: Retrofit) : IUserService = retrofit.create(IUserService::class.java)

    @Provides
    @Singleton
    fun provideCourseService(@AuthenticatedRetrofit retrofit: Retrofit) : ICourseService = retrofit.create(ICourseService::class.java)

    @Provides
    @Singleton
    fun provideLanguageService(@AuthenticatedRetrofit retrofit: Retrofit) : ILanguageService = retrofit.create(ILanguageService::class.java)

    @Provides
    @Singleton
    fun provideLeaderboardService(@AuthenticatedRetrofit retrofit: Retrofit) : ILeaderboardService = retrofit.create(ILeaderboardService::class.java)
}