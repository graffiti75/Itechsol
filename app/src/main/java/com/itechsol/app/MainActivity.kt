@file:Suppress("DEPRECATION")

package com.itechsol.app

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

@SuppressLint("SetJavaScriptEnabled")
class MainActivity : AppCompatActivity() {

    //--------------------------------------------------
    // Activity Life Cycle
    //--------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLayout()
    }

    //--------------------------------------------------
    // Layout Methods
    //--------------------------------------------------

    private fun initLayout() {
        activity_main__button.setOnClickListener {
            setWebView()
        }
    }

    //--------------------------------------------------
    // WebView Methods
    //--------------------------------------------------

    private fun setWebView() {
        val progressBar = ProgressDialog.show(
            this, getString(R.string.loading_url), getString(R.string.opening_url))

        val webView = activity_main__web_view
        val context = this
        with(webView) {
            settings.javaScriptEnabled = true
            webViewClient = MyWebViewClient(context, progressBar)
            loadUrl("https://www.androidpro.com.br/blog/")
        }
    }

    //--------------------------------------------------
    // WebView Client
    //--------------------------------------------------

    private class MyWebViewClient(val activity: MainActivity, val progressBar: ProgressDialog): WebViewClient() {
        /**
         * If it returns True, keeps the navigation inside the WebView.
         * Else, when a link is clicked, open Web Browser and leave the WebView.
         */
        override fun shouldOverrideUrlLoading(webView: WebView?, url: String): Boolean {
            if (url.contains("androidpro.com.br")) return false
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            activity.startActivity(intent)
            return true
        }
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            // TODO
        }
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            if (progressBar.isShowing) {
                progressBar.dismiss()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && activity_main__web_view.canGoBack()) {
            activity_main__web_view.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}