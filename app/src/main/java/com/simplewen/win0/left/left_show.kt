package com.simplewen.win0.left

import android.content.res.Configuration
import android.support.v4.app.Fragment
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View


import android.widget.Toast
import com.simplewen.win0.R
import com.simplewen.win0.iwh_view_page_adapter
import com.simplewen.win0.left.jc.*
import kotlinx.android.synthetic.main.activity_left_show.*

/**首页左侧知识体系->功能首页->控制Fg输出**/
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
        val ori =resources.configuration .orientation //获取屏幕方向
        if (ori ==  Configuration.ORIENTATION_LANDSCAPE) {
            //横屏
            toolbar_left_show.visibility = View.GONE
            findViewById<TabLayout>(R.id.left_show_tab).visibility = View.GONE
        } else {
            //竖屏
            toolbar_left_show.visibility = View.VISIBLE
            left_show_tab.visibility  =  View.VISIBLE
        }

        /**添加 tab 面板
         * @param chKey 篇章序号
         * **/
        fun addTab(chKey:Int) {
                        for (ii in 0..2) {
                            val fg = fg_ch_eg()
                            val args = Bundle()
                            args.putInt("chKey",chKey)
                            args.putInt("sonKey",ii)
                            fg.arguments = args
                            fg_list.add(ii, fg)
                        }

            tab.addTab(tab.newTab().setText("基础知识"))
            tab.addTab(tab.newTab().setText("视频教程"))
            tab.addTab(tab.newTab().setText("真考例题"))

            }
            val gintent = intent
            //接收活动数据
           // Log.d("intent", gintent.getIntExtra("key", 0).toString())

            /** nkey 主页面传来的下标，区分类型0：word,1:excel,2:ppt**/
            val nkey = gintent.getIntExtra("key", 10)
            when (nkey) {
                10 -> {
                    Toast.makeText(this@left_show, "出现错误", Toast.LENGTH_SHORT).show()
                }
                else ->{
                    supportActionBar?.title = PreData.chTitle[nkey]["title"].toString()
                    addTab(nkey)
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
