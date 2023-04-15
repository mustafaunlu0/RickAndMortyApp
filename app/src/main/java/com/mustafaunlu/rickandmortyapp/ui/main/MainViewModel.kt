package com.mustafaunlu.rickandmortyapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mustafaunlu.rickandmortyapp.data.model.character.Character
import com.mustafaunlu.rickandmortyapp.data.model.locations.Location
import com.mustafaunlu.rickandmortyapp.data.model.locations.Result
import com.mustafaunlu.rickandmortyapp.data.paging.LocationPagingSource
import com.mustafaunlu.rickandmortyapp.data.repository.CharacterRepository
import com.mustafaunlu.rickandmortyapp.data.repository.LocationRepository
import com.mustafaunlu.rickandmortyapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val charRepository: CharacterRepository,
    private val locationRepository : LocationRepository,
    private val userRepository: UserRepository

) : ViewModel(){

    private var persons : MutableLiveData<ArrayList<Character>> = MutableLiveData()

    // API SERVICE
    fun getLocations() : Flow<PagingData<Result>>{
        return locationRepository.getLocationsForPaging().cachedIn(viewModelScope)
    }

    fun getPersonData() : MutableLiveData<ArrayList<Character>>{
        return persons
    }

     fun fetchPersons(ids: String){
        viewModelScope.launch {
            charRepository.fetchData(personSingleData = persons,ids=ids)
        }
    }

    // Alınan nesnedeki(Result sınıfı nesnesi) residents lerin idlerini ayıklar ve parametre olarak verilen idse yükler.
    fun uploadData(locations: MutableList<Result>, ids: ArrayList<String>, index : Int){
        locations[index].residents.forEach {  humanUrl ->
            ids.add(findId(humanUrl))
        }
    }

    // SHARED PREF
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



    // SIMPLE TOOLS

    fun findId(url : String) : String{
        return  url.substring((url.indexOf("character")+10),url.length)
    }

    // Bölüm sayısı ayıklar.
    fun findEpisode(url : String) : String{
        return  url.substring((url.indexOf("episode")+8),url.length)
    }
    // Iddeki boşlukları kaldırırç
    fun convertToString(ids : MutableList<String>): String {
        val string = ids.joinToString()
        return string.replace(" ","")
    }







}