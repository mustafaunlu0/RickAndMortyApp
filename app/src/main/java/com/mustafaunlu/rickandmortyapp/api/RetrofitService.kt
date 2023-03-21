package com.mustafaunlu.rickandmortyapp.api

import com.mustafaunlu.rickandmortyapp.model.character.Person
import com.mustafaunlu.rickandmortyapp.model.locations.Location
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("api/location")
    fun getLocations() : Call<Location>

    @GET("api/character/{id}")
    fun getCharacter(@Path("id") ids : String) : Call<Person>

}