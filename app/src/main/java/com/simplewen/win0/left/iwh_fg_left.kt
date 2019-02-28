package com.simplewen.win0.left


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.simplewen.win0.R
import com.simplewen.win0.left.view.jcShow

class iwh_fg_left: Fragment(){

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val vi = inflater?.inflate(R.layout.fg_left,container,false)
        val itemsV = arrayListOf<LinearLayout>()//存放itemsView
        //word，ppt，excel 学习
        itemsV.add(vi!!.findViewById(R.id.left_list_item2))
        itemsV.add(vi.findViewById(R.id.left_list_item3))
        itemsV.add(vi.findViewById(R.id.left_list_item4))
        //设置监听器
        for (i in itemsV.indices){
            itemsV[i].setOnClickListener{
                val intent = Intent(activity,left_show::class.java)
                intent.putExtra("key",i)
                activity.startActivity(intent)
               // iwhToast("暂未开放！")
            }
        }

        //其它
        val listenerOthers  = View.OnClickListener{
            when(it.id){
               R.id.left_list_item1 -> {
                    AlertDialog.Builder(activity)
                            .setItems(PreData.jcItems){
                                _,which ->
                                AlertDialog.Builder(activity)
                                        .setItems(PreData.jcC[which]){
                                            _,whichIn ->
                                            val intent = Intent(activity,jcShow::class.java)
                                            intent.putExtra("jcKey",PreData.jcC[which][whichIn])
                                            startActivity(intent)
                                        }.setTitle("选择小节")
                                        .create().show()
                            }.setTitle("选择篇章").create().show()
               }
                R.id.left_list_item5 ->{
                    val intent = Intent(activity,jcShow::class.java)
                    intent.putExtra("jcKey","大纲")
                    startActivity(intent)
                }
                R.id.left_list_item6 ->{
                    AlertDialog.Builder(activity)
                            .setItems(PreData.jcItems2){
                                _,which ->
                                val intent = Intent(activity,jcShow::class.java)
                                intent.putExtra("jcKey",PreData.jcItems2[which])
                                startActivity(intent)
                            }.setTitle("选择篇章").create().show()
                }
            }
        }
        //设置监听器
        vi.findViewById<LinearLayout>(R.id.left_list_item1).setOnClickListener(listenerOthers)//计算机基础
        vi.findViewById<LinearLayout>(R.id.left_list_item5).setOnClickListener(listenerOthers)//考试大纲
        vi.findViewById<LinearLayout>(R.id.left_list_item6).setOnClickListener(listenerOthers)//考试注意
        return vi
    }
}