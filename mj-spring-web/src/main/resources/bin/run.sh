echo off
set ENV=%1
if [%1]==[] (
set ENV=dev
)
java -Xms256m -Xmx512m -server -Xloggc:../logs/gc.log -verbose:gc -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=../logs -Dlogging.config=../config/log4j2.properties -Dspring.config.location=../config/application.properties,../config/messages_en.properties,../config/env/%ENV%/env-config.properties -jar ../lib/mj-spring-boot-web-app.jar
