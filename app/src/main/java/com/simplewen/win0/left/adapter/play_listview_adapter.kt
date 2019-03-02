package com.simplewen.win0.left.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.simplewen.win0.R
import com.simplewen.win0.app.App
import com.simplewen.win0.left.modal.PreData

/**
 * 播放列表适配器
 */
class play_listview_adapter(private  val chNum:Int,val chType:Int):BaseAdapter(){
   init {

   }
    override fun getCount(): Int {

       return chNum
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
      return chNum.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
      return {
           val vi = LinearLayout.inflate(App.getContext(), R.layout.play_listview,null)
          //设置标题
          vi.findViewById<TextView>(R.id.playNum).text = PreData.jcTitles[chType][position]
          vi

       }()

    }
}