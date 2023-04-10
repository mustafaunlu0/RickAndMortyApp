package com.mustafaunlu.rickandmortyapp.data.network

import com.mustafaunlu.rickandmortyapp.data.model.character.Character
import com.mustafaunlu.rickandmortyapp.data.model.locations.Location
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
interface RetrofitService {


    @GET("api/location")
    suspend fun getLocationsForPaging(@Query("page") page :Int) : Location

    @GET("api/character/{id}")
    fun getCharacters(@Path("id") ids : String) : Call<ArrayList<Character>>

    @GET("api/character/{id}")
    fun getSingleChar(@Path("id") ids : String) : Call<Character>
}