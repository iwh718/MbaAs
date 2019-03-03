package com.simplewen.win0.app

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.AsyncTask
import android.support.v4.app.Fragment
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.simplewen.win0.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**顶级函数
 * 该项目使用的各种工具函数
 * author：iwh
 * time：2018.12.01
 * **/



/**自定义 Toast
 * @author iwh
 * @param showText 自定义文本
 * @param gravity 自定义位置
 * @param type 风格
 * **/

fun iwhToast(showText: String, gravity: Int = Gravity.BOTTOM, type: Int = R.color.right) {

    val iwhContext = App._context
    val setParame = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    val iwhText = TextView(iwhContext).apply {
        setTextColor(Color.WHITE)
        textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        setPadding(5, 5, 5, 5)
        layoutParams = setParame

    }
    val iwhLyout = LinearLayout(iwhContext).apply {
        layoutParams = setParame
        setBackgroundResource(type)
        addView(iwhText)
    }
   with( Toast.makeText(App.getContext(), showText, Toast.LENGTH_SHORT)){
        setGravity(Gravity.FILL_HORIZONTAL or gravity, 0, 0)
        view = iwhLyout
        setMargin(0f, 0f)
        iwhText.text = showText
        show()
    }

}

/**添加QQ群
 * @param qqGroupKey 群key需要申请
 * **/
fun iwhJoinQQ(qqGroupKey: String = "nj4Jn2uG6kfAkS0HmGZJBzq6h5P01IJP") {
    val intent = Intent()
    intent.data = Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D$qqGroupKey")
    // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面 //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    try {
        App.getContext().startActivity(intent)
    } catch (e: Exception) {
        iwhToast("未安装QQ或版本不支持，请手动添加")
    }
}

/**时间转换
 * @param toSeconds 输入毫秒返回日期**/
fun formatTime(toSeconds: String): String {

    return ""
}

/**
 * @param timeFormat 时间格式
 * @return 指定格式，返回毫秒**/
fun formatTime(timeFormat: String = "yyyy/MM/dd", timeString: String): Long {
    val spd = SimpleDateFormat(timeFormat, Locale.CHINA)
    return spd.parse(timeString).time
}

/**返回倒计时
 * @param timeN 新时间
 * @param timeO 旧时间
 * @return 返回时差
 * **/
fun getDjs(timeN: String, timeO: String): Int {
    return ((timeN.toLong() - timeO.toLong()) / (1000 * 24 * 60 * 60)).toInt()
}

/**扩展ArrayList链式调用添加
 * @param addFg 添加的Fragment
 * @param parentFg 添加到容器
 * @return 返回ArrayLIst
 * **/
fun ArrayList<Fragment>.addNew(addFg: Fragment, parentFg: ArrayList<Fragment>): ArrayList<Fragment> {
    parentFg.add(addFg)
    return parentFg
}

/**扩展日志
 * @param level 日志等级
 * @param logText 日志
 * **/
fun Log.showLog(level: String = "D", logText: String = "@@here!") {
    when (level) {
        "D" -> {
            Log.d("@@logD:", logText)
            iwhToast(logText)
        }
        "W" -> {
            Log.w("@@logW:", logText)
            iwhToast(logText)
        }
        "E" -> {
            Log.e("@@logW:", logText)
            iwhToast(logText)
        }

    }
}

/**数据操作类
@author:iwh
@time:2019.01.10
 **/
class iwhDataOperator {

    companion object {
        /**操作SharePreferences
         * @param saveKey 存储键
         * @param saveText 存储数据
         * @param shpName 指定文件
         * @return 返回伴生对象
         * **/
        fun <T> setSHP(saveKey: String, saveText: T, shpName: String): Companion {
            //打开指定文件
            val SHP_Text = App.getContext().getSharedPreferences(shpName, Activity.MODE_PRIVATE)
            when (saveText) {
                is Int -> {
                    //存放整型数据
                    SHP_Text.edit().putInt(saveKey, saveText).apply()
                }
                //存放字符型数据
                is String -> {
                    SHP_Text.edit().putString(saveKey, saveText).apply()
                }
                else -> {
                    throw Throwable("类型不匹配！")
                }

            }
            //链式调用
            return Companion
        }

        /**获取私有数据
         * @param getKey 获取指定文件指定键
         * @param type 取出指定类型值
         * @param shpName 获取指定文件**/
        fun <T> getSHP(getKey: String, shpName: String, type: T): T {
            val SHP_Text = App.getContext().getSharedPreferences(shpName, Activity.MODE_PRIVATE)
            when (type) {
                is Int -> {
                    //返回整型数据
                    return SHP_Text.getInt(getKey, 0) as T
                }
                //返回字符型数据
                is String -> {
                    return SHP_Text.getString(getKey, "0") as T
                }
                //返回布尔值数据
                is Boolean -> {
                    return SHP_Text.getBoolean(getKey, false) as T
                }
                //抛出异常
                else -> {
                    throw Throwable("类型不匹配！")
                }

            }
        }

    }


}

/**
 * 删除缓存
 */
fun deleteCache( videoIds:Int,type:Int):Boolean{
    var flag  = false
    var te =when(type){
        0 -> "w"
        1 -> "e"
        2 -> "p"
        else ->"w"
    }

       val file = File("${App._cache}/$te${videoIds+1}.mov")
        if (!file.exists()) {
           iwhToast("删除失败,文件不存在！")

        } else {
            if (file.isFile)
                Log.d("删除完成！",videoIds.toString())
                return file.delete()
        }


    return flag
}