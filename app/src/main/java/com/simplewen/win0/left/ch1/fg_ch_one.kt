package com.simplewen.win0.left.ch1


import android.graphics.Bitmap
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ListView
import android.widget.TextView
import com.simplewen.win0.R

class fg_ch_one :Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val key = arguments.getInt("ch_key")
         var dia2: AlertDialog? =null
        val dia = AlertDialog.Builder(activity).setView(R.layout.loading).setCancelable(false).create()
        var vi = inflater.inflate(R.layout.fg_ch_one,container,false)


        when(key){
            0 -> {

                vi = inflater.inflate(R.layout.fg_ch_one,container,false)
                val web = vi.findViewById<WebView>(R.id.fg_ch_one_web)
                web.settings.javaScriptEnabled = true
                web.loadUrl("https://www.borebooks.top/MAS/MAS_ch_one_1.htm")
                web.webViewClient = object :WebViewClient(){
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

                        dia2 = dia
                        dia.show()
                        super.onPageStarted(view, url, favicon)
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                            dia2?.dismiss()

                           super.onPageFinished(view, url)
                    }
                    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                        return false
                    }

                }
            }
            1 -> {
                vi = inflater.inflate(R.layout.fg_ch_one_1,container,false)
            }
            2 -> {
                vi = inflater.inflate(R.layout.fg_ch_one_2,container,false)
            }
            3 -> {
                vi  = inflater.inflate(R.layout.fg_ch_one_3,container,false)
            }
            4 -> {
                vi = inflater.inflate(R.layout.fg_ch_one_eg,container,false)
            }
        }
       return vi
    }
}