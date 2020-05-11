@ECHO off
@COLOR 2
TITLE Aion-Shard GS v4.7.5.x

SET MODE=DEVELOPMENT
SET JAVA_OPTS=-Xms2g -Xmx20g -XX:MaxHeapSize=20g -Xdebug -Xrunjdwp:transport=dt_socket,address=8998,server=y,suspend=n -ea
CALL StartGS.bat