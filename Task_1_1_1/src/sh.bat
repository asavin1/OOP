javac -d .\out .\main\java\org\example\*.java
javadoc -d .\javadoc .\main\java\org\example\*.java
cd .\out
java -classpath . org.example.Main
pause