package com.application.nuntium.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.nuntium.Adapter.HomeChoiceAdapter
import com.application.nuntium.R
import com.application.nuntium.constants
import com.application.nuntium.databinding.FragmentSportBinding
import com.application.nuntium.model.articles
import com.application.nuntium.viewModel.NewsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SportFragment : Fragment() {
    private val newsViewModel by viewModel<NewsViewModel>()
    private lateinit var binding: FragmentSportBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val language = constants.getLanguage(requireContext())?:"en"
        val country = constants.getCountry(requireContext())?:"in"
        binding = FragmentSportBinding.bind(LayoutInflater.from(context).inflate(R.layout.fragment_sport, container, false))
        GlobalScope.launch(Dispatchers.IO) {
            newsViewModel.topHeadlinesSports(country,language)
        }

        newsViewModel._sportList.observe(viewLifecycleOwner,
        Observer {
            when(it.size){
                10 ->{
                    var list =it
                    binding.recyclerView.apply {
                        adapter = HomeChoiceAdapter<articles>(requireContext(),it,5,onItemClicked = {model->
                           var data = model as articles
                            var  sharingIntent = Intent(Intent.ACTION_SEND)
                            sharingIntent.type = "text/plain"
                            sharingIntent.putExtra(Intent.EXTRA_TEXT,data.url)
                            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, data.title)
                            startActivity(Intent.createChooser(sharingIntent, "Share via"))
                        })
                        layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                    }
                }
                else->Log.i("TAG","empty")
            }
        }
        )


        return binding.root
    }
}