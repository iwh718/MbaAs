package com.simplewen.win0.left.view


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView

import android.widget.ListView
import com.simplewen.win0.R
import com.simplewen.win0.app.iwhToast
import com.simplewen.win0.left.PlayVideo
import com.simplewen.win0.left.PreData
import com.simplewen.win0.left.adapter.play_listview_adapter

/**章节视频的Fragment重写**/
class fg_ch_play_list : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val adapterPlayListView:play_listview_adapter?

        val chType = arguments.getInt("chType")//类型
        var chNum:Int = PreData.getNum(chType)
        adapterPlayListView = play_listview_adapter(chNum)
        var vi = inflater.inflate(R.layout.video_play_listview,container,false)
        try {
       with(vi.findViewById<ListView>(R.id.left_play_listview)){
           adapter = adapterPlayListView
             onItemClickListener = AdapterView.OnItemClickListener{
                 _,_,positio,_->
                 iwhToast("你点击的是$positio")
                 Intent(activity,PlayVideo::class.java).apply {
                     putExtra("chType",chType)
                     putExtra("chNum",chNum)
                     startActivity(this)
                 }
             }
         }

        } catch (e:NullPointerException){
            iwhToast(e.printStackTrace().toString(), R.color.warn)
        }
        return vi
    }

}