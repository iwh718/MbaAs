package com.simplewen.win0

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class iwhToast(var context:Context,var showText:String):Toast(context){


    override fun getView(): View {
        return  App().getActivity().layoutInflater.inflate(R.layout.iwhtoast,null)
    }
    override fun setText(s: CharSequence?) {
        super.getView().findViewById<TextView>(R.id.iwhToast_show).text = this.showText
    }

    override fun setGravity(gravity: Int, xOffset: Int, yOffset: Int) {
        super.setGravity(Gravity.BOTTOM, xOffset, yOffset)
    }
}