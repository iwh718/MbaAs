package com.simplewen.win0.main

import android.app.AlertDialog
import android.content.Intent
import android.didikee.donate.AlipayDonate
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.simplewen.win0.R
import com.simplewen.win0.app.iwhDataOperator
import com.simplewen.win0.app.iwhJoinQQ
import com.simplewen.win0.app.iwhToast
import com.simplewen.win0.center.iwh_fg_center
import com.simplewen.win0.left.view.fragment.iwh_fg_left
import com.simplewen.win0.modal.ImportDB
import com.simplewen.win0.modal.mySql
import com.simplewen.win0.right.iwh_fg_right
import com.simplewen.win0.view.iwh_view_page_adapter
import kotlinx.android.synthetic.main.app_bar_main.*
import java.io.File
class MainActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        val temSql = mySql(this@MainActivity, "glx", 1)//创建数据
        temSql.writableDatabase
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if(iwhDataOperator.getSHP("versionFlag","version",0) <= 8){
            AlertDialog.Builder(this@MainActivity)
                    .setTitle("更新内容！").setMessage(R.string.upDate).create().show()
            iwhDataOperator.setSHP("versionFlag",9,"version")
        }
        val list_fg = arrayListOf<Fragment>(iwh_fg_left(),iwh_fg_center(),iwh_fg_right())
        val  iwh_tab = findViewById<TabLayout>(R.id.tab)
        val iwh_viewPage = findViewById<ViewPager>(R.id.viewPage)
        val iwh_view_page_adapter = iwh_view_page_adapter(supportFragmentManager, list_fg)
        iwh_viewPage.adapter = iwh_view_page_adapter//设置viewpage的adapter
        with(iwh_tab){
           setSelectedTabIndicatorColor(Color.WHITE)//tab下划线颜色
           setTabTextColors(Color.WHITE,Color.WHITE)
           addTab(this.newTab().setText("基础理论与视频"))
           addTab(this.newTab().setText("选择题库"))
           addTab(this.newTab().setText("我的"))
        }

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
                val searchIntent = Intent(this@MainActivity, searchTm::class.java)
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
                if(inDb.copyDatabase()) iwhDataOperator.setSHP("dbFlag","1","dbFlag") else iwhToast("复制失败！", Gravity.BOTTOM)
            }
        }

        fab.setOnClickListener {
           val db =   mySql(this@MainActivity,"glx",1).writableDatabase

           val dia = AlertDialog.Builder(this@MainActivity)
            dia.setIcon(R.drawable.fab_bg).setTitle("加入QQ群一起交流！").setMessage("群号：959281938")
                    .setPositiveButton("确认"){ _,_ ->
                        iwhJoinQQ()
                    }.setNegativeButton("不了"){_,_ ->}.create().show()
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
                textIntent.putExtra(Intent.EXTRA_TEXT, "Ms-Office助手:https://www.coolapk.com/apk/com.simplewen.win0")
                startActivity(Intent.createChooser(textIntent, "分享Ms助手给小伙伴！"))
            }
            R.id.action_about -> {
                val ab = layoutInflater.inflate(R.layout.about,null)
                ab.findViewById<TextView>(R.id.toStar).setOnClickListener{
                    val uri = Uri.parse("https://github.com/iwh718/MbaAs")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
                ab.findViewById<TextView>(R.id.likeIwh).setOnClickListener{
                                val payCode="FKX03272QHJKIU7YQ2VS68"
                                val hasInstalledAlipayClient = AlipayDonate.hasInstalledAlipayClient(this)
                                if (hasInstalledAlipayClient) {
                                    AlipayDonate.startAlipayClient(this, payCode)
                                    iwhToast("感谢您的支持！")
                                }else{
                                    iwhToast("您未安装支付宝哦！")
                                }

                }
               AlertDialog.Builder(this@MainActivity)
                .setView(ab).create().show()
            }
        }
        return true
    }



}
