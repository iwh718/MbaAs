package com.simplewen.win0.left
import android.graphics.Bitmap
import android.webkit.*
import com.simplewen.win0.R
import com.simplewen.win0.iwhToast

/**静态类 存储web链接
 * author：iwh
 * time ：2019.01.8
 * **/
 class PreData{
   companion object {
       val jcItems = arrayOf("1.数据结构与算法","2.程序设计基础","3.软件工程基础","4.数据库设计基础")
       val jcC = arrayOf(
             arrayOf("算法","数据结构的基本概念","线性表与存储结构","栈和队列","线性链表","树与二叉树","查找技术","排序技术"),
             arrayOf("程序设计方法与风格"),
             arrayOf("基本概念","结构化分析方法","结构化设计方法","软件测试","软件调试"),
             arrayOf("基本概念","数据模型","关系代数","数据库设计与管理"))
       val jcItems2 = arrayOf("1.概述", "2.信息得表述与存储", "3.硬件系统", "4.软件系统", "5.多媒体", "6.病毒及防护", "7.Internet使用")

       val chTitle = arrayListOf(
               mapOf("chNum" to 0,"title" to "Word相关练习"),
               mapOf("chNum" to 1,"title" to "Excel相关练习"),
               mapOf("chNum" to 2,"title" to "PowerPoint相关练习"))

       //存储web教程链接,word,excel,ppt
       val jcWordUrls = arrayListOf("file://www.borebooks.top/MAS/jcs/iwhWordJc.html",
               "https://www.borebooks.top/MAS/jcs/iwhWordJc.html",
               "file://www.borebooks.top/MAS/jcs/iwhWordJc.html")
       val jcExcelUrls = arrayListOf("file://www.borebooks.top/MAS/jcs/iwhExcelJc.html",
               "https://www.borebooks.top/MAS/jcs/iwhExcelJc.html",
               "file://www.borebooks.top/MAS/jcs/iwhExcelJc.html")
       val jcPPTUrls = arrayListOf("file://www.borebooks.top/MAS/jcs/iwhPPTJc.html",
               "https://www.borebooks.top/MAS/jcs/iwhPPTJc.html",
               "file://www.borebooks.top/MAS/jcs/iwhPPTJc.html")

       val jcUrls = arrayListOf(jcWordUrls, jcExcelUrls, jcPPTUrls)

       /**获取章节
        * @param ch 教程类型
        * @param sonKey 获取子项url
        * **/
       fun getPreUrl(ch:Int = 0,sonKey:Int = 0) = jcUrls[ch][sonKey]

       /**web加载方法
        * @param web webview组件
        * @param urls 链接
        * **/
       fun webAutoLoad(web: WebView, urls:String){
           web.settings.javaScriptEnabled = true
           web.loadUrl(urls)
           web.webViewClient = object :WebViewClient(){
               override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                   iwhToast("加载中！")
                   super.onPageStarted(view, url, favicon)
               }

               override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                   iwhToast("网络错误！", R.color.warn)
                   super.onReceivedError(view, request, error)
               }
               override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                   return false
               }

           }


       }
   }


}
