package com.application.nuntium.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.application.nuntium.R
import com.application.nuntium.model.TileModel
import com.google.android.material.card.MaterialCardView

class ChoiceAdapter(val list: List<TileModel>):
    RecyclerView.Adapter<ChoiceAdapter.ChoiceViewHolder>() {
    var tracker: SelectionTracker<TileModel>? = null

    inner class ChoiceViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val img = itemView.findViewById<ImageView>(R.id.imgChoices)
        val text = itemView.findViewById<TextView>(R.id.tile_name)
        val card = itemView.findViewById<MaterialCardView>(R.id.card)
        val linearLayout = itemView.findViewById<LinearLayout>(R.id.linearLayout)
        @SuppressLint("ResourceAsColor")
        fun bind(position: Int, selected: Boolean=false){
            img.setImageResource(list[position].src)
            text.text = list[position].name
            itemView.isActivated= selected
            if(selected){
               card.strokeWidth=2
               card.strokeColor = R.color.primary_purple
            }
            else {
                card.strokeWidth = 0
            }
        }


        fun getItemDetails():ItemDetailsLookup.ItemDetails<TileModel> =
            object : ItemDetailsLookup.ItemDetails<TileModel>(){
                override fun getPosition(): Int {
                    return adapterPosition
                }
                override fun inSelectionHotspot(e: MotionEvent): Boolean {
                    return true
                }

                override fun getSelectionKey(): TileModel? {
                    return list[position]
                }

            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoiceViewHolder {
        return ChoiceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.tile_choices,parent,false))
    }

    override fun onBindViewHolder(holder: ChoiceViewHolder, position: Int) {
        tracker.let {
            if (it != null) {
                holder.bind(position,it.isSelected(list[position]))
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun getItem(position: Int) = list[position]
    fun getPosition(name: String) = list.indexOfFirst { it.name == name }

}