--数据备份定时job 凌晨一点执行
declare
  job number;
begin
  sys.dbms_job.submit(job,
                      'backup_data_prc;',
                      sysdate,
                      'TRUNC(SYSDATE)+1+23/24');
commit;
end;
/

--本地自我知识库数据更新
declare
  job number;
begin
  sys.dbms_job.submit(job,
                      'analy_yz_data_prc;',
                      sysdate,
                      'TRUNC(SYSDATE)+1+22/24');
commit;
end;
/


declare
  job number;
begin
  sys.dbms_job.submit(job,
                      'backup_print_data_prc;',
                      sysdate,
                      'TRUNC(SYSDATE)+1+1/24');
commit;
end;
/

--打印条件设置，用药日期自动切换
declare
  job number;
begin
  sys.dbms_job.submit(job,
                      'change_printcon_yyrq_prc;',
                      sysdate,
                      'SYSDATE+10/1440');
commit;
end;
/

--将停止的未审核的医嘱默认为审方通过
declare
  job number;
begin
  sys.dbms_job.submit(job,
                      'check_yzshzt_stop;',
                      sysdate,
                       'SYSDATE+10/1440');
  commit;
end;
/

--数据备份定时job 每月凌晨2点执行
declare
  job number;
begin
  sys.dbms_job.submit(job,
                      'backup_scheduleData;',
                      sysdate,
                      'TRUNC(LAST_DAY(SYSDATE))+1+2/24');
commit;
end;
/