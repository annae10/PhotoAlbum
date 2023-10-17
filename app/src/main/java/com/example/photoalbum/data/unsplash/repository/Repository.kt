package com.example.photoalbum.data.unsplash.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.photoalbum.data.unsplash.UnsplashApi
import com.example.photoalbum.data.unsplash.UnsplashDatabase
import com.example.photoalbum.data.unsplash.UnsplashImage
import com.example.photoalbum.data.unsplash.paging.UnsplashRemoteMediator
import com.example.photoalbum.data.utils.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class Repository @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val unsplashDatabase: UnsplashDatabase
) {

    fun getAllImages(): Flow<PagingData<UnsplashImage>> {
        val pagingSourceFactory = { unsplashDatabase.unsplashImageDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = UnsplashRemoteMediator(
                unsplashApi = unsplashApi,
                unsplashDatabase = unsplashDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun searchImages(query: String): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchPagingSource(unsplashApi = unsplashApi, query = query)
            }
        ).flow
    }

}