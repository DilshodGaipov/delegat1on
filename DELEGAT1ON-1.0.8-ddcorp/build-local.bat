@echo off
setlocal ENABLEEXTENSIONS ENABLEDELAYEDEXPANSION
echo Checking Java and Maven...
java -version
mvn -v
echo.
echo Building JAR...
mvn -DskipTests package
if errorlevel 1 (
  echo Build failed.
  exit /b 1
)
echo.
echo ✔ Done. See target\delegat1on-1.0.8-ddcorp.jar
