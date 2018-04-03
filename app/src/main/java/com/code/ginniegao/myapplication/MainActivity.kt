package com.code.ginniegao.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.code.ginniegao.fpsplot.FpsPlotter

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FpsPlotter.start(applicationContext, "fps")
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
