package com.application.nuntium.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.nuntium.Adapter.HomeChoiceAdapter
import com.application.nuntium.constants
import com.application.nuntium.databinding.FragmentRandomHomeBinding
import com.application.nuntium.local.NewsDatabase
import com.application.nuntium.model.articles
import com.application.nuntium.utils.LoadingState
import com.application.nuntium.viewModel.NewsViewModel
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RandomHomeFragment : Fragment() {
    private lateinit var binding:FragmentRandomHomeBinding
    private val newsViewModel by viewModel<NewsViewModel>()
    lateinit var list:ArrayList<articles>
    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val language = constants.getLanguage(requireContext())
        val country = constants.getCountry(requireContext())
        val sharedPref = requireContext().getSharedPreferences(constants.sharedPreferencesKey,
            AppCompatActivity.MODE_PRIVATE
        )
        val data=sharedPref?.getStringSet(constants.sharedPreferencesKey,null)
        Log.i("TAG","${data?.size}")
        var map = data?.random()!!
        Log.i("saved", "$language$country $map")
        binding = FragmentRandomHomeBinding.inflate(layoutInflater,container,false)
        GlobalScope.launch {
            list  =  newsViewModel.topHeadlines("in","en")
            newsViewModel.getSearch(map,"en","4")
        }
        newsViewModel._loadingState.observe(viewLifecycleOwner,
            Observer {
                when(it.status){
                    LoadingState.Status.ERROR ->Log.i("TAG","ERROR")
                    LoadingState.Status.RUNNING -> Log.i("TAG","LOADING")
                    LoadingState.Status.SUCCESS -> {
                        list.addAll(newsViewModel.topHeadlines)
                        Log.i("TAG"," ${list.size}")
                        binding.topHeadlinesRecyclerView.apply {
                            adapter =HomeChoiceAdapter<articles>(requireContext(),newsViewModel.topHeadlines,1,onItemClicked = {model->
                                var data = model as articles
                                var  sharingIntent = Intent(Intent.ACTION_SEND)
                                sharingIntent.type = "text/plain"
                                sharingIntent.putExtra(Intent.EXTRA_TEXT,data.url);
                                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, data.title);
                                startActivity(Intent.createChooser(sharingIntent, "Share via"))
                            })
                            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                        }
                    }
                }
            }
        )
        newsViewModel._interests.observe(viewLifecycleOwner,
            Observer {
                Log.i("size","${it.size}")
                when(it.isNotEmpty()){
                    false -> Log.i("message","00")
                    true -> {
                        var list =it
                        Log.i("success shayad",list.toString())
                        binding.recommendedRecyclerView.apply {
                            adapter = HomeChoiceAdapter<articles>(requireContext(),it,3,onItemClicked = {model->
                                var data = model as articles
                                var  sharingIntent = Intent(Intent.ACTION_SEND)
                                sharingIntent.type = "text/plain"
                                sharingIntent.putExtra(Intent.EXTRA_TEXT,data.url);
                                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, data.title);
                                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                            })
                            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                        }
                    }
                }
            }
        )

        return binding.root
    }

}