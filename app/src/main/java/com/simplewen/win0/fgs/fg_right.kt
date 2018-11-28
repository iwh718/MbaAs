package com.simplewen.win0.fgs


import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import com.simplewen.win0.R
import com.simplewen.win0.right.myLIke


class iwh_fg_right: Fragment(){
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var vi = inflater?.inflate(R.layout.fg_right,container,false)
        val myLike = vi!!.findViewById<TextView>(R.id.fg_right_my_like)
        val myError = vi.findViewById<TextView>(R.id.fg_right_my_error)
        myLike.setOnClickListener {

            val intent  = Intent(activity,myLIke::class.java)
            intent.putExtra("my_flag","like")
            startActivity(intent)
        }
        myError.setOnClickListener {
            val intent  = Intent(activity,myLIke::class.java)
            intent.putExtra("my_flag","error")
            startActivity(intent)
        }




        return vi
    }
}