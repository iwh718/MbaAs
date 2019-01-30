package com.simplewen.win0.view
import kotlin.collections.arrayListOf
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.View
import android.view.ViewGroup
/**重写viewPge中的Fragment**/
class iwh_view_page_adapter(fm:FragmentManager,fg_list:ArrayList<Fragment>): FragmentStatePagerAdapter(fm){
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



    override fun getItemPosition(`object`: Any?): Int {
            return PagerAdapter.POSITION_NONE
    }

    override  fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        super.destroyItem(container, position, `object`)

    }


}