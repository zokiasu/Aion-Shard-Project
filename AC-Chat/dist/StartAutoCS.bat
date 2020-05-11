@ECHO off
@COLOR 0C
TITLE Aion-Shard CS v4.7.5.x

SET MODE=DEVELOPMENT
SET JAVA_OPTS=-Xms128m -Xmx512m -Xdebug -Xrunjdwp:transport=dt_socket,address=8997,server=y,suspend=n -ea
CALL StartCS.bat