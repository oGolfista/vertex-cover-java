@echo off
chcp 65001 > nul
set JAVA_BIN=C:\Program Files\Eclipse Adoptium\jdk-25.0.3.9-hotspot\bin

echo Compilando...
"%JAVA_BIN%\javac" *.java
if errorlevel 1 (
    echo ERRO na compilacao!
    pause
    exit /b 1
)
echo Compilado com sucesso!
echo.

if "%1"=="exemplo" (
    "%JAVA_BIN%\java" -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 Main < exemplo_entrada.txt
) else (
    "%JAVA_BIN%\java" -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 Main
)
pause
