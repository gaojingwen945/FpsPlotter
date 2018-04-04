package com.code.ginniegao.myapplication

import android.app.Activity
import android.os.Bundle
import com.code.ginniegao.fpsplot.FpsPlotter

class MainActivity : Activity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FpsPlotter.start(applicationContext, "fps")
        openWrite(true)
    }

    /**
     * 控制文件写开关状态，通常可以在列表静止时关闭，在列表滑动时打开
     */
    fun openWrite(open: Boolean) {
        FpsPlotter.writeOpen = open
    }

    override fun onDestroy() {
        super.onDestroy()
        FpsPlotter.stop()
    }
}
