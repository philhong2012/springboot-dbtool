db tool     
============

简介
--------------------------
数据库并发连接测试工具

参数
-------------------
* threadCount 连接线程数

使用方法
--------------------------
java -jar db-tool.jar --threadCount=1000  


java -jar db-tool.jar  type=oracle url=jdbc:oracle:thin:@192.168.18.89:1521:orcl user="sys as sysdba" password=tepper