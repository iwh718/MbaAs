package com.simplewen.win0

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class mySql(context: Context,name:String,version:Int ):SQLiteOpenHelper(context,name,null,version) {

    val Create_ = "Create table sj(id integer primary key autoincrement,name varchar);"//SQL语句
    val Create_2 = "Create table tm_dx(" +
            "sj_id integer," +
            "id integer primary key autoincrement," +
            "content varchar，" +
            "A varchar,B varchar, C varchar, D varchar,answer_desc varchar,answer char(1),my_sort varchar);"
    val mContext = context
    var sjs = arrayListOf<Map<String, Any>>()//存放数据
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //
    }

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db!!.execSQL(Create_)//创建试卷表
            db.execSQL(Create_2)//创建表题目表
            Log.d("dbInit","")
            Toast.makeText(mContext, "初始化数据库", Toast.LENGTH_SHORT).show()
        } catch (e: NullPointerException) {
            println(e)
        }
    }

    /*查询方法*/
    /**@param db 数据库
     * @param hand handler
     * @param table_name 表名
     * @param sort_type 分类
     * @param sj_id 试卷id**/
    fun wen_query( db: SQLiteDatabase, hand: Handler,table_name:String,sort_type:String,sj_id:Int = 1): Boolean {
        this.sjs.clear()
        var tableName:String = table_name//获取查询的表
        val sort_type = sort_type//查询类型：全部，错题，收藏。
        //var colums  = null
        var selection:String?  = null
        when(sort_type){
            "all" -> {
                if(sj_id == 0)   selection = null
                else selection = "sj_id = $sj_id"


            }
            "error" -> {
                selection = "my_sort = 'error'"
            }
            "like" -> {
                selection ="my_sort = 'like'"

            }
            else ->{
                selection = "content LIKE '%${sort_type}%'"
            }
        }
        val cursor = db.query(tableName, null, selection, null, null, null, null, null)


        when(tableName){
            //查询试卷
            "sj" -> {
                if (cursor.moveToFirst()) {
                    do {
                        var temMAp = linkedMapOf<String, Any>()
                        var name = cursor.getString(cursor.getColumnIndex("name"))
                        var id = cursor.getString(cursor.getColumnIndex("id"))
                        temMAp.put("name", name)
                        temMAp.put("id", id)
                        sjs.add(temMAp)
                        println(temMAp)
                        //temMAp.clear()
                    } while (cursor.moveToNext())
                    println(sjs)
                    Log.d("look_sj",sjs.toString())
                    val msg = Message()
                    msg.what = 0
                    hand.sendMessage(msg)
                    cursor.close()
                }
            }
            //查询题目
            "tm_dx" -> {
                if (cursor.moveToFirst()) {
                    do {
                        val temMAp = linkedMapOf<String, Any>()
                        val content = cursor.getString(cursor.getColumnIndex("content"))
                        val sj_id = cursor.getString(cursor.getColumnIndex("sj_id"))
                        val id = cursor.getString(cursor.getColumnIndex("id"))
                        val A = cursor.getString(cursor.getColumnIndex("A"))
                        val B = cursor.getString(cursor.getColumnIndex("B"))
                        val C = cursor.getString(cursor.getColumnIndex("C"))
                        val D = cursor.getString(cursor.getColumnIndex("D"))
                        val answer = cursor.getString(cursor.getColumnIndex("answer"))
                        val answer_desc = cursor.getString(cursor.getColumnIndex("answer_desc"))

                        temMAp.put("sj_id", sj_id)
                        temMAp.put("id", id)
                        temMAp.put("content", content.replace("<br/>",""))
                        temMAp.put("A", A)
                        temMAp.put("B", B)
                        temMAp.put("C", C)
                        temMAp.put("D", D)
                        temMAp.put("answer",answer)
                        temMAp.put("answer_desc", answer_desc)

                        sjs.add(temMAp)
                        println(temMAp)
                        //temMAp.clear()
                    } while (cursor.moveToNext())
                    println(sjs)
                    Log.d("look_tm",sjs.toString())
                    val msg = Message()
                    msg.what = 0
                    hand.sendMessage(msg)
                    cursor.close()
                }
            }

        }

        return false
    }
    //收藏题目，更改题目状态,my_type:错题，收藏
    //传入：数据库对象，handle，题目类型，题目id
    fun wen_update(db: SQLiteDatabase,my_type:String,tm_id:String):Int{
        Log.d("update1",my_type)
        Log.d("update2",tm_id)
        val content = ContentValues()
        content.put("my_sort",my_type)
        val re =   db.update("tm_dx",content,"id = ?", arrayOf(tm_id))
        return re

    }

}