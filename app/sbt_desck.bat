set SCRIPT_DIR=%~dp0
"C:\Program Files\Java\jdk1.7.0_80\bin\java.exe" -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=2048M -Xmx524M -jar "%SCRIPT_DIR%\sbt-launcher.jar" %*
