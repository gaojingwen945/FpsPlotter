package com.code.ginniegao.fpsplot

import android.content.Context
import android.util.Log

/**
 * FPS数据监听及保存
 *
 * Created by ginniegao on 2018/3/29.
 */
object FpsPlotter {
    private val TAG = "FpsPlotter"

    private val fpsSampleUtil = FpsSampleUtil()
    private val logFileWriter = FpsFileWriter()
    @Volatile
    var writeOpen = true // 文件写开关，通常可以在列表静止时关闭，在列表滑动时打开

    fun start(mContext: Context, csvFileName: String) {
        // 初始化，设置文件名
        logFileWriter.init(mContext, csvFileName)
        // 设置监听
        fpsSampleUtil.interval(200).listener(object : Audience {
            override fun heartbeat(fps: Double) {
                Log.i(TAG, "heartbeat: --> fps: " + fps)
                if (writeOpen) {
                    logFileWriter.write(fps)
                }
            }
        }).start()
    }

    fun stop() {
        // 停止监听和写文件
        fpsSampleUtil.stop()
        logFileWriter.closeWriter()
    }
}