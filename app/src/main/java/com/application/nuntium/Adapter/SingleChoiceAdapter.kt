package com.application.nuntium.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.application.nuntium.MainActivity
import com.application.nuntium.R
import com.application.nuntium.model.TileModel
import com.google.android.material.card.MaterialCardView

class SingleChoiceAdapter(var context: Context,var list:List<TileModel>): RecyclerView.Adapter<SingleChoiceAdapter.SingleChoiceViewHolder>() {
    inner class SingleChoiceViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val img = itemView.findViewById<ImageView>(R.id.imgChoices)
        val text = itemView.findViewById<TextView>(R.id.tile_name)
        val card = itemView.findViewById<MaterialCardView>(R.id.card)
        fun bind(position: Int){
            img.setImageResource(list[position].src)
            text.text = list[position].name
            card.setOnClickListener {
                val intent = Intent(context ,MainActivity::class.java)
                intent.putExtra("title",list[position].name)
                //context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChoiceViewHolder {
        return SingleChoiceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.tile_choices,parent,false))
    }

    override fun onBindViewHolder(holder: SingleChoiceViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}