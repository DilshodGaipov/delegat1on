@echo off
setlocal ENABLEEXTENSIONS ENABLEDELAYEDEXPANSION

echo === DELEGAT1ON build (Windows) ===
echo Checking Java and Maven...
java -version
if errorlevel 1 (
  echo.
  echo [ERROR] Java (JDK) not found in PATH. Please install JDK 11 or 8 and reopen terminal.
  exit /b 1
)
mvn -v
if errorlevel 1 (
  echo.
  echo [ERROR] Maven not found in PATH. Please install Maven 3.6+ and reopen terminal.
  exit /b 1
)

echo.
echo Building JAR...
mvn -DskipTests package
if errorlevel 1 (
  echo.
  echo [ERROR] Build failed.
  exit /b 1
)

echo.
echo ✔ Done. See target\delegat1on-1.0.8-ddcorp.jar
exit /b 0
