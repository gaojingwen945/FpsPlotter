package com.code.ginniegao.fpsplot

import android.content.Context
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.lang.Exception
import java.text.SimpleDateFormat

/**
 * 将FPS数据写到csv文件
 *
 * Created by ginniegao on 2018/3/27.
 */
class FpsFileWriter {
    private val TAG = "FpsFileWriter"
    private val logSDF = SimpleDateFormat("yyyy-MM-dd-HH:mm:ss")
    private var bufferedWriter: BufferedWriter? = null
    private val handlerThread = HandlerThread(TAG)
    private var handler:Handler? = null

    /**
     * 生成APP日志文件，若已存在，旧文件将被覆盖
     *
     * 生成的文件路径为：
     * 有SD卡：sdcard\Android\data\包名\files\Log\csvFileName.csv
     * 无SD卡：\data\data\包名\files\Log\csvFileName.csv
     *
     * @param mContext
     * @param csvFileName csv文件名，不带后缀
     * @return APP日志文件
     */
    fun init(mContext: Context, csvFileName: String): File {
        val file: File
        // 判断是否有SD卡或者外部存储器
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            // 有SD卡则使用SD - PS:没SD卡但是有外部存储器，会使用外部存储器
            // sdcard\Android\data\包名\files\Log\csvFileName.csv
            file = File(mContext.getExternalFilesDir("Log")!!.getPath() + "/")
        } else {
            // 没有SD卡或者外部存储器，使用内部存储器
            // \data\data\包名\files\Log\csvFileName.csv
            file = File(mContext.getFilesDir().getPath() + "/Log/")
        }
        // 若目录不存在则创建目录
        if (!file.exists()) {
            file.mkdir()
        }
        val logFile = File(file.path + "/" + csvFileName + ".csv")
        if (!logFile.exists()) {
            try {
                logFile.createNewFile()
            } catch (e: Exception) {
                Log.e(TAG, "Create log file failure: --> " + e.toString())
            }
        }

        handlerThread.start()
        handler = Handler(handlerThread.looper)
        bufferedWriter = BufferedWriter(FileWriter(logFile))

        // 写入文件首行
        postWriteLn("create_date,fps")

        return logFile
    }

    /**
     * 将fps和时间写入文件
     */
    fun write(fps: Double) {
        postWriteLn(logSDF.format(java.util.Date()) + "," + fps)
    }

    fun postWriteLn(string: String) {
        handler?.post({
            bufferedWriter?.let {
                synchronized(it) {
                    it.write(string + "\r\n")
                    it.flush()
                }
            }
        })
    }

    /**
     * 停止写文件
     */
    fun closeWriter() {
        bufferedWriter?.let {
            synchronized(it) {
                it.flush()
                it.close()
            }
        }
        bufferedWriter = null
        handlerThread.stop()
    }
}