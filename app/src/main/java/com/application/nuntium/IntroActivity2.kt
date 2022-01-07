package com.application.nuntium

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.application.nuntium.Adapter.ImageAdapter
import com.application.nuntium.databinding.ActivityIntro2Binding
import com.application.nuntium.model.ImageModelViewPager
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.abs

class IntroActivity2 : AppCompatActivity() {
    private lateinit var binding:ActivityIntro2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntro2Binding.inflate(layoutInflater)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        setContentView(binding.root)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val imgList  = listOf<ImageModelViewPager>(
            ImageModelViewPager(R.drawable.imageone),
            ImageModelViewPager(R.drawable.imagetwo),
            ImageModelViewPager(R.drawable.imagethree)
        )
        val adapterMain = ImageAdapter(imgList,viewPager)
        viewPager.apply {
            adapter =adapterMain
            clipToPadding=false
            clipChildren = false
            offscreenPageLimit=3
        }
        TabLayoutMediator(binding.tabLayout,binding.viewPager){ _, _->
        }.attach()
        binding.viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer( MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        viewPager.currentItem = 1
        viewPager.setPageTransformer(compositePageTransformer)
        binding.nextBtn.setOnClickListener {
            startActivity(Intent(this,IntroActivity3::class.java))
        }
    }
}