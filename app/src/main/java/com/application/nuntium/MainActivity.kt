package com.application.nuntium

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.airbnb.lottie.LottieAnimationView
import com.application.nuntium.utils.NetworkHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        setContentView(R.layout.activity_main)
        val lottie = findViewById<LottieAnimationView>(R.id.no_internet)
        val linearLayout = findViewById<LinearLayout>(R.id.linearSplash)

        val data =MutableLiveData<Boolean>()
        data.value = NetworkHelper(this).isNetworkConnected()
        val networkState=data.value!!
        val status = constants.getUserTime(this)!!
        if(networkState) {
            Handler().postDelayed(
                Runnable {
                    if(status=="OK")
                    startActivity(Intent(this, HomeActivity::class.java))
                    else startActivity(Intent(this,IntroActivity2::class.java))
                }, 5000
            )
        }
        else{
            linearLayout.visibility =View.GONE
            lottie.visibility = View.VISIBLE
        }
    }
}