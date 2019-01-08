package com.simplewen.win0.center

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.constraint.solver.GoalRow
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.simplewen.win0.R
import com.simplewen.win0.mySql
import com.simplewen.win0.right.iwhToast
import com.simplewen.win0.right.myLIke
import org.w3c.dom.Text
import java.lang.IllegalStateException

/**实现题目的Fragment
 * author：iwh
 * time：2018.10
 * 实现题目的显示，错题自动保存，添加收藏，错题集的显示与收藏显示**/
class fg_center_tm_fg:Fragment(){
    interface Callbacks{

        fun onRemove(position:Int){

        }
    }
        private  var mcallbacks:Callbacks?= null//回调

        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val ags = arguments
            val position = arguments.getInt("position")//获取FG下标
            var fab_type = arguments.getString("fab_type")
            val temSql = mySql(activity,"glx",1)//初始化数据库链接
            val db = temSql.writableDatabase//获取读写对象
            var vi = inflater?.inflate(R.layout.fg_center_tm,container,false)
            vi?.findViewById<TextView>(R.id.tm_content)!!.text = ags.getString("tm_content") + "__"
            vi.findViewById<TextView>(R.id.tm_answer_right).text = "正确答案：" +  ags.getString("tm_answer")
            Log.d("answer_desc",ags.getString("tm_answer_desc"))
            vi.findViewById<TextView>(R.id.tm_answer_desc).text = "解析:" +  ags.getString("tm_answer_desc")
            vi.findViewById<RadioButton>(R.id.tm_a).text = "A:${ags.getString("tm_a")}"
            vi.findViewById<RadioButton>(R.id.tm_b).text = "B:" +  ags.getString("tm_b")
            vi.findViewById<RadioButton>(R.id.tm_c).text = "C:" +  ags.getString("tm_c")
            vi.findViewById<RadioButton>(R.id.tm_d).text = "D:" +  ags.getString("tm_d")
            val id = ags.getString("tm_id")//获取id
            Log.d("id",id)
            val right_answer = ags.getString("tm_answer")//正确答案
            val my_like_btn = vi.findViewById<FloatingActionButton>(R.id.my_like)//添加收藏按钮
            if(fab_type == "add"){
                my_like_btn.setOnClickListener{
                    val dia = AlertDialog.Builder(activity)
                    dia.setTitle("添加收藏?").setMessage("稍后可以在我的收藏页面查看该题目")
                            .setPositiveButton("确认"){
                                _,_ ->

                                val res =  temSql.wen_update(db,"like",id)
                                if(res == 0){
                                    Toast.makeText(activity,"收藏失败:$id:res:$res",Toast.LENGTH_SHORT).show()
                                }else{
                                    Toast.makeText(activity,"收藏完成",Toast.LENGTH_SHORT).show()

                                }

                            }.setNegativeButton("取消",null).create().show()
                }
            }else if(fab_type == "delete"){
                my_like_btn.setImageResource(R.drawable.my_delete)
                my_like_btn.setOnClickListener{
                    val temSql = mySql(activity,"glx",1)
                    val db = temSql.writableDatabase
                    val res =  temSql.wen_update(db,"delete",id)
                    if(res == 0){
                        Toast.makeText(activity,"移除失败:$id,res:$res",Toast.LENGTH_SHORT).show()
                    }else{
                         Toast.makeText(activity,"移除完成",Toast.LENGTH_SHORT).show()
                        mcallbacks!!.onRemove(position)
                    }
                }
            }

            val tm_select_group = vi.findViewById<RadioGroup>(R.id.tm_select_group)//获取单选组
            val tm_answer_btn = vi.findViewById<Switch>(R.id.tm_answer_btn)//获取查看解析按钮
            val tm_answer_box = vi.findViewById<LinearLayout>(R.id.tm_answer_box)//解析区
            val Tos = fun(str:String){
                Toast.makeText(activity, str, Toast.LENGTH_LONG).show()
                tm_answer_btn.isChecked = true
            }
            tm_answer_box.visibility = View.GONE
            tm_answer_btn.setOnClickListener{
                if(tm_answer_box.visibility == View.GONE){
                    tm_answer_box.visibility = View.VISIBLE
                }else{
                    tm_answer_box.visibility = View.GONE
                }
            }
            fun my_error():Boolean{
                //自动保存错题到数据库
                iwhToast("回答错误！",Gravity.BOTTOM,R.color.warn)
                val res =  temSql.wen_update(db,"error",id)
                if(res ==0 ){
                   iwhToast("添加失败！",Gravity.BOTTOM,R.color.warn)
                    return false
                }
                return true

            }
            tm_select_group.setOnCheckedChangeListener{
              _,checkedId ->
                var tmFlag = false
                when(checkedId){
                    R.id.tm_a -> {

                        if(right_answer != "A"){
                            tmFlag = true
                        }
                    }
                    R.id.tm_b ->{
                        if(right_answer != "C"){
                            tmFlag = true
                        }
                    }
                    R.id.tm_c -> {
                        if(right_answer != "C"){
                            tmFlag = true
                        }
                    }
                    R.id.tm_d -> {
                        if(right_answer != "D"){
                            tmFlag = true
                        }
                    }

                }
                if(tmFlag){
                    my_error()//错题添加数据库
                } else{
                   iwhToast("回答正确！",Gravity.BOTTOM)
                }
                tm_answer_btn.isChecked = true
                tm_answer_box.visibility = View.VISIBLE

            }

            return vi
        }

    override fun onAttach(context: Context?) {
        if(context !is Callbacks){
            throw IllegalStateException("接口未实现")
        }
        super.onAttach(context)
        mcallbacks = context

    }





    }
