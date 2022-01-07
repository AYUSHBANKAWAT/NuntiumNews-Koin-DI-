package com.application.nuntium.Adapter

import androidx.recyclerview.selection.ItemKeyProvider
import com.application.nuntium.model.TileModel

class MyItemKeyProvider(private val adapter: ChoiceAdapter):ItemKeyProvider<TileModel>(SCOPE_CACHED){
    override fun getKey(position: Int): TileModel? {
        return adapter.getItem(position)
    }

    override fun getPosition(key: TileModel): Int {
        return adapter.getPosition(key.name)
    }
}