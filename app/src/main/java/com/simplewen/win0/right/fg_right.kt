package com.simplewen.win0.right


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.simplewen.win0.R
import com.simplewen.win0.app.getDjs
import com.simplewen.win0.app.iwhToast
import java.lang.Exception
import java.util.*
import java.text.SimpleDateFormat


class iwh_fg_right : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var now_time = System.currentTimeMillis()//现在时间
        val vi = inflater?.inflate(R.layout.fg_right, container, false)
        val myLike = vi!!.findViewById<TextView>(R.id.fg_right_my_like)
        val djs_things = vi.findViewById<TextView>(R.id.fg_right_time_calendar_btn)//自定义倒计时事件
        val djs_time = vi?.findViewById<TextView>(R.id.djs_time)//倒计时时间
        val quote_btn = vi.findViewById<TextView>(R.id.fg_right_quote)//自定义语录

        val pr = activity.getSharedPreferences("djs", Context.MODE_PRIVATE)
        val default_quote = pr.getString("quote", "没有什么是一蹴而就的。(点击自定义)")
        val default_time = pr.getString("djs_time", "1548777600000")
        val default_things = pr.getString("djs_things", "自定义（点我)")
        quote_btn.text = default_quote//设置默认语录
        djs_things.text = default_things //默认事件
        djs_time.text ="${getDjs(default_time.toString(), now_time.toString())}天"//更新倒计时
        val myError = vi.findViewById<TextView>(R.id.fg_right_my_error)
        myLike.setOnClickListener {

            val intent = Intent(activity, myLIke::class.java)
            intent.putExtra("my_flag", "like")
            startActivity(intent)
        }
        myError.setOnClickListener {
            val intent = Intent(activity, myLIke::class.java)
            intent.putExtra("my_flag", "error")
            startActivity(intent)
        }
        //语录自定义弹窗
        fun quote() {
            val quote_dia = AlertDialog.Builder(activity)
            val lay = layoutInflater.inflate(R.layout.quote_dia, null)

            quote_dia.setView(lay)
                    .setPositiveButton("确认") { _, _ ->

                        val quo = lay.findViewById<EditText>(R.id.quote_edit).text//获取输入的句子
                        if (quo.toString().isNotEmpty()) {
                            val pr = activity.getSharedPreferences("djs", Context.MODE_PRIVATE)
                            pr.edit().putString("quote", quo.toString()).apply()
                            Toast.makeText(activity, quo, Toast.LENGTH_SHORT).show()
                            quote_btn.text = quo
                        } else {
                            iwhToast("不可以为空哦！", Gravity.BOTTOM)
                        }

                    }
                    .setNegativeButton("取消", null).create().show()

        }
        quote_btn.setOnClickListener {
            quote()

        }//倒计时弹窗
        fun djsTime() {
            now_time = System.currentTimeMillis()///现在时间
            var tem_time: Long = 1548777600000//时间
            var select_time: Long = 1 //选择时间
            val lay = layoutInflater.inflate(R.layout.djs_dia, null)//载入倒计时弹窗
            var djs_rz = lay.findViewById<EditText>(R.id.djs_dia_edit).text//获取自定义日子
            val djs_cal = lay.findViewById<CalendarView>(R.id.djs_dia_calendar)//获取自定义时间
            djs_cal.setOnDateChangeListener { _, year, month, day ->
                val spM: String = if (month < 10) "0${month + 1}" else "${month + 1}"
                val spDay = if (day < 10) "0$day" else day.toString()
                val spd = SimpleDateFormat("yyyy/MM/dd", Locale.CHINA)
                tem_time = spd.parse("$year/$spM/$spDay").time
                 }

            AlertDialog.Builder(activity)
                    .setView(lay)
                    .setPositiveButton("确认") { _, _ ->
                        //储存事件和日期
                        if (djs_rz.toString().isNotEmpty()) {
                            val pr2 = activity.getSharedPreferences("djs", Context.MODE_PRIVATE)
                            try {
                                pr2.edit().putString("djs_things", djs_rz.toString()).putString("djs_time", "$tem_time").apply()
                                djs_things.text = djs_rz//立即更新事件
                                djs_time.text = "还有${(tem_time - now_time) / (60 * 60 * 24 * 1000)}天"//立即更新时间
                            } catch (e: Exception) {
                                println(e.printStackTrace())
                                iwhToast("程序出现问题！", Gravity.BOTTOM)
                            }
                        } else {
                            iwhToast("不可以为空哦！", Gravity.BOTTOM)
                        }

                    }.setNegativeButton("取消", null)
                    .create().show()

        }
        djs_things.setOnClickListener {
            djsTime()
        }


        return vi
    }
}