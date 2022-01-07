package com.application.nuntium.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TileModel (
    val src:Int,
    val name:String
        ):Parcelable