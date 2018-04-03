#! /bin/bash
adb pull /sdcard/Android/data/com.code.ginniegao.myapplication/files/Log/fps.csv
python plotFps.py fps
