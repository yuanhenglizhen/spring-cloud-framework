#!/bin/sh
#该脚本为Linux下启动java程序的通用脚本。即可以作为开机自启动service脚本被调用，
#也可以作为启动java程序的独立脚本来使用。
#
#Author: tudaxia.com, Date: 2011/6/7
#
#警告!!!：该脚本stop部分使用系统kill命令来强制终止指定的java程序进程。
#在杀死进程前，未作任何条件检查。在某些情况下，如程序正在进行文件或数据库写操作，
#可能会造成数据丢失或数据不完整。如果必须要考虑到这类情况，则需要改写此脚本，
#增加在执行kill命令前的一系列检查。
#
#
###################################
#环境变量及程序执行参数
#JAVA_HOME="/usr/java/jdk"
APP_MAINCLASS="com.huilian.hlej.server.Application"
APP_HOME="$(dirname $(pwd))"
APP_NAME="${APP_HOME##*/}"

PID_FILE=$APP_HOME/bin/PID 
#java虚拟机启动参数
JAVA_OPTS="-ms512m -mx512m -Xmn256m -Djava.awt.headless=true"
###################################
#当前目录 
DIR="$( cd "$( dirname "$0"  )" && pwd  )"
#echo ${DIR}
cd ${DIR} 
#需要根据实际环境以及Java程序名称来修改这些参数
#JDK所在路径
if [ -z "$JAVA_HOME" -a -z "$JRE_HOME" ]; then
  JAVA_PATH=`which java 2>/dev/null`
  if [ "x$JAVA_PATH" != "x" ]; then
    JAVA_PATH=`dirname $JAVA_PATH 2>/dev/null`
    JRE_HOME=`dirname $JAVA_PATH 2>/dev/null`
  fi


  if [ "x$JRE_HOME" = "x" ]; then
    # XXX: Should we try other locations?
    if [ -x /usr/bin/java ]; then
      JRE_HOME=/usr
    fi
  fi
  if [ -z "$JAVA_HOME" -a -z "$JRE_HOME" ]; then
    echo "Neither the JAVA_HOME nor the JRE_HOME environment variable is defined"
    echo "At least one of these environment variable is needed to run this program"
    exit 1
  fi
fi
 




#执行程序启动所使用的系统用户，考虑到安全，推荐不使用root帐号
#RUNNING_USER=root
 
#Java程序所在的目录（classes的上一级目录）
#APP_HOME=/opt/tudaxia/test/WEB-INF
 
#需要启动的Java主程序（main方法类）
#PP_MAINCLASS=com.tudaxia.test.TestMain
 
#拼凑完整的classpath参数，包括指定lib目录下所有的jar
CLASSPATH="$APP_HOME/config:$APP_HOME/lib/*"
#for i in "$APP_HOME"/lib/*.jar; do
#  CLASSPATH="$CLASSPATH":"$i"
#done

echo "Using APP_HOME:$APP_HOME"
echo "Using JRE_HOME:$JRE_HOME" 
echo "Using CLASSPATH:$CLASSPATH" 
echo "Using APP_NAME:$APP_NAME" 

#JAVA_OPTS="$JAVA_OPTS -Xdebug -Xrunjdwp:transport=dt_socket,address=32044,server=y,suspend=n"
  


###################################
#(函数)启动程序 
###################################
start() { 
    if [ ! -z "$PID_FILE" ]; then
        if [ -f "$PID_FILE" ]; then
        if [ -s "$PID_FILE" ]; then
            echo "Existing PID file found during start."
            if [ -r "$PID_FILE" ]; then
            PID=`cat "$PID_FILE"`
            ps -p $PID >/dev/null 2>&1
            if [ $? -eq 0 ] ; then
                echo "Service appears to still be running with PID $PID. Start aborted."
                echo "If the following process is not a Service process, remove the PID file and try again:"
                ps -f -p $PID
                echo  -e  "\nService start failed...\n" 
                exit 1
            else
                echo "Removing/clearing stale PID file."
                rm -f "$PID_FILE" >/dev/null 2>&1
                if [ $? != 0 ]; then
                if [ -w "$PID_FILE" ]; then
                    cat /dev/null > "$PID_FILE"
                else
                    echo "Unable to remove or clear stale PID file. Start aborted."
                    exit 1
                fi
                fi
            fi
            else
            echo "Unable to read PID file. Start aborted."
            exit 1
            fi
        else
            rm -f "$PID_FILE" >/dev/null 2>&1
            if [ $? != 0 ]; then
            if [ ! -w "$PID_FILE" ]; then
                echo "Unable to remove or write to empty PID file. Start aborted."
                exit 1
            fi
            fi
        fi
        fi
    fi

   
      echo "Starting $APP_MAINCLASS ..."
      JAVA_CMD="$JRE_HOME/bin/java $JAVA_OPTS -classpath $CLASSPATH $APP_MAINCLASS >/dev/null 2>&1 &" 
      eval $JRE_HOME/bin/java $JAVA_OPTS -classpath $CLASSPATH $APP_MAINCLASS "$@" \
      >> "/dev/null" 2>&1 "&"
      if [ ! -z "$PID_FILE" ]; then
        echo $! > "$PID_FILE"
      fi 
      echo  -e  "\nService start succsss...\n" 
} 


