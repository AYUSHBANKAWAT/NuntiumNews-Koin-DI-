package com.application.nuntium

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        val getData = intent.getStringExtra("web_url")
        Log.i("webUrl",getData.toString())

       val webView =  findViewById<WebView>(R.id.webView);
        webView.webViewClient = WebViewClient()
       webView.apply {
           loadUrl(getData.toString())
           settings.javaScriptEnabled=true
           settings.safeBrowsingEnabled=true
       }
    }
}