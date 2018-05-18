@echo off

:restart
java -Xms512M -Xmx1G -XX:+UseConcMarkSweepGC -jar spigot.jar
pause
goto restart