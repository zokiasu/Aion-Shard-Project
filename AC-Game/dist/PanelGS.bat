@ECHO off
@COLOR 2
TITLE Aion-Shard GS v4.7.5.x
:MENU
CLS
ECHO.
ECHO   ^*--------------------------------------------------------------------------^*
ECHO                         Aion-Shard GS v4.7.5.x
ECHO   ^*--------------------------------------------------------------------------^*
ECHO   ^|                                                                          ^|
ECHO   ^|    1 - Development                                       4 - Quit        ^|
ECHO   ^|    2 - Production X1                                                     ^|
ECHO   ^|    3 - Production X2                                                     ^|
ECHO   ^|                                                                          ^|
ECHO   ^*--------------------------------------------------------------------------^*
ECHO.
SET /P OPTION=Type your option and press ENTER: 
IF %OPTION% == 1 (
SET MODE=DEVELOPMENT
SET JAVA_OPTS=-Xms2g -Xmx20g -XX:MaxHeapSize=20g -Xdebug -Xrunjdwp:transport=dt_socket,address=8998,server=y,suspend=n -ea
CALL StartGS.bat
)
IF %OPTION% == 2 (
SET MODE=PRODUCTION
SET JAVA_OPTS=-Xms1536m -Xmx1536m -server
CALL StartGS.bat
)
IF %OPTION% == 3 (
SET MODE=PRODUCTION X2
SET JAVA_OPTS=-Xms3872m -Xmx3872m -server
CALL StartGS.bat
)
IF %OPTION% == 4 (
EXIT
)
IF %OPTION% GEQ 5 (
GOTO :MENU
)