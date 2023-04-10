package com.mustafaunlu.rickandmortyapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.mustafaunlu.rickandmortyapp.data.model.character.Character
import com.mustafaunlu.rickandmortyapp.data.network.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class CharacterRepository @Inject constructor(
    private val retrofitService : RetrofitService
) {


    suspend fun fetchData(personSingleData : MutableLiveData<ArrayList<Character>>, ids: String){

        withContext(Dispatchers.IO){
            retrofitService.getCharacters(ids).enqueue(object  : Callback<ArrayList<Character>>{
                override fun onResponse(
                    call: Call<ArrayList<Character>>,
                    response: Response<ArrayList<Character>>
                ) {
                    personSingleData.postValue(response.body())
                }

                override fun onFailure(call: Call<ArrayList<Character>>, t: Throwable) {
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


    suspend fun getSingleChar(personSingleData : MutableLiveData<ArrayList<Character>>, ids: String ){
            withContext(Dispatchers.IO){
                val oneList : ArrayList<Character> = arrayListOf()
                retrofitService.getSingleChar(ids).enqueue(object  : Callback<Character>{
                    override fun onResponse(call: Call<Character>, response: Response<Character>) {
                        oneList.add(response.body()!!)
                        personSingleData.postValue(oneList)
                    }
                    override fun onFailure(call: Call<Character>, t: Throwable) {
                        println(t.message)
                    }
                })
            }




    }

}