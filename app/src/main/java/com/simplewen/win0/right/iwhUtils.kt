package com.simplewen.win0.right
import android.view.Gravity
import android.widget.Toast
import com.simplewen.win0.App
import com.simplewen.win0.R

fun iwhToast(showText:String){
    val iwhToast = Toast(App())
    val iwhLyout = App().getActivity().layoutInflater.inflate(R.layout.iwhtoast,null)
    iwhToast.setGravity(Gravity.BOTTOM,0,0)
    iwhToast.view = iwhLyout
    iwhToast.duration = Toast.LENGTH_SHORT
    iwhToast.setText(showText)
    iwhToast.show()
}