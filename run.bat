@echo off
echo Compiling...
javac -d bin -cp ".;lib/*" src\*.java


echo Running...
java -cp ".;bin;lib/*" Bank

pause