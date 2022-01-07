package com.application.nuntium

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.application.nuntium.Fragment.CategoriesFragment
import com.application.nuntium.Fragment.HomeFragment
import com.application.nuntium.Fragment.SavedFragment
import com.application.nuntium.Fragment.UserFragment
import com.application.nuntium.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(HomeFragment())
        val sharedPref = getSharedPreferences(constants.sharedPreferencesKey, MODE_PRIVATE)
        val data=sharedPref?.getStringSet("interest",null)
        Log.i("TAG","${data?.size}")
        binding.bottomNavigation.setOnNavigationItemSelectedListener( mOnNavigationItemSelectedListener )
    }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        val fragment:Fragment
        when(it.itemId){
            R.id.home_menu->{
                fragment = HomeFragment()
                loadFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.category_menu->{
                fragment = CategoriesFragment()
                loadFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.saved_menu->{
                fragment = SavedFragment()
                loadFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.user_menu->{
                fragment = UserFragment()
                loadFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.FrameHome,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}