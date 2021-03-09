#! /bin/bash

#======================================================================
 # Project start shell script
 # boot directory: spring boot jar package
 # config directory: configuration file directory
 # logs directory: project operation log directory
 # logs/spring-boot-assembly_startup.log: Record startup log
 # logs/back directory: project running log backup directory
 # nohup background operation
#
#======================================================================

 # project name
APPLICATION="mj-spring-web-0.0.4-SNAPSHOT"

 # Project startup jar package name
APPLICATION_JAR="${APPLICATION}.jar"

 # bin directory absolute path
BIN_PATH=$(cd `dirname $0`; pwd)
 # Enter the bin directory
cd `dirname $0`
 # Return to the root directory path of the previous project
cd ..
 # Print the absolute path of the project root directory
 # `pwd` Execute system commands and get results
BASE_PATH=`pwd`

 # The absolute directory of the external configuration file, if the directory needs / end, you can also directly specify the file
 # If you specify a directory, spring will read all configuration files in the directory
CONFIG_DIR=${BASE_PATH}"/config/"

 # Project log output absolute path
LOG_DIR=${BASE_PATH}"/logs"
LOG_FILE="${APPLICATION}.log"
LOG_PATH="${LOG_DIR}/${LOG_FILE}"
 # Log backup directory
LOG_BACK_DIR="${LOG_DIR}/back/"

 # Project startup log output absolute path
LOG_STARTUP_PATH="${LOG_DIR}/${APPLICATION}_startup.log"

 # current time
NOW=`date +'%Y-%m-%m-%H-%M-%S'`
NOW_PRETTY=`'date +%Y-%m-%m %H:%M:%S'`

 # Start log
STARTUP_LOG="================================================ ${NOW_PRETTY} ================================================\n"

 # If the logs folder does not exist, create a folder
if [[ ! -d "${LOG_DIR}" ]]; then
  mkdir "${LOG_DIR}"
fi

 # If the logs/back folder does not exist, create a folder
if [[ ! -d "${LOG_BACK_DIR}" ]]; then
  mkdir "${LOG_BACK_DIR}"
fi

 # If the project log exists, rename the backup
if [[ -f "${LOG_PATH}" ]]; then
	mv ${LOG_PATH} "${LOG_BACK_DIR}/${APPLICATION}_back_${NOW}.log"
fi

 # Create a new project run log
echo "" > ${LOG_PATH}

 # If the project startup log does not exist, create it, otherwise append
echo "${STARTUP_LOG}" >> ${LOG_STARTUP_PATH}

#==========================================================================================
# JVM Configuration
 # -Xmx256m: Set the maximum available memory of the JVM to 256m, depending on the actual situation of the project. It is recommended that the minimum and maximum settings be the same.
 # -Xms256m: Set the initial memory of the JVM. This value can be set the same as -Xmx to avoid the JVM reallocating memory after each garbage collection is completed
 # -Xmn512m: Set the young generation size to 512m. The entire JVM memory size = young generation size + old generation size + persistent generation size.
 # The permanent generation generally has a fixed size of 64m, so increasing the young generation will reduce the size of the old generation. This value has a great impact on system performance. Sun officially recommends a configuration of 3/8 of the entire heap
 # -XX:MetaspaceSize=64m: The memory size of the storage class, the larger the value, the later the time to trigger Metaspace GC
 # -XX:MaxMetaspaceSize=320m: Limit the upper limit of Metaspace growth to prevent Metaspace from using local memory indefinitely due to certain circumstances, affecting other programs
 # -XX:-OmitStackTraceInFastThrow: solve the problem of not printing stack information for repeated exceptions
#==========================================================================================
JAVA_OPT="-server -Xms256m -Xmx256m -Xmn512m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=256m"
JAVA_OPT="${JAVA_OPT} -XX:-OmitStackTraceInFastThrow"

#=======================================================
 # Append command startup related logs to the log file
#=======================================================

 # Output project name
STARTUP_LOG="${STARTUP_LOG}application name: ${APPLICATION}\n"
 # Output jar package name
STARTUP_LOG="${STARTUP_LOG}application jar name: ${APPLICATION_JAR}\n"
 # Output project bin path
STARTUP_LOG="${STARTUP_LOG}application bin  path: ${BIN_PATH}\n"
 # Output project root directory
STARTUP_LOG="${STARTUP_LOG}application root path: ${BASE_PATH}\n"
 # Print log path
STARTUP_LOG="${STARTUP_LOG}application log  path: ${LOG_PATH}\n"
 # Print JVM configuration
STARTUP_LOG="${STARTUP_LOG}application JAVA_OPT : ${JAVA_OPT}\n"


 # Print start command
STARTUP_LOG="${STARTUP_LOG}application background startup command: nohup java ${JAVA_OPT} -jar ${BASE_PATH}/boot/${APPLICATION_JAR} --spring.config.location=${CONFIG_DIR} > ${LOG_PATH} 2>&1 &\n"


#======================================================================
# Execute the startup command: start the project in the background, and output the log to the logs folder under the project root directory
#======================================================================
nohup java ${JAVA_OPT} -jar ${BASE_PATH}/boot/${APPLICATION_JAR} --spring.config.location=${CONFIG_DIR} > ${LOG_PATH} 2>&1 &


 # Process ID
PID=$(ps -ef | grep "${APPLICATION_JAR}" | grep -v grep | awk '{ print $2 }')
STARTUP_LOG="${STARTUP_LOG}application pid: ${PID}\n"

 # The startup log is appended to the startup log file
echo -e ${STARTUP_LOG} >> ${LOG_STARTUP_PATH}
 # Print startup log
echo -e ${STARTUP_LOG}

 # Print project log
tail -f ${LOG_PATH}
