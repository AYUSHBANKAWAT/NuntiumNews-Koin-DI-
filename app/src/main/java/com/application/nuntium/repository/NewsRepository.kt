package com.application.nuntium.repository

import androidx.lifecycle.LiveData
import com.application.nuntium.local.NewsDao
import com.application.nuntium.model.ResponseItem
import com.application.nuntium.model.Source
import com.application.nuntium.model.SourcesItem
import com.application.nuntium.model.articles
import com.application.nuntium.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import retrofit2.Response

class NewsRepository(private val apiService:ApiService,private val newsDao:NewsDao ) {
    suspend fun getTopHeadlines(country:String,language:String,category: String="general"): Response<ResponseItem> {
        val data = withContext(IO){
            apiService.getTopHeadlines(country=country,language=language,category = category)
        }
       // data.body()?.let { newsDao.insertList(it.articles) }
        return data
    }
    suspend fun getSearch(q:String,language:String,limit:String): Response<ResponseItem> {
        val data =  withContext(IO){
            apiService.getSearch(topic = q,language=language,limit = limit)
        }
        return data
    }
    suspend fun insertData(topic:articles){
        withContext(IO){
            newsDao.insertData(topic)
        }
    }

    suspend fun getSavedData():List<articles>?{
      val data =  withContext(Dispatchers.IO){
            newsDao.getData().value
        }
        return data
    }

    suspend fun getSources():Response<Source>{
        val data =withContext(IO){
            apiService.getSources()
        }
        return data
    }

    suspend fun getSearchTopic(q:String,language: String):Response<ResponseItem>{
        val data = withContext(Dispatchers.IO){
            apiService.getSearchTopic(topic = q,language = language)
        }
        return data
    }

}