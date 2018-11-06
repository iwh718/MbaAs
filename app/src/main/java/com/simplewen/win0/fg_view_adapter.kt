package com.simplewen.win0
import kotlin.collections.arrayListOf
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class iwh_view_page_adapter(fm:FragmentManager,fg_list:ArrayList<Fragment>):FragmentPagerAdapter(fm){
    private var listFg = arrayListOf<Fragment>()
    init {
        listFg = fg_list
    }
    override fun getCount(): Int {
        return listFg.size
    }
    override fun getItem(position: Int): Fragment {
        return listFg[position]
    }
}