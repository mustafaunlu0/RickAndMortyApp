package com.mustafaunlu.rickandmortyapp.repo

import android.content.SharedPreferences
import com.mustafaunlu.rickandmortyapp.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val preferences: SharedPreferences
) {


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