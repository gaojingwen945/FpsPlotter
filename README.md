# FpsPlotter Android帧率监控和曲线图生成工具
A library for Android platform to observe fps status and convert the fps data to curve graph.
监测应用fps数据，并提供将fps数据生成曲线图的python工具。

How to use
  1. Like the example in MyApplication, add fpsPlotter to your project：
    a) In build.gradle, add dependency:
        
              allprojects {
            		repositories {
            			...
            			maven { url 'https://jitpack.io' }
            		}
            	}
            	
              dependencies {
            	        compile 'com.github.gaojingwen945:FpsPlotter:v1.0.10'
            	}

    b) In your activity.onCreate(), add "FpsPlotter.start(applicationContext, "fps")" in Kotlin, where "fps" is your
    data file name of type .csv

    c) In your activity.onDestroy(), add "FpsPlotter.stop()" in Kotlin

    d) You can also control when to start/stop fps sampling via "FpsPlotter.writeOpen = true/false" in Kotlin. The
    writeOpen is set to default false. Normally, you can set it to 'true' when your observing list starts scrolling,
    and set it to 'false' when your list is idle.

  2. Run your project to collect data to "fps.csv" (by default):
    the default data directory is "sdcard\Android\data\yourPackageName\files\Log\fps.csv" (remember to replace the "yourPackageName" with your real package name) if you have sdcard,
    and "\data\data\yourPackageName\files\Log\csvFileName.csv" if sdcard is absent.

  3. Pull the data file from your phone to your computer and put it in the "plotFps" folder of this project.

  4. In the "plotFps" folder, run the python script to generate curve graph, e.g. "python plotFps.py fps" where fps is your csv file name.
 
 
