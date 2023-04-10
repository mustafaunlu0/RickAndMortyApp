package com.mustafaunlu.rickandmortyapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mustafaunlu.rickandmortyapp.data.network.RetrofitService
import com.mustafaunlu.rickandmortyapp.data.paging.LocationPagingSource


import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val retrofitService: RetrofitService
){

    fun getLocationsForPaging() = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),

        pagingSourceFactory = {
            LocationPagingSource(retrofitService)
        }
    ).flow




}