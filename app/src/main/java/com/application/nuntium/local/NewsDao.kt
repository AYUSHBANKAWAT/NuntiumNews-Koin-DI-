package com.application.nuntium.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.application.nuntium.model.articles
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insertData(news:articles):Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(news:List<articles>)


    @Query("SELECT * FROM articles")
    fun getData(): LiveData<List<articles>>
}