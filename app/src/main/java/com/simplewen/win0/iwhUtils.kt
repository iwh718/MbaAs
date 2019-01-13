package com.simplewen.win0
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*


/**自定义 Toast
 * @param showText 自定义文本
 * @param gravity 自定义位置
 * @param type 风格
 * **/

val TYPE_DEFAULT  = R.color.right
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
fun iwhJoinQQ(){
        val intent = Intent()
        val key="hKgBCQNgklW4c2dHwinkN85CCq-Fvyyg"
        intent.data = Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D$key")
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面 //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
               App.getContext().startActivity(intent)
        } catch (e: Exception) {
               iwhToast("未安装QQ或版本不支持，请手动添加")
        }
}
/**时间转换
 * @param toSeconds 输入毫秒返回日期**/
fun formatTime(toSeconds:String):String{

 return ""
}

/**
 * @param timeFormat 时间格式
 * 指定格式，返回毫秒**/
fun formatTime(timeFormat:String = "yyyy/MM/dd",timeString:String):Long{
        val spd = SimpleDateFormat(timeFormat, Locale.CHINA)
        return spd.parse(timeString).time
}
/**返回倒计时**/
fun getDjs(timeN:String,timeO:String):Int{
        return ((timeN.toLong() - timeO.toLong())/(1000*24*60*60)).toInt()
}