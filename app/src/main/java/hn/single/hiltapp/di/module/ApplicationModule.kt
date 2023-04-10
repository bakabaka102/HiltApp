package hn.single.hiltapp.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
//import dagger.hilt.android.components.ApplicationComponent
import hn.single.hiltapp.BuildConfig
import hn.single.hiltapp.data.api.ApiHelper
import hn.single.hiltapp.data.api.ApiHelperImpl
import hn.single.hiltapp.data.api.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper
}