package com.mustafaunlu.rickandmortyapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mustafaunlu.rickandmortyapp.data.model.locations.Result
import com.mustafaunlu.rickandmortyapp.data.network.RetrofitService
import javax.inject.Inject

class LocationPagingSource @Inject constructor(
    private val retrofitService: RetrofitService,
) : PagingSource<Int, Result>(){

    // Sayfalar arasındaki ilişkiyi belirler ve sayfalama işleminin sırasını düzenler.
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    // Sayfa yüklemesi yapar.
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val page = params.key ?: 1
            val response = retrofitService.getLocationsForPaging(page = page)

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}