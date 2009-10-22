
del sample.war

del /Q WEB-INF\classes\view

set PATH=C:\Program Files\Java\jdk1.5.0_10\bin;D:\samarjit\apache-ant-1.7.1-bin\apache-ant-1.7.1\bin;

set clspath=.\WEB-INF\lib\commons-logging-1.0.4.jar;.\WEB-INF\lib\freemarker-2.3.8.jar;.\WEB-INF\lib\jstl.jar;.\WEB-INF\lib\ognl-2.6.11.jar;.\WEB-INF\lib\standard.jar;.\WEB-INF\lib\struts2-core-2.0.14.jar;.\WEB-INF\lib\xwork-2.0.7.jar;


javac -d .\WEB-INF\classes -cp %clspath% .\src\view\*.java



jar -cvf  sample.war .


copy  sample.war   "C:\Program Files\Apache Software Foundation\Apache Tomcat 6.0.16\webapps\"

