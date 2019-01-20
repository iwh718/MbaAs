package com.simplewen.win0.left.ch


import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.simplewen.win0.R
import com.simplewen.win0.left.PreData
import com.simplewen.win0.iwhToast

/**章节知识的Fragment重写**/
class fg_ch_eg :Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val chKey = arguments.getInt("chKey")
        val sonKey = arguments.getInt("sonKey")
        iwhToast("sonkey:$sonKey")

        var vi = inflater.inflate(R.layout.fg_ch_one,container,false)

        try {
            vi = inflater.inflate(R.layout.fg_ch_one,container,false)
            val web = vi.findViewById<WebView>(R.id.fg_ch_one_web)
            web?.let{
                PreData.webAutoLoad(web,PreData.getPreUrl(chKey,sonKey) )
            }

        } catch (e:NullPointerException){
            iwhToast(e.printStackTrace().toString(), R.color.warn)
        }
       return vi
    }

}