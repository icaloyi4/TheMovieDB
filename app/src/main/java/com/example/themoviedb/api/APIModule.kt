package com.example.themoviedb.api

import com.example.themoviedb.mvvm.model.DetailRepository
import com.example.themoviedb.mvvm.model.MainRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class APIModule constructor(baseURL:String) {
    var baseURL:String?=""

    init {
        this.baseURL = baseURL
    }
    @Singleton
    @Provides
    fun provideOKHttpClient(): OkHttpClient {
        return  OkHttpClient.Builder()
            .readTimeout(1200, TimeUnit.SECONDS)
            .connectTimeout(1200,TimeUnit.SECONDS)
            .build()

    }
    @Singleton
    @Provides
    fun provideGSON(): GsonConverterFactory {

        return  GsonConverterFactory.create()

    }
    @Singleton
    @Provides
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory,okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideMainRepository():MainRepository{
        return MainRepository()
    }

    @Provides
    fun provideDetailRepository(): DetailRepository {
        return DetailRepository()
    }

}