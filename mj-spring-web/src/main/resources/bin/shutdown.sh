#! /bin/bash

#======================================================================
 # Project shutdown shell script
 # Find PID by project name
 # Then kill -9 pid
#
#======================================================================

 # project name
APPLICATION="mj-spring-web-0.0.4-SNAPSHOT"

 # Project startup jar package name
APPLICATION_JAR="${APPLICATION}.jar"

PID=$(ps -ef | grep "${APPLICATION_JAR}" | grep -v grep | awk '{ print $2 }')
if [[ -z "$PID" ]]
then
    echo ${APPLICATION} is already stopped
else
    echo kill  ${PID}
    kill -9 ${PID}
    echo ${APPLICATION} stopped successfully
fi