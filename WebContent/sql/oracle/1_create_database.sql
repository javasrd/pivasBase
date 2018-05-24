/*==============================================================*/
/* 创建临时表空间，数据表空间，索引表空间，创建用户并授权。                                                     */
/*==============================================================*/
/*create temporary tablespace pivas_tablespace_temp tempfile 'D:\oracledata\pivas_temp.dbf' size 100m autoextend on next 50m MAXSIZE UNLIMITED extent management local;*/

/*create tablespace pivas_tablespace LOGGING datafile 'D:\oracledata\pivas_data.dbf' size 100m autoextend on next 50m MAXSIZE UNLIMITED extent management local;*/

/*create tablespace pivas_index_tablespace LOGGING  datafile 'D:\oracledata\pivas_index_data.dbf' size 100m autoextend on next 50m MAXSIZE UNLIMITED extent management local; */

create temporary tablespace pivas_tablespace_temp tempfile '/u01/app/oracle/oradata/XE/pivas_temp.dbf' size 100m autoextend on next 50m MAXSIZE UNLIMITED extent management local;
create tablespace pivas_tablespace LOGGING datafile '/u01/app/oracle/oradata/XE/pivas_data.dbf' size 100m autoextend on next 50m MAXSIZE UNLIMITED extent management local;
create tablespace pivas_index_tablespace LOGGING  datafile '/u01/app/oracle/oradata/XE/pivas_index_data.dbf' size 100m autoextend on next 50m MAXSIZE UNLIMITED extent management local;

DROP USER pivas CASCADE;
CREATE USER pivas IDENTIFIED BY pivas DEFAULT TABLESPACE pivas_tablespace TEMPORARY TABLESPACE pivas_tablespace_temp;
GRANT CREATE SESSION, RESOURCE, CREATE VIEW, UNLIMITED TABLESPACE TO pivas;
exit;
