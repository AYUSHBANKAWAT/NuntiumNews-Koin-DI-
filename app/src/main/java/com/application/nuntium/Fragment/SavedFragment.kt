package com.application.nuntium.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.nuntium.Adapter.HomeChoiceAdapter
import com.application.nuntium.R
import com.application.nuntium.databinding.FragmentSavedBinding
import com.application.nuntium.local.NewsDatabase
import com.application.nuntium.model.SourcesItem
import com.application.nuntium.model.articles
import com.application.nuntium.viewModel.NewsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SavedFragment : Fragment() {
    private lateinit var binding:FragmentSavedBinding
    val newsViewModel by viewModel<NewsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedBinding.inflate(layoutInflater,container,false)
        GlobalScope.launch {
            newsViewModel.getSources()
        }
        newsViewModel._sourceList.observe(
            viewLifecycleOwner,
            Observer {
                when(it.isEmpty()){
                    true -> Log.i("Source Fragment","empty")
                    false -> {
                        var list = ArrayList<SourcesItem>()
                        list.addAll(it)
                        binding.savedRecyclerView.apply {
                            adapter = HomeChoiceAdapter<SourcesItem>(requireContext(),list,6,onItemClicked = {it->
                                val data = it as SourcesItem
                                val intent = Intent(Intent.ACTION_VIEW)
                                intent.data = Uri.parse(data.url)
                                startActivity(intent)
                            })
                            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                        }
                    }
                }
            }
        )

        binding.savedRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        }

        return binding.root
    }
}