CREATE OR REPLACE PROCEDURE backup_data_prc AS

  v_parentNo varchar2(32);

  v_count number;

BEGIN
  v_count := 0;

  delete SRVS_TASK_RESULT where task_exestop_time  < ADD_MONTHS(sysdate, -1) ;

  insert into SRVS_PRESCRIPTION_RECORD_his   select * from SRVS_PRESCRIPTION_RECORD t where to_date(t.occdt, 'yyyy-MM-DD hh24:mi:ss') < sysdate -1;

  delete  SRVS_PRESCRIPTION_RECORD t where to_date(t.occdt, 'yyyy-MM-DD hh24:mi:ss') < sysdate -1;

  commit;
  -- 获取需要备份的主医嘱号
  DECLARE
      CURSOR parentNOs IS select parent_no from SRVS_DOCTOR_ADVICE_main where to_date(syndate, 'yyyy-mm-dd hh24:mi:ss') < sysdate - 60 and yzzt <> 0 and parent_no not in (select distinct (t.groupno) from SRVS_PRESCRIPTION_RECORD t) ;

  begin
    open parentNOs;

    loop
      fetch parentNOs  into v_parentNo;

      EXIT WHEN parentNOs%NOTFOUND;

      -- 备份医嘱信息
      insert into SRVS_DOCTOR_ADVICE_his  select * from SRVS_DOCTOR_ADVICE where parent_no = v_parentNo;

      delete SRVS_DOCTOR_ADVICE where parent_no = v_parentNo;

      -- 备份SRVS_PRESCRIPTION
      insert into SRVS_PRESCRIPTION_his select * from SRVS_PRESCRIPTION where parent_no = v_parentNo;
      delete SRVS_PRESCRIPTION where parent_no = v_parentNo;

      -- 备份SRVS_PRESCRIPTION_main
      insert into SRVS_PRESCRIPTION_main_his select * from SRVS_PRESCRIPTION_main where parent_no = v_parentNo;
      delete SRVS_PRESCRIPTION_main where parent_no = v_parentNo;

      -- 备份SRVS_LABEL
      insert into SRVS_LABEL_his select * from SRVS_LABEL where parent_no = v_parentNo;
      delete SRVS_LABEL where parent_no = v_parentNo;

      --备份 SRVS_PRESCRIPTION_SCAN
      insert into SRVS_PRESCRIPTION_SCAN_his select * from SRVS_PRESCRIPTION_SCAN where yd_id like v_parentNo || '%';
      delete SRVS_PRESCRIPTION_SCAN where yd_id like v_parentNo || '%';

      --备份 SRVS_PRESCRIPTION_FEES
      insert into SRVS_PRESCRIPTION_FEES_his select * from SRVS_PRESCRIPTION_FEES where pq_ref_fee_id in (select gid from SRVS_LABEL_ref_config_fee where pidsj like v_parentNo || '%');
      delete SRVS_LABEL_ref_config_fee where pidsj like v_parentNo || '%';

      --备份 SRVS_LABEL_ref_config_fee
      insert into SRVS_LABEL_ref_config_fee_his select * from SRVS_LABEL_ref_config_fee where pidsj like v_parentNo || '%';
      delete SRVS_PRESCRIPTION_FEES where pq_ref_fee_id in (select gid from SRVS_LABEL_ref_config_fee  where pidsj like v_parentNo || '%');

      insert into SRVS_DOCTOR_ADVICE_main_his select * from SRVS_DOCTOR_ADVICE_main where parent_no = v_parentNo;
      delete SRVS_DOCTOR_ADVICE_main where parent_no = v_parentNo;

       insert into yhsystem.vpivastonurse_his   select * from yhsystem.vpivastonurse t where t.groupno = v_parentNo;

       delete  yhsystem.vpivastonurse t where  t.groupno = v_parentNo;

      v_count := v_count + 1;
      IF MOD(v_count, 100) = 0 THEN
        COMMIT;
        v_count := 0;
      END IF;

    END LOOP;
    commit;
    CLOSE parentNOs;
    
    insert into SRVS_TRACKINGRECORD_his select * from SRVS_TRACKINGRECORD t where t.relevance not in (select pidsj from SRVS_LABEL);
    delete SRVS_TRACKINGRECORD t where t.relevance not in (select pidsj from SRVS_LABEL);
    commit;
  end;
