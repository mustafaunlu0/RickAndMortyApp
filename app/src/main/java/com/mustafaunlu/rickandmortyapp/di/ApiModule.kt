package com.mustafaunlu.rickandmortyapp.di

import android.content.Context
import android.content.SharedPreferences
import com.mustafaunlu.rickandmortyapp.common.Constants
import com.mustafaunlu.rickandmortyapp.common.Constants.BASE_URL
import com.mustafaunlu.rickandmortyapp.data.network.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    //Retrofit - API
    @Provides
    @Singleton
    fun getRetrofitServiceInstance(retrofit: Retrofit) : RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }

    @Provides
    @Singleton
    fun getRetrofitInstance() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //Shared Preferences
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context : Context) : SharedPreferences =
        context.getSharedPreferences(Constants.PREF_NAME,Context.MODE_PRIVATE)



}