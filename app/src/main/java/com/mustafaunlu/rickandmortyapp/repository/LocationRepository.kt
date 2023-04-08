package com.mustafaunlu.rickandmortyapp.repository

import androidx.lifecycle.MutableLiveData
import com.mustafaunlu.rickandmortyapp.model.locations.Location
import com.mustafaunlu.rickandmortyapp.network.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val retrofitService: RetrofitService
){
    suspend fun getLocations(locationData : MutableLiveData<Location>, pageNumber : Int){
        withContext(Dispatchers.IO){
            retrofitService.getLocations(pageNumber).enqueue(object : Callback<Location> {
                override fun onResponse(call: Call<Location>, response: Response<Location>) {
                    locationData.postValue(response.body())
                }
                override fun onFailure(call: Call<Location>, t: Throwable) {
                    println(t.message)
                }
            })
        }
    }




}