package com.mustafaunlu.rickandmortyapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mustafaunlu.rickandmortyapp.model.character.PersonItem
import com.mustafaunlu.rickandmortyapp.model.locations.Location
import com.mustafaunlu.rickandmortyapp.model.locations.Result
import com.mustafaunlu.rickandmortyapp.repo.RetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RetrofitRepository
) : ViewModel(){

    private var locationList : MutableLiveData<Location> = MutableLiveData()
    private var persons : MutableLiveData<ArrayList<PersonItem>> = MutableLiveData()


    //API SERVICE
    fun getLocationData() : MutableLiveData<Location>{
        return locationList
    }
    fun getPersonData() : MutableLiveData<ArrayList<PersonItem>>{
        return persons
    }

    suspend fun loadLocations(){
        repository.getLocations(locationList)
    }

    suspend fun fetchPersons(ids: String){
        repository.fetchData(personSingleData = persons,ids=ids)
    }

    fun uploadData(locations: MutableList<Result>,ids: MutableList<String>,index : Int){
        locations[index].residents.forEach {  human ->
            ids.add(findId(human))
        }
    }

    fun findId(url : String) : String{
        return  url.substring((url.indexOf("character")+10),url.length)
    }

    fun findEpisode(url : String) : String{
        return  url.substring((url.indexOf("episode")+8),url.length)
    }
    fun convertToString(ids : MutableList<String>): String {
        val string = ids.joinToString()
        return string.replace(" ","")
    }





}