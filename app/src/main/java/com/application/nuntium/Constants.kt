package com.application.nuntium

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.application.nuntium.model.articles

object constants{
    const val sharedPreferencesKey ="com.application.nuntium"
    const val country = "COUNTRY"
    const val language ="LANGUAGE"
    const val title ="TITLE"
    const val firstTime = "intro"
    private  fun getSharedPreference(ctx: Context?): SharedPreferences? {
        return PreferenceManager.getDefaultSharedPreferences(ctx)
    }


    private fun  editor(context: Context, const:String, string: String){
        getSharedPreference(
            context
        )?.edit()?.putString(const,string)?.apply()
    }
    private fun  editorBoolean(context: Context, const:String, bool: Boolean){
        getSharedPreference(
            context
        )?.edit()?. putBoolean(const,bool)?.apply()
    }

    fun getLanguage(context: Context)= getSharedPreference(
        context
    )?.getString(constants.language,"en")

    fun setLanguage(context: Context, lang: String){
        editor(
            context,
            constants.language,
            lang
        )
    }

    fun setCountry(context: Context,country:String){
        editor(
            context,
            constants.country,
            country
        )
    }
    fun getCountry(context: Context) = getSharedPreference(
        context
    )?.getString(constants.country,"in")

    fun setUserTime(context: Context,status:String){
        editor(
            context,
            constants.firstTime,
            status
        )

    }
    fun getUserTime(context: Context) = getSharedPreference(
        context
    )?.getString(constants.firstTime,"NO")
}