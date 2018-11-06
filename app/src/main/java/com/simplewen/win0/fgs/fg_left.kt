package com.simplewen.win0.fgs


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.simplewen.win0.R
import com.simplewen.win0.left.left_show
import org.w3c.dom.Text

class iwh_fg_left: Fragment(){
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val vi = inflater?.inflate(R.layout.fg_left,container,false)
        val itemsV = arrayListOf<LinearLayout>()//存放itemsView
        itemsV.add(vi!!.findViewById(R.id.left_list_item0))
        itemsV.add(vi.findViewById(R.id.left_list_item1))
        itemsV.add(vi.findViewById(R.id.left_list_item2))
        itemsV.add(vi.findViewById(R.id.left_list_item3))
        itemsV.add(vi.findViewById(R.id.left_list_item4))
        itemsV.add(vi.findViewById(R.id.left_list_item5))
        itemsV.add(vi.findViewById(R.id.left_list_item6))
        itemsV.add(vi.findViewById(R.id.left_list_item7))
        //设置监听器
        for (i in itemsV.indices){
            itemsV[i].setOnClickListener{

                Toast.makeText(activity,"${i}",Toast.LENGTH_SHORT).show()
                val intent = Intent(activity,left_show::class.java)
                intent.putExtra("key",i)
                activity.startActivity(intent)


            }
        }
        return vi
    }
}