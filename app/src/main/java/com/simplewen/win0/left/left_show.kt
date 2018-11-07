package com.simplewen.win0.left

import android.support.v4.app.Fragment
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.ViewParent


import android.widget.Toast
import com.simplewen.win0.R
import com.simplewen.win0.iwh_view_page_adapter
import com.simplewen.win0.left.ch1.*

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
        var vpgAdapter = iwh_view_page_adapter(supportFragmentManager,fg_list)
       // var fg = fg_ch_one()

        tab.setTabTextColors(Color.WHITE,Color.WHITE)
        tab.setSelectedTabIndicatorColor(Color.parseColor("#185639"))//tab下划线颜色
        tab.isScrollContainer
        fun toa(st:String){
            Toast.makeText(this@left_show,"${st}",Toast.LENGTH_SHORT).show()
        }
        fun addTab(n:Int,ch:Int){

          when(ch){
              1 -> {
                  for(i in 0 until n+1){
                      var fg = fg_ch_one()
                      var args = Bundle()
                      args.putInt("ch_key",i)
                      fg.arguments = args
                      fg_list.add(i,fg)
                      tab.addTab(tab.newTab().setText("章节${i+1}"))
                  }
                    tab.getTabAt(n)?.setText("案例")
              }
              2 -> {
                  for(i in 0 until n+1){
                      var fg = fg_ch_two()
                      var args = Bundle()
                      args.putInt("ch_key",i)
                      fg.arguments = args
                      fg_list.add(fg)
                      tab.addTab(tab.newTab().setText("章节${i+1}"))
                  }
                  tab.getTabAt(n)?.setText("案例")


              }
              3 -> {
                  for(i in 0 until n+1){
                      var fg = fg_ch_three()
                      var args = Bundle()
                      args.putInt("ch_key",i)
                      fg.arguments = args
                      fg_list.add(fg)
                      tab.addTab(tab.newTab().setText("章节${i+1}"))
                  }
                  tab.getTabAt(n)?.setText("案例")
              }
              4 -> {
                  for(i in 0 until n+1){
                      var fg = fg_ch_four()
                      var args = Bundle()
                      args.putInt("ch_key",i)
                      fg.arguments = args
                      fg_list.add(fg)
                      tab.addTab(tab.newTab().setText("章节${i+1}"))
                  }
                  tab.getTabAt(n)?.setText("案例")
              }
              5 -> {
                  for(i in 0 until n+1){
                      var fg = fg_ch_five()
                      var args = Bundle()
                      args.putInt("ch_key",i)
                      fg.arguments = args
                      fg_list.add(fg)
                      tab.addTab(tab.newTab().setText("章节${i+1}"))
                  }
                  tab.getTabAt(n)?.setText("案例")
              } 6 -> {
              for(i in 0 until n+1){
                  var fg = fg_ch_six()
                  var args = Bundle()
                  args.putInt("ch_key",i)
                  fg.arguments = args
                  fg_list.add(fg)
                  tab.addTab(tab.newTab().setText("章节${i+1}"))
              }
              tab.getTabAt(n)?.setText("案例")
          }
          }
        }
        val gintent = intent
        //接收活动数据
        Log.d("intent",gintent.getIntExtra("key",0).toString())
        val nkey = gintent.getIntExtra("key",10)//默认为0
        when(nkey){
            10 -> {
                Toast.makeText(this@left_show,"出现错误",Toast.LENGTH_SHORT).show()
            }
            0 -> {
                supportActionBar?.title = "管理学总论"
                addTab(4,1)
                vpgAdapter.notifyDataSetChanged()
                viewPage.adapter = vpgAdapter

            }
            1 -> {
                supportActionBar?.title = "决策与计划"
                addTab(3,2)
                vpgAdapter.notifyDataSetChanged()
                viewPage.adapter = vpgAdapter
            }
            2 -> {
                supportActionBar?.title = "组织"
                addTab(3,3)
                vpgAdapter.notifyDataSetChanged()
                viewPage.adapter = vpgAdapter
            }
            3 -> {
                supportActionBar?.title = "领导"
                addTab(3,4)
                vpgAdapter.notifyDataSetChanged()
                viewPage.adapter = vpgAdapter

            }
            4 -> {
                supportActionBar?.title = "控制"
                addTab(2,5)
                vpgAdapter.notifyDataSetChanged()
                viewPage.adapter = vpgAdapter
            }
            5 -> {
                supportActionBar?.title = "创新"
                addTab(3,6)
                vpgAdapter.notifyDataSetChanged()
                viewPage.adapter = vpgAdapter
            }

        }
        findViewById<FloatingActionButton>(R.id.fg_ch_fab).setOnClickListener{
            Toast.makeText(this@left_show,"添加收藏？",Toast.LENGTH_SHORT).show()
        }
        viewPage.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //
            }
            override fun onPageSelected(position: Int) {
                tab.getTabAt(position)?.select()
            }

            override fun onPageScrollStateChanged(state: Int) {
                //
            }
        })
        tab.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPage.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                //
            }
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
