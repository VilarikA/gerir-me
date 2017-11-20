set SCRIPT_DIR=%~dp0
"C:\Program Files\Java\jdk1.7.0_65\bin\java.exe" -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=1024M -Xmx524M -jar "%SCRIPT_DIR%\sbt-launcher.jar" %*
