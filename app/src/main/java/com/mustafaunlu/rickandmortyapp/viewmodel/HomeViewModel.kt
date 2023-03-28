package com.mustafaunlu.rickandmortyapp.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafaunlu.rickandmortyapp.model.character.Character
import com.mustafaunlu.rickandmortyapp.model.locations.Location
import com.mustafaunlu.rickandmortyapp.model.locations.Result
import com.mustafaunlu.rickandmortyapp.repo.CharacterRepository
import com.mustafaunlu.rickandmortyapp.repo.UserRepository
import com.mustafaunlu.rickandmortyapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CharacterRepository,
    private val userRepository: UserRepository

) : ViewModel(){

    private var locationList : MutableLiveData<Location> = MutableLiveData()
    private var persons : MutableLiveData<ArrayList<Character>> = MutableLiveData()


    //API SERVICE
    fun getLocationData() : MutableLiveData<Location>{
        return locationList
    }
    fun getPersonData() : MutableLiveData<ArrayList<Character>>{
        return persons
    }
     fun loadLocations(){
        viewModelScope.launch {
            repository.getLocations(locationList)
        }
    }
     fun fetchPersons(ids: String){
        viewModelScope.launch {
            repository.fetchData(personSingleData = persons,ids=ids)
        }
    }

    fun uploadData(locations: MutableList<Result>,ids: MutableList<String>,index : Int){
        locations[index].residents.forEach {  human ->
            ids.add(findId(human))
        }
    }

    //Shared Pref
    fun isFirstTime(onResult: (Boolean) -> Unit){
        viewModelScope.launch{
            onResult(userRepository.isFirstTime())
        }
    }

    fun setNotFirstTime(){
        viewModelScope.launch {
            userRepository.setNotFirstTime()
        }
    }



    //Simple Tools
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