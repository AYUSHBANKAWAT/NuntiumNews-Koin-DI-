package com.application.nuntium.Fragment

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.nuntium.Adapter.HomeChoiceAdapter
import com.application.nuntium.Adapter.ScreenSlidingAdapter
import com.application.nuntium.R
import com.application.nuntium.SearchActivity
import com.application.nuntium.databinding.FragmentHomeBinding
import com.application.nuntium.model.TopHeadlines
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.bind( LayoutInflater.from(context).inflate(R.layout.fragment_home,container,false) )
        // Inflate the layout for this fragment
//        val categories = arrayListOf<String>(
//            "Random","Sport","Gaming","Politics","Food","Animal"
//        )
//        binding.homeCategoryRecyclerView.apply {
//            adapter = HomeChoiceAdapter<String>(requireContext(),categories,0)
//            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
//        }
        binding.viewpager.adapter = ScreenSlidingAdapter(this)
        TabLayoutMediator( binding.tabLayout,binding.viewpager ) { tab: TabLayout.Tab, position: Int ->
            when(position){
                0 -> tab.text = "RANDOM"
                1 -> tab.text = "SPORTS"
                2 -> tab.text = "TECHNOLOGY"
                3 -> tab.text = "BUSINESS"
                4 -> tab.text = "POLITICS"
                5 -> tab.text = "HEALTH"
            }
        }.attach()
        binding.homeSearch.setOnClickListener {
          val intent =  Intent(requireContext(),SearchActivity::class.java)
          startActivity(intent)
        }
       return binding.root
    }



}