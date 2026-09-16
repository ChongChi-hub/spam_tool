@echo off
echo Dang bien dich ma nguon...
javac -encoding UTF-8 AutoTyper.java
if %errorlevel% neq 0 (
    echo.
    echo Bien dich that bai! Vui long kiem tra lai.
    pause
    exit /b %errorlevel%
)
echo.
echo Khoi chay ung dung...
java AutoTyper
pause
