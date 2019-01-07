package com.simplewen.win0.left.ch1

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import com.simplewen.win0.R

class fg_ch_four: Fragment(){
    private val url_ch_three = arrayListOf(
            "https://www.borebooks.top/MAS/MAS_four/MAS_ch_four_1.htm",
            "https://www.borebooks.top/MAS/MAS_four/MAS_ch_four_2.htm",
            "https://www.borebooks.top/MAS/MAS_four/MAS_ch_four_3.htm",
            "https://www.borebooks.top/MAS/MAS_four/MAS_ch_four_eg.htm")
    fun webLoad(web: WebView, urls:String){
        val wt = web.settings
        web.webChromeClient =object : WebChromeClient(){

        }
        wt.setDomStorageEnabled(true)
        wt.allowContentAccess =true
        wt.allowFileAccess = true
        wt.allowFileAccessFromFileURLs = true
        web.loadUrl(urls)
        web.webViewClient = object : WebViewClient(){

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                Toast.makeText(activity,"网络错误", Toast.LENGTH_SHORT).show()
                super.onReceivedError(view, request, error)
            }
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return false
            }

        }

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val key = arguments.getInt("ch_key")

        var vi = inflater.inflate(R.layout.fg_ch_four_1,container,false)
        when(key){
            0 -> {

                vi = inflater.inflate(R.layout.fg_ch_four_1,container,false)
                val web = vi.findViewById<WebView>(R.id.fg_ch_four_web_1)
                webLoad(web,url_ch_three[0])
            }
            1 -> {
                vi = inflater.inflate(R.layout.fg_ch_four_2,container,false)
                val web = vi.findViewById<WebView>(R.id.fg_ch_four_web_2)
                webLoad(web,url_ch_three[1])
            }
            2 -> {
                vi = inflater.inflate(R.layout.fg_ch_four_3,container,false)
                val web = vi.findViewById<WebView>(R.id.fg_ch_four_web_3)
                webLoad(web,url_ch_three[2])
            }
            3 -> {
                vi = inflater.inflate(R.layout.fg_ch_four_eg,container,false)
                val web = vi.findViewById<WebView>(R.id.fg_ch_four_web_eg)
                webLoad(web,url_ch_three[3])
            }

        }
        return vi
    }
}