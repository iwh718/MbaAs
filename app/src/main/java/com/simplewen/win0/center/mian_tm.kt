package com.simplewen.win0.center

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
import com.simplewen.win0.R
import com.simplewen.win0.app.App
import com.simplewen.win0.view.iwh_view_page_adapter
import com.simplewen.win0.modal.mySql
import kotlinx.android.synthetic.main.activity_fg_center_tm.*

class mian_tm : AppCompatActivity(), fg_center_tm_fg.Callbacks {
    override fun onRemove(position: Int) {
        //Log.d("callback:",position.toString())
    }
    var temSql:mySql? = null
    var sjs:ArrayList<Map<String,Any>>? = null
    lateinit var handle: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fg_center_tm)
        App._bt_flag = "0"
        setSupportActionBar(toolbar)
        toolbar.title = "公共基础题目练习"
        toolbar.setTitleTextColor(Color.WHITE)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        temSql = mySql(this@mian_tm, "glx", 1)
        val db = temSql!!.writableDatabase
         sjs = temSql!!.sjs//存放题目数据集合
        val list_fg = arrayListOf<Fragment>()
        val sj_id = intent.getIntExtra("sj_id", 0)
        // if(sj_id!=0) iwhToast(sj_id.toString())
        val fab_type = intent.getStringExtra("fab_type")
        val iwh_viewPage = findViewById<ViewPager>(R.id.viewPage_tm)
        var iwh_view_page_adapter = iwh_view_page_adapter(supportFragmentManager, list_fg)
        iwh_viewPage.adapter = iwh_view_page_adapter//设置viewpage的adapter



        handle = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message?) {

                when (msg!!.what) {
                    0 -> {

                        sjs = temSql!!.sjs

                        for (i in sjs!!.indices) {
                            val fg = fg_center_tm_fg()
                            val content = sjs!![i]
                            val arguments = Bundle()
                            arguments.putString("tm_content", content["content"].toString())
                            arguments.putString("tm_answer", content["answer"].toString())
                            arguments.putString("tm_answer_desc", content["answer_desc"].toString())
                            arguments.putString("tm_a", content["A"].toString())
                            arguments.putString("tm_b", content["B"].toString())
                            arguments.putString("tm_c", content["C"].toString())
                            arguments.putString("tm_d", content["D"].toString())
                            arguments.putString("tm_id", content["id"].toString())
                            arguments.putString("sj_id", content["sj_d"].toString())
                            arguments.putString("fab_type", fab_type)//fab类型，用来删除还是移除收藏
                            arguments.putInt("position", i)//FG下标
                            fg.arguments = arguments
                            list_fg.add(fg)
                        }
                        iwh_view_page_adapter.notifyDataSetChanged()

                    }


                }
            }
        }
        temSql!!.wen_query(db, handle, "tm_dx", "all", sj_id)//查询题目
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_tool, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                AlertDialog.Builder(this@mian_tm).setMessage("如果现在退出，将无法保留进度！")
                        .setPositiveButton("确定") { _, _ ->
                            finish()
                        }
                        .setNegativeButton("继续做题", null)
                        .create().show()
            }
            R.id.menu_bug -> {
               if(App._bt_flag == "0"){
                   AlertDialog.Builder(this@mian_tm).setMessage("背题模式，将会直接显示答案！")
                           .setPositiveButton("确定") { _, _ ->
                               App._bt_flag = "1"
                               viewPage_tm.currentItem = 0
                               sjs!!.clear()
                               with(Message()){
                                   what = 0
                                   handle.sendMessage(this)
                               }
                           }
                           .setNegativeButton("取消", null)
                           .create().show()
               }else{
                   AlertDialog.Builder(this@mian_tm).setMessage("是否关闭背题模式！")
                           .setPositiveButton("确定") { _, _ ->
                               App._bt_flag = "0"
                               viewPage_tm.currentItem = 0
                               sjs!!.clear()
                               with(Message()){
                                   what = 0
                                   handle.sendMessage(this)
                               }
                           }
                           .setNegativeButton("取消", null)
                           .create().show()
               }
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this@mian_tm).setMessage("如果现在退出，将无法保留进度！")
                .setPositiveButton("确定") { _, _ ->
                    finish()
                }
                .setNegativeButton("继续做题", null)
                .create().show()
    }
}
