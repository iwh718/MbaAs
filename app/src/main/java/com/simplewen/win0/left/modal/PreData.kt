package com.simplewen.win0.left.modal
import android.graphics.Bitmap
import android.os.Environment
import android.webkit.*
import com.simplewen.win0.R
import com.simplewen.win0.app.iwhToast

/**静态类 存储该项目的各类静态数据
 * author：iwh
 * time ：2019.01.8
 * **/
 class PreData{
   companion object {
       val jcItems = arrayOf("1.数据结构与算法","2.程序设计基础","3.软件工程基础","4.数据库设计基础")
       val jcC = arrayOf(
             arrayOf("算法","数据结构的基本概念","线性表与存储结构","栈和队列","线性链表","树与二叉树","查找技术","排序技术"),
             arrayOf("程序设计方法与风格"),
             arrayOf("软件工程基础"),
             arrayOf("数据库基础"))
       val jcItems2 = arrayOf("计算机基础")
       val chTitle = arrayListOf(
               mapOf("chNum" to 0,"title" to "Word相关练习"),
               mapOf("chNum" to 1,"title" to "Excel相关练习"),
               mapOf("chNum" to 2,"title" to "PowerPoint相关练习"))

       //存储下载的web教程,word,excel,ppt
       val jcVideoBaseUrl = "https://www.borebooks.top/MAS/jcs/videos"
       val jcUrls = arrayListOf("https://www.borebooks.top/MAS/local_html/word.html",
               "https://www.borebooks.top/MAS/local_html/excel.html",
               "https://www.borebooks.top/MAS/local_html/ppt.html")
       //章节标题
        val jcTitles = arrayListOf(
                arrayListOf(
                        "•第M01讲-1：考试和培训介绍及保存",
                        "•第M01讲-2：文本选择、格式刷的学习",
                        "•第M02讲-1：文本格式，段落格式（行距）",
                        "•第M02讲-2：段落格式（段距，首行缩进，左右缩进）",
                        "•第M03讲-1：页面设置（大小、方向、边距、水印、背景）",
                        "•第M03讲-2：页面设置真题讲解",
                        "•第M04讲-1：插入形状、插入表格、插入文本框",
                        "•第M04讲-2：插入图片",
                        "•第M05讲-1：表格编辑：课表的制作",
                        "•第M05讲-2：插入图表、超链接、域及第4套题的WORD",
                        "•第M05讲-3：插入SmartArt对象",
                        "•第M06讲-1：样式的创建、修改、样式集、更新、复制",
                        "•第M06讲-2：第5套WOrd操作",
                        "•第M07讲-1：分页符与分隔符，分栏符，页眉，页脚",
                        "•第M07讲-2：页码和分节符、分页符、首页不同、页码格式",
                        "•第M07讲-3：页码奇偶页不同，链接到前一条",
                        "•第M07讲-4：页码实践，第14套真题",
                        "•第M08讲-1：项目符号、编号、多级列表",
                        "•第M08讲-2：多级列表与样式的链接及第9套题的第2小题",
                        "•第M08讲-3：第9套题第345题",
                        "•第M09讲-1：目录的创建与修改",
                        "•第M09讲-2：修订、批注、保存文档部件",
                        "•第M10讲-1：邮件合并",
                        "•第M10讲-2：第3套题的Word部分"
                        ),
                arrayListOf(
                        "•第M11讲-1：数据的输入和填充（自定义填充）",
                        "•第M11讲-2：数据有效性、行和列的调整、边框和底纹",
                        "•第M11讲-3：真题库第6套前3题",
                        "•第M12讲-1：单元格样式，表格样式，条件格式",
                        "•第M12讲-2：页面设置（打印标题、打印区域、分隔符）",
                        "•第M12讲-3：真题第9套的第7-8小题",
                        "•第M13讲-1：工作簿的基本操作与保护，工作表的基本操作",
                        "•第M13讲-2：工作表区域保",
                        "•第M13讲-3：多工作表操作",
                        "•第M14讲-1：名称与公式",
                        "•第M14讲-2：函数专讲（sum,sumif,sumifs）",
                        "•第M15讲-1：max,min,average,if函数",
                        "•第M15讲-2：int,trunc,round,today,mid,left,right函数和&连接",
                        "•第M15讲-3：第5套前三个题",
                        "•第M16讲-1函数专讲：vlookup和lookup函数",
                        "•第M16讲-2函数专讲：hlookup和rank，count,average函数",
                        "•第M17讲-1：函数真题专讲：第5套题的EXCEL",
                        "•第M17讲-2：图表操作-1",
                        "•第M18讲-1：图表操作-2",
                        "•第M18讲-2：图表操作-3",
                        "•第M18讲-3：合并计算",
                        "•第M19讲-1：排序与筛选",
                        "•第M19讲-2：分类汇总",
                        "•第M20讲-1：数据透视表",
                        "•第M20讲-2：模拟分析",
                        "•第M21讲-1：获取外部数据、插入链接、宏",
                        "•第M21讲-2：第9套题的前5题"
                        ),
                arrayListOf(
                        "•第M22讲-1：幻灯片编辑、版式、背景、母版",
                        "•第M22讲-2：第2套题的PPT",
                        "•第M23讲-1：PPT专讲(第9套题)",
                        "•第M23讲-2：PPT专讲(第9套题)",
                        "•第M24讲：PPT专讲"
                        )
        )
       /**获取章节
        * @param ch 教程类型
        * @param sonKey 获取子项url
        * **/
       fun getPreUrl(ch:Int = 0) = jcUrls[ch]

       /**
        * 拼接链接
        * @param base 基础链接
        * @param num 集数
        * @param type 类型
        * @return 返回播放链接
        */
       fun getPlayUrl(num:Int,type:String) = "$jcVideoBaseUrl/$type$num.mov"

       /**
        * 获取集数
        * @param type 分类
        */
        fun getNum(type:Int):Int{
          return  when(type){
               0 -> 24
               1-> 27
               2-> 5
                else -> 0
            }

        }
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
