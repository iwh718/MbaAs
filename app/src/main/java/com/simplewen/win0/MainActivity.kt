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
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        if(iwhDataOperator.getSHP("versionFlag","version",0) == 0){
            AlertDialog.Builder(this@MainActivity)
                    .setTitle("更新内容！").setMessage(R.string.upDate).create().show()
            iwhDataOperator.setSHP("versionFlag",1,"version")
        }
        val list_fg = arrayListOf<Fragment>()
        ArrayList<Fragment>().addNew(iwh_fg_left(),list_fg).addNew(iwh_fg_center(),list_fg).addNew(iwh_fg_right(),list_fg)
        val  iwh_tab = findViewById<TabLayout>(R.id.tab)
        val iwh_viewPage = findViewById<ViewPager>(R.id.viewPage)
        val iwh_view_page_adapter = iwh_view_page_adapter(supportFragmentManager,list_fg)
        iwh_viewPage.adapter = iwh_view_page_adapter//设置viewpage的adapter
        iwh_tab.setSelectedTabIndicatorColor(Color.WHITE)//tab下划线颜色
        iwh_tab.setTabTextColors(Color.WHITE,Color.WHITE)
        iwh_tab.addTab(iwh_tab.newTab().setText("基础理论与操作题"))
        iwh_tab.addTab(iwh_tab.newTab().setText("选择题库"))
        iwh_tab.addTab(iwh_tab.newTab().setText("我的"))
        iwh_tab.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                iwh_tab.getTabAt(tab.position)?.select()
                iwh_viewPage.setCurrentItem(tab.position,true)
            }
            override fun onTabReselected(tab: TabLayout.Tab) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })

        //绑定搜索按钮
        indexSearchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                val searchIntent = Intent(this@MainActivity,searchTm::class.java)
                if(query!!.isNotEmpty())   searchIntent.putExtra("searchKey",query) else {
                    iwhToast("不可以为空！")
                    return  false
                }
                startActivity(searchIntent)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean = true
        })
        //viewpage事件监听器
        iwh_viewPage.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit
            override fun onPageScrollStateChanged(state: Int) = Unit
            override fun onPageSelected(position: Int) {
                iwh_tab.getTabAt(position)?.select()
            }
        })

        val file = File(ImportDB.DB_PATH + "/glx")//调用伴生对象
        if (file.exists()){
            //内部数据库存在，开始导入外部
            if(iwhDataOperator.getSHP("dbFlag","dbFlag","") == "1"){
                //iwhToast("数据库已经存在！")
                 }else{
                val inDb = ImportDB(this@MainActivity)
                if(inDb.copyDatabase())iwhDataOperator.setSHP("dbFlag","1","dbFlag") else  iwhToast("复制失败！", Gravity.BOTTOM)
            }
        }

        fab.setOnClickListener {
           val dia = AlertDialog.Builder(this@MainActivity)
            dia.setIcon(R.drawable.fab_bg)
                    .setTitle("欢迎你加入我们")
                    .setMessage("群号：726619838")
                    .setPositiveButton("确认"){
                        _,_ ->
                        //iwhJoinQQ()
                        iwhToast("你没事和我聊天不行吗！！")
                    }
                    .setNegativeButton("不了"){_,_ ->}
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
