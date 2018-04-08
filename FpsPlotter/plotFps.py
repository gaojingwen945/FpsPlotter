# coding=UTF-8
#导入需要的模块
import sys
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.mlab as mlab
import matplotlib.ticker as ticker

fileName = sys.argv[1]
#读取CSV数据为numpy record array记录
r = mlab.csv2rec(fileName+'.csv')
r.sort()

#形成Y轴坐标数组
N = len(r)
ind = np.arange(N)  # the evenly spaced plot indices
#ind1这里是为了把图撑大一点
ind1 = np.arange(N+3)

#将X轴格式化为日期形式，X轴默认为0.5步进，
#这里将整数X轴坐标格式化为日期，.5的不对应日期，
#因为扩展了3格坐标，所以N+的坐标点也不显示日期
def format_date(x, pos=None):
    if not x%1 and  x<N:
        thisind = np.clip(int(x), 0, N-1)
        return r.create_date[thisind].strftime('%Y-%m-%d-%H:%M:%S')
    else:
        return ''

#绘图
fig = plt.figure()
ax = fig.add_subplot(111)
#下行为了将图扩大一点，用白色线隐藏显示
ax.plot(ind1,ind1,'-',color='white')
#正常要显示的bug总数折线
ax.plot(ind, r['fps'], 'o-',label=fileName)
#图标的标题
ax.set_title(u"FPS Chart")
#线型示意说明
ax.legend(loc='upper left')

#在折线图上标记数据，-+0.1是为了错开一点显示数据
datadotxy=tuple(zip(ind-0.1,r['fps']+0.1))
for dotxy in datadotxy:
    ax.annotate(str(int(dotxy[1]-0.1)),xy=dotxy)

#将X轴格式化为日期形式    
ax.xaxis.set_major_formatter(ticker.FuncFormatter(format_date))
fig.autofmt_xdate() 

#将图片保存到指定目录
plt.savefig(fileName+'.png')

#显示图片
plt.show()

