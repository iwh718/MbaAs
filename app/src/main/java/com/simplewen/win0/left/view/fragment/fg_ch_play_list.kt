package com.simplewen.win0.left.view.fragment


import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.simplewen.win0.R
import com.simplewen.win0.app.DownLoadListener
import com.simplewen.win0.app.DownLoadVideos
import com.simplewen.win0.app.iwhToast

import com.simplewen.win0.left.modal.PreData
import com.simplewen.win0.left.adapter.play_listview_adapter

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.support.constraint.Constraints

import android.support.v4.app.ActivityCompat
import android.util.Log
import android.widget.*
import com.simplewen.win0.left.view.activity.PlayVideo
import java.io.File


/**
 * @author IWH
 * 章节视频的Fragment重写
 * 实现下载监听接口
 * 完成视频下载
 * **/
class fg_ch_play_list : Fragment(), DownLoadListener {

    //进度布局
    lateinit var ly:View
    //进度条
    private var downLoading: AlertDialog?  = null
    //下载器
    private var dv: DownLoadVideos? = null

    override fun onCancel() {
        iwhToast("下载已经取消！")
        downLoading!!.dismiss()
    }

    override fun onError() {
        iwhToast("下载出错！")
        downLoading?.dismiss()
    }

    override fun onFailed() {
        iwhToast("下载失败！")
        downLoading?.dismiss()
    }
    //刷新进度
    override fun onProgress(progress: Int,videoSize:Int) {
      with(ly) {
            this.findViewById<TextView>(R.id.down_load_text).text = "共：$videoSize M 已经下载下载:$progress%"
            this.findViewById<ProgressBar>(R.id.down_load_progressbar).progress = progress
        }
    }

    override fun onSuccess(vChTypeCall:Int,vChNumCall:Int) {
        downLoading?.dismiss()
        iwhToast("已经下载完成！")
       /** with(Intent(activity,PlayVideo::class.java)){
            putExtra("chNum",vChNumCall)
            putExtra("chType",vChTypeCall)
            startActivity(this)
        }**/
       val chType = when(vChTypeCall){
           0 -> "w"
           1 -> "e"
           2 -> "p"
           else -> "w"
       }
        val intent = Intent(activity,PlayVideo::class.java)
            val path = PreData.getPlayUrl(vChNumCall, chType)
            val file = File(path)
        val uri = Uri.fromFile(file)
        Log.d("@@pathplay:",path)
        intent.setDataAndType(uri,"video/*")
        startActivity(intent)



    }

    //读写权限
    private val PERMISSIONS_STORAGE = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    //请求状态码
    private val REQUEST_PERMISSION_CODE = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val adapterPlayListView: play_listview_adapter?
        //申请权限
        ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE)
        ly = Constraints.inflate(activity, R.layout.down_load, null).apply {
            this.findViewById<TextView>(R.id.down_cancel).setOnClickListener {
                this@fg_ch_play_list.dv!!.cancelDownload()

            }
        }
        downLoading = AlertDialog.Builder(activity)
                .setMessage("正在下载,请不要关闭该页面！")
                .setView(ly)
                .setCancelable(false)
                .create()
        val chType = arguments.getInt("chType")//类型
        var chNum: Int = PreData.getNum(chType)
        adapterPlayListView = play_listview_adapter(chNum, chType)

        var vi = inflater.inflate(R.layout.video_play_listview, container, false)
        try {
            with(vi.findViewById<ListView>(R.id.left_play_listview)) {
                adapter = adapterPlayListView
                onItemClickListener = AdapterView.OnItemClickListener { _, _, positio, _ ->
                    Log.d("@@playUrl:", PreData.getPlayUrl(positio + 1, "w"))
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE)
                    } else {
                        iwhToast("即将开始下载！")
                        dv = DownLoadVideos(this@fg_ch_play_list,chType,positio+1)
                        dv!!.execute(PreData.getPlayUrl(positio + 1, "w"))
                        downLoading!!.show()

                    }


                }
            }

        } catch (e: NullPointerException) {
            iwhToast(e.printStackTrace().toString(), R.color.warn)
        }
        return vi
    }

    /**
     * 请求文件读写
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (i in permissions.indices) {
               // Log.i("@@MainActivity", "申请的权限为：" + permissions[i] + ",申请结果：" + grantResults[i])
            }
        }
    }


}