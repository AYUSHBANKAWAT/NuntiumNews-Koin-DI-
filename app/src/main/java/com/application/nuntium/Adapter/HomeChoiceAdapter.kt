package com.application.nuntium.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.application.nuntium.R
import com.application.nuntium.WebActivity
import com.application.nuntium.model.SourcesItem
import com.application.nuntium.model.TopHeadlines
import com.application.nuntium.model.articles
import com.application.nuntium.repository.NewsRepository
import com.application.nuntium.viewModel.NewsViewModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso


class HomeChoiceAdapter<T>(var context: Context, private var list:ArrayList<T>, val code:Int,val onItemClicked:(Any)->Unit):RecyclerView.Adapter<HomeChoiceAdapter<T>.HomeChoiceViewHolder>() {
    inner class HomeChoiceViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
           @SuppressLint("ResourceAsColor")
           fun bind(position: Int) {
              if(code==0 ) {
                  val text =  itemView.findViewById<TextView>(R.id.text_categories)
                  text.text = list[position].toString()
                  var card = itemView.findViewById<LinearLayout>(R.id.card_home_chip)
              }
              if(code==1){
                  val text_title = itemView.findViewById<TextView>(R.id.home_title)
                  val text_discription = itemView.findViewById<TextView>(R.id.home_description)
                  val img = itemView.findViewById<ImageView>(R.id.home_img)
                  val imgBookMark = itemView.findViewById<ImageView>(R.id.book_mark_img_card)
                  val data = list[position] as articles
                  text_title.text = data.title
                  text_discription.text=data.description
                 Picasso.get().load(data.urlToImage).into(img)
                  imgBookMark.setOnClickListener {
                      onItemClicked(list[position] as articles)
                     // Toast.makeText(context,"$position",Toast.LENGTH_LONG).show()
                  }
              }
              if(code==3){
                  val text_title = itemView.findViewById<TextView>(R.id.title)
                  val layout = itemView.findViewById<MaterialCardView>(R.id.card)
                  val img = itemView.findViewById<ShapeableImageView>(R.id.img)
                  val data = list[position] as articles
                  text_title.text = data.title
                  Picasso.get().load(data.urlToImage).into(img)
              }

              if(code==5){
                  val headline = itemView.findViewById<TextView>(R.id.headlines)
                  val date = itemView.findViewById<TextView>(R.id.date)
                  val card = itemView.findViewById<MaterialCardView>(R.id.card)
                  val body = itemView.findViewById<TextView>(R.id.body)
                  val img = itemView.findViewById<ShapeableImageView>(R.id.img)
                  val share = itemView.findViewById<ImageView>(R.id.share)
                  val data = list[position] as articles
                  headline.text = data.title
                  date.text =data.publishedAt
                  body.text =data.content
                  Picasso.get().load(data.urlToImage).error(R.drawable.error).into(img)
                  share.setOnClickListener {
                      onItemClicked(data)
                  }
                  card.setOnClickListener {
                      val data = list[position] as articles
                      Log.i("web_url",data.url)
                      val intent = Intent(context,WebActivity::class.java)
                      intent.putExtra("web_url",data.url)
                      context.startActivity(intent)
                  }

              }
              if(code==6){
                  val title = itemView.findViewById<TextView>(R.id.title)
                  val language = itemView.findViewById<TextView>(R.id.language)
                  val category = itemView.findViewById<TextView>(R.id.category)
                  val web= itemView.findViewById<ImageView>(R.id.web)
                  val description = itemView.findViewById<TextView>(R.id.description)
                  val languageCard=itemView.findViewById<MaterialCardView>(R.id.language_card)
                  val categoryCard=itemView.findViewById<MaterialCardView>(R.id.category_card)
                  val data = list[position] as SourcesItem
                  title.text= data.name
                  language.text= "Language:${data.language}"
                  category.text=data.category
                  description.text=data.description
                  web.setOnClickListener {
                      onItemClicked(list[position] as articles)
                  }
              }

           }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeChoiceViewHolder {
        return when(code){
           0 ->{
               HomeChoiceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_category_chip,parent,false))
           }
           1 -> HomeChoiceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.img_card_home,parent,false))
            5->HomeChoiceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_feed_tile,parent,false))
            3 ->HomeChoiceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recommended_plate,parent,false))
            6 ->HomeChoiceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.source_file,parent,false))
            else -> HomeChoiceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recommended_plate,parent,false))
        }

    }

    override fun onBindViewHolder(holder: HomeChoiceViewHolder, position: Int) {
        return holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}