package com.simplewen.win0.app

import android.app.Application
import android.content.Context

class App :Application(){

    companion object {
        var _bt_flag:String = "0"
        var  _context:Application? = null
        fun getContext():Context{
            return _context!!
        }


    }

    override fun onCreate() {
        super.onCreate()
        _context = this
    }

}