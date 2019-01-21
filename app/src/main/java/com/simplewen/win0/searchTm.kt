package com.simplewen.win0

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.constraint.Constraints
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_search_tm.*
import kotlinx.android.synthetic.main.fg_center_tm.*

class searchTm : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_tm)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "搜索题目"
        toolbar.setTitleTextColor(Color.WHITE)
        val temSql = mySql(this@searchTm, "glx", 1)
        var sjs = temSql.sjs
        val db = temSql.writableDatabase
        var adapter = SimpleAdapter(this@searchTm, sjs, R.layout.index_search_listview, arrayOf("content"), intArrayOf(R.id.search_tm_content))
        indexSearchListView?.adapter = adapter
        val handle = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message?) {
                when (msg!!.what) {
                    0 -> {
                        sjs = temSql.sjs
                        adapter.notifyDataSetChanged()
                        if (sjs.size < 1) iwhToast("没有这个题目哦！")
                    }
                }
            }
        }
        indexSearchListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            // iwhToast("你点击是：${sjs[position]}")
            val showTmLy = LinearLayout.inflate(this@searchTm, R.layout.fg_center_tm, null)
            with(showTmLy) {
                findViewById<TextView>(R.id.tm_content).text = sjs[position]["content"].toString()
                findViewById<TextView>(R.id.tm_answer_desc).text = sjs[position]["answer_desc"].toString()
                findViewById<TextView>(R.id.tm_answer_right).text = sjs[position]["answer"].toString()
                findViewById<RadioButton>(R.id.tm_a).text = sjs[position]["A"].toString()
                findViewById<RadioButton>(R.id.tm_b).text = sjs[position]["B"].toString()
                findViewById<RadioButton>(R.id.tm_c).text = sjs[position]["C"].toString()
                findViewById<RadioButton>(R.id.tm_d).text = sjs[position]["D"].toString()
                findViewById<Switch>(R.id.tm_answer_btn).visibility = View.GONE
                findViewById<FloatingActionButton>(R.id.my_like).visibility = View.GONE
                findViewById<LinearLayout>(R.id.tm_answer_box).visibility = View.VISIBLE
            }

            AlertDialog.Builder(this@searchTm)
                    .setView(showTmLy).create().show()
        }
        intent.getStringExtra("searchKey")?.let {
            temSql.wen_query(db, handle, "tm_dx", it)
        }
    }
}
