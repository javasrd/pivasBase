create sequence sq_sys_login_info
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;


create sequence sq_sys_user_login
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;


create sequence sq_sys_role
minvalue 10000
maxvalue 999999999999999999999999999
start with 10000
increment by 1
nocache;


create sequence sq_sys_user
minvalue 10000
maxvalue 999999999999999999999999999
start with 10000
increment by 1
nocache;


create sequence sq_sys_log_oper
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;


create sequence sq_sys_user_history
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;


create sequence sq_SRVS_MEDICAMENTS_category
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;


create sequence sq_SRVS_MEDICAMENTS_label
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

create sequence sq_SRVS_MEDICAMENTS
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

create sequence sq_srvs_batch
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

create sequence sq_SRVS_MEDICAMENTS_frequency
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

create sequence sq_SRVS_GENERAL_RULE
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

-- 病区
create sequence SQ_SRVS_INPATIENTAREA
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

-- 病人
create sequence SQ_SRVS_PATIENT
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

--用药途径表
create sequence SQ_SRVS_DRUGWAY
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

--配置费规则表
create sequence SQ_SRVS_CONFIGFEE_RULE
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

--配置费/材料费
create sequence SQ_SRVS_CONFIG_FEE
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

--配置费类别
create sequence SQ_SRVS_CONFIG_FEETYPE
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

--计量单位
create sequence SQ_SRVS_MEASUREMENT_UNIT
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

--核对类型
create sequence SQ_SRVS_CHECKTYPE
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

--审方错误类型表
create sequence SQ_SRVS_TRIAL_ERRORTYPE
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;


create sequence sq_SRVS_DOCTOR_ADVICE
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

create sequence sq_SRVS_PRESCRIPTION
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

create sequence sq_SRVS_PRESCRIPTION_main
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

create sequence sq_SRVS_PRESCRIPTION_FEES
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

create sequence sq_SRVS_LABEL_ref_config_fee
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

--员工信息
create sequence SQ_SRVS_EMPLOYEEINFO
minvalue 1
maxvalue 999999999999999999999999999
start with 4169
increment by 1
nocache;

--优先级规则 配置表 序列
create sequence SQ_SRVS_PRIORITY_RULES
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
nocache;

--优先级规则 配置表 序列
create sequence SQ_SRVS_VOLUME_RULES
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
nocache;

--强制规则 配置表 序列
create sequence SQ_SRVS_OTHER_RULE
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
nocache;

--APP登录表 配置表 序列
create sequence SQ_SYS_APP_USER_LOGIN
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
nocache;

--服务器节点表
create sequence SQ_SYS_SERVER_NODE
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
nocache;

-- Create sequence 
create sequence SQ_SRVS_ADVICE_CHKRESULT
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
nocache;

create sequence SQ_DEM_YDRecord
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

-- 病区分组
create sequence SQ_SRVS_GROUP
minvalue 0
nomaxvalue
start with 1
increment by 1
nocache;

-- Create sequence 
create sequence SQ_PIVAS_GID
minvalue 1
maxvalue 99999
start with 1
increment by 1
nocache
cycle;

--追踪记录
create sequence SQ_SRVS_TRACKINGRECORD
minvalue 0
nomaxvalue
start with 1
increment by 1
nocache;


--医嘱/批次条件配置
create sequence sq_SRVS_SHOWDRUGLIST_main
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
nocache;

--药品破损登记
create sequence SQ_SRVS_DRUG_DAMAGE
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

--拆药量
create sequence SQ_SRVS_DRUGOPEN_AMOUNT
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

--瓶签打印日志
create sequence sq_SRVS_PRINT_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

--打印条件设置
create sequence sq_SRVS_PRINTLABEL_CON
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

--打印序号
create sequence sq_pivas_printlabel_index
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
nocache;

create sequence SQ_SRVS_PRESCRIPTION_REFUND
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

--打印机配置
create sequence SQ_SRVS_PRINTER_CONF
minvalue 1
maxvalue 999999999999999999999999999
start with 2000
increment by 1
nocache;

--打印用pidsj临时
create sequence SQ_SRVS_PIDSJ_TEMP
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
nocache;


create sequence sq_company
increment by 1
start with 1
nomaxvalue
minvalue 0
nocache;


create sequence sq_dict
increment by 1
start with 1
nomaxvalue
minvalue 0
nocache;


create sequence sq_group
increment by 1
start with 1
nomaxvalue
minvalue 0
nocache;


create sequence sq_holiday
increment by 1
start with 1
nomaxvalue
minvalue 0
nocache;


create sequence sq_SCHE_USER
increment by 1
start with 1
nomaxvalue
minvalue 0
nocache;


create sequence sq_position
increment by 1
start with 1
nomaxvalue
minvalue 0
nocache;


create sequence sq_SCHE_RECORD
increment by 1
start with 1
nomaxvalue
nocache
minvalue 0;


create sequence sq_work
increment by 1
start with 1
nomaxvalue
minvalue 0
nocache;

create sequence sq_remark
increment by 1
start with 1
nomaxvalue
minvalue 0
nocache;

create sequence sq_sys_error_log
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
nocache;

create sequence sq_sign
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
nocache;