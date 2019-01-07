package com.simplewen.win0.right

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.simplewen.win0.R
import com.simplewen.win0.center.fg_center_tm_fg

import com.simplewen.win0.iwh_view_page_adapter
import com.simplewen.win0.mySql
import kotlinx.android.synthetic.main.activity_my_like.*


class myLIke : AppCompatActivity(),fg_center_tm_fg.Callbacks {

    var handle:Handler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_like)
        setSupportActionBar(toolbar)
       toolbar.setTitleTextColor(Color.WHITE)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var temSql = mySql(this@myLIke,"glx",1)
        var db = temSql.writableDatabase
        var sjs = temSql.sjs//存放题目数据集合
        val list_fg = arrayListOf<Fragment>()
        var iwh_view_page_adapter = iwh_view_page_adapter(supportFragmentManager,list_fg)
        val title = findViewById<TextView>(R.id.toobar_tm_title)
        val my_flag = intent.getStringExtra("my_flag")
        if(my_flag == "like"){
            title.text = "我的收藏"
        }else{
            title.text = "我的错题"
        }
        //重写handle
        handle = object : Handler(Looper.getMainLooper()){
            override fun handleMessage(msg: Message?) {

                when(msg!!.what){
                    0 -> {
                      //  Toast.makeText(this@myLIke,"回调方法执行ok",Toast.LENGTH_SHORT).show()
                        //Toast.makeText(this@myLIke,"题目查询完成\n${temSql.sjs}",Toast.LENGTH_LONG).show()
                        sjs = temSql.sjs
                        if(sjs.size == 0){
                            Toast.makeText(this@myLIke,"还没有题目哦",Toast.LENGTH_SHORT).show()
                            return
                        }
                        for (i in sjs.indices){
                            val fg = fg_center_tm_fg()
                            val content = sjs[i]
                            Log.d("sjs[i]:",content["content"].toString())
                            val arguments = Bundle()
                            arguments.putString("tm_content",content["content"].toString())
                            arguments.putString("tm_answer",content["answer"].toString())
                            arguments.putString("tm_answer_desc",content["answer_desc"].toString())
                            arguments.putString("tm_a",content["A"].toString())
                            arguments.putString("tm_b",content["B"].toString())
                            arguments.putString("tm_c",content["C"].toString())
                            arguments.putString("tm_d",content["D"].toString())
                            arguments.putString("tm_id",content["id"].toString())
                            arguments.putString("sj_id",content["sj_d"].toString())
                            arguments.putString("fab_type","delete")//设置fab删除功能
                            arguments.putInt("position",i)//FG下标

                            fg.arguments = arguments

                            list_fg.add(fg)
                        }
                        iwh_view_page_adapter.notifyDataSetChanged()

                        Log.d("myTm:",sjs.toString())


                    }
                    1->{

                        list_fg.removeAt(msg.arg1)
                        Log.d("msg:",msg.arg1.toString())
                        iwh_view_page_adapter.notifyDataSetChanged()
                    }

                }
            }
        }




        //Toast.makeText(this@myLIke,my_flag,Toast.LENGTH_SHORT).show()
        val iwh_viewPage = findViewById<ViewPager>(R.id.viewPage_tm_like)

        iwh_viewPage.adapter = iwh_view_page_adapter//设置viewpage的adapter



        temSql.wen_query(db,handle!!,"tm_dx",my_flag)//查询题目

    }
    //实现回调方法
    override fun onRemove(position:Int) {

        val msg  = Message()
        msg.what = 1
        msg.arg1 = position
        handle?.sendMessage(msg)
        Log.d("callback:",msg.what.toString())

    }


}
