#!/bin/sh
mkdir -p classes
javac -d classes -cp lib/gson-2.3.1.jar:lib/spark-core-2.5.4.jar:lib/javax.servlet-api-3.1.0.jar src/se/kth/sda/othello/service/*.java
