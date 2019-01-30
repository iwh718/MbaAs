package com.simplewen.win0.modal

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.FileOutputStream

/**导入数据库**/
class ImportDB internal constructor(private val context: Context) {
    private val BUFFER_SIZE =10000
    companion object {
        val DB_NAME = "glx" //保存的数据库文件名
        val PACKAGE_NAME = "com.simplewen.win0"//工程包名
        val DB_PATH = ("/data${Environment.getDataDirectory().absolutePath}/$PACKAGE_NAME/databases")  //在手机里存放数据库的位置
    }
    fun copyDatabase():Boolean{
        val dbfile = "$DB_PATH/$DB_NAME"
        Log.d("look",dbfile)
        try {
            //执行数据库导入
            val db = this.context.resources.assets.open(DB_NAME) //欲导入的数据库
            val fos = FileOutputStream(dbfile)
            val buffer = ByteArray(BUFFER_SIZE)
            var count = 0
            while ({ count = db.read(buffer);count}() > 0) {
                        fos.write(buffer, 0, count)
            }
            fos.close()//关闭输出流
            db.close()//关闭数据库
            return true
        }catch (e: Throwable) {
            Log.d("look",e.toString())
            e.printStackTrace()
            return false
        }

    }


}