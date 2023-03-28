package com.mustafaunlu.rickandmortyapp.network

import com.mustafaunlu.rickandmortyapp.model.character.Character
import com.mustafaunlu.rickandmortyapp.model.locations.Location
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("api/location")
    fun getLocations() : Call<Location>

    @GET("api/character/{id}")
    fun getCharacters(@Path("id") ids : String) : Call<ArrayList<Character>>

    @GET("api/character/{id}")
    fun getSingleChar(@Path("id") ids : String) : Call<Character>
}