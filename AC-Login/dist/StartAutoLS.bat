@ECHO off
@COLOR 0C
TITLE Aion-Shard LS v4.7.5.x

SET MODE=DEVELOPMENT
SET JAVA_OPTS=-Xms32m -Xmx512m -Xdebug -Xrunjdwp:transport=dt_socket,address=8999,server=y,suspend=n -ea
CALL StartLS.bat