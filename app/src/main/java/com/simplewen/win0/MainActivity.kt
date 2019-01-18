package com.simplewen.win0


import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import com.simplewen.win0.center.iwh_fg_center
import com.simplewen.win0.left.iwh_fg_left
import com.simplewen.win0.right.iwh_fg_right
import kotlinx.android.synthetic.main.app_bar_main.*
import java.io.File

class MainActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        val temSql = mySql(this@MainActivity,"glx",1)//创建数据
        temSql.writableDatabase
        val hand: Handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                when (msg?.what) {
                    0 -> {
                        iwhToast("复制成功！", Gravity.BOTTOM)

                    }
                    1 -> {
                        iwhToast("复制失败！", Gravity.BOTTOM)
                    }
                    2 -> {
                        iwhToast("数据库存在", Gravity.BOTTOM)
                    }
                    3 -> {
                        iwhToast("数据库不存在！", Gravity.BOTTOM)
                    }
                    4 -> {
                        iwhToast("查询完成！", Gravity.BOTTOM)
                    }

                }
            }

        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val search_box = findViewById<CardView>(R.id.card_search)//搜索快
        val search = findViewById<SearchView>(R.id.searchView)//搜索按钮
        val list_fg = arrayListOf<Fragment>()
        val fg_left = iwh_fg_left()
        val fg_center = iwh_fg_center()
        val fg_right = iwh_fg_right()
        list_fg.add(fg_left)
        list_fg.add(fg_center)
        list_fg.add(fg_right)
        val  iwh_tab = findViewById<TabLayout>(R.id.tab)
        val iwh_viewPage = findViewById<ViewPager>(R.id.viewPage)
        var iwh_view_page_adapter = iwh_view_page_adapter(supportFragmentManager,list_fg)
        iwh_viewPage.adapter = iwh_view_page_adapter//设置viewpage的adapter

        iwh_tab.setSelectedTabIndicatorColor(Color.WHITE)//tab下划线颜色
        iwh_tab.setTabTextColors(Color.WHITE,Color.WHITE)
        iwh_tab.addTab(iwh_tab.newTab().setText("知识体系"))
        iwh_tab.addTab(iwh_tab.newTab().setText("公共基础"))
        iwh_tab.addTab(iwh_tab.newTab().setText("其它"))
        //iwh_tab.setupWithViewPager(iwh_viewPage)//将viewpage与tablayout绑定一起
        iwh_tab.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                iwh_tab.getTabAt(tab.position)?.select()
                iwh_viewPage.setCurrentItem(tab.position,true)
            }
            override fun onTabReselected(tab: TabLayout.Tab) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })
        //viewpage事件监听器
        iwh_viewPage.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageSelected(position: Int) {
                iwh_tab.getTabAt(position)?.select()
            }
        })


        val file = File(ImportDB.DB_PATH + "/glx")//调用伴生对象
        Log.d("here",ImportDB.DB_PATH)
        val share = getSharedPreferences("dbFlag", Activity.MODE_PRIVATE)

        if (file.exists()){
            //内部数据库存在，开始导入外部
           // Toast.makeText(this@MainActivity, "存在内部数据库", Toast.LENGTH_SHORT).show()
            if(share.getString("dbFlag","0") == "1"){
              //  Toast.makeText(this@MainActivity, "已经导入过数据库", Toast.LENGTH_SHORT).show()
                 }else{
                val inDb = ImportDB(this@MainActivity)
                if(inDb.copyDatabase()){
                   // Toast.makeText(this@MainActivity, "复制完成", Toast.LENGTH_SHORT).show()
                    val shareP = getSharedPreferences("dbFlag", Activity.MODE_PRIVATE)
                    val edit = shareP.edit()
                    edit.putString("dbFlag","1")//导入完成，设置标志
                    edit.apply()
                }else{
                    iwhToast("复制失败！", Gravity.BOTTOM)
                }
            }


        }

        fab.setOnClickListener { _ ->
           val dia = AlertDialog.Builder(this@MainActivity)
            dia.setIcon(R.drawable.fab_bg)
                    .setTitle("欢迎你加入我们")
                    .setMessage("群号：726619838")
                    .setPositiveButton("确认"){
                        _,_ ->
                        //iwhJoinQQ()
                        iwhToast("你没事和我聊天不行吗！！")
                    }
                    .setNegativeButton("不了"){
                        _,_ ->
                       // Toast.makeText(this@MainActivity,"好吧",Toast.LENGTH_SHORT).show()
                    }
                    .create().show()

        }

    }

    override fun onBackPressed() {

            val intent = Intent()
            intent.action = Intent.ACTION_MAIN
            intent.addCategory(Intent.CATEGORY_HOME)
            startActivity(intent)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_share ->
            {
                val textIntent = Intent(Intent.ACTION_SEND)
                textIntent.type = "text/plain"
                textIntent.putExtra(Intent.EXTRA_TEXT, "Ms-Office助手:https://www.coolapk.com/apk/com.simplewen.win0.wd")
                startActivity(Intent.createChooser(textIntent, "分享Ms助手给小伙伴！"))
            }
            R.id.action_about -> {
                val ab = layoutInflater.inflate(R.layout.about,null)
                val about_dia = AlertDialog.Builder(this@MainActivity)
                about_dia.setView(ab).create().show()
            }




        }
        return true
    }



}
