package com.simplewen.win0.left


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.simplewen.win0.R

class iwh_fg_left: Fragment(){

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val vi = inflater?.inflate(R.layout.fg_left,container,false)
        val itemsV = arrayListOf<LinearLayout>()//存放itemsView
        val (item1,item5,item6) = arrayOf<LinearLayout>(vi!!.findViewById(R.id.left_list_item1),vi.findViewById(R.id.left_list_item5),
                vi.findViewById(R.id.left_list_item6))
        //word，ppt，excel 学习
        itemsV.add(vi.findViewById(R.id.left_list_item2))
        itemsV.add(vi.findViewById(R.id.left_list_item3))
        itemsV.add(vi.findViewById(R.id.left_list_item4))


        //设置监听器
        for (i in itemsV.indices){
            itemsV[i].setOnClickListener{
                val intent = Intent(activity,left_show::class.java)
                intent.putExtra("key",i)
                activity.startActivity(intent)
            }
        }

        //其它
        val listenerOthers  = View.OnClickListener{
            when(it.id){
               R.id.left_list_item1 -> {

               }
                R.id.left_list_item5 ->{

                }
                R.id.left_list_item6 ->{

                }
            }
        }
        //设置监听器
        item1.setOnClickListener(listenerOthers)//计算机基础
        item5.setOnClickListener(listenerOthers)//考试大纲
        item6.setOnClickListener(listenerOthers)//考试注意

        return vi
    }
}