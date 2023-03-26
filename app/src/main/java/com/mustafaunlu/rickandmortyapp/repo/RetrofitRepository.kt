package com.mustafaunlu.rickandmortyapp.repo

import androidx.lifecycle.MutableLiveData
import com.mustafaunlu.rickandmortyapp.api.RetrofitService
import com.mustafaunlu.rickandmortyapp.model.character.Person
import com.mustafaunlu.rickandmortyapp.model.character.PersonItem
import com.mustafaunlu.rickandmortyapp.model.locations.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class RetrofitRepository @Inject constructor(
    private val retrofitService : RetrofitService
) {

    suspend fun getLocations(locationData : MutableLiveData<Location>){

        withContext(Dispatchers.IO){
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


    }

    suspend fun fetchData(personSingleData : MutableLiveData<ArrayList<PersonItem>>,ids: String){

        withContext(Dispatchers.IO){
            retrofitService.getCharacters(ids).enqueue(object  : Callback<ArrayList<PersonItem>>{
                override fun onResponse(
                    call: Call<ArrayList<PersonItem>>,
                    response: Response<ArrayList<PersonItem>>
                ) {
                    personSingleData.postValue(response.body())
                }

                override fun onFailure(call: Call<ArrayList<PersonItem>>, t: Throwable) {
                    if(ids.isNotEmpty()){
                        runBlocking {
                            getSingleChar(personSingleData = personSingleData,ids=ids)

                        }

                    }else{
                        println(t.message)
                    }
                }

            })

        }


    }

    suspend fun getSingleChar(personSingleData : MutableLiveData<ArrayList<PersonItem>>,ids: String ){
            withContext(Dispatchers.IO){
                val oneList : ArrayList<PersonItem> = arrayListOf()
                retrofitService.getSingleChar(ids).enqueue(object  : Callback<PersonItem>{
                    override fun onResponse(call: Call<PersonItem>, response: Response<PersonItem>) {
                        oneList.add(response.body()!!)
                        personSingleData.postValue(oneList)
                    }
                    override fun onFailure(call: Call<PersonItem>, t: Throwable) {
                        println("son method da çalışmadı!")
                    }
                })
            }




    }

}