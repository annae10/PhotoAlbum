package com.example.photoalbum.data.unsplash


import com.example.photoalbum.data.utils.API_KEY.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    @Headers("Authorization: Client-ID ${API_KEY}")
    @GET("/photos")
    suspend fun getAllImages(
        @Query("page")page: Int,
        @Query("per_page")perPage: Int
    ): List<UnsplashImage>

    @Headers("Authorization: Client-ID ${API_KEY}")
    @GET("/search/photos")
    suspend fun searchImages(
        @Query("query") query: String,
        @Query("per_page") perPage: Int
    ): SearchResult

}