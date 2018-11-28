package com.simplewen.win0

import android.content.DialogInterface
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.simplewen.win0.center.fg_center_tm_fg
import com.simplewen.win0.fgs.iwh_fg_right
import kotlinx.android.synthetic.main.activity_fg_center_tm.*

class fg_center_tm : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fg_center_tm)
        setSupportActionBar(toolbar)
        toolbar.title = "题目练习"
        toolbar.setTitleTextColor(Color.WHITE)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val list_fg = arrayListOf<Fragment>()
        //list_fg.add(fg_center_tm_fg())

        val sj_id = intent.getStringExtra("sj_id")
        val iwh_viewPage = findViewById<ViewPager>(R.id.viewPage_tm)
        var iwh_view_page_adapter = iwh_view_page_adapter(supportFragmentManager,list_fg)
        iwh_viewPage.adapter = iwh_view_page_adapter//设置viewpage的adapter

        val temSql = mySql(this@fg_center_tm,"glx",1)
        val db = temSql.writableDatabase
        var sjs = temSql.sjs//存放题目数据集合
        val handle = object : Handler(Looper.getMainLooper()){
            override fun handleMessage(msg: Message?) {

                when(msg!!.what){
                    0 -> {
                        //Toast.makeText(this@fg_center_tm,"题目查询完成\n${temSql.sjs}",Toast.LENGTH_LONG).show()
                        sjs = temSql.sjs

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


                            fg.arguments = arguments

                            list_fg.add(fg)
                        }
                        iwh_view_page_adapter.notifyDataSetChanged()

                        Log.d("tm:",sjs.toString())


                    }
                    1 -> {

                    }
                }
            }
        }
       temSql.wen_query(db,handle,"tm_dx","all")//查询题目
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menu = menuInflater.inflate(R.menu.menu_tool,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                finish()
            }
            R.id.menu_bug -> {
                Toast.makeText(this@fg_center_tm,"请在主界面加群反馈哦",Toast.LENGTH_SHORT).show()
            }

        }
        return super.onOptionsItemSelected(item)
    }
}
