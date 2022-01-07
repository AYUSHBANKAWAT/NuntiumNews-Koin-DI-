package com.application.nuntium.utils

import android.util.Log


data class LoadingState(
    val status:Status,
    val msg:String?=null
){
    companion object {
        val LOADED = LoadingState(Status.SUCCESS)
        val LOADING = LoadingState(Status.RUNNING)
        fun ERROR(msg: String?) :LoadingState {
            Log.i("error",msg.toString())
            return LoadingState(Status.ERROR, msg)  }
    }
    enum class Status{
        RUNNING,
        SUCCESS,
        ERROR
    }
}
