package com.simplewen.win0

import android.app.Activity
import android.app.Application
import android.content.Context

class App :Application(){
    lateinit var iwhContxt:Context

    override fun onCreate() {
        super.onCreate()
        iwhContxt = applicationContext
    }
    fun getContext():Context{
        return this.iwhContxt
    }
    fun getActivity(): Activity {
        return Activity()
    }
}