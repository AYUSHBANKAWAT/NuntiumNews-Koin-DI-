package com.application.nuntium.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.application.nuntium.R
import com.application.nuntium.model.ImageModelViewPager
import com.google.android.material.imageview.ShapeableImageView

class ImageAdapter(var list:List<ImageModelViewPager>,var viewPager:ViewPager2) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
       var img = itemView.findViewById<ShapeableImageView>(R.id.img_intro)
        fun bind(imageModelViewPager: ImageModelViewPager) {
           img.setImageResource(imageModelViewPager.img)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.imgcard,parent,false)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
       holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}