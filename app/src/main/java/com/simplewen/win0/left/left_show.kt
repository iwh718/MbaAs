package com.simplewen.win0.left

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Toast
import com.simplewen.win0.R

class left_show : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_left_show)
        val tbar = findViewById<Toolbar>(R.id.toolbar_left_show)
        setSupportActionBar(tbar)
        actionBar?.setDisplayHomeAsUpEnabled(true)



        fun toa(st:String){
            Toast.makeText(this@left_show,"${st}",Toast.LENGTH_SHORT).show()
        }
        val gintent = intent
        //接收活动数据
        Log.d("intent",gintent.getIntExtra("key",0).toString())
        val nkey = gintent.getIntExtra("key",10)//默认为0
        when(nkey){
            10 -> {
                Toast.makeText(this@left_show,"没有收到key",Toast.LENGTH_SHORT).show()
            }
            0 -> {
                tbar.title = "管理学原理概述"
                val items = arrayOf("管理活动","中外早期管理思想","管理理论形成与发展")
                val dia  = AlertDialog.Builder(this@left_show)
                        .setTitle("选择章节")
                        .setItems(items){
                            _,which ->
                            when(which){
                                0 -> {
                                   toa("管理活动")
                                }
                                1 -> {
                                    toa("中外")
                                }
                                2 -> {
                                    toa("管理理论形成与发展")
                                }
                            }
                        }
                        .setCancelable(false)
                        .setIcon(R.drawable.fab_bg)
                        .create().show()
            }
            1 -> {
                tbar.title = "管理学理论发展"
                Toast.makeText(this@left_show,"${nkey}",Toast.LENGTH_SHORT).show()
            }
            2 -> {
                Toast.makeText(this@left_show,"${nkey}",Toast.LENGTH_SHORT).show()
            }

            else -> {
                Toast.makeText(this@left_show,"ok",Toast.LENGTH_SHORT).show()
            }
        }


    }

}
