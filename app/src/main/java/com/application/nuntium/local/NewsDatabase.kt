package com.application.nuntium.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.application.nuntium.model.articles
import com.google.android.gms.safetynet.HarmfulAppsData
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
const val DB_NAME ="article.db"
@Database(entities = [articles::class],version = 1)
abstract class NewsDatabase:RoomDatabase() {
    abstract val getDao:NewsDao

    companion object{
        @Volatile
        private var INSTANCES:NewsDatabase?=null

        @InternalCoroutinesApi
        fun getDatabase(context:Context):NewsDatabase{
            val tempInstance = INSTANCES
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCES =instance
                return instance
            }
        }
    }

}