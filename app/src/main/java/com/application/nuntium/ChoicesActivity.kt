package com.application.nuntium

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.application.nuntium.Adapter.ChoiceAdapter
import com.application.nuntium.Adapter.MyItemDetailsLookup
import com.application.nuntium.Adapter.MyItemKeyProvider
import com.application.nuntium.databinding.ActivityChoicesBinding
import com.application.nuntium.model.TileModel

class ChoicesActivity : AppCompatActivity() {
    private lateinit var binding:ActivityChoicesBinding
    var tracker: SelectionTracker<TileModel>? = null
    lateinit var listA:List<String>
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChoicesBinding.inflate(layoutInflater)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(constants.sharedPreferencesKey,MODE_PRIVATE)

        var editor = sharedPreferences.edit()
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
            TileModel(R.drawable.dress,"Fashion")
        )
        val adapterchoices = ChoiceAdapter(list)
        binding.staggeredRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            adapter=adapterchoices
        }
         tracker = SelectionTracker.Builder<TileModel>(
            "mySelection",
            binding.staggeredRecyclerView,
            MyItemKeyProvider(adapterchoices),
            MyItemDetailsLookup(binding.staggeredRecyclerView),
            StorageStrategy.createParcelableStorage(TileModel::class.java)
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()
        adapterchoices.tracker=tracker

        tracker?.addObserver(
            object :SelectionTracker.SelectionObserver<TileModel>(){
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    tracker?.let {
                        listA = it.selection.toMutableList().map {
                            it.name
                        }
                        Log.i("message",listA.toString())
                    }
                }
            }
        )
        binding.nextBtnChoices.setOnClickListener {
            val set:HashSet<String> = HashSet<String>()
            set.addAll(listA)
            Log.i("choices",set.size.toString())
            editor.putStringSet(constants.sharedPreferencesKey,set)
            editor.apply()
            startActivity(Intent(this,HomeActivity::class.java))
        }
    }
}