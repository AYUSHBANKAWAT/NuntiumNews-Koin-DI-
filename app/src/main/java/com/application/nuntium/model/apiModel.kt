package com.application.nuntium.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

data class ResponseItem(
val totalResults: Int? = null,
val articles: ArrayList<articles>,
val status: String? = null
)

@Entity
data class articles(
    @PrimaryKey(autoGenerate = true)
    val ide:Int,
    val publishedAt: String? = null,
    val author: String? = null,
    val urlToImage: String? = null,
    val description: String,
    val title: String,
    val url: String,
    val content: String? = null
)

