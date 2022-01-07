package com.application.nuntium.di

import android.app.Application
import androidx.room.Room
import com.application.nuntium.local.DB_NAME
import com.application.nuntium.local.NewsDao
import com.application.nuntium.local.NewsDatabase
import com.application.nuntium.remote.ApiService
import com.application.nuntium.remote.apiPoint
import com.application.nuntium.repository.NewsRepository
import com.application.nuntium.viewModel.NewsViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    single { NewsViewModel(get()) }
}
val apiModule = module {
    fun provideNewsApi(retrofit: Retrofit):ApiService{
       return retrofit.create(ApiService::class.java)
    }
    single { provideNewsApi(get()) }
}
val netModule = module {
    fun ProvideCache(application: Application):Cache{
        val cacheSize =10*1024*1024
        return Cache(application.cacheDir,cacheSize.toLong())
    }
    fun provideOkHttpClient(cache:Cache):OkHttpClient{
        val okHttpClient =OkHttpClient.Builder()
            .cache(cache)
        return okHttpClient.build()
    }
    fun provideGson():Gson{
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }
    fun provideRetrofit(factory:Gson,client:OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl(apiPoint.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client)
            .build()
    }
    single { ProvideCache(androidApplication()) }
    single { provideOkHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get(),get()) }
}

val dataBaseModule = module {
    fun provideDatabase(application: Application):NewsDatabase{
        return Room.databaseBuilder(application,NewsDatabase::class.java, DB_NAME)
            .build()
    }
    fun provideDao(database: NewsDatabase): NewsDao {
        return  database.getDao
    }
    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}
val repositoryModule = module {
    fun provideRepository(api:ApiService,dao: NewsDao):NewsRepository{
        return NewsRepository(api,dao)
    }
    single { provideRepository(get(),get()) }
}