//package eu.fitped.priscilla.api
//
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import eu.fitped.priscilla.BASE_URL
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object RetrofitInstance {
//
//    @Singleton
//    @Provides
//    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
//        .apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//
//    @Singleton
//    @Provides
//    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) : OkHttpClient =
//        OkHttpClient()
//            .newBuilder()
//            .addInterceptor(httpLoggingInterceptor)
//            .build()
//
//    @Singleton
//    @Provides
//    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit = Retrofit
//        .Builder()
//        .addConverterFactory(GsonConverterFactory.create())
//        .baseUrl(BASE_URL)
//        .client(okHttpClient)
//        .build()
//
//    @Singleton
//    @Provides
//    fun provideRetrofitInstance(retrofit: Retrofit) : RetrofitInstance = retrofit.create(RetrofitInstance::class.java)
//}