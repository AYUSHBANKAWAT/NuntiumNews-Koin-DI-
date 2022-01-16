package com.application.nuntium.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.nuntium.constants
import com.application.nuntium.model.SourcesItem
import com.application.nuntium.model.articles
import com.application.nuntium.repository.NewsRepository
import com.application.nuntium.utils.LoadingState
import kotlinx.coroutines.*

class NewsViewModel(private val newsRepository:NewsRepository):ViewModel() {
    private val loadingState = MutableLiveData<LoadingState>()
    var topHeadlines:ArrayList<articles> = arrayListOf()
    private val sportList =MutableLiveData<ArrayList<articles>>()
    val _sportList:LiveData<ArrayList<articles>>
    get() = sportList
    private val businessList =MutableLiveData<ArrayList<articles>>()
    val _businessList:LiveData<ArrayList<articles>>
        get() = businessList
    private val healthList =MutableLiveData<ArrayList<articles>>()
    val _healthList:LiveData<ArrayList<articles>>
        get() = healthList
    private val technologyList =MutableLiveData<ArrayList<articles>>()
    val _technologyList:LiveData<ArrayList<articles>>
        get() = technologyList
    private var interests=MutableLiveData<ArrayList<articles>>()
    val _interests : LiveData<ArrayList<articles>>
    get() = interests
    private var politicsList=MutableLiveData<ArrayList<articles>>()
    val _politicsList:LiveData<ArrayList<articles>>
    get() = politicsList
    val _loadingState:LiveData<LoadingState>
    get() = loadingState
    val loadedResult= arrayListOf<articles>()


    private var sourceList = MutableLiveData<List<SourcesItem>>()
    val _sourceList:LiveData<List<SourcesItem>>
    get() = sourceList

    private var loadingStateSearch = MutableLiveData<LoadingState>()
    val _loadingStateSearch:LiveData<LoadingState>
    get() = loadingStateSearch

     fun topHeadlines(country:String,language:String): ArrayList<articles> {
         var list :ArrayList<articles> = arrayListOf()
         viewModelScope.launch {
            try {
               loadingState.value=LoadingState.LOADING
              val data = newsRepository.getTopHeadlines(country,language)
             if(data.isSuccessful){
                 val body = data.body()!!
                  topHeadlines =body.articles
                 loadingState.value = LoadingState.LOADED
             }
            }catch(e:Exception){
                loadingState.value =LoadingState.ERROR(e.localizedMessage)
            }
        }
         return topHeadlines
    }

    fun topHeadlinesSports(country:String,language:String) {

        viewModelScope.launch {
            try {
                loadingState.value=LoadingState.LOADING
                val data = newsRepository.getTopHeadlines(country,language,"sports")
                if(data.isSuccessful){
                    val body = data.body()!!
                    sportList.postValue(body.articles)
                    loadingState.value = LoadingState.LOADED
                }
            }catch(e:Exception){
                loadingState.value =LoadingState.ERROR(e.localizedMessage)
            }
        }
    }
    fun topHeadlinesBusiness(country:String,language:String) {
        runBlocking (Dispatchers.Main) {
            try {
                loadingState.value=LoadingState.LOADING
                val data =  withContext(Dispatchers.IO) {
                    newsRepository.getTopHeadlines(
                        country,
                        language,
                        "business"
                    )
                }
                Log.i("business url",data.code().toString())
                Log.i("business url",data.raw().toString())
                if(data.isSuccessful){
                    val body = data.body()!!
                    businessList.postValue(body.articles)
                    loadingState.value = LoadingState.LOADED
                }
                else{
                    Log.i("business","${data.raw().body()}")
                }
            }catch(e:Exception){
                loadingState.value =LoadingState.ERROR(e.toString())
            }
        }
    }

    fun topHeadlinesHealth(country:String,language:String) {
        runBlocking(Dispatchers.Main){
            try {
                loadingState.value=LoadingState.LOADING
                val data = newsRepository.getTopHeadlines(country,language,"health")
                if(data.isSuccessful){
                    val body = data.body()!!
                    healthList.postValue(body.articles)
                    loadingState.value = LoadingState.LOADED
                }
                else{
                    Log.i("health","${data.raw().body()}")
                }
            }catch(e:Exception){
                loadingState.value =LoadingState.ERROR(e.toString())
            }
        }
    }
    fun topHeadlinesTechnology(country:String,language:String) {
        viewModelScope.launch {
            try {
                loadingState.value=LoadingState.LOADING
                val data = newsRepository.getTopHeadlines(country,language,"health")
                if(data.isSuccessful){
                    val body = data.body()!!
                    technologyList.postValue(body.articles)
                    loadingState.value = LoadingState.LOADED
                }
            }catch(e:Exception){
                loadingState.value =LoadingState.ERROR(e.localizedMessage)
            }
        }
    }
    fun topHeadlinesPolitics(country:String,language:String) {
        runBlocking(Dispatchers.Main) {
            try {
                loadingState.value=LoadingState.LOADING
                val data = newsRepository.getTopHeadlines(country,language,"health")
                if(data.isSuccessful){
                    val body = data.body()!!
                    politicsList.postValue(body.articles)
                    loadingState.value = LoadingState.LOADED
                }
            }catch(e:Exception){
                loadingState.value =LoadingState.ERROR(e.localizedMessage)
            }
        }
    }

    fun saveHeadlines(model:articles){
        GlobalScope.launch(Dispatchers.IO) {
            newsRepository.insertData(model)
        }
    }

    fun getSavedData(){
        GlobalScope.launch(Dispatchers.IO){
            val value = newsRepository.getSavedData()
            Log.i("info",value.toString())
        }
    }

    fun getSources(){
        runBlocking(Dispatchers.Main){
            val data = newsRepository.getSources()
            if(data.isSuccessful){
                val body = data.body()!!
                Log.i("message_source_error",body.toString())
                sourceList.postValue(body.sources)
            }
            else{
                Log.i("message_source_error",data.raw().toString())
            }
        }
    }

    fun getSearchData(q:String,lang:String){
       // loadingStateSearch.value = LoadingState.LOADING
        runBlocking {
            try {
                loadingStateSearch.value = LoadingState.LOADING
                val data = newsRepository.getSearch(q,lang,"10")
                Log.i("messageTwo",data.toString())
                if(data.isSuccessful){
                    loadedResult.clear()
                    val body = data.body()!!
                    loadedResult.addAll(body.articles)
                    Log.i("size",loadedResult.size.toString())
                    loadingStateSearch.value =LoadingState.LOADED
                }
                else{
                    Log.i("messageOne",data.raw().message() )
                }
            }catch (e:Exception){
                loadingStateSearch.value = LoadingState.ERROR(e.message.toString())
            }
        }
    }

    fun getSearch(q:String,language:String,limit:String){
        viewModelScope.launch {
            try {
                interests.value?.clear()
                loadingState.value = LoadingState.LOADING
                val data = newsRepository.getSearch(q,language,limit)
                Log.i("messageTwo",data.toString())
                if(data.isSuccessful){
                    val body = data.body()!!
                    interests.postValue(body.articles)
                    loadedResult.addAll(body.articles)
                    loadingStateSearch.value =LoadingState.LOADED
                    Log.i("size",interests.value?.size.toString())
                    loadingState.value =LoadingState.LOADED
                }
                else{
                    Log.i("messageOne",data.raw().message() )
                }
            }catch (e:Exception){
                loadingState.value = LoadingState.ERROR(e.message.toString())
            }
        }
    }

    //val data = newsRepository.readAll

}