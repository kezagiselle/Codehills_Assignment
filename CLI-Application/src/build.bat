@echo off
echo Building Car Fuel CLI...

REM Create output directory if it doesn't exist
if not exist "out" mkdir out

REM Compile all Java files
javac -d out -cp ".;lib/*" src/com/example/CLI_Application/*.java

REM Create JAR file
echo Main-Class: com.example.CLI_Application.Main > manifest.txt
jar cfm car-fuel-cli.jar manifest.txt -C out .

REM Clean up
del manifest.txt

echo Build complete! JAR created: car-fuel-cli.jar
echo.
echo To run: java -jar car-fuel-cli.jar
pause