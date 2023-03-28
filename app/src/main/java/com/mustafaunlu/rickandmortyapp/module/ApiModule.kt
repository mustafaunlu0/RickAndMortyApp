package com.mustafaunlu.rickandmortyapp.module

import android.content.Context
import android.content.SharedPreferences
import com.mustafaunlu.rickandmortyapp.api.RetrofitService
import com.mustafaunlu.rickandmortyapp.repo.UserRepository
import com.mustafaunlu.rickandmortyapp.util.Constants
import com.mustafaunlu.rickandmortyapp.util.Constants.BASE_URL
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
    fun getRetrofitServiceInstance(retrofit: Retrofit) : RetrofitService{
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