END;
/

--自我知识库数据解析
CREATE OR REPLACE PROCEDURE analy_yz_data_prc AS

  --主医嘱号
  v_parentNo varchar2(32);

  --在分析结果表中同种数据出现次数
  v_count1 number;

  --需要查询的字段
  v_check_code varchar2(128);

  --拼接执行的sql
  v_sql varchar2(1024);

  v_param  varchar2(1024);

   --拼接执行的sql
  v_sql_excute varchar2(1024);

  v_key varchar2(1024);

  v_result integer;

  v_resul_old integer;

BEGIN

  select SYS_VALUE  into v_check_code from SYS_CONFIG where SYS_KEY = 'yz_localKnowledge_params';

  -- 获取需要解析的主医嘱号
  DECLARE
    -- 拼接执行的sql

    yz_main SRVS_DOCTOR_ADVICE_main%rowtype;

    CURSOR check_codes IS  SELECT * FROM TABLE(CAST(fn_split(v_check_code, ';') AS ty_str_split));
 
    CURSOR yz_mains IS select * from SRVS_DOCTOR_ADVICE_main t where yzshzt <> 0 and t.sfrq > sysdate - 2;

  begin

    v_sql := 'select ';

    v_param := 'select';

    open check_codes;

    loop

      fetch check_codes  into v_check_code;

      EXIT WHEN check_codes%NOTFOUND;

      v_sql := v_sql || ' wmsys.wm_concat( ' || v_check_code || ') || ''-'' || ';

      if v_param = 'select' then

       v_param := v_param  || ' ' ||  v_check_code;

         else

       v_param := v_param || ',' || v_check_code;

      end if;

    END LOOP;

    CLOSE check_codes;

    open yz_mains;

    loop
      fetch yz_mains into yz_main;

      EXIT WHEN yz_mains%NOTFOUND;

      v_parentNo      := yz_main.parent_no;

      v_result        := yz_main.yzshzt;

      if v_result = 1 then

        v_result := 0;

        else

           v_result := 1;

      end if;

       v_sql_excute := v_sql || ' '''' as key from  (' || v_param || ' from SRVS_DOCTOR_ADVICE  where PARENT_NO =''' ||
               v_parentNo || ''' and yzshzt <> 0 order by CHARGE_CODE )';

       dbms_output.put_line(v_sql_excute);

    execute immediate v_sql_excute into v_key;

      BEGIN
        -- 根据key查询srvs_autocheckdata，判断该条解析数据是否已存在
        select RESULT, count into v_resul_old, v_count1 from srvs_autocheckdata where key = v_key;

         -- 根据key，历史数据中已存在相同数据，判断结果是否一致
          v_count1 := v_count1 + 1;

          if v_resul_old = -1 then

            update srvs_autocheckdata set count = v_count1,oprdate = sysdate where key = v_key;

          else

            if v_resul_old = v_result then

              update srvs_autocheckdata set count = v_count1,oprdate = sysdate where key = v_key;

            else

              update srvs_autocheckdata set count = v_count1, result = -1,oprdate = sysdate where key = v_key;

            end if;

          end if;

      EXCEPTION
        --未查询到数据时，插入操作
        WHEN NO_DATA_FOUND THEN

          insert into srvs_autocheckdata （key, result, count) values (v_key, v_result, 1);

      END;

    commit;

    END LOOP;

    CLOSE yz_mains;

    delete srvs_autocheckdata where count < （select SYS_VALUE from SYS_CONFIG where SYS_KEY = 'yz_localKnowledge_limit' ）;

    commit;
  end;
END;
/

--打印瓶签历史表数据备份
CREATE OR REPLACE PROCEDURE backup_print_data_prc AS

  v_gid number;

  v_count number;

BEGIN
  v_count := 0;

  -- 获取需要备份的打印序列
  DECLARE
    CURSOR gids IS select distinct gid  from SRVS_PRINT_HISTORY  where to_char(PRINT_DATE,'yyyy-mm-dd') <= to_char(sysdate-3,'yyyy-mm-dd');

  begin
    open gids;

    loop
      fetch gids  into v_gid;

      EXIT WHEN gids%NOTFOUND;

      -- 备份打印记录
      insert into SRVS_PRINT_HISTORY_his  select * from SRVS_PRINT_HISTORY where gid = v_gid;
      delete SRVS_PRINT_HISTORY where gid = v_gid;


      v_count := v_count + 1;
      IF MOD(v_count, 100) = 0 THEN
        COMMIT;
        v_count := 0;
      END IF;

    END LOOP;
    commit;
    CLOSE gids;
  end;
END;
/

--打印条件设置，用药日期自动切换
CREATE OR REPLACE PROCEDURE change_printcon_yyrq_prc AS

   compareDate date;

BEGIN

  compareDate := to_date(to_char(sysdate, 'yyyy-mm-dd') || ' 10:00:00',
                         'yyyy-mm-dd hh24:mi:ss');

  IF sysdate > compareDate THEN

    update SRVS_PRINTLABEL_CON set yyrq = 1 where name not like '%临%' and yyrq = 0;
    COMMIT;
  END IF;

  IF sysdate < compareDate THEN

    update SRVS_PRINTLABEL_CON set yyrq = 0 where name not like '%临%'and yyrq = 1;
    COMMIT;
  END IF;

  commit;
commit;
END;
/

CREATE OR REPLACE PROCEDURE check_barcode_ydhl AS

BEGIN

  update SRVS_LABEL t

     set t.avdp = 0

   where t.yyrq > sysdate - 1

     and t.ydpq not in (select barcode
                          from lchis.met_nui_exerecord@to_his his
                         where his.his_order_no = t.parent_no
                           and his.use_time = t.yyrq);

   commit;
   
   update SRVS_DOCTOR_ADVICE_main set yzshzt = 1,sfyscode = 'admin',sfysmc='admin',sfrq = sysdate where yzshzt = 0 and yzzt = 1;
   
   
   update SRVS_DOCTOR_ADVICE set yzshzt = 1,sfyscode = 'admin',sfysmc='admin',sfrq = sysdate where  yzshzt = 0 and yzzt = 1;
   
   commit;
END;
/

--将停止的未审核的医嘱默认为审方通过
CREATE OR REPLACE PROCEDURE check_yzshzt_stop AS

BEGIN

   update SRVS_DOCTOR_ADVICE_main set yzshzt = 1,sfyscode = 'admin',sfysmc='admin',sfrq = sysdate where yzshzt = 0 and yzzt = 1;


   update SRVS_DOCTOR_ADVICE set yzshzt = 1,sfyscode = 'admin',sfysmc='admin',sfrq = sysdate where  yzshzt = 0 and yzzt = 1;

   commit;
END;
/

--数据备份
CREATE OR REPLACE PROCEDURE backup_scheduleData AS

  v_id integer;

  v_count number;

BEGIN
  v_count := 0;

  -- 获取需要备份的数据id
  DECLARE
    CURSOR ids IS select id from SCHE_RECORD where startdate < to_char(ADD_MONTHS(sysdate, -5), 'yyyy-mm-dd');

  begin
    open ids;

    loop
      fetch ids  into v_id;

      EXIT WHEN ids%NOTFOUND;

      -- 一周数据
      insert into SCHE_RECORD_HIS  select * from SCHE_RECORD where id = v_id;
      delete SCHE_RECORD where id = v_id;

      -- 每天数据
      insert into SCHE_RECORD_WEEK_HIS select * from SCHE_RECORD_WEEK where id = v_id;
      delete SCHE_RECORD_WEEK where id = v_id;

      --每一百次提交
      v_count := v_count + 1;
      IF MOD(v_count, 100) = 0 THEN
        COMMIT;
        v_count := 0;
      END IF;

    END LOOP;
    commit;
    CLOSE ids;
  end;
END;
/

