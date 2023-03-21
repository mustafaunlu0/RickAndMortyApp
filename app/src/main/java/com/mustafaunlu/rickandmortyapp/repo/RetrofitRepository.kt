package com.mustafaunlu.rickandmortyapp.repo

import androidx.lifecycle.MutableLiveData
import com.mustafaunlu.rickandmortyapp.api.RetrofitService
import com.mustafaunlu.rickandmortyapp.model.character.Person
import com.mustafaunlu.rickandmortyapp.model.locations.Location
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class RetrofitRepository @Inject constructor(
    private val retrofitService : RetrofitService
) {

    fun getLocations(locationData : MutableLiveData<Location>){

        retrofitService.getLocations().enqueue(object : Callback<Location>{
            override fun onResponse(call: Call<Location>, response: Response<Location>) {
                println("Veri çekildi! (Locations)")
                locationData.postValue(response.body())

            }

            override fun onFailure(call: Call<Location>, t: Throwable) {
                println("Hata! -- Location")
            }

        })

    }

    fun getCharacter(personData : MutableLiveData<Person>,ids: String ){

        retrofitService.getCharacter(ids).enqueue(object : Callback<Person>{
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                println("Veri çekildi! (Persons)")
                personData.postValue(response.body())
            }

            override fun onFailure(call: Call<Person>, t: Throwable) {
                println("Hata! -- Person")

            }

        })

    }


}