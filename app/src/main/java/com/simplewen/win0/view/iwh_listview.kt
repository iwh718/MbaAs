package com.simplewen.win0.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ListView

class iwh_listview(con: Context):ListView(con){
    constructor(con:Context,attrs:AttributeSet):this(con)
    constructor(con:Context,attrs: AttributeSet,de:Int):this(con)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2, View.MeasureSpec.AT_MOST))
    }
}