package com.mustafaunlu.rickandmortyapp.ui.detail

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class DetailViewModel : ViewModel(){

    @SuppressLint("NewApi")
    fun dateConvert(date: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        return LocalDateTime.parse(date, formatter)
            .format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm:ss"))
    }



}