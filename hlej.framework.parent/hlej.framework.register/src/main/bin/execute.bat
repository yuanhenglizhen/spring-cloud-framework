@echo off
setlocal
rem set JAVA_HOME="/usr/java/jdk"
set APP_MAINCLASS=com.huilian.hlej.server.Application
set APP_HOME=%~dp0
set APP_NAME=%~dp0
set PROFILE_ID=%APP_HOME%bin\PID
set JAVA_OPTS=-server -Xms1024m -Xmx2048m
set CUR_DIR=%cd%
set CMD_LINE_ARGS=
set CLASSPATH=


if "%JAVA_HOME%" == "" goto noJavaHome
if not exist "%JAVA_HOME%\bin\java.exe" goto noJavaHome
if not exist "%JAVA_HOME%\bin\javaw.exe" goto noJavaHome
if not exist "%JAVA_HOME%\bin\jdb.exe" goto noJavaHome
if not exist "%JAVA_HOME%\bin\javac.exe" goto noJavaHome

SET APP_HOME=%CD%
goto stripAPP_HOME
:stripAPP_HOME
	if not "_%APP_HOME%"=="_"  set APP_HOME=%APP_HOME:~0,-1%
	echo %APP_HOME%
	goto checkMCmd

:checkMCmd
if exist "%APP_HOME%\bin\execute.bat" goto init
goto stripAPP_HOME
:init
set APP_HOME=%APP_HOME:~0,-1%
for %%a in (%APP_HOME%) do SET "APP_NAME=%%~nxa"
set CLASSPATH=%CLASSPATH%%APP_HOME%\config;%APP_HOME%\lib\*
echo Using APP_HOME:%APP_HOME%
echo Using APP_NAME:%APP_NAME%
echo Using APP_MAINCLASS:%APP_MAINCLASS%
echo Using PROFILE_ID:%PROFILE_ID%
echo Using CUR_DIR:%CUR_DIR%
echo Using CLASSPATH:%CLASSPATH%
cd %APP_HOME%\bin\

if ""%1""=="""" goto gotRunJava
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
goto gotRunJava


:noJavaHome
echo The JAVA_HOME environment variable is not defined correctly.
echo It is needed to run this program in debug mode.
echo NB: JAVA_HOME should point to a JDK not a JRE.
pause
goto exit 

:gotRunJava
  set  JAVA_CMD="%JAVA_HOME%\bin\java.exe" %JAVA_OPTS% -classpath %CLASSPATH% %APP_MAINCLASS% %CMD_LINE_ARGS% &  
  echo JAVA_CMD: %JAVA_CMD%
  
TITLE "%APP_NAME%"
  start "%APP_NAME%" /b %JAVA_CMD%
 goto exit

:exit
exit /b 1


