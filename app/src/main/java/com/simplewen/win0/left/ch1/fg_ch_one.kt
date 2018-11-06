package com.simplewen.win0.left.ch1

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.simplewen.win0.R

class fg_ch_one :Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val key = arguments.getInt("ch_key")
        var vi = inflater.inflate(R.layout.fg_ch_one,container,false)
        when(key){
            0 -> {
                vi = inflater.inflate(R.layout.fg_ch_one,container,false)
            }
            1 -> {
                vi = inflater.inflate(R.layout.fg_ch_one_1,container,false)
            }
            2 -> {
                vi = inflater.inflate(R.layout.fg_ch_one_2,container,false)
            }
            3 -> {
                vi  = inflater.inflate(R.layout.fg_ch_one_3,container,false)
            }
            4 -> {
                vi = inflater.inflate(R.layout.fg_ch_one_eg,container,false)
            }
        }
       return vi
    }
}