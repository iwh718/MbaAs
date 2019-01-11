package com.simplewen.win0.left

import android.support.v4.app.Fragment
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem


import android.widget.Toast
import com.simplewen.win0.R
import com.simplewen.win0.iwh_view_page_adapter
import com.simplewen.win0.left.ch.*

class left_show : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_left_show)
        val tbar = findViewById<Toolbar>(R.id.toolbar_left_show)
        setSupportActionBar(tbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val tab = findViewById<TabLayout>(R.id.left_show_tab)
        val viewPage = findViewById<ViewPager>(R.id.left_show_viewpage)//获取vpg
        var fg_list = arrayListOf<Fragment>()
        var vpgAdapter = iwh_view_page_adapter(supportFragmentManager, fg_list)
        tab.setTabTextColors(Color.WHITE, Color.WHITE)
        tab.setSelectedTabIndicatorColor(Color.parseColor("#185639"))//tab下划线颜色
        tab.isScrollContainer

        /**添加 tab 面板
         * @param n 章节数
         * @param chKey 篇章序号
         * **/
        fun addTab(n: Int,chKey:Int) {
                        for (ii in 0..3) {
                            val fg = fg_ch_eg()
                            val args = Bundle()
                            args.putInt("chSonKey", ii)
                            args.putInt("chKey",chKey)
                            fg.arguments = args
                            fg_list.add(ii, fg)
                        }
            tab.addTab(tab.newTab().setText("基础知识"))
            tab.addTab(tab.newTab().setText("视频教程"))
            tab.addTab(tab.newTab().setText("操作例题"))

            }
            val gintent = intent
            //接收活动数据
            Log.d("intent", gintent.getIntExtra("key", 0).toString())
            val nkey = gintent.getIntExtra("key", 10)
            when (nkey) {
                10 -> {
                    Toast.makeText(this@left_show, "出现错误", Toast.LENGTH_SHORT).show()
                }
                else ->{
                    supportActionBar?.title = PreData.chTitle[nkey]["title"].toString()
                    addTab(PreData.chTitle[nkey]["chNum"].toString().toInt(), nkey+1)
                }

            }
                vpgAdapter.notifyDataSetChanged()
                viewPage.adapter = vpgAdapter
            //fab 按钮
            findViewById<FloatingActionButton>(R.id.fg_ch_fab).setOnClickListener {
                finish()
            }
            //ViewPage 监听器
            viewPage.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) = Unit
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit
                override fun onPageSelected(position: Int) {
                    tab.getTabAt(position)?.select()
                }
            })
            //tab 监听器
            tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    viewPage.currentItem = tab!!.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
                override fun onTabReselected(tab: TabLayout.Tab?) = Unit
            })
        }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }

}
