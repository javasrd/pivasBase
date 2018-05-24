CREATE OR REPLACE VIEW V_EWELL_INPATIENT_PIVAS AS
SELECT
      replace(yd.act_order_no,'_','') as order_no,
       replace(yd.parent_no,'_','') as group_no,
      pq.YDPQ as bar_code,
      To_char(yd.yyrq, 'yyyy-mm-dd')as plan_time,
      yd.CHARGE_CODE as order_code,
      yd.drugname as order_name,
      yd.INPATIENT_NO as patient_id,
      yd.case_id as admission_id,
      yd.WARD_CODE  as ward_id,
      yd.specifications as drug_spec,
      yd.FREQ_CODE as frequency,
      yd.dose as dosage,
      yd.dose_unit as  unit,
      yz.SUPPLY_CODE as supply_name,
      (select  DRUGWAYID from SRVS_DRUGWAY where DRUGWAYNAME  =  yz.SUPPLY_CODE ) as  supply_code,
  case
   when yz.YZZT = 0 then
    'Open'
    else
    'stop'
    end as order_status,
    case
    when yz.YZlx = 0 then
    '长嘱'
    else
    '临嘱'
    end as long_once_flag,
    yz.start_time as start_time,
    yd.doctor_name as doctor_name,
    yz.end_time as stop_time,
    bt.name_ as Batch_name,
    bt.start_time_ as Batch_start_name,
    bt.end_time_ as batch_end_time,
    yd.bedno as Bed_no,
    yd.dropspeed as Dripping_sped,
    yd.SERIAL_NUMBER as SERIAL_NUMBER
  FROM SRVS_PRESCRIPTION Yd
  left join SRVS_LABEL pq on YD.Pidrqzxbc = pq.pidrqzxbc
  left join SRVS_MEDICAMENTS_FREQUENCY pc on YD.freq_code  = pc.code_
  left join SRVS_DOCTOR_ADVICE_main yz on yz.parent_no = yd.parent_no
  left join srvs_batch bt on yd.zxbc = bt.id_

  where PQ.YDZT > 4 and yd.yyrq > sysdate -3;
