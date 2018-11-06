package com.simplewen.win0


import android.app.ActionBar
import android.app.AlertDialog
import android.app.FragmentTransaction
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Adapter
import android.widget.TextView
import android.widget.Toast
import com.simplewen.win0.fgs.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
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

        iwh_tab.setSelectedTabIndicatorColor(Color.parseColor("#185639"))//tab下划线颜色
        iwh_tab.setTabTextColors(Color.WHITE,Color.WHITE)
        iwh_tab.addTab(iwh_tab.newTab().setText("知识体系"))
        iwh_tab.addTab(iwh_tab.newTab().setText("习题册"))
        iwh_tab.addTab(iwh_tab.newTab().setText("知识导图"))
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



        fab.setOnClickListener { _ ->
           val dia = AlertDialog.Builder(this@MainActivity)
            dia.setIcon(R.drawable.fab_bg)
                    .setTitle("欢迎你加入我们")
                    .setMessage("群号：726619838")
                    .setPositiveButton("确认"){
                        _,_ ->
                        Toast.makeText(this@MainActivity,"欢迎哦",Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("不了"){
                        _,_ ->
                        Toast.makeText(this@MainActivity,"好吧",Toast.LENGTH_SHORT).show()
                    }
                    .create().show()

        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)




    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            val intent = Intent()
            intent.action = Intent.ACTION_MAIN
            intent.addCategory(Intent.CATEGORY_HOME)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_share -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_manage -> {
                // Handle the camera action
            }
            R.id.nav_iwh_like -> {

            }
            R.id.nav_iwh_error -> {

            }

            R.id.nav_iwh_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}
