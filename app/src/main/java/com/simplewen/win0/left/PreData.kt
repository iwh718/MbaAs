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
       val chTitle = arrayListOf(
               mapOf("chNum" to 4,"title" to "公共基础"),
               mapOf( "chNum" to 3,"title" to "计算机基础"),
               mapOf("chNum" to 3,"title" to "Word相关练习"),
               mapOf("chNum" to 3,"title" to "Excel相关练习"),
               mapOf("chNum" to 2,"title" to "PowerPoint相关练习"),
               mapOf("chNum" to 3,"title" to "支持"))
       val chOneUrl = arrayListOf("https://www.borebooks.top/MAS/MAS_one/MAS_ch_one_1.htm",
               "https://www.borebooks.top/MAS/MAS_one/MAS_ch_one_2.htm",
               "https://www.borebooks.top/MAS/MAS_one/MAS_ch_one_3.htm",
               "https://www.borebooks.top/MAS/MAS_one/MAS_ch_one_4.htm",
               "https://www.borebooks.top/MAS/MAS_one/MAS_ch_one_eg.htm")
       val chTwoUrl = arrayListOf("https://www.borebooks.top/MAS/MAS_two/MAS_ch_two_1.htm",
               "https://www.borebooks.top/MAS/MAS_two/MAS_ch_two_2.htm",
               "https://www.borebooks.top/MAS/MAS_two/MAS_ch_two_3.htm",
               "https://www.borebooks.top/MAS/MAS_two/MAS_ch_two_eg.htm")
       val chThree =arrayListOf(
               "https://www.borebooks.top/MAS/MAS_three/MAS_ch_three_1.htm",
               "https://www.borebooks.top/MAS/MAS_three/MAS_ch_three_2.htm",
               "https://www.borebooks.top/MAS/MAS_three/MAS_ch_three_3.htm",
               "https://www.borebooks.top/MAS/MAS_three/MAS_ch_three_eg.htm")
       val chFour = arrayListOf(
               "https://www.borebooks.top/MAS/MAS_four/MAS_ch_four_1.htm",
               "https://www.borebooks.top/MAS/MAS_four/MAS_ch_four_2.htm",
               "https://www.borebooks.top/MAS/MAS_four/MAS_ch_four_3.htm",
               "https://www.borebooks.top/MAS/MAS_four/MAS_ch_four_eg.htm")
       val chFive = arrayListOf(
               "https://www.borebooks.top/MAS/MAS_five/MAS_ch_five_1.htm",
               "https://www.borebooks.top/MAS/MAS_five/MAS_ch_five_2.htm",
               "https://www.borebooks.top/MAS/MAS_five/MAS_ch_five_eg.htm")
       val chSix  = arrayListOf(
               "https://www.borebooks.top/MAS/MAS_six/MAS_ch_six_1.htm",
               "https://www.borebooks.top/MAS/MAS_six/MAS_ch_six_2.htm",
               "https://www.borebooks.top/MAS/MAS_six/MAS_ch_six_3.htm",
               "https://www.borebooks.top/MAS/MAS_six/MAS_ch_six_eg.htm")

       var CHS = arrayListOf(chOneUrl, chTwoUrl, chThree, chFour, chFive,chSix)

       /**获取章节
        * @param ch 大章节
        * @param chSon 小章节
        * **/
       fun getPreUrl(ch:Int = 1,chSon:Int = 1) = CHS[ch-1][chSon-1]

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
