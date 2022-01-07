package com.application.nuntium.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.application.nuntium.Adapter.ChoiceAdapter
import com.application.nuntium.Adapter.SingleChoiceAdapter
import com.application.nuntium.R
import com.application.nuntium.databinding.FragmentCategoriesBinding
import com.application.nuntium.model.TileModel


class CategoriesFragment : Fragment() {
    private lateinit var binding:FragmentCategoriesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.bind(LayoutInflater.from(context).inflate(R.layout.fragment_categories, container, false) )
        // Inflate the layout for this fragment
        val list = listOf<TileModel>(
            TileModel(R.drawable.americanfootball,"Sports"),
            TileModel(R.drawable.politics,"Politics"),
            TileModel(R.drawable.sun,"Life"),
            TileModel(R.drawable.console,"Gaming"),
            TileModel(R.drawable.bear,"Animals"),
            TileModel(R.drawable.tree,"Nature"),
            TileModel(R.drawable.burger,"Food"),
            TileModel(R.drawable.art,"Art"),
            TileModel(R.drawable.building,"History"),
            TileModel(R.drawable.dress,"Fashion"),
            TileModel(R.drawable.covid,"Covid-19"),
            TileModel(R.drawable.fight,"Middle East")
        )
        val choiceAdapter = SingleChoiceAdapter(requireContext(),list)
        binding.staggeredRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter= choiceAdapter
        }
        return binding.root
    }
}