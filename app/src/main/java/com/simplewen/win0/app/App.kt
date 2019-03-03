package com.simplewen.win0.app

import android.app.Application
import android.content.Context

class App :Application(){

    companion object {
        var _bt_flag:String = "0"
        var  _context:Application? = null
        var _cache:String? = null
        fun getContext():Context{
            return _context!!
        }
        fun getCache():String{
            return _cache!!
        }


    }

    override fun onCreate() {
        super.onCreate()
        _context = this
        _cache = this.externalCacheDir.path
    }

}