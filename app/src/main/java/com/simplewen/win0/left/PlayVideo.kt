package com.simplewen.win0.left

import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.simplewen.win0.R
import com.simplewen.win0.app.iwhToast
import kotlinx.android.synthetic.main.activity_play_video.*
import android.view.View
import android.view.WindowManager


/**
 * 播放视频
 */
class PlayVideo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_play_video)
        supportActionBar?.hide()

        val chType = when(intent.getStringExtra("chType")){
            "0" -> "w"
            "1" -> "e"
            "2" -> "p"
            else -> "w"
        }
        iwhToast("第一次加载很慢，一会再来看看吧！")
        val chNum = intent.getIntExtra("chNum",0)
        iwhToast(Uri.parse(PreData.getPlayUrl(chNum,chType)).toString())
        playLoad.visibility = View.VISIBLE
        //Log.d("@@@playUrl:",Uri.parse(PreData.getPlayUrl(chNum,chType)).toString())
        with(playVideo){
            setOnPreparedListener{
                playLoad.visibility = View.GONE
                start()

            }
            setOnErrorListener{
                _,_,_ ->
                iwhToast("播放出错！")
                true
            }
            //播放监听
            setOnInfoListener { media, what, area ->
                if (MediaPlayer.MEDIA_INFO_BUFFERING_START == what) {
                    playLoad.visibility = View.VISIBLE
                    iwhToast("加载中")
                    true
                } else {
                    playLoad.visibility = View.GONE
                    true
                }
            }


            this.setMediaController(android.widget.MediaController(this@PlayVideo))
            setVideoURI(Uri.parse(PreData.getPlayUrl(chNum,chType)))

        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        //判断是否有焦点
            val decorView = window.decorView
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)


    }
}
