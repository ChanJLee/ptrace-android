# /bin/bash

echo "check process"
pid=`adb shell ps | grep app_process | awk -F ' ' '{print $2}'`
if [[ ${pid} != "" ]]; then
	adb shell kill ${pid}
	echo "kill process: ${pid}"
fi

package=`adb shell dumpsys activity top | grep ACTIVITY | awk '{print $2}' | awk -F '/' '{print $1}'`
echo "connect: ${package}"

cmd="export CLASSPATH=/data/app/${package}-1/base.apk; exec app_process /system/bin ${package}.Main"
echo $cmd
adb shell ${cmd}
