package com.mustafaunlu.rickandmortyapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mustafaunlu.rickandmortyapp.model.character.Person
import com.mustafaunlu.rickandmortyapp.model.locations.Location
import com.mustafaunlu.rickandmortyapp.repo.RetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RetrofitRepository
) : ViewModel(){

    private var locationList : MutableLiveData<Location> = MutableLiveData()
    private var person : MutableLiveData<Person> = MutableLiveData()


    fun getLocationData() : MutableLiveData<Location>{
        return locationList
    }

    fun getPerson() : MutableLiveData<Person>{
        return person
    }

    fun loadLocations(){
        repository.getLocations(locationList)
    }

    fun loadPersons(ids : String){
        repository.getCharacter(personData = person, ids = ids)
    }






}