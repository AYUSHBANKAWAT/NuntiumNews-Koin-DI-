package com.application.nuntium.remote


import com.application.nuntium.model.ResponseItem
import com.application.nuntium.model.Source
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")country:String ="in",
        @Query("category")category:String="general",
        @Query("language")language:String="en",
        @Query("pageSize")numberItem:String="10",
        @Query("apiKey")apiKey:String = apiPoint.API_KEY
    ):Response<ResponseItem>

    @GET("everything")
    suspend fun getSearch(
        @Query("q")topic:String,
        @Query("pageSize")limit:String="10",
        @Query("apiKey")apiKey:String=apiPoint.API_KEY,
        @Query("language")language:String="en",
        @Query("pageSize")numberItem:String="10"
    ):Response<ResponseItem>

    @GET("sources")
    suspend fun getSources(
        @Query("apiKey")apiKey:String=apiPoint.API_KEY,
        @Query("language")language:String="en",
        @Query("country")country:String="in"
    ):Response<Source>

    @GET("everything")
    suspend fun getSearchTopic(
        @Query("q")topic:String,
        @Query("pageSize")limit:String="10",
        @Query("apiKey")apiKey:String=apiPoint.API_KEY,
        @Query("language")language:String="en",
        @Query("pageSize")numberItem:String="10"
    ):Response<ResponseItem>

}