@echo off
echo !!! Compiling boredBOT ...
javac -d ./bin *.java
cd bin
echo !!! Starting boredBOT ...
java StartBOT
cd ..