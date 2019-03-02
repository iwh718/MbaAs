package com.simplewen.win0.app

import android.os.AsyncTask
import android.os.Environment
import android.system.Os.close
import android.system.Os.read
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.RandomAccessFile
import java.lang.Exception
import android.telecom.VideoProfile.isPaused
import android.util.Log


/**
 * 下载回调
 */
interface DownLoadListener {
    /**
     * 下载成功
     */
    fun onSuccess(vcp:Int,vcm:Int)

    /**
     * 下载失败
     */
    fun onFailed()

    /**
     * 进度
     * @param progress 进度
     * @param videoSize 大小
     */
    fun onProgress(progress: Int,videoSize:Int)

    /**
     * 下载取消
     */
    fun onCancel()

    /**
     * 暂停下载
     */
    fun onPause()
    /**
     * 下载错误
     */
    fun onError()
}

/**
 * 视频下载
 */
class DownLoadVideos(private val listener: DownLoadListener,private val vChType:Int,private val vChNum:Int) : AsyncTask<String, Int, Int>() {
    //视频大小
    private var contentLength:Long = 0
    private val TYPE_SUCCESS = 0//下载成功
    private val TYPE_FAILED = 1//下载失败
    private val TYPE_PAUSED = 2//下载暂停
    private val TYPE_CANCELED = 3//下载取消
    private val TYPE_ERROR = 4 //下载出错
    //更新进度
    private var lastProgress: Int = 0
    //暂停
    private var isPause = false
    //取消
    private var isCancel = false

    @Throws(IOException::class)
    /**
     * 计算文件大小
     */
    private fun getContentLength(downloadUrl: String): Long {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(downloadUrl)
                .build()
        val response = client.newCall(request).execute()
        //Log.d("@@resVideo:","$response")
        if (response != null && response.isSuccessful) {
             contentLength = response.body().contentLength()
            response.body().close()
            return contentLength
        }
        return 0
    }

    /**
     * 暂停下载
     */
    fun pauseDownload() {

        isPause = true

    }

    /**
     * 取消下载
     */
    fun cancelDownload() {

        isCancel = true

    }



    /**
     * 更新UI线程
     */
    override fun onPostExecute(status: Int?) {
        super.onPostExecute(status)
        when (status) {
            TYPE_SUCCESS -> listener.onSuccess(vChType,vChNum)
            TYPE_FAILED -> listener.onFailed()
            TYPE_PAUSED -> listener.onPause()
            TYPE_CANCELED -> listener.onCancel()
            TYPE_ERROR -> listener.onError()
            else -> {
            }
        }
    }

    /**
     * 更新进度
     */
    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        val progress = values[0]
        Log.d("@@lastProgress:",lastProgress.toString())
        if (progress!! > lastProgress) {
            listener.onProgress(progress,(contentLength/1000000).toInt())
            lastProgress = progress
        }

    }

    /**
     * 后台下载
     */
    override fun doInBackground(vararg params: String?): Int {
        var inputS: InputStream? = null
        Log.d("@@params:",params.toString())
        var savedFile: RandomAccessFile? = null
        var file: File? = null
        try {
            var downloadedLength: Long = 0//记录已下载的文件长度
            val downloadUrl = params[0]
            val fileName = downloadUrl?.substring(downloadUrl.lastIndexOf("/"))
            val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
            file = File("$directory/$fileName")
            Log.d("@@path:",directory)
            if (file.exists()) {
                downloadedLength = file.length()

            }else{
                Log.d("@@不存在！","-------")
            }
            val contentLength: Long = getContentLength(downloadUrl!!)
            Log.d("@@fileSize:","$contentLength")
            if (contentLength.toInt() == 0) {
                return TYPE_ERROR
            } else if (contentLength == downloadedLength) {
                //已下载字节和文件总字节相等，说明已经下载完成了
                return TYPE_SUCCESS
            }
            val client = OkHttpClient()
            val request = Request.Builder()
                    //断点下载，指定从哪个字节开始下载
                    .addHeader("RANGE", "bytes=" + downloadedLength + "-")
                    .url(downloadUrl)
                    .build()
            val response: Response = client.newCall(request).execute()
            if (response != null) {
                inputS = response.body().byteStream()
                savedFile = RandomAccessFile(file, "rw")
                savedFile.seek(downloadedLength)//跳过已下载的字节
                var b = ByteArray(1024)
                var total = 0
                var len: Int? = null
                while ({
                            len = inputS.read(b)
                            len
                        }() != -1) {
                    if (isCancel) {
                        return TYPE_CANCELED
                    } else if (isPause) {
                        return TYPE_PAUSED
                    } else {
                        total += len!!
                        savedFile.write(b, 0, len!!)
                        //计算已下载的百分比
                        var progress = ((total + downloadedLength) * 100 / contentLength).toInt()

                        publishProgress(progress)
                    }
                }
                response.body().close()
                return TYPE_SUCCESS
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputS?.close()
                savedFile?.close()
                if (isCancel && file != null) {
                    file.delete()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return TYPE_FAILED

    }
}