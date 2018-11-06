package com.simplewen.win0.fgs


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.simplewen.win0.R


class iwh_fg_right: Fragment(){
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var vi = inflater?.inflate(R.layout.fg_right,container,false)
        return vi
    }
}