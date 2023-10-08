%~d0
cd %~dp0
java -Dtalend.component.manager.m2.repository="%cd%/../lib" -Xms256M -Xmx1024M -Dfile.encoding=UTF-8 -cp .;../lib/routines.jar;../lib/log4j-slf4j-impl-2.13.2.jar;../lib/log4j-api-2.13.2.jar;../lib/log4j-core-2.13.2.jar;../lib/jtds-1.3.1-patch-20190523.jar;../lib/jboss-marshalling-2.0.12.Final.jar;../lib/dom4j-2.1.3.jar;../lib/talend_DB_mssqlUtil-1.4.jar;../lib/crypto-utils-0.31.12.jar;../lib/slf4j-api-1.7.29.jar;sales_dm_0_1.jar; ecomevaluation.sales_dm_0_1.sales_DM %*
