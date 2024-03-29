package com.mustafaunlu.rickandmortyapp.data.repository

import android.content.SharedPreferences
import com.mustafaunlu.rickandmortyapp.common.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val preferences: SharedPreferences
) {

    // İlk giriş için welcome sonrasında hello yazması için gerekli kodlar
    suspend fun isFirstTime() : Boolean=
        withContext(Dispatchers.IO){
            preferences.getBoolean(Constants.FIRST_RUN_KEY,true)
        }


    suspend fun setNotFirstTime(){
        withContext(Dispatchers.IO){
            val editor = preferences.edit()
            editor.putBoolean(Constants.FIRST_RUN_KEY, false)
            editor.apply()
        }

    }


}