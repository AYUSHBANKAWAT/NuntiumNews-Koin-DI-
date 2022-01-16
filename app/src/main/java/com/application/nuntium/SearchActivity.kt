package com.application.nuntium

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.nuntium.Adapter.HomeChoiceAdapter
import com.application.nuntium.databinding.ActivitySearchBinding
import com.application.nuntium.model.articles
import com.application.nuntium.utils.LoadingState
import com.application.nuntium.viewModel.NewsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchBinding
    private val newsViewModel by viewModel<NewsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.itemSearchInput.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                runBlocking {
                    val data = query.toString()?:"New Delhi"
                    newsViewModel.getSearchData(data,"en")
                }
                binding.recyclerView.adapter?.notifyDataSetChanged()!!
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        newsViewModel._loadingStateSearch.observe(
            this,
            Observer {
                when(it.status){
                    LoadingState.Status.ERROR -> Log.i("TAG","ERROR")
                    LoadingState.Status.RUNNING -> Log.i("TAG","LOADING")
                    LoadingState.Status.SUCCESS ->{
                        binding.recyclerView.apply {
                            adapter = HomeChoiceAdapter<articles>(context,newsViewModel.loadedResult,5,onItemClicked = {model->
                                val data = model as articles
                                val  sharingIntent = Intent(Intent.ACTION_SEND)
                                sharingIntent.type = "text/plain"
                                sharingIntent.putExtra(Intent.EXTRA_TEXT,data.url);
                                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, data.title);
                                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                            })
                            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                        }
                        binding.recyclerView.adapter?.notifyDataSetChanged()!!
                    }
                }
            }
        )

    }
}