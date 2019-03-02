package com.simplewen.win0.center


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import com.simplewen.win0.R
import com.simplewen.win0.app.iwhToast
import com.simplewen.win0.modal.mySql

/**
 * 试卷Fragment
 */
class iwh_fg_center: Fragment(){


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val vi = inflater?.inflate(R.layout.fg_center,container,false)
        val listView = vi!!.findViewById<ListView>(R.id.fg_center_listview)
        val temSql = mySql(activity, "glx", 1)
        var sjs = temSql.sjs
        var adapter = SimpleAdapter(activity,sjs,R.layout.fg_center_listview, arrayOf("name","id"),intArrayOf(R.id.fg_center_listview_name))
        listView?.adapter = adapter
        val db = temSql.writableDatabase
        Log.d("@@@","中心启动")

        val handle = object :Handler(Looper.getMainLooper()){
            override fun handleMessage(msg: Message?) {
                when(msg!!.what){
                    0 -> {
                        sjs = temSql.sjs
                        adapter.notifyDataSetChanged()


                    }
                }
            }
        }
        vi.findViewById<TextView>(R.id.center_quote).setOnClickListener{
            temSql.wen_query(db,handle,"sj","all",0)
        }
        temSql.wen_query(db,handle,"sj","all",0)
        listView?.onItemClickListener = AdapterView.OnItemClickListener {
            _,_,position,_->
            Log.d("position:",sjs.get(position).get("id").toString())
            val intent = Intent(activity, mian_tm::class.java)
            intent.putExtra("sj_id",position)
            intent.putExtra("fab_type","add")
            startActivity(intent)
        }
        return vi
    }
}