###################################
#(函数)停止程序 
###################################
stop() { 
 SLEEP=5

   if [ -z "$PID_FILE" -o ! -f "$PID_FILE"  -o -s "$PID_FILE" ]; then
		echo "The PID File is not exist ,will kill all service $APP_NAME."
		ps -ef|grep $APP_HOME|grep -v grep|awk  '{print "kill -9 " $2}' |sh
        exit 0
	fi

  if [ ! -z "$PID_FILE" ]; then #长度不为空
    if [ -f "$PID_FILE" ]; then #文件存在
      if [ -s "$PID_FILE" ]; then #文件大小不为空
        kill -0 `cat "$PID_FILE"` >/dev/null 2>&1
        #查看进程是否存在，如果不存在 直接删除pid文件
        if [ $? -gt 0 ]; then
          #echo "PID file found but no matching process was found. Stop aborted." 
          #exit 1
            rm -f "$PID_FILE" >/dev/null 2>&1
            if [ $? != 0 ]; then
                if [ ! -w "$PID_FILE" ]; then
                    echo "Unable to remove or write to empty PID file. Start aborted."
                    exit 1
                fi
            fi
        fi
      else #文件为空
        echo "PID file is empty and has been ignored."
        exit 1
      fi
    else #文件不存在
      echo "\$PID_FILE was set but the specified file does not exist. Is Tomcat running? Stop aborted."
      exit 1
    fi
  fi

#   if [ ! -z "$PID_FILE" ]; then #长度不为空
      kill -15 `cat "$PID_FILE"` >/dev/null 2>&1
#   fi

#文件存在 ，这里是kill -3 通知进行关闭
#   if [ ! -z "$PID_FILE" ]; then
#     if [ -f "$PID_FILE" ]; then
    while [ $SLEEP -ge 0 ]; do
        kill -0 `cat "$PID_FILE"` >/dev/null 2>&1
        if [ $? -gt 0 ]; then
            rm -f "$PID_FILE" >/dev/null 2>&1
            if [ $? != 0 ]; then
                if [ -w "$PID_FILE" ]; then
                cat /dev/null > "$PID_FILE"
                # If Tomcat has stopped don't try and force a stop with an empty PID file
                FORCE=0
                else
                echo "The PID file could not be removed or cleared."
                fi
            fi
            echo -e "\nService stopped...\n"
            break
        fi
        if [ $SLEEP -gt 0 ]; then
                sleep 1
        fi
        if [ $SLEEP -eq 0 ]; then
            echo "service did not stop in time."
            if [ $FORCE -eq 0 ]; then
                echo "PID file was not removed."
            fi
        echo "To aid diagnostics a thread dump has been written to standard out."
        kill -3 `cat "$PID_FILE"`
        fi
        SLEEP=`expr $SLEEP - 1 `
    done
#     fi
#   fi

#   if [ ! -z "$PID_FILE" ]; then 
       if [ -r "$PID_FILE" ]; then
            PID=`cat "$PID_FILE"`
            ps -p $PID >/dev/null 2>&1
            if [ $? -eq 0 ] ; then
               kill -9 `cat "$PID_FILE"` >/dev/null 2>&1
            fi
        fi
#   fi
 
} 


status() { 
    if [ ! -z "$PID_FILE" ]; then
        if [ -f "$PID_FILE" ]; then
            if [ -s "$PID_FILE" ]; then
                if [ -r "$PID_FILE" ]; then
                    PID=`cat "$PID_FILE"`
                    ps -p $PID >/dev/null 2>&1
                    if [ $? -eq 0 ] ; then
                        #pid文件存在,且有值，且进程还在
                        echo "The Service is running...."
                    else
                        #pid文件存在,且有值，但进程不在
                        echo "The Service is stoped...."
                    fi
                else
                    #文件不可读
                    echo "Unable to read PID file. Start aborted."
                    exit 1
                fi
            else
                #不是文件
                echo "The Service is stoped...."
            fi
        else
            #pid文件不存在
            echo "The Service is stoped...."
        fi
    fi
} 

info() { 
   echo "System Information:"
   echo "****************************"
   echo `head -n 1 /etc/issue`
   echo `uname -a`
   echo "JRE_HOME=$JRE_HOME"
   echo `$JRE_HOME/bin/java -version`
   echo "APP_HOME=$APP_HOME"
   echo "APP_MAINCLASS=$APP_MAINCLASS"
    if [ ! -z "$PID_FILE" ]; then
        if [ -f "$PID_FILE" ]; then
            if [ -s "$PID_FILE" ]; then
                if [ -r "$PID_FILE" ]; then
           PID=`cat "$PID_FILE"`
           echo "PID=$PID"
           ps -p $PID
    fi
    fi
    fi
    fi


   echo "****************************"
} 

###################################
#读取脚本的第一个参数($1)，进行判断
#参数取值范围：{start|stop|restart|status|info}
#如参数不在指定范围之内，则打印帮助信息
###################################

if [ "$1" = "start" ] ; then
    echo "start"
    start
elif [ "$1" = "stop" ]; then
    echo "stop"
    stop
elif [ "$1" = "restart" ]; then
    echo "restart"
      if [ ! -z "$PID_FILE" ]; then
      if [ -f "$PID_FILE" ]; then
      if [ -s "$PID_FILE" ]; then
        kill -0 `cat "$PID_FILE"` >/dev/null 2>&1
        if [ $? -eq 0 ]; then
          stop
        fi 
        if [ $? -gt 0 ]; then
          exit 1
        fi
      fi
      fi
      fi   
    start
elif [ "$1" = "status" ]; then
    echo "status"
    status
elif [ "$1" = "info" ]; then
    echo "info"
    info
else
    echo "Usage: $0 {start|stop|restart|status|info}"
    exit 1
fi