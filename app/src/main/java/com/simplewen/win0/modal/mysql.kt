package com.simplewen.win0.modal

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.simplewen.win0.app.App
import com.simplewen.win0.app.iwhDataOperator
import com.simplewen.win0.app.iwhToast
import java.io.File

/**
 * 数据库操作类
 */
class mySql(context: Context, name: String, version: Int) : SQLiteOpenHelper(context, name, null, version) {

    val Create_ = "Create table sj(_id integer primary key autoincrement,name varchar);"//SQL语句
    val Create_2 = "Create table tm_dx(sj_id integer,id integer primary key autoincrement,content varchar，" +
            "A varchar,B varchar, C varchar, D varchar,answer_desc varchar,answer char(1),my_sort varchar);"
    val mContext = context
    var sjs = arrayListOf<Map<String, Any>>()//存放数据
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val file = File(ImportDB.DB_PATH + "/glx")//调用伴生对象
        if (file.exists()){
            //内部数据库存在，开始导入外部
            if(iwhDataOperator.getSHP("dbFlag","dbFlag","") == "1"){
                //iwhToast("数据库已经存在！")
            }else{
                val inDb = ImportDB(App.getContext())
                if(inDb.copyDatabase()) iwhDataOperator.setSHP("dbFlag","1","dbFlag") else iwhToast("复制失败！", Gravity.BOTTOM)
            }
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db!!.execSQL(Create_)//创建试卷表
            db.execSQL(Create_2)//创建表题目表
            Log.d("dbInit", "")
            Toast.makeText(mContext, "初始化数据库", Toast.LENGTH_SHORT).show()
        } catch (e: NullPointerException) {
            Log.d("@@@error:",e.toString())
        }
    }

    /*查询方法*/
    /**@param db 数据库
     * @param hand handler
     * @param table_name 表名
     * @param sort_type 分类
     * @param sj_id 试卷id**/
    fun wen_query(db: SQLiteDatabase, hand: Handler, table_name: String, sort_type: String, sj_id: Int = 1): Boolean {
        this.sjs.clear()
        val tableName: String = table_name//获取查询的表
        //var colums  = null
        var selection: String? = null
        when (sort_type) {
            "all" -> {
                if (sj_id == 0) selection = null
                else selection = "sj_id = $sj_id"


            }
            "error" -> {
                selection = "my_sort = 'error'"
            }
            "like" -> {
                selection = "my_sort = 'like'"

            }
            else -> {
                selection = "content LIKE '%${sort_type}%'"
            }
        }
       // Log.d("@@@selection:",selection.toString())
        val cursor = db.query(tableName, null, selection, null, null, null, null, null)


        when (tableName) {
            //查询试卷
            "sj" -> {
                Log.d("@@@cursor:","------${cursor.moveToFirst()}")
                if (cursor.moveToFirst()) {
                    do {
                        var temMAp = linkedMapOf<String, Any>()
                        var name = cursor.getString(cursor.getColumnIndex("name"))
                        var id = cursor.getString(cursor.getColumnIndex("_id"))
                        temMAp.put("name", name)
                        temMAp.put("id", id)
                        sjs.add(temMAp)
                      //  Log.d("@@@temMap:","$temMAp")
                        //temMAp.clear()
                    } while (cursor.moveToNext())

                   // Log.d("@@@look_sj", sjs.toString())
                    val msg = Message()
                    msg.what = 0
                    hand.sendMessage(msg)
                    cursor.close()
                }else{
                    Log.d("@@@else","no data")
                }
            }
            //查询题目
            "tm_dx" -> {
                if (cursor.moveToFirst()) {
                    do {
                        val temMAp = linkedMapOf<String, Any>()
                        val content = cursor.getString(cursor.getColumnIndex("content"))
                        val sj_idIn = cursor.getString(cursor.getColumnIndex("sj_id"))
                        val id = cursor.getString(cursor.getColumnIndex("id"))
                        val A = cursor.getString(cursor.getColumnIndex("A"))
                        val B = cursor.getString(cursor.getColumnIndex("B"))
                        val C = cursor.getString(cursor.getColumnIndex("C"))
                        val D = cursor.getString(cursor.getColumnIndex("D"))
                        val answer = cursor.getString(cursor.getColumnIndex("answer"))
                        val answer_desc = cursor.getString(cursor.getColumnIndex("answer_desc"))
                        with(temMAp) {
                            put("sj_id", sj_idIn)
                            put("id", id)
                            put("content", content.replace("<br/>", ""))
                            put("A", A.replace("<br/>", ""))
                            put("B", B.replace("<br/>", ""))
                            put("C", C.replace("<br/>", ""))
                            put("D", D.replace("<br/>", ""))
                            put("answer", answer)
                            put("answer_desc", answer_desc.replace("<br/>", ""))
                        }
                        sjs.add(temMAp)
                    } while (cursor.moveToNext())
                   // println(sjs)
                    //Log.d("look_tm", sjs.toString())
                    val msg = Message()
                    msg.what = 0
                    hand.sendMessage(msg)
                    cursor.close()
                }
            }

        }

        return true
    }

    //收藏题目，更改题目状态,my_type:错题，收藏
    //传入：数据库对象，handle，题目类型，题目id
    fun wen_update(db: SQLiteDatabase, my_type: String, tm_id: String): Int {

        val content = ContentValues()
        content.put("my_sort", my_type)
        val re = db.update("tm_dx", content, "id = ?", arrayOf(tm_id))
        return re

    }

}