package com.application.nuntium.Adapter

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.application.nuntium.model.TileModel

class MyItemDetailsLookup(private val recyclerView: RecyclerView): ItemDetailsLookup<TileModel>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<TileModel>? {
        val view = recyclerView.findChildViewUnder(e.x,e.y)
        if(view != null ){
            return (recyclerView.getChildViewHolder(view) as ChoiceAdapter.ChoiceViewHolder).getItemDetails()
        }
        return null
    }
}