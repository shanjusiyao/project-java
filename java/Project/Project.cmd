@ECHO OFF
javac src\edu\zhengy7\*.java
javadoc -author -version -d docs src\edu\zhengy7\*.java
cd src
java edu.zhengy7.Launch
