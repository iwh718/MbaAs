package com.simplewen.win0.right
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.simplewen.win0.App
import com.simplewen.win0.R


/**自定义 Toast
 * @param showText 自定义文本
 * @param gravity 自定义位置
 * @param type 风格
 * **/

val TYPE_DEFAULT  = R.color.colorAccent
fun iwhToast(showText:String,gravity:Int = Gravity.BOTTOM,type:Int = TYPE_DEFAULT){

        val iwhType = type
        val iwhContext = App._context
        val iwhToast = Toast.makeText(App.getContext(),showText,Toast.LENGTH_SHORT)
        val iwhLyout = LinearLayout(iwhContext)
        val iwhText = TextView(iwhContext)
        val setParame = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        iwhText.setTextColor(Color.WHITE)
        iwhLyout.layoutParams = setParame
        iwhLyout.setBackgroundResource(type)
        iwhText.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        iwhLyout.addView(iwhText)
        iwhText.setPadding(5,5,5,5)
        iwhText.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        iwhToast.setGravity(Gravity.FILL_HORIZONTAL or gravity,0,0)
        iwhToast.view = iwhLyout
        iwhToast.setMargin(0f,0f)
        iwhText.text = showText
        iwhToast.show()
    }
