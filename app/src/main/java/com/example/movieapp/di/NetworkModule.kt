package com.example.movieapp.di

import com.example.movieapp.utlis.Constants.BASE_URL
import com.example.movieapp.remote.MovieResponseApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    fun getRetrofitInstance(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @Provides
    fun provideApiService(retrofit: Retrofit): MovieResponseApi =
        retrofit.create(MovieResponseApi::class.java)

}