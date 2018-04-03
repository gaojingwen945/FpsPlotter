package com.code.ginniegao.fpsplot

import android.view.Choreographer
import java.util.ArrayList
import java.util.concurrent.TimeUnit

/**
 * Fps采样工具
 *
 * Created by ginniegao on 2018/3/28.
 */
class FpsSampleUtil(private val fpsCallback: FpsCallback = FpsCallback()) {
    /**
     * 设置采样间隔，单位ms
     */
    fun interval(ms: Int): FpsSampleUtil {
        fpsCallback.setInterval(ms)
        return this
    }

    /**
     * 添加采样监听
     */
    fun listener(audience: Audience): FpsSampleUtil {
        fpsCallback.addListener(audience)
        return this
    }

    /**
     * 开始监听
     */
    fun start() {
        fpsCallback.start()
    }

    /**
     * 停止监听
     */
    fun stop() {
        fpsCallback.stop()
    }
}

interface Audience {
    fun heartbeat(fps: Double)
}

/**
 * 根据采样间隔，计算平均FPS
 *
 * Created by ginniegao on 2018/3/28.
 */
class FpsCallback : Choreographer.FrameCallback {
    private val choreographer: Choreographer = Choreographer.getInstance()

    private var frameStartTime: Long = 0
    private var framesRendered = 0

    private val listeners = ArrayList<Audience>()
    private var interval = 500

    fun start() {
        choreographer.postFrameCallback(this)
    }

    fun stop() {
        frameStartTime = 0
        framesRendered = 0
        choreographer.removeFrameCallback(this)
    }

    fun addListener(l: Audience) {
        listeners.add(l)
    }

    fun setInterval(interval: Int) {
        this.interval = interval
    }

    override fun doFrame(frameTimeNanos: Long) {
        val currentTimeMillis = TimeUnit.NANOSECONDS.toMillis(frameTimeNanos)

        if (frameStartTime > 0) {
            // 间隔时间ms
            val timeSpan = currentTimeMillis - frameStartTime
            framesRendered++

            if (timeSpan > interval) {
                // 转换为每秒的帧数
                val fps = framesRendered * 1000 / timeSpan.toDouble()

                frameStartTime = currentTimeMillis
                framesRendered = 0

                for (audience in listeners) {
                    audience.heartbeat(fps)
                }
            }
        } else {
            frameStartTime = currentTimeMillis
        }

        choreographer.postFrameCallback(this)
    }
}