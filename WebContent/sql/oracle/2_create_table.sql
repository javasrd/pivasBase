/*==============================================================*/
/* 更改所有表VARCHAR2默认单位为 'CHAR'                            */
/*==============================================================*/
ALTER SESSION SET NLS_LENGTH_SEMANTICS='CHAR';

/*==============================================================*/
/* TABLE: "SYS_DICT"                                       */
/*==============================================================*/
CREATE TABLE "SYS_DICT" 
(
   "DICT_ID"            INTEGER              NOT NULL,
   "CATEGORY"           VARCHAR2(64)         NOT NULL,
   "DESCRIPTION"        VARCHAR2(256),
   "CODE"               VARCHAR2(32)         NOT NULL,
   "SORT"               INTEGER              NOT NULL,
   CONSTRAINT PK_SYS_DICT PRIMARY KEY ("DICT_ID", "CATEGORY")  USING INDEX tablespace pivas_index_tablespace
) tablespace pivas_tablespace;
	
COMMENT ON TABLE "SYS_DICT" IS
'字典表';

COMMENT ON COLUMN "SYS_DICT"."DICT_ID" IS
'字典表主键ID';

COMMENT ON COLUMN "SYS_DICT"."CATEGORY" IS
'代码种类，定义规则为：表名.字段名';

COMMENT ON COLUMN "SYS_DICT"."DESCRIPTION" IS
'描述';

COMMENT ON COLUMN "SYS_DICT"."CODE" IS
'代码';

COMMENT ON COLUMN "SYS_DICT"."SORT" IS
'显示排序';

/*==============================================================*/
/* TABLE: "SYS_DICT_INFO"                                  */
/*==============================================================*/
CREATE TABLE "SYS_DICT_INFO" 
(
   "DICT_ID"            INTEGER              NOT NULL,
   "LANGUAGE_ID"        INTEGER              NOT NULL,
   "CONTENT"            VARCHAR2(128)        NOT NULL,
   CONSTRAINT PK_SYS_DICT_INFO PRIMARY KEY ("DICT_ID", "LANGUAGE_ID")  USING INDEX tablespace pivas_index_tablespace 
) tablespace pivas_tablespace;

COMMENT ON TABLE "SYS_DICT_INFO" IS
'数据字典国际化表';

COMMENT ON COLUMN "SYS_DICT_INFO"."DICT_ID" IS
'字典表主键ID';

COMMENT ON COLUMN "SYS_DICT_INFO"."LANGUAGE_ID" IS
'主键';

COMMENT ON COLUMN "SYS_DICT_INFO"."CONTENT" IS
'具体某种语言翻译的内容';


/*==============================================================*/
/* TABLE: "SYS_I18N"                                             */
/*==============================================================*/
CREATE TABLE "SYS_I18N" 
(
   "LANGUAGE_ID"        INTEGER              NOT NULL,
   "NAME"               VARCHAR2(64),
   "OBJECT_ID"          INTEGER              NOT NULL,
   "TYPE"               INTEGER              NOT NULL,
   CONSTRAINT PK_SYS_I18N PRIMARY KEY ("LANGUAGE_ID", "OBJECT_ID", "TYPE")  USING INDEX tablespace pivas_index_tablespace 
) tablespace pivas_tablespace;

COMMENT ON TABLE "SYS_I18N" IS
'语言标识';

COMMENT ON COLUMN "SYS_I18N"."LANGUAGE_ID" IS
'主键';

COMMENT ON COLUMN "SYS_I18N"."NAME" IS
'国际化信息描述';

COMMENT ON COLUMN "SYS_I18N"."OBJECT_ID" IS
'国际化对象标识';

COMMENT ON COLUMN "SYS_I18N"."TYPE" IS
'1：权限  方便以后扩展其他类型国际化信息';

/*==============================================================*/
/* INDEX: "RELATIONSHIP_6_FK"                                   */
/*==============================================================*/
CREATE INDEX "RELATIONSHIP_6_FK" ON "SYS_I18N" (
   "OBJECT_ID" ASC
) tablespace pivas_index_tablespace ;

/*==============================================================*/
/* TABLE: "SYS_LANG"                                         */
/*==============================================================*/
CREATE TABLE "SYS_LANG" 
(
   "LANGUAGE_ID"        INTEGER              NOT NULL,
   "CODE"               VARCHAR2(32),
   "DESCRIPTION"        VARCHAR2(32),
   CONSTRAINT PK_SYS_LANG PRIMARY KEY ("LANGUAGE_ID")  USING INDEX tablespace pivas_index_tablespace 
) tablespace pivas_tablespace;

COMMENT ON TABLE "SYS_LANG" IS
'语言表';

COMMENT ON COLUMN "SYS_LANG"."LANGUAGE_ID" IS
'主键';

COMMENT ON COLUMN "SYS_LANG"."CODE" IS
'国际编码';

COMMENT ON COLUMN "SYS_LANG"."DESCRIPTION" IS
'描述';

/*==============================================================*/
/* TABLE: "SYS_LOG_OPER"                                    */
/*==============================================================*/
CREATE TABLE "SYS_LOG_OPER" 
(
   "LOG_ID"             INTEGER              NOT NULL,
   "USER_ACCOUNT"       INTEGER              NOT NULL,
   "MODULE"             VARCHAR2(32)         NOT NULL,
   "CONTENT"            VARCHAR2(4000)       NOT NULL,
   "STATUS"             INTEGER              NOT NULL,
   "CREATE_TIME"        DATE                 NOT NULL,
   "SUB_SYSTEM"         INTEGER              NOT NULL,
   CONSTRAINT PK_SYS_LOG_OPER PRIMARY KEY ("LOG_ID")  USING INDEX tablespace pivas_index_tablespace 
) tablespace pivas_tablespace;

COMMENT ON TABLE "SYS_LOG_OPER" IS
'操作日志表';

COMMENT ON COLUMN "SYS_LOG_OPER"."LOG_ID" IS
'主键';

COMMENT ON COLUMN "SYS_LOG_OPER"."USER_ACCOUNT" IS
'用户名称';

COMMENT ON COLUMN "SYS_LOG_OPER"."MODULE" IS
'操作模块';

COMMENT ON COLUMN "SYS_LOG_OPER"."CONTENT" IS
'操作内容';

COMMENT ON COLUMN "SYS_LOG_OPER"."STATUS" IS
'操作是否成功，0表示失败，1表示成功';

COMMENT ON COLUMN "SYS_LOG_OPER"."CREATE_TIME" IS
'创建时间';

COMMENT ON COLUMN "SYS_LOG_OPER"."SUB_SYSTEM" IS
'0：系统管理   1：pivas管理  2：配置管理 ';

/*==============================================================*/
/* TABLE: "SYS_LOGIN_INFO"                                       */
/*==============================================================*/
CREATE TABLE "SYS_LOGIN_INFO" 
(
   "LOGIN_INFO_ID"      INTEGER              NOT NULL,
   "USER_ID"            INTEGER              NOT NULL,
   "ACCOUNT"            VARCHAR2(32)         NOT NULL,
   "LST_ERR_LOGIN_TIME" DATE,
   "FST_SUC_LOGIN_TIME" DATE,
   "ERROR_COUNT"        INTEGER              NOT NULL,
   "LOCKED"             INTEGER              NOT NULL,
   "LOCK_TIME"          DATE,
   "LOCK_LOST_EFF_TIME" DATE,
   CONSTRAINT PK_SYS_LOGIN_INFO PRIMARY KEY ("LOGIN_INFO_ID")  USING INDEX tablespace pivas_index_tablespace 
) tablespace pivas_tablespace;

COMMENT ON TABLE "SYS_LOGIN_INFO" IS
'登录信息表';

COMMENT ON COLUMN "SYS_LOGIN_INFO"."LOGIN_INFO_ID" IS
'主键';

COMMENT ON COLUMN "SYS_LOGIN_INFO"."USER_ID" IS
'用户ID';

COMMENT ON COLUMN "SYS_LOGIN_INFO"."ACCOUNT" IS
'用户帐号';

COMMENT ON COLUMN "SYS_LOGIN_INFO"."LST_ERR_LOGIN_TIME" IS
'第一次登录失败的时间，如果后面连续登录失败不做更新';

COMMENT ON COLUMN "SYS_LOGIN_INFO"."FST_SUC_LOGIN_TIME" IS
'第一次成功登录时间';

COMMENT ON COLUMN "SYS_LOGIN_INFO"."ERROR_COUNT" IS
'累计登录失败次数，只针对用户登录密码校验错误，登录成功后清空';

COMMENT ON COLUMN "SYS_LOGIN_INFO"."LOCKED" IS
'1：锁定  0：正常';

COMMENT ON COLUMN "SYS_LOGIN_INFO"."LOCK_TIME" IS
'锁定时间';

COMMENT ON COLUMN "SYS_LOGIN_INFO"."LOCK_LOST_EFF_TIME" IS
'锁定失效时间';

/*==============================================================*/
/* TABLE: "SYS_PRIVILEGE"                                        */
/*==============================================================*/
CREATE TABLE "SYS_PRIVILEGE" 
(
   "PRIVILEGE_ID"       INTEGER              NOT NULL,
   "PARENT_ID"          INTEGER,
   "NAME"               VARCHAR2(64),
   "TYPE"               INTEGER,
   "URL"                VARCHAR2(256),
   "MODULE"             VARCHAR2(32),
   "SEQ"                INTEGER,
   "CREATE_TIME"        DATE,
   "IS_HTTPS"           INTEGER,
   "DESCRIPTION"        VARCHAR2(256),
   "PRIVILEGE_CODE"     VARCHAR2(32),
   "SYS_CODE"           INTEGER,
   "LEVEL"              INTEGER,
   "ICON"               VARCHAR2(32),
   CONSTRAINT PK_SYS_PRIVILEGE PRIMARY KEY ("PRIVILEGE_ID")  USING INDEX tablespace pivas_index_tablespace 
) tablespace pivas_tablespace;

COMMENT ON TABLE "SYS_PRIVILEGE" IS
'权限表';

COMMENT ON COLUMN "SYS_PRIVILEGE"."PRIVILEGE_ID" IS
'权限ID，主键，自动生成';

COMMENT ON COLUMN "SYS_PRIVILEGE"."PARENT_ID" IS
'父节点，自关联';

COMMENT ON COLUMN "SYS_PRIVILEGE"."NAME" IS
'权限名';

COMMENT ON COLUMN "SYS_PRIVILEGE"."TYPE" IS
'类型，0:菜单目录 1:菜单 2:功能';

COMMENT ON COLUMN "SYS_PRIVILEGE"."URL" IS
'路径';

COMMENT ON COLUMN "SYS_PRIVILEGE"."MODULE" IS
'模块名';

COMMENT ON COLUMN "SYS_PRIVILEGE"."SEQ" IS
'排序的序号';

COMMENT ON COLUMN "SYS_PRIVILEGE"."CREATE_TIME" IS
'时间戳';

COMMENT ON COLUMN "SYS_PRIVILEGE"."IS_HTTPS" IS
'是否HTTPS访问，0为否，1为是';

COMMENT ON COLUMN "SYS_PRIVILEGE"."DESCRIPTION" IS
'备注';

COMMENT ON COLUMN "SYS_PRIVILEGE"."PRIVILEGE_CODE" IS
'权限码';

COMMENT ON COLUMN "SYS_PRIVILEGE"."SYS_CODE" IS
'1：系统管理,2：配置管理,3：PIVAS管理,4：扫描核对';

COMMENT ON COLUMN "SYS_PRIVILEGE"."LEVEL" IS
'菜单深度';

COMMENT ON COLUMN "SYS_PRIVILEGE"."ICON" IS
'菜单图标文件名';

/*==============================================================*/
/* TABLE: "SYS_ROLE"                                             */
/*==============================================================*/
CREATE TABLE "SYS_ROLE" 
(
   "ROLE_ID"            INTEGER              NOT NULL,
   "NAME"               VARCHAR2(32)         NOT NULL,
   "DESCRIPTION"        VARCHAR2(256),
   "CREATOR"            INTEGER,
   "CREATE_TIME"        DATE                 NOT NULL,
   CONSTRAINT PK_SYS_ROLE PRIMARY KEY ("ROLE_ID")  USING INDEX tablespace pivas_index_tablespace 
) tablespace pivas_tablespace;

COMMENT ON TABLE "SYS_ROLE" IS
'角色表';

COMMENT ON COLUMN "SYS_ROLE"."ROLE_ID" IS
'角色ID，主键，自动生成';

COMMENT ON COLUMN "SYS_ROLE"."NAME" IS
'角色名';

COMMENT ON COLUMN "SYS_ROLE"."DESCRIPTION" IS
'备注';

COMMENT ON COLUMN "SYS_ROLE"."CREATE_TIME" IS
'创建时间';

/*==============================================================*/
/* TABLE: "SYS_ROLE_REF_PRIVILEGE"                               */
/*==============================================================*/
CREATE TABLE "SYS_ROLE_REF_PRIVILEGE" 
(
   "PRIVILEGE_ID"       INTEGER              NOT NULL,
   "ROLE_ID"            INTEGER              NOT NULL,
   CONSTRAINT PK_SYS_ROLE_REF_PRIVILEGE PRIMARY KEY ("ROLE_ID", "PRIVILEGE_ID")  USING INDEX tablespace pivas_index_tablespace 
) tablespace pivas_tablespace;

COMMENT ON TABLE "SYS_ROLE_REF_PRIVILEGE" IS
'角色权限关系表';

COMMENT ON COLUMN "SYS_ROLE_REF_PRIVILEGE"."PRIVILEGE_ID" IS
'权限ID';

COMMENT ON COLUMN "SYS_ROLE_REF_PRIVILEGE"."ROLE_ID" IS
'角色ID';

/*==============================================================*/
/* TABLE: "SYS_CONFIG"                                    */
/*==============================================================*/
CREATE TABLE "SYS_CONFIG" 
(
   "SYS_KEY"            VARCHAR2(128)        NOT NULL,
   "SYS_VALUE"          VARCHAR2(256),
   "TYPE"               INTEGER              NOT NULL,
   "DESCRIPTION"        VARCHAR2(256),
   CONSTRAINT PK_SYS_CONFIG PRIMARY KEY ("SYS_KEY")  USING INDEX tablespace pivas_index_tablespace 
) tablespace pivas_tablespace;

COMMENT ON TABLE "SYS_CONFIG" IS
'系统配置项';

COMMENT ON COLUMN "SYS_CONFIG"."SYS_KEY" IS
'配置项键值,主键';

COMMENT ON COLUMN "SYS_CONFIG"."SYS_VALUE" IS
'配置项键对应的值';

COMMENT ON COLUMN "SYS_CONFIG"."TYPE" IS
'参数类型';

COMMENT ON COLUMN "SYS_CONFIG"."DESCRIPTION" IS
'备注';

/*==============================================================*/
/* TABLE: "SYS_USER"                                             */
/*==============================================================*/
CREATE TABLE "SYS_USER" 
(
   "USER_ID"            INTEGER              NOT NULL,
   "ACCOUNT"            VARCHAR2(32)         NOT NULL,
   "PASSWORD"           VARCHAR2(256)        NOT NULL,
   "NAME"               VARCHAR2(32)         NOT NULL,
   "GENDER"             INTEGER              DEFAULT 1  NOT NULL,
   "TELEPHONE"          VARCHAR2(32),
   "EMAIL"              VARCHAR2(128),
   "RESETPWD"           INTEGER              DEFAULT 0,
   "DESCRIPTION"        VARCHAR2(256),
   "CREATOR"            INTEGER,
   "CREATE_TIME"        DATE                 NOT NULL,
   "ACCOUNT_TYPE"       INTEGER              DEFAULT 1,
   "DEL_FLAG"           INTEGER              DEFAULT 0 NOT NULL,
   "IS_ACTIVE"          INTEGER              DEFAULT 1,
   "USER_TYPE"          INTEGER              DEFAULT 0,
   CONSTRAINT PK_SYS_USER PRIMARY KEY ("USER_ID")  USING INDEX tablespace pivas_index_tablespace 
) tablespace pivas_tablespace;

COMMENT ON TABLE "SYS_USER" IS
'用户信息表';

COMMENT ON COLUMN "SYS_USER"."USER_ID" IS
'用户ID，主键，自动生成';

COMMENT ON COLUMN "SYS_USER"."ACCOUNT" IS
'用户名';

COMMENT ON COLUMN "SYS_USER"."PASSWORD" IS
'密码';

COMMENT ON COLUMN "SYS_USER"."NAME" IS
'用户名称';

COMMENT ON COLUMN "SYS_USER"."GENDER" IS
'性别';

COMMENT ON COLUMN "SYS_USER"."TELEPHONE" IS
'电话';

COMMENT ON COLUMN "SYS_USER"."EMAIL" IS
'电子邮件';

COMMENT ON COLUMN "SYS_USER"."RESETPWD" IS
'强制重置密码';

COMMENT ON COLUMN "SYS_USER"."DESCRIPTION" IS
'备注';

COMMENT ON COLUMN "SYS_USER"."CREATOR" IS
'创建者标识';

COMMENT ON COLUMN "SYS_USER"."CREATE_TIME" IS
'创建时间';

COMMENT ON COLUMN "SYS_USER"."ACCOUNT_TYPE" IS
'账号类型1：普通';

COMMENT ON COLUMN "SYS_USER"."DEL_FLAG" IS
'删除标志(0为未删除，1为已删除，默认为0)';

COMMENT ON COLUMN "SYS_USER"."USER_TYPE" IS
'用户类型(0未设置，1药师，6护士)';


/*==============================================================*/
/* TABLE: "SYS_USER_REF_SICKROOM"                                */
/*==============================================================*/
CREATE TABLE "SYS_USER_REF_SICKROOM"
(
   "USER_ID"           INTEGER              NOT NULL,
   "SICKROOM_ID"       INTEGER              NOT NULL,
   CONSTRAINT PK_SYS_USER_REF_SICKROOM PRIMARY KEY ("USER_ID", "SICKROOM_ID")  USING INDEX tablespace pivas_index_tablespace
) tablespace pivas_tablespace;

COMMENT ON TABLE "SYS_USER_REF_SICKROOM" IS
'用户病室关联表';

COMMENT ON COLUMN "SYS_USER_REF_SICKROOM"."USER_ID" IS
'用户ID';

COMMENT ON COLUMN "SYS_USER_REF_SICKROOM"."SICKROOM_ID" IS
'病室ID';

/*==============================================================*/
/* TABLE: "SYS_USER_GROUP"                                       */
/*==============================================================*/
CREATE TABLE "SYS_USER_GROUP" 
(
   "GROUP_ID"           INTEGER              NOT NULL,
   "GROUP_NAME"         VARCHAR2(32)         NOT NULL,
   "CREATE_TIME"        DATE,
   "CREATE_ID"          INTEGER,
   "DESCRIPTION"        VARCHAR2(256),
   CONSTRAINT PK_SYS_USER_GROUP PRIMARY KEY ("GROUP_ID")  USING INDEX tablespace pivas_index_tablespace
) tablespace pivas_tablespace;

COMMENT ON TABLE "SYS_USER_GROUP" IS
'用户组表';

COMMENT ON COLUMN "SYS_USER_GROUP"."GROUP_ID" IS
'主键标识，自增长';

COMMENT ON COLUMN "SYS_USER_GROUP"."GROUP_NAME" IS
'用户名称';

COMMENT ON COLUMN "SYS_USER_GROUP"."CREATE_TIME" IS
'创建时间';

COMMENT ON COLUMN "SYS_USER_GROUP"."CREATE_ID" IS
'创建者';

COMMENT ON COLUMN "SYS_USER_GROUP"."DESCRIPTION" IS
'备注';

/*==============================================================*/
/* INDEX: "FK1_USER_KEY_GROUP_FK"                               */
/*==============================================================*/
CREATE INDEX "FK1_USER_KEY_GROUP_FK" ON "SYS_USER_GROUP" (
   "CREATE_ID" ASC
) tablespace pivas_index_tablespace;


/*==============================================================*/
/* TABLE: "SYS_USER_HISTORY"                                     */
/*==============================================================*/
CREATE TABLE "SYS_USER_HISTORY" 
(
   "ID"                 INTEGER              NOT NULL,
   "USER_ID"            INTEGER,
   "PASSWORD"           VARCHAR2(256),
   "MODIFY_TIME"        DATE,
   CONSTRAINT PK_SYS_USER_HISTORY PRIMARY KEY ("ID")  USING INDEX tablespace pivas_index_tablespace 
) tablespace pivas_tablespace;

COMMENT ON TABLE "SYS_USER_HISTORY" IS
'用户历史密码数据表';

COMMENT ON COLUMN "SYS_USER_HISTORY"."ID" IS
'主键';

COMMENT ON COLUMN "SYS_USER_HISTORY"."USER_ID" IS
'用户标识';

COMMENT ON COLUMN "SYS_USER_HISTORY"."PASSWORD" IS
'密码';

COMMENT ON COLUMN "SYS_USER_HISTORY"."MODIFY_TIME" IS
'密码修改时间';

/*==============================================================*/
/* INDEX: "FK2_USER_KEY_HISTORY_FK"                             */
/*==============================================================*/
CREATE INDEX "FK2_USER_KEY_HISTORY_FK" ON "SYS_USER_HISTORY" (
   "USER_ID" ASC
) tablespace pivas_index_tablespace;

/*==============================================================*/
/* TABLE: "SYS_USER_LOGIN"                                       */
/*==============================================================*/
CREATE TABLE "SYS_USER_LOGIN" 
(
   "ID"                 INTEGER              NOT NULL,
   "USER_ID"            INTEGER,
   "LOGIN_DATE"         DATE,
   "IP_ADDR"            VARCHAR2(32),
   CONSTRAINT PK_SYS_USER_LOGIN PRIMARY KEY ("ID")  USING INDEX tablespace pivas_index_tablespace 
) tablespace pivas_tablespace;

COMMENT ON TABLE "SYS_USER_LOGIN" IS
'用户登录历史表';

COMMENT ON COLUMN "SYS_USER_LOGIN"."ID" IS
'主键';

COMMENT ON COLUMN "SYS_USER_LOGIN"."USER_ID" IS
'用户标识';

COMMENT ON COLUMN "SYS_USER_LOGIN"."LOGIN_DATE" IS
'登录时间';

COMMENT ON COLUMN "SYS_USER_LOGIN"."IP_ADDR" IS
'用户登录IP';

/*==============================================================*/
/* INDEX: "FK_USER_KEY_LOGIN_FK"                                */
/*==============================================================*/
CREATE INDEX "FK_USER_KEY_LOGIN_FK" ON "SYS_USER_LOGIN" (
   "USER_ID" ASC
) tablespace pivas_index_tablespace;

/*==============================================================*/
/* TABLE: "SYS_USER_REF_ROLE"                                    */
/*==============================================================*/
CREATE TABLE "SYS_USER_REF_ROLE" 
(
   "ROLE_ID"            INTEGER              NOT NULL,
   "USER_ID"            INTEGER              NOT NULL,
   CONSTRAINT PK_SYS_USER_REF_ROLE PRIMARY KEY ("ROLE_ID", "USER_ID")  USING INDEX tablespace pivas_index_tablespace 
) tablespace pivas_tablespace;

COMMENT ON TABLE "SYS_USER_REF_ROLE" IS
'用户角色关系表';

COMMENT ON COLUMN "SYS_USER_REF_ROLE"."ROLE_ID" IS
'角色ID';

COMMENT ON COLUMN "SYS_USER_REF_ROLE"."USER_ID" IS
'用户ID';


/*==============================================================*/
/* TABLE: "SYS_USER_REF_GROUP"                                   */
/*==============================================================*/
CREATE TABLE "SYS_USER_REF_GROUP" 
(
   "GROUP_ID"           INTEGER              NOT NULL,
   "USER_ID"            INTEGER              NOT NULL,
   CONSTRAINT PK_SYS_USER_REF_GROUP PRIMARY KEY ("GROUP_ID", "USER_ID") USING INDEX tablespace pivas_index_tablespace 
) tablespace pivas_tablespace;

COMMENT ON TABLE "SYS_USER_REF_GROUP" IS
'用户组和用户关系表';

COMMENT ON COLUMN "SYS_USER_REF_GROUP"."GROUP_ID" IS
'用户组标识，联合主键';

COMMENT ON COLUMN "SYS_USER_REF_GROUP"."USER_ID" IS
'用户标识，联合主键';

/*==============================================================*/
/* TABLE: "SRVS_MEDICAMENTS_CATEGORY"                                   */
/*==============================================================*/
CREATE TABLE "SRVS_MEDICAMENTS_CATEGORY"
(
   "CATEGORY_ID"              INTEGER              NOT NULL,
   "CATEGORY_NAME"            VARCHAR2(32)         NOT NULL,
   "CATEGORY_CODE"            VARCHAR2(32)         ,
   "CATEGORY_PRIORITY"        INTEGER              NOT NULL,
   CONSTRAINT PK_SRVS_MEDICAMENTS_CATEGORY PRIMARY KEY ("CATEGORY_ID") USING INDEX tablespace pivas_index_tablespace
) tablespace pivas_tablespace;

alter table SRVS_MEDICAMENTS_CATEGORY add CATEGORY_ISHARD INTEGER  default 1 NOT NULL;

COMMENT ON TABLE "SRVS_MEDICAMENTS_CATEGORY" IS
'药品分类表';

COMMENT ON COLUMN "SRVS_MEDICAMENTS_CATEGORY"."CATEGORY_ID" IS
'主键标识，自增长';

COMMENT ON COLUMN "SRVS_MEDICAMENTS_CATEGORY"."CATEGORY_NAME" IS
'分类名称';

COMMENT ON COLUMN "SRVS_MEDICAMENTS_CATEGORY"."CATEGORY_CODE" IS
'分类瓶签编码';

COMMENT ON COLUMN "SRVS_MEDICAMENTS_CATEGORY"."CATEGORY_PRIORITY" IS
'分类优先级';

COMMENT ON COLUMN "SRVS_MEDICAMENTS_CATEGORY"."CATEGORY_ISHARD" IS
'是否难配药,0是 1否';

/*==============================================================*/
/* TABLE: "SRVS_MEDICAMENTS_LABEL"                                   */
/*==============================================================*/
create table SRVS_MEDICAMENTS_LABEL
(
  label_id   INTEGER not null,
  label_name VARCHAR2(32 CHAR) not null,
  is_active  INTEGER default 1 not null,
  label_no   VARCHAR2(32 CHAR),
  is_null    INTEGER default 0 not null
)
tablespace pivas_tablespace;
-- Add comments to the table 
comment on table SRVS_MEDICAMENTS_LABEL
  is '药品标签表';
-- Add comments to the columns 
comment on column SRVS_MEDICAMENTS_LABEL.label_id
  is '主键标识，自增长';
comment on column SRVS_MEDICAMENTS_LABEL.label_name
  is '标签名称';
comment on column SRVS_MEDICAMENTS_LABEL.is_active
  is '是否可用 0不可用,1可用';
comment on column SRVS_MEDICAMENTS_LABEL.label_no
  is '支持中文';
 comment on column SRVS_MEDICAMENTS_LABEL.is_null
  is '是否空包 0不是空包 1是空包';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_MEDICAMENTS_LABEL
  add constraint PK_SRVS_MEDICAMENTS_LABEL primary key (LABEL_ID) USING INDEX tablespace pivas_index_tablespace ;


/*==============================================================*/
/* TABLE: "SRVS_MEDICAMENTS"                                   */
/*==============================================================*/
create table SRVS_MEDICAMENTS
(
  medicaments_id             INTEGER not null,
  medicaments_name           VARCHAR2(256 CHAR) not null,
  medicaments_code           VARCHAR2(32 CHAR) not null,
  category_id                VARCHAR2(32 CHAR) default 2001,
  medicaments_suchama        VARCHAR2(32 CHAR),
  medicaments_specifications VARCHAR2(32 CHAR),
  medicaments_volume         VARCHAR2(32 CHAR),
  medicaments_volume_unit    VARCHAR2(32 CHAR),
  medicaments_dosage         VARCHAR2(32 CHAR),
  medicaments_dosage_unit    VARCHAR2(32 CHAR),
  medicaments_packing_unit   VARCHAR2(400 CHAR),
  medicaments_test_flag      INTEGER default 1,
  medicaments_place          VARCHAR2(32 CHAR),
  medicaments_price          VARCHAR2(32 CHAR) default 0,
  medicaments_menstruum      INTEGER  default 0,
  medicaments_isactive       INTEGER default 1,
  medicaments_ismaindrug     INTEGER default 0,
  medicaments_danger         INTEGER default 1,
  medicaments_place_code     VARCHAR2(32 CHAR),
  medicaments_shelf_no       VARCHAR2(32 CHAR),
  medicaments_stock          VARCHAR2(32 CHAR) default 0,
  medicaments_operator       VARCHAR2(32 CHAR),
  medicaments_consumption    VARCHAR2(32 CHAR) default 0,
  medicaments_over           INTEGER default 0,
  medicaments_limit          VARCHAR2(32 CHAR) default 10,
  medicaments_alias_name     VARCHAR2(256 CHAR),
  medicaments_unitchange_before   VARCHAR2(64 CHAR),
  medicaments_unitchange_after    VARCHAR2(64 CHAR)
)tablespace pivas_tablespace;
-- Add comments to the table 
comment on table SRVS_MEDICAMENTS
  is '药品表';
-- Add comments to the columns 
comment on column SRVS_MEDICAMENTS.medicaments_id
  is '主键标识，自增长';
comment on column SRVS_MEDICAMENTS.medicaments_name
  is '药品名称';
comment on column SRVS_MEDICAMENTS.medicaments_code
  is '药品编码';
comment on column SRVS_MEDICAMENTS.category_id
  is '药品分类ID';
comment on column SRVS_MEDICAMENTS.medicaments_suchama
  is '药品速查码';
comment on column SRVS_MEDICAMENTS.medicaments_specifications
  is '药品规格';
comment on column SRVS_MEDICAMENTS.medicaments_volume
  is '药品体积';
comment on column SRVS_MEDICAMENTS.medicaments_volume_unit
  is '药品体积单位';
comment on column SRVS_MEDICAMENTS.medicaments_dosage
  is '药品使用次剂量';
comment on column SRVS_MEDICAMENTS.medicaments_dosage_unit
  is '药品使用次剂量单位';
comment on column SRVS_MEDICAMENTS.medicaments_packing_unit
  is '包装单位';
comment on column SRVS_MEDICAMENTS.medicaments_test_flag
  is '皮试标志 0不需要,1需要';
comment on column SRVS_MEDICAMENTS.medicaments_place
  is '药品产地';
comment on column SRVS_MEDICAMENTS.medicaments_price
  is '单价';
comment on column SRVS_MEDICAMENTS.medicaments_menstruum
  is '是否溶媒 0不是溶媒,1是溶媒';
comment on column SRVS_MEDICAMENTS.medicaments_isactive
  is '是否可用 0不可用,1可用';
comment on column SRVS_MEDICAMENTS.medicaments_ismaindrug
  is '是否做主药 1不是主药,0主药';
comment on column SRVS_MEDICAMENTS.medicaments_danger
  is '高危药标志 1不是 0是';
comment on column SRVS_MEDICAMENTS.medicaments_place_code
  is '药品产地编码';
comment on column SRVS_MEDICAMENTS.medicaments_shelf_no
  is '药品货架号';
comment on column SRVS_MEDICAMENTS.medicaments_stock
  is '药品库存';
comment on column SRVS_MEDICAMENTS.medicaments_operator
  is '修改人';
comment on column SRVS_MEDICAMENTS.medicaments_consumption
  is '：实际使用';
comment on column SRVS_MEDICAMENTS.medicaments_over
  is '是否需要告警0：否1：是';
comment on column SRVS_MEDICAMENTS.medicaments_limit
  is '药品库存下限';
comment on column SRVS_MEDICAMENTS.medicaments_alias_name
  is '药品别名';
comment on column SRVS_MEDICAMENTS.medicaments_unitchange_before
  is '单位转换前的值';
comment on column SRVS_MEDICAMENTS.medicaments_unitchange_after
  is '单位转换后的值';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_MEDICAMENTS
  add constraint PK_SRVS_MEDICAMENTS primary key (MEDICAMENTS_ID) USING INDEX tablespace pivas_index_tablespace;

alter table SRVS_MEDICAMENTS add medicaments_used VARCHAR2(32 CHAR) default 0;
comment on column SRVS_MEDICAMENTS.medicaments_used
  is '消耗量';

-- Add/modify columns 
alter table SRVS_MEDICAMENTS add difficulty_degree VARCHAR2(32 CHAR) default 0;
-- Add comments to the columns 
comment on column SRVS_MEDICAMENTS.difficulty_degree
  is '药品配置难度系数';
  
-- Add/modify columns 
alter table SRVS_MEDICAMENTS add effective_date VARCHAR2(32 CHAR);
-- Add comments to the columns 
comment on column SRVS_MEDICAMENTS.effective_date
  is '药品有效期';
  
-- Add/modify columns 
alter table SRVS_MEDICAMENTS add phyfunctiy VARCHAR2(64 CHAR);
-- Add comments to the columns 
comment on column SRVS_MEDICAMENTS.phyfunctiy
  is '药理作用';
  
create index MEDICAMENTS_CODE on SRVS_MEDICAMENTS (medicaments_code)  tablespace pivas_index_tablespace;
create index MEDICAMENTS_CATEGORY_ID on SRVS_MEDICAMENTS (category_id)  tablespace pivas_index_tablespace;
  
/*==============================================================*/
/* TABLE: "SRVS_MEDICAMENTS_REF_LABEL"                         */
/*==============================================================*/
CREATE TABLE "SRVS_MEDICAMENTS_REF_LABEL"
(
   "MEDICAMENTS_ID"              INTEGER              NOT NULL,
   "LABEL_ID"                    INTEGER              NOT NULL,
   CONSTRAINT SRVS_MEDICAMENTS_REF_LABEL PRIMARY KEY ("MEDICAMENTS_ID","LABEL_ID") USING INDEX tablespace pivas_index_tablespace
) tablespace pivas_tablespace;

COMMENT ON TABLE "SRVS_MEDICAMENTS_REF_LABEL" IS
'药品与药品标签关联关系表';

COMMENT ON COLUMN "SRVS_MEDICAMENTS_REF_LABEL"."MEDICAMENTS_ID" IS
'药品ID';

COMMENT ON COLUMN "SRVS_MEDICAMENTS_REF_LABEL"."LABEL_ID" IS
'标签ID';

/*==============================================================*/
/* TABLE: "srvs_batch"      批次表			                    */
/*==============================================================*/
CREATE TABLE srvs_batch
(
	ID_ 				NUMBER(20, 0) NOT NULL, 
	NUM_ 				VARCHAR2(32 CHAR) NOT NULL, 
	NAME_ 				VARCHAR2(32 CHAR), 
	IS_EMPTY_BATCH_ 	NUMBER(2, 0) DEFAULT 0, 
	START_TIME_ 		VARCHAR2(32 CHAR), 
	END_TIME_ 			VARCHAR2(32 CHAR), 
	IS_SECEND_ADVICE_ 	NUMBER(2, 0) DEFAULT 0, 
	COLOR_ 				VARCHAR2(20), 
	ENABLED_ 			NUMBER(2, 0) DEFAULT 0, 
	RESERVE1_			VARCHAR2(20 CHAR), 
	RESERVE2_ 			VARCHAR2(20 CHAR), 
	RESERVE3_ 			VARCHAR2(20 CHAR), 
	IS0P INTEGER DEFAULT 0,
	timetype VARCHAR2(20 CHAR) DEFAULT 'other',
  	ISFORCE           INTEGER default 1,
  	ISVOLUME          INTEGER default 1,
	CONSTRAINT srvs_batch_PK PRIMARY KEY(ID_) USING INDEX tablespace pivas_index_tablespace
) tablespace pivas_tablespace;

COMMENT ON TABLE srvs_batch IS '批次表';

COMMENT ON COLUMN srvs_batch.ID_ IS '主键';

COMMENT ON COLUMN srvs_batch.NUM_ IS '批次号';

COMMENT ON COLUMN srvs_batch.NAME_ IS '批次名称';

COMMENT ON COLUMN srvs_batch.IS_EMPTY_BATCH_ IS '是否为空批，0:不是,1:是';

COMMENT ON COLUMN srvs_batch.START_TIME_ IS '给药开始时间';

COMMENT ON COLUMN srvs_batch.END_TIME_ IS '给药结束时间';

COMMENT ON COLUMN srvs_batch.IS_SECEND_ADVICE_ IS '是否记第二医嘱，0:否,1:是';

COMMENT ON COLUMN srvs_batch.COLOR_ IS '颜色值';

COMMENT ON COLUMN srvs_batch.ENABLED_ IS '是否启用，0:不启用,1启用';

COMMENT ON COLUMN srvs_batch.RESERVE1_ IS '自定义批次';

COMMENT ON COLUMN srvs_batch.RESERVE2_ IS '预留字段2';

COMMENT ON COLUMN srvs_batch.RESERVE3_ IS '预留字段3';

COMMENT ON COLUMN srvs_batch.IS0P IS '是否临批，0:非临批,1是临批';

comment on column srvs_batch.timetype is '上午morning,下午afternoon,其他other';

comment on column srvs_batch.ISFORCE is '是否开启强制';

comment on column srvs_batch.ISVOLUME is '是否开启容积';
alter table srvs_batch add order_num INTEGER;

comment on column srvs_batch.order_num
  is '排序号';
  
  
/*==============================================================*/
/* TABLE: "SRVS_MEDICAMENTS_FREQUENCY"      用药频次表             */
/*==============================================================*/
CREATE TABLE SRVS_MEDICAMENTS_FREQUENCY
(
	ID_ 			NUMBER(20, 0) NOT NULL, 
	CODE_ 			VARCHAR2(32 CHAR) NOT NULL, 
	NAME_ 			VARCHAR2(32 CHAR), 
	INTERVAL_ 		NUMBER(5, 0), 
	TIME_OF_DAY_ 	NUMBER(5, 0), 
	START_TIME_ 	VARCHAR2(32 CHAR), 
	END_TIME_ 		VARCHAR2(32 CHAR), 
	RESERVE1_ 		VARCHAR2(20 CHAR), 
	RESERVE2_ 		VARCHAR2(20 CHAR), 
	RESERVE3_ 		VARCHAR2(20 CHAR), 
	CONSTRAINT SRVS_MEDICAMENTS_FREQUEN_PK PRIMARY KEY(ID_) USING INDEX tablespace pivas_index_tablespace
) tablespace pivas_tablespace;

COMMENT ON TABLE SRVS_MEDICAMENTS_FREQUENCY IS '用药频次表';

COMMENT ON COLUMN SRVS_MEDICAMENTS_FREQUENCY.ID_ IS '主键';

COMMENT ON COLUMN SRVS_MEDICAMENTS_FREQUENCY.CODE_ IS '用药频次编码';

COMMENT ON COLUMN SRVS_MEDICAMENTS_FREQUENCY.NAME_ IS '用药频次名称';

COMMENT ON COLUMN SRVS_MEDICAMENTS_FREQUENCY.INTERVAL_ IS '用药间隔时间(小时)';

COMMENT ON COLUMN SRVS_MEDICAMENTS_FREQUENCY.TIME_OF_DAY_ IS '每天次数';

COMMENT ON COLUMN SRVS_MEDICAMENTS_FREQUENCY.START_TIME_ IS '开始时间';

COMMENT ON COLUMN SRVS_MEDICAMENTS_FREQUENCY.END_TIME_ IS '结束时间';

COMMENT ON COLUMN SRVS_MEDICAMENTS_FREQUENCY.RESERVE1_ IS '预留字段1';

COMMENT ON COLUMN SRVS_MEDICAMENTS_FREQUENCY.RESERVE2_ IS '预留字段2';

COMMENT ON COLUMN SRVS_MEDICAMENTS_FREQUENCY.RESERVE3_ IS '预留字段3';

/*==============================================================*/
/* TABLE: 一般规则表								             */
/*==============================================================*/
CREATE TABLE SRVS_GENERAL_RULE
(
	ID_						NUMBER(20, 0) NOT NULL,
	MEDICAL_FREQUENCY_ID_ 	NUMBER(20, 0) NOT NULL,
	MEDICAL_KEYWORD_ 		VARCHAR2(32 CHAR),
	CONSTRAINT SRVS_GENERAL_RULE_PK PRIMARY KEY(ID_) USING INDEX tablespace pivas_index_tablespace
) tablespace pivas_tablespace;

COMMENT ON TABLE SRVS_GENERAL_RULE IS '一般规则表';

COMMENT ON COLUMN SRVS_GENERAL_RULE.ID_ IS '主键';

COMMENT ON COLUMN SRVS_GENERAL_RULE.MEDICAL_FREQUENCY_ID_ IS '用药频次ID';

COMMENT ON COLUMN SRVS_GENERAL_RULE.MEDICAL_KEYWORD_ IS '药品关键字(用“|”分割)';

/*==============================================================*/
/* TABLE: 一般规则与批次关联表					             */
/*==============================================================*/
CREATE TABLE SRVS_RULE_REF_BATCH
(
	RULE_ID_				NUMBER(20, 0) NOT NULL,
	SERIAL_NUMBER_ 			NUMBER(10, 0),
	BATCH_ID_				NUMBER(20, 0) NOT NULL,
	IS_FIXED_BATCH_			NUMBER(2, 0)
) tablespace pivas_tablespace;

COMMENT ON TABLE SRVS_RULE_REF_BATCH IS '一般规则与批次关联表';

COMMENT ON COLUMN SRVS_RULE_REF_BATCH.RULE_ID_ IS '规则ID';

COMMENT ON COLUMN SRVS_RULE_REF_BATCH.SERIAL_NUMBER_ IS '频次序号';

COMMENT ON COLUMN SRVS_RULE_REF_BATCH.BATCH_ID_ IS '批次ID';

COMMENT ON COLUMN SRVS_RULE_REF_BATCH.IS_FIXED_BATCH_ IS '是否固定批次0否，1是';


/*==============================================================*/
/* TABLE: SRVS_DOCTOR_ADVICE医嘱表					                    */
/*==============================================================*/
-- Create table
create table SRVS_DOCTOR_ADVICE
(
  yz_id                    NUMBER(20) not null,
  act_order_no             VARCHAR2(32 CHAR) not null,
  parent_no                VARCHAR2(32 CHAR) not null,
  ward_code                VARCHAR2(32 CHAR),
  wardname                 VARCHAR2(32 CHAR),
  inpatient_no             VARCHAR2(32 CHAR),
  doctor                   VARCHAR2(32 CHAR),
  doctor_name              VARCHAR2(32 CHAR),
  drawer                   VARCHAR2(32 CHAR),
  drawername               VARCHAR2(256 CHAR),
  freq_code                VARCHAR2(32 CHAR),
  supply_code              VARCHAR2(32 CHAR),
  charge_code              VARCHAR2(32 CHAR),
  drugname                 VARCHAR2(256 CHAR),
  specifications           VARCHAR2(32 CHAR),
  dose                     VARCHAR2(32 CHAR),
  dose_unit                VARCHAR2(32 CHAR),
  quantity                 VARCHAR2(32 CHAR),
  start_time               DATE,
  end_time                 DATE,
  remark                   VARCHAR2(256 CHAR),
  yzlx                     INTEGER,
  yzzt                     INTEGER,
  yzshzt                   INTEGER default 0,
  yzshbtglx                INTEGER,
  yzzdshzt                 INTEGER,
  yzzdshbtglx              INTEGER,
  yzshbtgyy                VARCHAR2(256 CHAR),
  selfbuy                  INTEGER,
  tpn                      INTEGER,
  sfysmc                   VARCHAR2(32 CHAR),
  sfyscode                 VARCHAR2(32 CHAR),
  sfrq                     DATE,
  reserve1                 VARCHAR2(32 CHAR),
  reserve2                 VARCHAR2(32 CHAR),
  reserve3                 VARCHAR2(32 CHAR),
  medicaments_packing_unit VARCHAR2(32 CHAR),
  zxrq                     VARCHAR2(24 CHAR),
  zxsj                     VARCHAR2(24 CHAR),
  pidsj                    VARCHAR2(40 CHAR),
  patname                  VARCHAR2(60 CHAR),
  sex                      INTEGER,
  birthday                 DATE,
  age                      VARCHAR2(24 CHAR),
  ageunit                  INTEGER,
  avdp                     VARCHAR2(24 CHAR),
  transfusion              VARCHAR2(64 CHAR) default 0,
  dropspeed                VARCHAR2(64 CHAR),
  yzresource               INTEGER default 0,
  bedno                    VARCHAR2(24 CHAR),
  case_id                  VARCHAR2(24 CHAR),
  medicaments_name         VARCHAR2(64 CHAR),
  syndate                  VARCHAR2(32 CHAR)
)
tablespace pivas_tablespace;
-- Add comments to the table 
comment on table SRVS_DOCTOR_ADVICE
  is '医嘱表';
-- Add comments to the columns 
comment on column SRVS_DOCTOR_ADVICE.yz_id
  is '主键标识，自增长';
comment on column SRVS_DOCTOR_ADVICE.act_order_no
  is '医嘱编码,此字段须与HIS医嘱信息中编码一致。';
comment on column SRVS_DOCTOR_ADVICE.parent_no
  is '父医嘱编码或组编码';
comment on column SRVS_DOCTOR_ADVICE.ward_code
  is '病区(科室)编码';
comment on column SRVS_DOCTOR_ADVICE.wardname
  is '病区(科室)名称';
comment on column SRVS_DOCTOR_ADVICE.inpatient_no
  is '病人就诊唯一编码';
comment on column SRVS_DOCTOR_ADVICE.doctor
  is '开嘱医生的工号。';
comment on column SRVS_DOCTOR_ADVICE.doctor_name
  is '开嘱医生姓名。';
comment on column SRVS_DOCTOR_ADVICE.drawer
  is '录入医生工号。';
comment on column SRVS_DOCTOR_ADVICE.drawername
  is '录入医生姓名。';
comment on column SRVS_DOCTOR_ADVICE.freq_code
  is '医嘱的执行频率(或用法)。如: 每日一次; BID; Q12H等.';
comment on column SRVS_DOCTOR_ADVICE.supply_code
  is '医嘱的用药途径。如: 静推; IVGTT等。';
comment on column SRVS_DOCTOR_ADVICE.charge_code
  is '医嘱的药品编码。';
comment on column SRVS_DOCTOR_ADVICE.drugname
  is '医嘱的药品名称。';
comment on column SRVS_DOCTOR_ADVICE.specifications
  is '医嘱的药品规格。';
comment on column SRVS_DOCTOR_ADVICE.dose
  is '医嘱的药品单次剂量。';
comment on column SRVS_DOCTOR_ADVICE.dose_unit
  is '医嘱的药品单次剂量单位。';
comment on column SRVS_DOCTOR_ADVICE.quantity
  is '药品数量';
comment on column SRVS_DOCTOR_ADVICE.start_time
  is '医嘱开始时间  YYYY-MM-DD HH:MM:SS';
comment on column SRVS_DOCTOR_ADVICE.end_time
  is '医嘱停止时间 YYYY-MM-DD HH:MM:SS';
comment on column SRVS_DOCTOR_ADVICE.remark
  is '备注';
comment on column SRVS_DOCTOR_ADVICE.yzlx
  is '医嘱类型 0:长期 1:短期';
comment on column SRVS_DOCTOR_ADVICE.yzzt
  is 'HIS医嘱状态。0：执行 1：停止 2：撤销';
comment on column SRVS_DOCTOR_ADVICE.yzshzt
  is 'HIS医嘱审核状态。0：未审核 1：审核通过 2：审核不通过';
comment on column SRVS_DOCTOR_ADVICE.yzshbtglx
  is '审方问题，关联审方错误类型表';
comment on column SRVS_DOCTOR_ADVICE.yzzdshzt
  is '医嘱自动审核状态。0：未审核 1：审核通过 2：审核不通过';
comment on column SRVS_DOCTOR_ADVICE.yzzdshbtglx
  is '自动审方问题，关联审方错误类型表';
comment on column SRVS_DOCTOR_ADVICE.yzshbtgyy
  is '医嘱审核不通过原因备注';
comment on column SRVS_DOCTOR_ADVICE.selfbuy
  is '自备、嘱托等标识 0 非自备药，1自备药';
comment on column SRVS_DOCTOR_ADVICE.tpn
  is '是否营养液 1 是 0 否';
comment on column SRVS_DOCTOR_ADVICE.sfysmc
  is '医嘱审核药师名名称，如[詹姆斯]';
comment on column SRVS_DOCTOR_ADVICE.sfyscode
  is '医嘱审核药师名code，如[1001]';
comment on column SRVS_DOCTOR_ADVICE.sfrq
  is '医嘱审核时间 YYYY-MM-DD HH:MM:SS';
comment on column SRVS_DOCTOR_ADVICE.reserve1
  is '备用字段1';
comment on column SRVS_DOCTOR_ADVICE.reserve2
  is '备用字段2';
comment on column SRVS_DOCTOR_ADVICE.reserve3
  is '备用字段3';
comment on column SRVS_DOCTOR_ADVICE.medicaments_packing_unit
  is '包装单位';
comment on column SRVS_DOCTOR_ADVICE.zxrq
  is '执行日期';
comment on column SRVS_DOCTOR_ADVICE.zxsj
  is '执行时间';
comment on column SRVS_DOCTOR_ADVICE.patname
  is '患者姓名';
comment on column SRVS_DOCTOR_ADVICE.sex
  is '性别：0女，1男，默认0';
comment on column SRVS_DOCTOR_ADVICE.birthday
  is '病人出生日期';
comment on column SRVS_DOCTOR_ADVICE.age
  is '病人年龄';
comment on column SRVS_DOCTOR_ADVICE.ageunit
  is '年龄单位，0天 1月 2年';
comment on column SRVS_DOCTOR_ADVICE.avdp
  is '病人体重';
comment on column SRVS_DOCTOR_ADVICE.transfusion
  is '输液量ML';
comment on column SRVS_DOCTOR_ADVICE.dropspeed
  is '医嘱滴速100ML/MIN';
comment on column SRVS_DOCTOR_ADVICE.yzresource
  is '临时医嘱来源 0临时医嘱  1长嘱  2 pivas 3 医院过来临时 化疗';
comment on column SRVS_DOCTOR_ADVICE.bedno
  is '床号';
comment on column SRVS_DOCTOR_ADVICE.case_id
  is '病人唯一住院号';
comment on column SRVS_DOCTOR_ADVICE.medicaments_name
  is '关联药品表的药品名称';
comment on column SRVS_DOCTOR_ADVICE.syndate
  is '同步时间';
-- Create/Recreate indexes 
create index SRVS_ADVICE_ORDERGPNO on SRVS_DOCTOR_ADVICE (PARENT_NO) tablespace pivas_index_tablespace;
create index SRVS_DOCTOR_ADVICE_ORDERNO on SRVS_DOCTOR_ADVICE (ACT_ORDER_NO) tablespace pivas_index_tablespace;
create index SRVS_DOCTOR_ADVICE_PIDSJ on SRVS_DOCTOR_ADVICE (PIDSJ) tablespace pivas_index_tablespace;
-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_DOCTOR_ADVICE  add constraint SRVS_DOCTOR_ADVICE_PK primary key (YZ_ID, ACT_ORDER_NO, PARENT_NO) USING INDEX tablespace pivas_index_tablespace;
-- Add/modify columns 
alter table SRVS_DOCTOR_ADVICE add confirm_date VARCHAR2(32 CHAR);
-- Add comments to the columns 
comment on column SRVS_DOCTOR_ADVICE.confirm_date
  is '护士医嘱确认时间';
  
-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_DOCTOR_ADVICE drop constraint SRVS_DOCTOR_ADVICE_PK cascade;
alter table SRVS_DOCTOR_ADVICE add constraint SRVS_DOCTOR_ADVICE_PK primary key (YZ_ID) using index  tablespace PIVAS_INDEX_TABLESPACE;
alter table SRVS_DOCTOR_ADVICE add constraint SRVS_DOCTOR_ADVICE_PK_ORDERNO unique (ACT_ORDER_NO, PARENT_NO) using index  tablespace PIVAS_INDEX_TABLESPACE;
-- Add/modify columns 
alter table SRVS_DOCTOR_ADVICE add adddate date default sysdate;

/*==============================================================*/
/* TABLE: SRVS_DOCTOR_ADVICE_MAIN医嘱主表					                    */
/*==============================================================*/
-- Create table
create table SRVS_DOCTOR_ADVICE_MAIN
(
  yz_main_id               NUMBER(20) not null,
  act_order_no             VARCHAR2(400 CHAR),
  parent_no                VARCHAR2(32 CHAR) not null,
  ward_code                VARCHAR2(32 CHAR),
  wardname                 VARCHAR2(32 CHAR),
  inpatient_no             VARCHAR2(32 CHAR),
  doctor                   VARCHAR2(32 CHAR),
  doctor_name              VARCHAR2(32 CHAR),
  drawer                   VARCHAR2(32 CHAR),
  drawername               VARCHAR2(256 CHAR),
  freq_code                VARCHAR2(32 CHAR),
  supply_code              VARCHAR2(400 CHAR),
  charge_code              VARCHAR2(400 CHAR),
  drugname                 VARCHAR2(800 CHAR),
  specifications           VARCHAR2(400 CHAR),
  dose                     VARCHAR2(400 CHAR),
  dose_unit                VARCHAR2(400 CHAR),
  quantity                 VARCHAR2(400 CHAR),
  start_time               DATE,
  end_time                 DATE,
  remark                   VARCHAR2(256 CHAR),
  yzlx                     INTEGER,
  yzzt                     INTEGER,
  yzshzt                   INTEGER default 0,
  yzshbtglx                INTEGER,
  yzzdshzt                 INTEGER,
  yzzdshbtglx              INTEGER,
  yzshbtgyy                VARCHAR2(256 CHAR),
  selfbuy                  INTEGER,
  tpn                      INTEGER,
  sfysmc                   VARCHAR2(32 CHAR),
  sfyscode                 VARCHAR2(32 CHAR),
  sfrq                     DATE,
  reserve1                 VARCHAR2(32 CHAR),
  reserve2                 VARCHAR2(32 CHAR),
  reserve3                 VARCHAR2(32 CHAR),
  medicaments_packing_unit VARCHAR2(512 CHAR),
  zxrq                     VARCHAR2(24 CHAR),
  zxsj                     VARCHAR2(24 CHAR),
  pidsj                    VARCHAR2(40 CHAR),
  patname                  VARCHAR2(60 CHAR),
  sex                      INTEGER,
  birthday                 DATE,
  age                      VARCHAR2(24 CHAR),
  ageunit                  INTEGER,
  avdp                     VARCHAR2(24 CHAR),
  transfusion              VARCHAR2(64 CHAR),
  dropspeed                VARCHAR2(64 CHAR),
  yzresource               INTEGER default 0,
  bedno                    VARCHAR2(24 CHAR),
  case_id                  VARCHAR2(24 CHAR),
  medicaments_name         VARCHAR2(512 CHAR),
  syndate                  VARCHAR2(32 CHAR),
  confirm_date             VARCHAR2(32 CHAR),
  RUCANG_NEED_CHECK 	   INTEGER DEFAULT 0,
  wardname_en              VARCHAR2(32 CHAR),
  bedno_en                 VARCHAR2(32 CHAR),
  recheckstate             VARCHAR2(1 CHAR),
  recheckcause             VARCHAR2(256 CHAR)
)tablespace pivas_tablespace;

create index inx_yzm_PARENT_NO on SRVS_DOCTOR_ADVICE_MAIN (PARENT_NO) tablespace pivas_index_tablespace;
create index inx_yzm_ACT_ORDER_NO on SRVS_DOCTOR_ADVICE_MAIN (ACT_ORDER_NO) tablespace pivas_index_tablespace;
create index inx_yzm_PIDSJ on SRVS_DOCTOR_ADVICE_MAIN (PIDSJ) tablespace pivas_index_tablespace;
create index inx_yzm_WARD_CODE on SRVS_DOCTOR_ADVICE_MAIN (WARD_CODE) tablespace pivas_index_tablespace;
create index inx_yzm_YZSHBTGLX on SRVS_DOCTOR_ADVICE_MAIN (YZSHBTGLX) tablespace pivas_index_tablespace;
create index inx_yzm_YZZDSHBTGLX on SRVS_DOCTOR_ADVICE_MAIN (YZZDSHBTGLX) tablespace pivas_index_tablespace;
create index inx_yzm_YZZT on SRVS_DOCTOR_ADVICE_MAIN (YZZT) tablespace pivas_index_tablespace;
create index inx_yzm_YZLX on SRVS_DOCTOR_ADVICE_MAIN (YZLX) tablespace pivas_index_tablespace;
create index inx_yzm_YZRESOURCE on SRVS_DOCTOR_ADVICE_MAIN (YZRESOURCE) tablespace pivas_index_tablespace;

-- Add comments to the table 
comment on table SRVS_DOCTOR_ADVICE_MAIN
  is '医嘱主表';

-- Add comments to the columns 
comment on column SRVS_DOCTOR_ADVICE_MAIN.yz_main_id
  is '主键标识，自增长';
comment on column SRVS_DOCTOR_ADVICE_MAIN.act_order_no
  is '子医嘱编码集合';
comment on column SRVS_DOCTOR_ADVICE_MAIN.parent_no
  is '父医嘱编码或组编码';
comment on column SRVS_DOCTOR_ADVICE_MAIN.ward_code
  is '病区(科室)编码';
comment on column SRVS_DOCTOR_ADVICE_MAIN.wardname
  is '病区(科室)名称';
comment on column SRVS_DOCTOR_ADVICE_MAIN.inpatient_no
  is '病人就诊唯一编码';
comment on column SRVS_DOCTOR_ADVICE_MAIN.doctor
  is '开嘱医生的工号。';
comment on column SRVS_DOCTOR_ADVICE_MAIN.doctor_name
  is '开嘱医生姓名。';
comment on column SRVS_DOCTOR_ADVICE_MAIN.drawer
  is '录入医生工号。';
comment on column SRVS_DOCTOR_ADVICE_MAIN.drawername
  is '录入医生姓名。';
comment on column SRVS_DOCTOR_ADVICE_MAIN.freq_code
  is '医嘱的执行频率(或用法)。如: 每日一次; BID; Q12H等.';
comment on column SRVS_DOCTOR_ADVICE_MAIN.supply_code
  is '医嘱的用药途径。如: 静推; IVGTT等。';
comment on column SRVS_DOCTOR_ADVICE_MAIN.charge_code
  is '医嘱的药品编码。';
comment on column SRVS_DOCTOR_ADVICE_MAIN.drugname
  is '医嘱的药品名称。';
comment on column SRVS_DOCTOR_ADVICE_MAIN.specifications
  is '医嘱的药品规格。';
comment on column SRVS_DOCTOR_ADVICE_MAIN.dose
  is '医嘱的药品单次剂量。';
comment on column SRVS_DOCTOR_ADVICE_MAIN.dose_unit
  is '医嘱的药品单次剂量单位。';
comment on column SRVS_DOCTOR_ADVICE_MAIN.quantity
  is '药品数量';
comment on column SRVS_DOCTOR_ADVICE_MAIN.start_time
  is '医嘱开始时间  YYYY-MM-DD HH:MM:SS';
comment on column SRVS_DOCTOR_ADVICE_MAIN.end_time
  is '医嘱停止时间 YYYY-MM-DD HH:MM:SS';
comment on column SRVS_DOCTOR_ADVICE_MAIN.remark
  is '备注';
comment on column SRVS_DOCTOR_ADVICE_MAIN.yzlx
  is '医嘱类型 0:长期 1:短期';
comment on column SRVS_DOCTOR_ADVICE_MAIN.yzzt
  is 'HIS医嘱状态。0：执行 1：停止 2：撤销';
comment on column SRVS_DOCTOR_ADVICE_MAIN.yzshzt
  is 'HIS医嘱审核状态。0：未审核 1：审核通过 2：审核不通过';
comment on column SRVS_DOCTOR_ADVICE_MAIN.yzshbtglx
  is '审方问题，关联审方错误类型表';
comment on column SRVS_DOCTOR_ADVICE_MAIN.yzzdshzt
  is '医嘱自动审核状态。0：未审核 1：审核通过 2：审核不通过';
comment on column SRVS_DOCTOR_ADVICE_MAIN.yzzdshbtglx
  is '自动审方问题，关联审方错误类型表';
comment on column SRVS_DOCTOR_ADVICE_MAIN.yzshbtgyy
  is '医嘱审核不通过原因备注';
comment on column SRVS_DOCTOR_ADVICE_MAIN.selfbuy
  is '自备、嘱托等标识 0 非自备药，1自备药';
comment on column SRVS_DOCTOR_ADVICE_MAIN.tpn
  is '是否营养液 1 是 0 否';
comment on column SRVS_DOCTOR_ADVICE_MAIN.sfysmc
  is '医嘱审核药师名名称，如[詹姆斯]';
comment on column SRVS_DOCTOR_ADVICE_MAIN.sfyscode
  is '医嘱审核药师名code，如[1001]';
comment on column SRVS_DOCTOR_ADVICE_MAIN.sfrq
  is '医嘱审核时间 YYYY-MM-DD HH:MM:SS';
comment on column SRVS_DOCTOR_ADVICE_MAIN.reserve1
  is '备用字段1';
comment on column SRVS_DOCTOR_ADVICE_MAIN.reserve2
  is '备用字段2';
comment on column SRVS_DOCTOR_ADVICE_MAIN.reserve3
  is '备用字段3';
comment on column SRVS_DOCTOR_ADVICE_MAIN.zxrq
  is '执行日期';
comment on column SRVS_DOCTOR_ADVICE_MAIN.zxsj
  is '执行时间';
comment on column SRVS_DOCTOR_ADVICE_MAIN.patname
  is '患者姓名';
comment on column SRVS_DOCTOR_ADVICE_MAIN.sex
  is '性别：0女，1男，默认0';
comment on column SRVS_DOCTOR_ADVICE_MAIN.birthday
  is '病人出生日期';
comment on column SRVS_DOCTOR_ADVICE_MAIN.age
  is '病人年龄';
comment on column SRVS_DOCTOR_ADVICE_MAIN.ageunit
  is '年龄单位，0天 1月 2年';
comment on column SRVS_DOCTOR_ADVICE_MAIN.avdp
  is '病人体重';
comment on column SRVS_DOCTOR_ADVICE_MAIN.transfusion
  is '输液量ML';
comment on column SRVS_DOCTOR_ADVICE_MAIN.dropspeed
  is '医嘱滴速100ML/MIN';
comment on column SRVS_DOCTOR_ADVICE_MAIN.yzresource
  is '临时医嘱来源 0临时医嘱  1长嘱  2 pivas 3 医院过来临时 化疗';
comment on column SRVS_DOCTOR_ADVICE_MAIN.bedno
  is '床号';
comment on column SRVS_DOCTOR_ADVICE_MAIN.case_id
  is '病人唯一住院号';
comment on column SRVS_DOCTOR_ADVICE_MAIN.medicaments_name
  is '关联药品表的药品名称';
comment on column SRVS_DOCTOR_ADVICE_MAIN.syndate
  is '同步时间';
comment on column SRVS_DOCTOR_ADVICE_MAIN.confirm_date
  is '护士医嘱确认时间';
comment on column SRVS_DOCTOR_ADVICE_MAIN.recheckstate
  is '复核确认状态 0:有复核  1：强制打包 2:拒绝';
comment on column SRVS_DOCTOR_ADVICE_MAIN.recheckcause
  is '复核原因';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_DOCTOR_ADVICE_MAIN
  add constraint SRVS_DOCTOR_ADVICE_MAIN_PK primary key (YZ_MAIN_ID, PARENT_NO) USING INDEX tablespace pivas_index_tablespace;
  
-- SRVS_DOCTOR_ADVICE_mian
alter table SRVS_DOCTOR_ADVICE_MAIN drop constraint SRVS_DOCTOR_ADVICE_MAIN_PK cascade;
alter table SRVS_DOCTOR_ADVICE_MAIN add constraint SRVS_DOCTOR_ADVICE_MAIN_PK primary key (YZ_MAIN_ID) using index  tablespace PIVAS_INDEX_TABLESPACE;
alter table SRVS_DOCTOR_ADVICE_MAIN add constraint SRVS_ADVICE_MAIN_PRTNO unique (PARENT_NO);

alter table SRVS_DOCTOR_ADVICE_MAIN add recheckuser VARCHAR2(32 CHAR);
-- Add comments to the columns 
comment on column SRVS_DOCTOR_ADVICE_MAIN.recheckuser
  is '复核人';
  -- Add/modify columns 
--医嘱异常表
create table SRVS_DOCTOR_ADVICE_EXCEPTION
(
  yz_main_id               NUMBER(20) not null,
  act_order_no             VARCHAR2(400 CHAR) not null,
  parent_no                VARCHAR2(32 CHAR) not null,
  ward_code                VARCHAR2(32 CHAR),
  wardname                 VARCHAR2(32 CHAR),
  inpatient_no             VARCHAR2(32 CHAR),
  doctor                   VARCHAR2(32 CHAR),
  doctor_name              VARCHAR2(32 CHAR),
  drawer                   VARCHAR2(32 CHAR),
  drawername               VARCHAR2(256 CHAR),
  freq_code                VARCHAR2(32 CHAR),
  supply_code              VARCHAR2(400 CHAR),
  charge_code              VARCHAR2(400 CHAR),
  drugname                 VARCHAR2(800 CHAR),
  specifications           VARCHAR2(400 CHAR),
  dose                     VARCHAR2(400 CHAR),
  dose_unit                VARCHAR2(400 CHAR),
  quantity                 VARCHAR2(400 CHAR),
  start_time               DATE,
  end_time                 DATE,
  remark                   VARCHAR2(256 CHAR),
  yzlx                     INTEGER,
  yzzt                     INTEGER,
  yzshzt                   INTEGER default 0,
  yzshbtglx                INTEGER,
  yzzdshzt                 INTEGER,
  yzzdshbtglx              INTEGER,
  yzshbtgyy                VARCHAR2(256 CHAR),
  selfbuy                  INTEGER,
  tpn                      INTEGER,
  sfysmc                   VARCHAR2(32 CHAR),
  sfyscode                 VARCHAR2(32 CHAR),
  sfrq                     DATE,
  reserve1                 VARCHAR2(32 CHAR),
  reserve2                 VARCHAR2(32 CHAR),
  reserve3                 VARCHAR2(32 CHAR),
  medicaments_packing_unit VARCHAR2(32 CHAR),
  zxrq                     VARCHAR2(24 CHAR),
  zxsj                     VARCHAR2(24 CHAR),
  pidsj                    VARCHAR2(40 CHAR),
  patname                  VARCHAR2(60 CHAR),
  sex                      INTEGER,
  birthday                 DATE,
  age                      VARCHAR2(24 CHAR),
  ageunit                  INTEGER,
  avdp                     VARCHAR2(24 CHAR),
  transfusion              VARCHAR2(64 CHAR),
  dropspeed                VARCHAR2(64 CHAR),
  yzresource               INTEGER default 0,
  bedno                    VARCHAR2(24 CHAR),
  case_id                  VARCHAR2(24 CHAR),
  medicaments_name         VARCHAR2(256 CHAR),
  syndate                  VARCHAR2(32 CHAR),
  confirm_date             VARCHAR2(32 CHAR),
  check_date 			   VARCHAR2(32 CHAR),
  firstusecount            INTEGER
)
tablespace pivas_tablespace;
-- Add comments to the table 
comment on table SRVS_DOCTOR_ADVICE_EXCEPTION
  is '医嘱表';
-- Add comments to the columns 
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.yz_main_id
  is '主键标识，自增长';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.act_order_no
  is '子医嘱编码集合';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.parent_no
  is '父医嘱编码或组编码';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.ward_code
  is '病区(科室)编码';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.wardname
  is '病区(科室)名称';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.inpatient_no
  is '病人就诊唯一编码';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.doctor
  is '开嘱医生的工号。';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.doctor_name
  is '开嘱医生姓名。';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.drawer
  is '录入医生工号。';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.drawername
  is '录入医生姓名。';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.freq_code
  is '医嘱的执行频率(或用法)。如: 每日一次; BID; Q12H等.';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.supply_code
  is '医嘱的用药途径。如: 静推; IVGTT等。';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.charge_code
  is '医嘱的药品编码。';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.drugname
  is '医嘱的药品名称。';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.specifications
  is '医嘱的药品规格。';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.dose
  is '医嘱的药品单次剂量。';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.dose_unit
  is '医嘱的药品单次剂量单位。';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.quantity
  is '药品数量';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.start_time
  is '医嘱开始时间  YYYY-MM-DD HH:MM:SS';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.end_time
  is '医嘱停止时间 YYYY-MM-DD HH:MM:SS';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.remark
  is '备注';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.yzlx
  is '医嘱类型 0:长期 1:短期';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.yzzt
  is 'HIS医嘱状态。0：执行 1：停止 2：撤销';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.yzshzt
  is 'HIS医嘱审核状态。0：未审核 1：审核通过 2：审核不通过';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.yzshbtglx
  is '审方问题，关联审方错误类型表';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.yzzdshzt
  is '医嘱自动审核状态。0：未审核 1：审核通过 2：审核不通过';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.yzzdshbtglx
  is '自动审方问题，关联审方错误类型表';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.yzshbtgyy
  is '医嘱审核不通过原因备注';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.selfbuy
  is '自备、嘱托等标识 0 非自备药，1自备药';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.tpn
  is '是否营养液 1 是 0 否';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.sfysmc
  is '医嘱审核药师名名称，如[詹姆斯]';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.sfyscode
  is '医嘱审核药师名code，如[1001]';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.sfrq
  is '医嘱审核时间 YYYY-MM-DD HH:MM:SS';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.reserve1
  is '备用字段1';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.reserve2
  is '备用字段2';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.reserve3
  is '备用字段3';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.zxrq
  is '执行日期';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.zxsj
  is '执行时间';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.patname
  is '患者姓名';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.sex
  is '性别：0女，1男，默认0';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.birthday
  is '病人出生日期';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.age
  is '病人年龄';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.ageunit
  is '年龄单位，0天 1月 2年';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.avdp
  is '病人体重';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.transfusion
  is '输液量ML';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.dropspeed
  is '医嘱滴速100ML/MIN';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.yzresource
  is '临时医嘱来源 0临时医嘱  1长嘱  2 pivas 3 医院过来临时 化疗';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.bedno
  is '床号';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.case_id
  is '病人唯一住院号';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.medicaments_name
  is '关联药品表的药品名称';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.syndate
  is '同步时间';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.confirm_date
  is '护士医嘱确认时间';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.firstusecount
  is '首日用药次数';
comment on column SRVS_DOCTOR_ADVICE_EXCEPTION.check_date
  is '护士医嘱审核时间';
create index SRVS_ADVICE_EX_ORDERGPNO on SRVS_DOCTOR_ADVICE_EXCEPTION (PARENT_NO) tablespace PIVAS_INDEX_TABLESPACE;
create index SRVS_DOCTOR_ADVICE_EX_ORDERNO on SRVS_DOCTOR_ADVICE_EXCEPTION (ACT_ORDER_NO)  tablespace PIVAS_INDEX_TABLESPACE;
create index SRVS_DOCTOR_ADVICE_EX_PIDSJ on SRVS_DOCTOR_ADVICE_EXCEPTION (PIDSJ) tablespace PIVAS_INDEX_TABLESPACE;
-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_DOCTOR_ADVICE_EXCEPTION
  add constraint SRVS_ADVICE_EX_PK primary key (YZ_MAIN_ID, ACT_ORDER_NO, PARENT_NO) USING INDEX tablespace pivas_index_tablespace;


/*==============================================================*/
/* TABLE: SRVS_PRESCRIPTION药单表					                    */
/*==============================================================*/
create table SRVS_PRESCRIPTION
(
  yd_id                    NUMBER(20) not null,
  act_order_no             VARCHAR2(32 CHAR) not null,
  parent_no                VARCHAR2(32 CHAR) not null,
  dybz                     INTEGER,
  yyrq                     DATE,
  scrq                     DATE,
  zxbc                     INTEGER,
  ydzxzt                   INTEGER,
  medicaments_code         VARCHAR2(32 CHAR),
  charge_code              VARCHAR2(32 CHAR),
  drugname                 VARCHAR2(256 CHAR),
  specifications           VARCHAR2(32 CHAR),
  dose                     VARCHAR2(32 CHAR),
  dose_unit                VARCHAR2(32 CHAR),
  quantity                 VARCHAR2(32 CHAR),
  bottle_label_num         VARCHAR2(32 CHAR),
  reserve1                 VARCHAR2(32 CHAR),
  reserve2                 VARCHAR2(32 CHAR),
  reserve3                 VARCHAR2(32 CHAR),
  medicaments_packing_unit VARCHAR2(400 CHAR),
  freq_code                VARCHAR2(32 CHAR),
  yzshzt                   INTEGER,
  yzzdshzt                 INTEGER,
  ward_code                VARCHAR2(32 CHAR),
  wardname                 VARCHAR2(32 CHAR),
  inpatient_no             VARCHAR2(32 CHAR),
  patname                  VARCHAR2(60 CHAR),
  sex                      INTEGER,
  bedno                    VARCHAR2(24 CHAR),
  case_id                  VARCHAR2(24 CHAR),
  birthday                 DATE,
  age                      VARCHAR2(24 CHAR),
  ageunit                  INTEGER,
  zxrq                     VARCHAR2(24 CHAR) not null,
  zxsj                     VARCHAR2(24 CHAR) not null,
  pidsj                    VARCHAR2(40 CHAR),
  yzlx                     INTEGER,
  sfysmc                   VARCHAR2(32 CHAR),
  sfyscode                 VARCHAR2(32 CHAR),
  sfrq                     DATE,
  pidrqzxbc                VARCHAR2(40 CHAR),
  doctor                   VARCHAR2(32 CHAR),
  doctor_name              VARCHAR2(32 CHAR),
  avdp                     VARCHAR2(24 CHAR),
  transfusion              VARCHAR2(64 CHAR),
  dropspeed                VARCHAR2(64 CHAR),
  yzresource               INTEGER default 0,
  start_time               DATE,
  end_time                 DATE,
  SERIAL_NUMBER		   	   NUMBER(10, 0)
)
tablespace pivas_tablespace;
-- Add comments to the table 
comment on table SRVS_PRESCRIPTION
  is '药单表';
-- Add comments to the columns 
comment on column SRVS_PRESCRIPTION.yd_id
  is '主键标识，自增长';
comment on column SRVS_PRESCRIPTION.act_order_no
  is '医嘱编码,此字段须与HIS医嘱信息中编码一致';
comment on column SRVS_PRESCRIPTION.parent_no
  is '父医嘱编码或组编码';
comment on column SRVS_PRESCRIPTION.dybz
  is '药单打印标志,0已打印 1未打印';
comment on column SRVS_PRESCRIPTION.yyrq
  is '用药日期  YYYY/MM/DD';
comment on column SRVS_PRESCRIPTION.scrq
  is '药单生成日期  YYYY/MM/DD HH:MM:SS';
comment on column SRVS_PRESCRIPTION.zxbc
  is '执行批次';
comment on column SRVS_PRESCRIPTION.ydzxzt
  is '药单执行状态  0,执行  1,停止  2,撤销 3,退费';
comment on column SRVS_PRESCRIPTION.medicaments_code
  is '药品编码';
comment on column SRVS_PRESCRIPTION.charge_code
  is '医嘱的药品编码';
comment on column SRVS_PRESCRIPTION.drugname
  is '医嘱的药品名称';
comment on column SRVS_PRESCRIPTION.specifications
  is '医嘱的药品规格';
comment on column SRVS_PRESCRIPTION.dose
  is '医嘱的药品单次剂量';
comment on column SRVS_PRESCRIPTION.dose_unit
  is '医嘱的药品单次剂量单位';
comment on column SRVS_PRESCRIPTION.quantity
  is '药品数量';
comment on column SRVS_PRESCRIPTION.bottle_label_num
  is '药单瓶签的唯一编号,打印药单瓶签时生成';
comment on column SRVS_PRESCRIPTION.reserve1
  is '备用字段1';
comment on column SRVS_PRESCRIPTION.reserve2
  is '备用字段2';
comment on column SRVS_PRESCRIPTION.reserve3
  is '备用字段3';
comment on column SRVS_PRESCRIPTION.medicaments_packing_unit
  is '包装单位';
comment on column SRVS_PRESCRIPTION.freq_code
  is '医嘱的执行频率(或用法)。如: 每日一次; BID; Q12H等.';
comment on column SRVS_PRESCRIPTION.yzshzt
  is 'HIS医嘱审核状态。0：未审核 1：审核通过 2：审核不通过';
comment on column SRVS_PRESCRIPTION.yzzdshzt
  is '医嘱自动审核状态。0：未审核 1：审核通过 2：审核不通过';
comment on column SRVS_PRESCRIPTION.ward_code
  is '病区(科室)编码';
comment on column SRVS_PRESCRIPTION.wardname
  is '病区(科室)名称';
comment on column SRVS_PRESCRIPTION.inpatient_no
  is '病人就诊唯一编码';
comment on column SRVS_PRESCRIPTION.patname
  is '患者姓名';
comment on column SRVS_PRESCRIPTION.sex
  is '性别：0女，1男，默认0';
comment on column SRVS_PRESCRIPTION.bedno
  is '患者住院期间，所住床位对应的编号';
comment on column SRVS_PRESCRIPTION.case_id
  is '病人唯一住院号';
comment on column SRVS_PRESCRIPTION.birthday
  is '病人出生日期';
comment on column SRVS_PRESCRIPTION.age
  is '病人年龄';
comment on column SRVS_PRESCRIPTION.ageunit
  is '年龄单位，0天 1月 2年';
comment on column SRVS_PRESCRIPTION.zxrq
  is '执行日期';
comment on column SRVS_PRESCRIPTION.zxsj
  is '执行时间';
comment on column SRVS_PRESCRIPTION.doctor
  is '开嘱医生的工号';
comment on column SRVS_PRESCRIPTION.doctor_name
  is '开嘱医生的姓名';
comment on column SRVS_PRESCRIPTION.avdp
  is '病人体重';
comment on column SRVS_PRESCRIPTION.transfusion
  is '输液量ML';
comment on column SRVS_PRESCRIPTION.dropspeed
  is '医嘱滴速100ML/MIN';
comment on column SRVS_PRESCRIPTION.yzresource
  is '临时医嘱来源 0临时医嘱  1长嘱  2 pivas 3 医院过来临时 化疗';
comment on column SRVS_PRESCRIPTION.SERIAL_NUMBER
  is '批次序号';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_PRESCRIPTION add constraint SRVS_PRESCRIPTION_PK primary key (PIDSJ, ACT_ORDER_NO) USING INDEX tablespace pivas_index_tablespace;
create index SRVS_PRESCRIPTION_pidsj on SRVS_PRESCRIPTION (pidsj);
create index SRVS_PRESCRIPTION_drugcode on SRVS_PRESCRIPTION (medicaments_code);
alter table SRVS_PRESCRIPTION add pzcode VARCHAR2(32 CHAR);
alter table SRVS_PRESCRIPTION add pzmc VARCHAR2(32 CHAR);
alter table SRVS_PRESCRIPTION add pzrq date;
-- Add comments to the columns 
comment on column SRVS_PRESCRIPTION.pzcode
  is '配置人员编码';
comment on column SRVS_PRESCRIPTION.pzmc
  is '配置人员名称';
comment on column SRVS_PRESCRIPTION.pzrq
  is '配置日期';

create index INX_YD_BOTTLE_LABEL_NUM on SRVS_PRESCRIPTION(BOTTLE_LABEL_NUM) tablespace pivas_index_tablespace;
  
-- 药单主表
create table SRVS_PRESCRIPTION_MAIN
(
  yd_main_id               NUMBER(20) not null,
  act_order_no             VARCHAR2(400 CHAR),
  parent_no                VARCHAR2(32 CHAR) not null,
  dybz                     INTEGER,
  yyrq                     DATE,
  scrq                     DATE,
  zxbc                     INTEGER,
  ydzxzt                   INTEGER,
  medicaments_code         VARCHAR2(400 CHAR),
  charge_code              VARCHAR2(400 CHAR),
  drugname                 VARCHAR2(400 CHAR),
  specifications           VARCHAR2(400 CHAR),
  dose                     VARCHAR2(400 CHAR),
  dose_unit                VARCHAR2(400 CHAR),
  quantity                 VARCHAR2(400 CHAR),
  bottle_label_num         VARCHAR2(32 CHAR),
  reserve1                 VARCHAR2(32 CHAR),
  reserve2                 VARCHAR2(32 CHAR),
  reserve3                 VARCHAR2(32 CHAR),
  medicaments_packing_unit VARCHAR2(400 CHAR),
  freq_code                VARCHAR2(32 CHAR),
  yzshzt                   INTEGER,
  yzzdshzt                 INTEGER,
  ward_code                VARCHAR2(32 CHAR),
  wardname                 VARCHAR2(32 CHAR),
  inpatient_no             VARCHAR2(32 CHAR),
  patname                  VARCHAR2(60 CHAR),
  sex                      INTEGER,
  bedno                    VARCHAR2(24 CHAR),
  case_id                  VARCHAR2(24 CHAR),
  birthday                 DATE,
  age                      VARCHAR2(24 CHAR),
  ageunit                  INTEGER,
  zxrq                     VARCHAR2(24 CHAR) not null,
  zxsj                     VARCHAR2(24 CHAR) not null,
  pidsj                    VARCHAR2(40 CHAR),
  yzlx                     INTEGER,
  sfysmc                   VARCHAR2(32 CHAR),
  sfyscode                 VARCHAR2(32 CHAR),
  sfrq                     DATE,
  pidrqzxbc                VARCHAR2(40 CHAR),
  doctor                   VARCHAR2(32 CHAR),
  doctor_name              VARCHAR2(32 CHAR),
  avdp                     VARCHAR2(24 CHAR),
  transfusion              VARCHAR2(64 CHAR),
  dropspeed                VARCHAR2(64 CHAR),
  yzresource               INTEGER default 0,
  start_time               DATE,
  end_time                 DATE,
  SERIAL_NUMBER		   	   NUMBER(10, 0),
  yzconfig_status          INTEGER default 0,	--审方确认状态0待确认，1已确认
  yzconfig_time            DATE,				--审方确认时间
  yzconfig_uid             NUMBER(20),			--审方确认人ID
  yzconfig_uname           VARCHAR2(64 CHAR),	--审方确认人姓名
  ydreorder_status		   INTEGER default 0,   --批次优化是否确认：0未确认，1已确认
  ydreorder_code           INTEGER default 0,	--自动调整批次结果：0待处理，11批次未调整-有异常，12批次有调整-有异常，21批次未调整-无异常，22批次有调整-无异常，23用户手动调整批次，并将其置为正常
  ydreorder_time           DATE,	            --自动调整批次时间
  ydreorder_mess           VARCHAR2(300 CHAR),  --自动调整批次结果
  zxbc_change_before       INTEGER,				--调整前批次
  zxbc_change_beforeS      VARCHAR2(64 CHAR), 	--调整前批次S
  wardname_en              VARCHAR2(32 CHAR),
  bedno_en                 VARCHAR2(32 CHAR)
)tablespace pivas_tablespace;

create index inx_ydm_PIDSJ on SRVS_PRESCRIPTION_MAIN (PIDSJ) tablespace pivas_index_tablespace;
create index inx_ydm_SCRQ on SRVS_PRESCRIPTION_MAIN (SCRQ) tablespace pivas_index_tablespace;
create index inx_ydm_YDZXZT on SRVS_PRESCRIPTION_MAIN (YDZXZT) tablespace pivas_index_tablespace;
create index inx_ydm_REORDER_STATUS on SRVS_PRESCRIPTION_MAIN (YDREORDER_STATUS) tablespace pivas_index_tablespace;
create index inx_ydm_WARDCODE on SRVS_PRESCRIPTION_MAIN (WARD_CODE) tablespace pivas_index_tablespace;
create index inx_YDM_BOTTLE_LABEL_NUM on SRVS_PRESCRIPTION_MAIN (BOTTLE_LABEL_NUM) tablespace pivas_index_tablespace;
-- Add comments to the table 
comment on table SRVS_PRESCRIPTION_MAIN
  is '药单表';
-- Add comments to the columns 
comment on column SRVS_PRESCRIPTION_MAIN.yd_main_id
  is '主键标识，自增长';
comment on column SRVS_PRESCRIPTION_MAIN.act_order_no
  is '医嘱编码,此字段须与HIS医嘱信息中编码一致';
comment on column SRVS_PRESCRIPTION_MAIN.parent_no
  is '父医嘱编码或组编码';
comment on column SRVS_PRESCRIPTION_MAIN.dybz
  is '药单打印标志,0已打印 1未打印';
comment on column SRVS_PRESCRIPTION_MAIN.yyrq
  is '用药日期  YYYY/MM/DD';
comment on column SRVS_PRESCRIPTION_MAIN.scrq
  is '药单生成日期  YYYY/MM/DD HH:MM:SS';
comment on column SRVS_PRESCRIPTION_MAIN.zxbc
  is '执行批次，对应批次表的主键';
comment on column SRVS_PRESCRIPTION_MAIN.ydzxzt
  is '药单执行状态  0,执行  1,停止  2,撤销 3,退费';
comment on column SRVS_PRESCRIPTION_MAIN.medicaments_code
  is '药品编码';
comment on column SRVS_PRESCRIPTION_MAIN.charge_code
  is '医嘱的药品编码';
comment on column SRVS_PRESCRIPTION_MAIN.drugname
  is '医嘱的药品名称';
comment on column SRVS_PRESCRIPTION_MAIN.specifications
  is '医嘱的药品规格';
comment on column SRVS_PRESCRIPTION_MAIN.dose
  is '医嘱的药品单次剂量';
comment on column SRVS_PRESCRIPTION_MAIN.dose_unit
  is '医嘱的药品单次剂量单位';
comment on column SRVS_PRESCRIPTION_MAIN.quantity
  is '药品数量';
comment on column SRVS_PRESCRIPTION_MAIN.bottle_label_num
  is '药单瓶签的唯一编号,打印药单瓶签时生成';
comment on column SRVS_PRESCRIPTION_MAIN.reserve1
  is '备用字段1';
comment on column SRVS_PRESCRIPTION_MAIN.reserve2
  is '备用字段2';
comment on column SRVS_PRESCRIPTION_MAIN.reserve3
  is '备用字段3';
comment on column SRVS_PRESCRIPTION_MAIN.medicaments_packing_unit
  is '包装单位';
comment on column SRVS_PRESCRIPTION_MAIN.freq_code
  is '医嘱的执行频率(或用法)。如: 每日一次; BID; Q12H等.';
comment on column SRVS_PRESCRIPTION_MAIN.yzshzt
  is 'HIS医嘱审核状态。0：未审核 1：审核通过 2：审核不通过';
comment on column SRVS_PRESCRIPTION_MAIN.yzzdshzt
  is '医嘱自动审核状态。0：未审核 1：审核通过 2：审核不通过';
comment on column SRVS_PRESCRIPTION_MAIN.ward_code
  is '病区(科室)编码';
comment on column SRVS_PRESCRIPTION_MAIN.wardname
  is '病区(科室)名称';
comment on column SRVS_PRESCRIPTION_MAIN.inpatient_no
  is '病人就诊唯一编码';
comment on column SRVS_PRESCRIPTION_MAIN.patname
  is '患者姓名';
comment on column SRVS_PRESCRIPTION_MAIN.sex
  is '性别：0女，1男，默认0';
comment on column SRVS_PRESCRIPTION_MAIN.bedno
  is '患者住院期间，所住床位对应的编号';
comment on column SRVS_PRESCRIPTION_MAIN.case_id
  is '病人唯一住院号';
comment on column SRVS_PRESCRIPTION_MAIN.birthday
  is '病人出生日期';
comment on column SRVS_PRESCRIPTION_MAIN.age
  is '病人年龄';
comment on column SRVS_PRESCRIPTION_MAIN.ageunit
  is '年龄单位，0天 1月 2年';
comment on column SRVS_PRESCRIPTION_MAIN.zxrq
  is '执行日期';
comment on column SRVS_PRESCRIPTION_MAIN.zxsj
  is '执行时间';
comment on column SRVS_PRESCRIPTION_MAIN.doctor
  is '开嘱医生的工号';
comment on column SRVS_PRESCRIPTION_MAIN.doctor_name
  is '开嘱医生的姓名';
comment on column SRVS_PRESCRIPTION_MAIN.avdp
  is '病人体重';
comment on column SRVS_PRESCRIPTION_MAIN.transfusion
  is '输液量ML';
comment on column SRVS_PRESCRIPTION_MAIN.dropspeed
  is '医嘱滴速100ML/MIN';
comment on column SRVS_PRESCRIPTION_MAIN.yzresource
  is '临时医嘱来源 0临时医嘱  1长嘱  2 pivas 3 医院过来临时 化疗';
comment on column SRVS_PRESCRIPTION_MAIN.start_time
  is '医嘱开始时间';
comment on column SRVS_PRESCRIPTION_MAIN.end_time
  is '医嘱结束时间';
comment on column SRVS_PRESCRIPTION_MAIN.SERIAL_NUMBER
  is '批次序号';
comment on column SRVS_PRESCRIPTION_MAIN.yzconfig_status
  is '审方确认状态0待确认，1已确认';
comment on column SRVS_PRESCRIPTION_MAIN.yzconfig_time
  is '批次确认时间';
comment on column SRVS_PRESCRIPTION_MAIN.yzconfig_uid
  is '批次确认人姓名';
comment on column SRVS_PRESCRIPTION_MAIN.yzconfig_uname
  is '批次确认人姓名';
comment on column SRVS_PRESCRIPTION_MAIN.ydreorder_status
  is '批次优化是否确认：0未确认，1已确认';
comment on column SRVS_PRESCRIPTION_MAIN.ydreorder_code
  is '自动调整批次结果：0待处理，11批次未调整-有异常，12批次有调整-有异常，21批次未调整-无异常，22批次有调整-无异常，23用户手动调整批次，并将其置为正常';
comment on column SRVS_PRESCRIPTION_MAIN.ydreorder_time
  is '自动调整批次时间';
comment on column SRVS_PRESCRIPTION_MAIN.ydreorder_mess
  is '自动调整批次结果';
comment on column SRVS_PRESCRIPTION_MAIN.zxbc_change_before
  is '调整前批次';
comment on column SRVS_PRESCRIPTION_MAIN.zxbc_change_beforeS
  is '调整前批次S';
comment on column SRVS_PRESCRIPTION_MAIN.wardname_en
  is '病区拼音';
comment on column SRVS_PRESCRIPTION_MAIN.bedno_en
  is '床号拼音'; 

-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_PRESCRIPTION_MAIN
  add constraint SRVS_PRESCRIPTION_MAIN_PK primary key (YD_MAIN_ID, PARENT_NO, ZXRQ, ZXSJ) USING INDEX tablespace pivas_index_tablespace;
alter table SRVS_PRESCRIPTION_MAIN
  add constraint SRVS_PRESCRIPTION_MAIN_EQ1 unique (PIDRQZXBC) USING INDEX tablespace pivas_index_tablespace;

COMMENT ON COLUMN SRVS_PRESCRIPTION_MAIN.DOCTOR  IS '开嘱医生的工号';

COMMENT ON COLUMN SRVS_PRESCRIPTION_MAIN.DOCTOR_NAME  IS '开嘱医生的姓名';
 
COMMENT ON COLUMN SRVS_PRESCRIPTION_MAIN.AVDP IS '病人体重';

COMMENT ON COLUMN SRVS_PRESCRIPTION_MAIN.TRANSFUSION IS '输液量ML';

COMMENT ON COLUMN SRVS_PRESCRIPTION_MAIN.DROPSPEED IS '医嘱滴速100ML/MIN';

COMMENT ON COLUMN SRVS_PRESCRIPTION_MAIN.YZRESOURCE IS '临时医嘱来源 0临时医嘱  1长嘱  2 pivas 3 医院过来临时 化疗';

COMMENT ON COLUMN SRVS_PRESCRIPTION_MAIN.START_TIME IS '医嘱开始时间';

COMMENT ON COLUMN SRVS_PRESCRIPTION_MAIN.END_TIME IS '医嘱结束时间';

alter table SRVS_PRESCRIPTION_MAIN add pzcode VARCHAR2(32 CHAR);
alter table SRVS_PRESCRIPTION_MAIN add pzmc VARCHAR2(32 CHAR);
alter table SRVS_PRESCRIPTION_MAIN add pzrq date;
-- Add comments to the columns 
comment on column SRVS_PRESCRIPTION_MAIN.pzcode
  is '配置人员编码';
comment on column SRVS_PRESCRIPTION_MAIN.pzmc
  is '配置人员名称';
comment on column SRVS_PRESCRIPTION_MAIN.pzrq
  is '配置日期';

-- Create/Recreate indexes 
create index inx_ydm_parentno on SRVS_PRESCRIPTION_MAIN (parent_no);

-- Create/Recreate indexes 
create index inx_ydm_yyrq on SRVS_PRESCRIPTION_MAIN (yyrq);
create index inx_ydm_zxbc on SRVS_PRESCRIPTION_MAIN (zxbc);

/*==============================================================*/
/* TABLE: 	SRVS_LABEL 药单瓶签表 			        */
/*==============================================================*/
create table SRVS_LABEL
(
  parent_no          VARCHAR2(24 CHAR) not null,
  zxbc               INTEGER not null,
  yyrq               DATE not null,
  ydzt               INTEGER,
  ydpq               VARCHAR2(60 CHAR),
  deptname           VARCHAR2(60 CHAR),
  category_code_list VARCHAR2(128 CHAR),
  inhospno           VARCHAR2(24 CHAR),
  patname            VARCHAR2(60 CHAR),
  sex                INTEGER default 0,
  case_id            VARCHAR2(24 CHAR),
  birthday           DATE,
  age                VARCHAR2(24 CHAR),
  ageunit            INTEGER,
  avdp               VARCHAR2(24 CHAR),
  sfysmc             VARCHAR2(32 CHAR),
  bedno              VARCHAR2(24 CHAR),
  zxrq               VARCHAR2(24 CHAR),
  zxsj               VARCHAR2(24 CHAR),
  pidsj              VARCHAR2(40 CHAR) not null,
  isrepeat           INTEGER default 0,
  deptcode           VARCHAR2(32 CHAR),
  pidrqzxbc          VARCHAR2(40 CHAR),
  yzlx               INTEGER,
  ydpq_last          VARCHAR2(2000 CHAR),
  zxbc_last          INTEGER,
  print_time         DATE,
  main_html          CLOB,
  maindrug_html      CLOB,
  menstruum_html     CLOB,
  signin             NUMBER,
  configurator       VARCHAR2(32 CHAR),
  checktype          VARCHAR2(1)
)tablespace pivas_tablespace;
-- Add comments to the table 
comment on table SRVS_LABEL
  is '药单瓶签表';
-- Add comments to the columns 
comment on column SRVS_LABEL.parent_no
  is '父医嘱编码或组编码';
comment on column SRVS_LABEL.zxbc
  is '执行批次，对应批次表的主键';
comment on column SRVS_LABEL.yyrq
  is '用药日期  YYYY/MM/DD';
comment on column SRVS_LABEL.ydzt
  is '药单状态步骤  0,执行  1,停止  2,撤销 3,未打印  4,已打印  5,入仓扫描核对完成  6,仓内扫描核对完成 7,出仓扫描核对完成  8,已签收 -1,退费';
comment on column SRVS_LABEL.ydpq
  is '药单瓶签的唯一编号，审核通过后自动生成，打印时写入二维码并显示在瓶签上';
comment on column SRVS_LABEL.deptname
  is '病区名称';
comment on column SRVS_LABEL.category_code_list
  is '分类瓶签编码列表';
comment on column SRVS_LABEL.inhospno
  is '住院流水号，病人唯一标识';
comment on column SRVS_LABEL.patname
  is '患者姓名';
comment on column SRVS_LABEL.sex
  is '性别：0女，1男，默认0';
comment on column SRVS_LABEL.case_id
  is '病人唯一住院号';
comment on column SRVS_LABEL.birthday
  is '病人出生日期';
comment on column SRVS_LABEL.age
  is '病人年龄';
comment on column SRVS_LABEL.ageunit
  is '年龄单位，0天 1月 2年';
comment on column SRVS_LABEL.avdp
  is '病人体重';
comment on column SRVS_LABEL.sfysmc
  is '医嘱审核药师名名称，如[1001,詹姆斯]';
comment on column SRVS_LABEL.bedno
  is '患者住院期间，所住床位对应的编号';
comment on column SRVS_LABEL.isrepeat
  is '是否重复打印 0否 1是';
comment on column SRVS_LABEL.deptcode
  is '病区(科室)编码';
comment on column SRVS_LABEL.print_time
  is '打印时间';
comment on column SRVS_LABEL.main_html
  is '瓶签首页';
comment on column SRVS_LABEL.menstruum_html
  is '溶媒页';
comment on column SRVS_LABEL.signin
  is '是否签收';
comment on column SRVS_LABEL.configurator
  is '配置人';
comment on column SRVS_LABEL.checktype
  is '扫描步骤0入仓，1出仓，2出仓';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_LABEL add constraint SRVS_LABEL_PK primary key (PIDSJ) USING INDEX tablespace pivas_index_tablespace;
create index SRVS_LABEL_ydpq on SRVS_LABEL (ydpq);
create index SRVS_LABEL_parent_no on SRVS_LABEL (parent_no);
alter table SRVS_LABEL add print_name VARCHAR2(60 CHAR);
comment on column SRVS_LABEL.print_name is '打印人员姓名';

alter table SRVS_LABEL add tfaccount VARCHAR2(32 CHAR);
-- Add comments to the columns 
comment on column SRVS_LABEL.tfaccount
  is '退费处理人';
  
/*==============================================================*/
/* TABLE: SRVS_PRESCRIPTION_SCAN药单扫描结果数据表					        */
/*==============================================================*/
CREATE TABLE SRVS_PRESCRIPTION_SCAN
(
	YD_ID				VARCHAR2(50) NOT NULL,
	SMRQ                DATE,
	SMJG                INTEGER,
	SMSBYY              VARCHAR2(256),
    SMLX                INTEGER,	
    OPERATOR 		    VARCHAR2(32), 
	YDPQ 		    	VARCHAR2(60), 
	RESERVE3 		    VARCHAR2(32),
	ZXBC                INTEGER
) tablespace pivas_tablespace;

COMMENT ON TABLE SRVS_PRESCRIPTION_SCAN IS '药单扫描结果数据表';

COMMENT ON COLUMN SRVS_PRESCRIPTION_SCAN.YD_ID IS '药单瓶签表的主键';

COMMENT ON COLUMN SRVS_PRESCRIPTION_SCAN.SMRQ IS '扫描日期  YYYY-MM-DD HH:MM:SS';

COMMENT ON COLUMN SRVS_PRESCRIPTION_SCAN.SMJG IS '扫描结果，0成功  1失败';

COMMENT ON COLUMN SRVS_PRESCRIPTION_SCAN.SMSBYY IS '扫描失败原因';

COMMENT ON COLUMN SRVS_PRESCRIPTION_SCAN.SMLX IS '扫描类型 0：入仓扫描  1：出仓扫描';

COMMENT ON COLUMN SRVS_PRESCRIPTION_SCAN.OPERATOR IS '操作人';

COMMENT ON COLUMN SRVS_PRESCRIPTION_SCAN.YDPQ IS '瓶签号';

COMMENT ON COLUMN SRVS_PRESCRIPTION_SCAN.RESERVE3 IS '备用字段3';

COMMENT ON COLUMN SRVS_PRESCRIPTION_SCAN.ZXBC IS '执行批次';

/*==============================================================*/
/* TABLE: SRVS_PRESCRIPTION_FEES药单配置费收取、退费数据表					        */
/*==============================================================*/
CREATE TABLE SRVS_PRESCRIPTION_FEES
(
  YD_PZF_ID           NUMBER(20, 0) NOT NULL,
	PQ_REF_FEE_ID				        NUMBER(20, 0) NOT NULL,
	PZFZT               INTEGER,
	PZFSBYY             VARCHAR2(256),
	PZFSQRQ             DATE,
	CONFIGFEERULEID     INTEGER,
	CZYMC               VARCHAR2(32),
  	GID                 INTEGER,	
  	RESERVE1 		    VARCHAR2(32), 
	RESERVE2 		    VARCHAR2(32), 
	RESERVE3 		    VARCHAR2(32),
	CASE_ID         VARCHAR2(24),
	COSTCODE        VARCHAR2(24),
	PIDRQZXBC VARCHAR2(40 CHAR),
	PRICE       VARCHAR2(24),
	AMOUNT       INTEGER,
	PC_ID        INTEGER,
	DEPTCODE	 VARCHAR2(32)		  
) tablespace pivas_tablespace;

COMMENT ON TABLE SRVS_PRESCRIPTION_FEES IS '药单配置费收取、退费数据表';

COMMENT ON COLUMN SRVS_PRESCRIPTION_FEES.YD_PZF_ID IS '药单配置费表主键，自增长';

COMMENT ON COLUMN SRVS_PRESCRIPTION_FEES.PQ_REF_FEE_ID IS '瓶签与配置费关系id';

COMMENT ON COLUMN SRVS_PRESCRIPTION_FEES.PZFZT IS '药单配置费收取、退费状态，0,配置费收取失败 1,配置费收费成功 2,配置费退费失败  3、配置费退费成功 ';

COMMENT ON COLUMN SRVS_PRESCRIPTION_FEES.PZFSBYY IS '药单配置费收取、退费失败原因';

COMMENT ON COLUMN SRVS_PRESCRIPTION_FEES.PZFSQRQ IS '药单配置费收取、退费时间  YYYY-MM-DD HH:MM:SS';

COMMENT ON COLUMN SRVS_PRESCRIPTION_FEES.CONFIGFEERULEID IS '对应配置费规则表的主键';

COMMENT ON COLUMN SRVS_PRESCRIPTION_FEES.GID IS '对应核对步骤表的主键';

COMMENT ON COLUMN SRVS_PRESCRIPTION_FEES.CZYMC IS '收费、退费操作员名称';

COMMENT ON COLUMN SRVS_PRESCRIPTION_FEES.RESERVE1 IS '备用字段1';

COMMENT ON COLUMN SRVS_PRESCRIPTION_FEES.RESERVE2 IS '备用字段2';

COMMENT ON COLUMN SRVS_PRESCRIPTION_FEES.RESERVE3 IS '备用字段3';

COMMENT ON COLUMN SRVS_PRESCRIPTION_FEES.CASE_ID IS '病人唯一住院号 ';

COMMENT ON COLUMN SRVS_PRESCRIPTION_FEES.COSTCODE is '配置费/材料费编码;对应配置费/材料费表中的costCode字段';

COMMENT ON COLUMN SRVS_PRESCRIPTION_FEES.PRICE is '价格';

COMMENT ON COLUMN SRVS_PRESCRIPTION_FEES.AMOUNT is '数量';

COMMENT ON COLUMN SRVS_PRESCRIPTION_FEES.PC_ID is '批次id';

COMMENT ON COLUMN SRVS_PRESCRIPTION_FEES.DEPTCODE is '病区编号';
/*==============================================================*/
/* TABLE: SRVS_TASK任务表                             */
/*==============================================================*/
CREATE TABLE SRVS_TASK
(
  TASK_ID         		NUMBER(20, 0) NOT NULL,
  TASK_NAME           	VARCHAR2(32)  NOT NULL,
  TASK_TYPE       		INTEGER NOT NULL,
  TASK_EXECUTE_MODE   	INTEGER,
  TASK_TASKPRIORITY     INTEGER,
  TASK_INTEVAL      	INTEGER,
  TASK_RETRYTIMES     	VARCHAR2(32 CHAR) not null,
  TASK_RETRYINTERVAL   	VARCHAR2(32 CHAR) not null,
  TASK_EXECUTE_TIME     DATE  NOT NULL,
  TASK_CREATE_TIME      DATE,
  TASK_UPDATE_TIME      DATE,
  TASK_CONTENT_TYPE     INTEGER NOT NULL,
  TASK_STATUS           INTEGER NOT NULL,
  TASK_REMARK           VARCHAR2(256),
  TASK_BEANCLASS      	VARCHAR2(256),
  RESERVE1          	VARCHAR2(32), 
  RESERVE2          	VARCHAR2(32), 
  RESERVE3          	VARCHAR2(32),
  CONSTRAINT SRVS_TASK_PK PRIMARY KEY(TASK_ID) USING INDEX tablespace pivas_index_tablespace
)tablespace pivas_tablespace;

COMMENT ON TABLE SRVS_TASK IS '任务表';

COMMENT ON COLUMN SRVS_TASK.TASK_ID IS '主键标识，任务创建成功后由数据中间件返回写入';

COMMENT ON COLUMN SRVS_TASK.TASK_NAME IS '任务名称，必须唯一';

COMMENT ON COLUMN SRVS_TASK.TASK_TYPE IS '任务类型，0,定时任务 1,一次性任务 ';

COMMENT ON COLUMN SRVS_TASK.TASK_EXECUTE_MODE IS '任务执行模式，仅选择定时任务时有效, 0,分钟 1,天';

COMMENT ON COLUMN SRVS_TASK.TASK_TASKPRIORITY IS '任务优先级';

COMMENT ON COLUMN SRVS_TASK.TASK_INTEVAL IS '任务执行间隔  仅选择定时任务时有效';

COMMENT ON COLUMN SRVS_TASK.TASK_RETRYTIMES IS '重试次数';

COMMENT ON COLUMN SRVS_TASK.TASK_RETRYINTERVAL IS '重试时间间隔';

COMMENT ON COLUMN SRVS_TASK.TASK_EXECUTE_TIME IS '任务执行时间  YYYY-MM-DD HH:MM:SS';

COMMENT ON COLUMN SRVS_TASK.TASK_CREATE_TIME IS '任务创建时间  YYYY-MM-DD HH:MM:SS';

COMMENT ON COLUMN SRVS_TASK.TASK_UPDATE_TIME IS '任务更新时间  YYYY-MM-DD HH:MM:SS';

COMMENT ON COLUMN SRVS_TASK.TASK_CONTENT_TYPE IS '任务执行内容类型  0病人、1医嘱、2药品字典、3病区信息、4医嘱频次、5用药途径';

COMMENT ON COLUMN SRVS_TASK.TASK_STATUS IS '任务状态  0,激活  1,去激活';

COMMENT ON COLUMN SRVS_TASK.TASK_REMARK IS '任务备注信息';

COMMENT ON COLUMN SRVS_TASK.TASK_BEANCLASS IS '任务执行类';

COMMENT ON COLUMN SRVS_TASK.RESERVE1 IS '备用字段1';

COMMENT ON COLUMN SRVS_TASK.RESERVE2 IS '备用字段2';

COMMENT ON COLUMN SRVS_TASK.RESERVE3 IS '备用字段3';

  
/*==============================================================*/
/* TABLE: SRVS_TASK_RESULT任务执行结果表                            */
/*==============================================================*/
CREATE TABLE SRVS_TASK_RESULT
(
  TASK_ID       		NUMBER(20, 0) NOT NULL,
  TASK_NAME         	VARCHAR2(32)  NOT NULL,
  TASK_TYPE     		INTEGER NOT NULL,
  TASK_RESULT       	INTEGER  NOT NULL,
  TASK_EXESTART_TIME   	DATE  NOT NULL, 
  TASK_EXESTOP_TIME   	DATE  NOT NULL, 
  TASK_CONTENT_TYPE   	INTEGER NOT NULL,
  RESERVE1        		VARCHAR2(32), 
  RESERVE2        		VARCHAR2(32), 
  RESERVE3        		VARCHAR2(32)
)tablespace pivas_tablespace;

COMMENT ON TABLE SRVS_TASK_RESULT IS '任务结果表';

COMMENT ON COLUMN SRVS_TASK_RESULT.TASK_ID IS '任务唯一标识';

COMMENT ON COLUMN SRVS_TASK_RESULT.TASK_NAME IS '任务名称';

COMMENT ON COLUMN SRVS_TASK_RESULT.TASK_TYPE IS '任务类型，0,定时任务 1,一次性任务 ';

COMMENT ON COLUMN SRVS_TASK_RESULT.TASK_RESULT IS '任务执行结果  0成功  1失败 ';

COMMENT ON COLUMN SRVS_TASK_RESULT.TASK_EXESTART_TIME IS '任务开始执行时间';

COMMENT ON COLUMN SRVS_TASK_RESULT.TASK_EXESTOP_TIME IS '任务执行结束时间';

COMMENT ON COLUMN SRVS_TASK_RESULT.TASK_CONTENT_TYPE IS '任务执行内容类型 0,病人、1,医嘱、2,病区、3,用药方式、4,医嘱频次、5,药品';

COMMENT ON COLUMN SRVS_TASK_RESULT.RESERVE1 IS '备用字段1';

COMMENT ON COLUMN SRVS_TASK_RESULT.RESERVE2 IS '备用字段2';

COMMENT ON COLUMN SRVS_TASK_RESULT.RESERVE3 IS '备用字段3';



ALTER TABLE "SYS_I18N"
   ADD CONSTRAINT FK_SYS_I18N_RELATIONS_SM_PRIVI FOREIGN KEY ("OBJECT_ID")
      REFERENCES "SYS_PRIVILEGE" ("PRIVILEGE_ID") ;

ALTER TABLE "SYS_USER_HISTORY"
   ADD CONSTRAINT FK_SYS_U_FK2_U_SYS_U FOREIGN KEY ("USER_ID")
      REFERENCES "SYS_USER" ("USER_ID") ;

ALTER TABLE "SYS_USER_LOGIN"
   ADD CONSTRAINT FK_SYS_U_FK_U_K_SYS_U FOREIGN KEY ("USER_ID")
      REFERENCES "SYS_USER" ("USER_ID") ;
      
-- 病区表
create table SRVS_INPATIENTAREA
(
  gid      NUMBER(20, 0) not null,
  deptcode VARCHAR2(32) not null,
  deptname VARCHAR2(60) not null,
  reserve0 VARCHAR2(100),
  reserve1 VARCHAR2(100),
  reserve2 VARCHAR2(100),
  ENABLED  VARCHAR2(8)
) tablespace pivas_tablespace;
-- Add comments to the table 
comment on table SRVS_INPATIENTAREA
  is '病区';
-- Add comments to the columns 
comment on column SRVS_INPATIENTAREA.gid
  is '主键，自增长';
comment on column SRVS_INPATIENTAREA.deptcode
  is '病区编码';
comment on column SRVS_INPATIENTAREA.deptname
  is '病区名称';
comment on column SRVS_INPATIENTAREA.reserve0
  is '预留字段0';
comment on column SRVS_INPATIENTAREA.reserve1
  is '预留字段1';
comment on column SRVS_INPATIENTAREA.reserve2
  is '预留字段2';
COMMENT ON COLUMN SRVS_INPATIENTAREA.ENABLED
  IS '是否启用，0:不启用,1启用';
  
alter table SRVS_INPATIENTAREA add deptaliasname VARCHAR2(32 CHAR);
-- Add comments to the columns 
comment on column SRVS_INPATIENTAREA.deptaliasname
  is '病区别名';
  
-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_INPATIENTAREA
  add constraint SRVS_INPATIENTAREA_GID primary key (GID) USING INDEX tablespace pivas_index_tablespace;
alter table SRVS_INPATIENTAREA
  add constraint SRVS_INPATIENTAREA_DEPTCODE unique (DEPTCODE) USING INDEX tablespace pivas_index_tablespace;

-- 病人表
create table SRVS_PATIENT
(
  gid      NUMBER(20) not null,
  inhospno VARCHAR2(32 CHAR) not null,
  patname  VARCHAR2(60 CHAR) not null,
  sex      INTEGER not null,
  wardcode VARCHAR2(32 CHAR),
  state    VARCHAR2(60 CHAR),
  bedno    VARCHAR2(32 CHAR),
  reserve0 VARCHAR2(100 CHAR),
  reserve1 VARCHAR2(100 CHAR),
  reserve2 VARCHAR2(100 CHAR),
  case_id  VARCHAR2(32 CHAR),
  birthday DATE,
  age      VARCHAR2(32 CHAR),
  ageunit  INTEGER,
  avdp     VARCHAR2(32 CHAR)
)
tablespace pivas_tablespace;
-- Add comments to the table 
comment on table SRVS_PATIENT
  is '病人表';
-- Add comments to the columns 
comment on column SRVS_PATIENT.gid
  is '主键，自增长';
comment on column SRVS_PATIENT.inhospno
  is '住院流水号，病人唯一标识';
comment on column SRVS_PATIENT.patname
  is '患者姓名';
comment on column SRVS_PATIENT.sex
  is '性别：0女，1男，默认0';
comment on column SRVS_PATIENT.wardcode
  is '病人当前病区(科室）对应病区表SRVS_INPATIENTAREA.deptCode';
comment on column SRVS_PATIENT.state
  is '病人当前状态';
comment on column SRVS_PATIENT.bedno
  is '患者住院期间，所住床位对应的编号';
comment on column SRVS_PATIENT.reserve0
  is '预留字段0';
comment on column SRVS_PATIENT.reserve1
  is '预留字段1';
comment on column SRVS_PATIENT.reserve2
  is '预留字段2';
comment on column SRVS_PATIENT.case_id
  is '病人唯一住院号';
comment on column SRVS_PATIENT.birthday
  is '病人出生日期';
comment on column SRVS_PATIENT.age
  is '病人年龄';
comment on column SRVS_PATIENT.ageunit
  is '年龄单位，0天 1月 2年';
comment on column SRVS_PATIENT.avdp
  is '病人体重';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_PATIENT
  add constraint SRVS_PATIENT_GID primary key (GID) USING INDEX tablespace pivas_index_tablespace;
alter table SRVS_PATIENT
  add constraint ISRVS_PATIENT_NHOSPNO unique (INHOSPNO) USING INDEX tablespace pivas_index_tablespace;
  
--用药途径表
create table SRVS_DRUGWAY
(
  gid         NUMBER(20, 0) not null,
  drugwayid   VARCHAR2(32) not null,
  drugwaycode VARCHAR2(32) not null,
  drugwayname VARCHAR2(60) not null,
  reserve0    VARCHAR2(100),
  reserve1    VARCHAR2(100),
  reserve2    VARCHAR2(100)
) tablespace pivas_tablespace;
-- Add comments to the table 
comment on table SRVS_DRUGWAY
  is '用药途径表';
-- Add comments to the columns 
comment on column SRVS_DRUGWAY.gid
  is '主键，自增长';
comment on column SRVS_DRUGWAY.drugwayid
  is '用药方法id';
comment on column SRVS_DRUGWAY.drugwaycode
  is '用药方法编码';
comment on column SRVS_DRUGWAY.drugwayname
  is '用药方法名字';
comment on column SRVS_DRUGWAY.reserve0
  is '预留字段0';
comment on column SRVS_DRUGWAY.reserve1
  is '预留字段1';
comment on column SRVS_DRUGWAY.reserve2
  is '预留字段2';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_DRUGWAY
  add constraint SRVS_DRUGWAY_GID primary key (GID) USING INDEX tablespace pivas_index_tablespace;
  
--配置费规则表
create table SRVS_CONFIGFEE_RULE
(
  gid          NUMBER(20, 0) not null,
  rulename     VARCHAR2(100) not null,
  drugtypecode VARCHAR2(32),
  drugcode     VARCHAR2(2000),
  volume       NUMBER(20, 0),
  reserve0     VARCHAR2(100),
  reserve1     VARCHAR2(100),
  reserve2     VARCHAR2(100)
) tablespace pivas_tablespace;
-- Add comments to the table 
comment on table SRVS_CONFIGFEE_RULE
  is '配置费规则表';
-- Add comments to the columns 
comment on column SRVS_CONFIGFEE_RULE.gid
  is '主键，支持自增长';
comment on column SRVS_CONFIGFEE_RULE.rulename
  is '规则名称';
comment on column SRVS_CONFIGFEE_RULE.drugtypecode
  is '药品分类编码，对应SRVS_MEDICAMENTS_CATEGORY表中id字段';
comment on column SRVS_CONFIGFEE_RULE.drugcode
  is '药品（可多选，用；分开）；对应药品表中的药品编码字段SRVS_MEDICAMENTS';
comment on column SRVS_CONFIGFEE_RULE.volume
  is '容积条件';
comment on column SRVS_CONFIGFEE_RULE.reserve0
  is '预留字段0';
comment on column SRVS_CONFIGFEE_RULE.reserve1
  is '预留字段1';
comment on column SRVS_CONFIGFEE_RULE.reserve2
  is '预留字段2';
alter table SRVS_CONFIGFEE_RULE
  add constraint SRVS_CONFIGFEE_RULE_GID primary key (GID) USING INDEX tablespace pivas_index_tablespace;
  
--配置费类别表 
CREATE TABLE "SRVS_CONFIG_FEETYPE"
(
   "GID"            NUMBER              NOT NULL,
   "TYPEDESC"       VARCHAR(50),
   "REMARK"         VARCHAR(50),
   CONSTRAINT SRVS_CONFIG_FEETYPE PRIMARY KEY ("GID")  USING INDEX tablespace pivas_index_tablespace
) tablespace pivas_tablespace;

COMMENT ON TABLE "SRVS_CONFIG_FEETYPE" IS
'配置费类别表';

COMMENT ON COLUMN "SRVS_CONFIG_FEETYPE"."GID" IS
'配置费类别主键，对应配置费CONFIGFEETYPE字段';

COMMENT ON COLUMN "SRVS_CONFIG_FEETYPE"."TYPEDESC" IS
'配置费类别描述';
  
COMMENT ON COLUMN "SRVS_CONFIG_FEETYPE"."REMARK" IS
'备注';
  
--配置费明细表
create table SRVS_CONFIGFEE_DETAIL
(
  detailcode NUMBER(20, 0) not null,
  costcode   VARCHAR2(32) not null,
  amount     VARCHAR2(64) not null,
  rate       NUMBER not null,
  reserve0   VARCHAR2(100),
  reserve1   VARCHAR2(100),
  reserve2   VARCHAR2(100)
) tablespace pivas_tablespace;
-- Add comments to the table 
comment on table SRVS_CONFIGFEE_DETAIL
  is '配置费明细';
-- Add comments to the columns 
comment on column SRVS_CONFIGFEE_DETAIL.detailcode
  is '配置费明细编码;对应配置规则表中的gid字段，detailCode 、costCode作为联合主键';
comment on column SRVS_CONFIGFEE_DETAIL.costcode
  is '配置费/材料费编码;对应配置费/材料费表中的costCode字段';
comment on column SRVS_CONFIGFEE_DETAIL.amount
  is '数量';
comment on column SRVS_CONFIGFEE_DETAIL.rate
  is '是否每天收取一次(0：是，1：否)';
comment on column SRVS_CONFIGFEE_DETAIL.reserve0
  is '预留字段0';
comment on column SRVS_CONFIGFEE_DETAIL.reserve1
  is '预留字段1';
comment on column SRVS_CONFIGFEE_DETAIL.reserve2
  is '预留字段2';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_CONFIGFEE_DETAIL
  add constraint SRVS_CONFIGFEE_DETAIL_KEY primary key (DETAILCODE, COSTCODE)
   USING INDEX tablespace pivas_index_tablespace 
  ;
  
--配置费/材料费
create table SRVS_CONFIG_FEE
(
  costcode      VARCHAR2(32) not null,
  costname      VARCHAR2(64) not null,
  price         VARCHAR2(32),
  reserve0      VARCHAR2(100),
  reserve1      VARCHAR2(100),
  reserve2      VARCHAR2(100),
  configfeetype NUMBER not null,
  gid           VARCHAR2(24) not null
) tablespace pivas_tablespace;
-- Add comments to the table 
comment on table SRVS_CONFIG_FEE
  is '配置费/材料费';
-- Add comments to the columns 
comment on column SRVS_CONFIG_FEE.costcode
  is '药品编码';
comment on column SRVS_CONFIG_FEE.costname
  is '名称';
comment on column SRVS_CONFIG_FEE.price
  is '价格';
comment on column SRVS_CONFIG_FEE.reserve0
  is '预留字段0';
comment on column SRVS_CONFIG_FEE.reserve1
  is '预留字段1';
comment on column SRVS_CONFIG_FEE.reserve2
  is '预留字段2';
comment on column SRVS_CONFIG_FEE.configfeetype
  is '配置费的类别，0:其他 1:抗肿瘤 2:营养液';
comment on column SRVS_CONFIG_FEE.gid
  is '主键，自增长';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_CONFIG_FEE
  add constraint SRVS_CONFIG_FEE_GID primary key (GID) USING INDEX tablespace pivas_index_tablespace;
alter table SRVS_CONFIG_FEE
  add constraint SRVS_CONFIG_FEE_COSTCODE unique (COSTCODE) USING INDEX tablespace pivas_index_tablespace;

--计量单位
create table SRVS_MEASUREMENT_UNIT
(
  gid      NUMBER(20, 0) not null,
  code     VARCHAR2(32) not null,
  unity    VARCHAR2(64) not null,
  protype  VARCHAR2(64) not null,
  isuse    NUMBER not null,
  model    NUMBER not null,
  precis   VARCHAR2(32),
  scale    VARCHAR2(32),
  reserve0 VARCHAR2(100),
  reserve1 VARCHAR2(100),
  reserve2 VARCHAR2(100)
) tablespace pivas_tablespace;
-- Add comments to the table 
comment on table SRVS_MEASUREMENT_UNIT
  is '计量单位';
-- Add comments to the columns 
comment on column SRVS_MEASUREMENT_UNIT.code
  is '编码';
comment on column SRVS_MEASUREMENT_UNIT.unity
  is '计量单位';
comment on column SRVS_MEASUREMENT_UNIT.protype
  is '产品类别';
comment on column SRVS_MEASUREMENT_UNIT.isuse
  is '是否可用（0：可用，1：不可用）';
comment on column SRVS_MEASUREMENT_UNIT.model
  is '类型';
comment on column SRVS_MEASUREMENT_UNIT.precis
  is '舍入精度';
comment on column SRVS_MEASUREMENT_UNIT.scale
  is '比例';
comment on column SRVS_MEASUREMENT_UNIT.reserve0
  is '预留字段0';
comment on column SRVS_MEASUREMENT_UNIT.reserve1
  is '预留字段1';
comment on column SRVS_MEASUREMENT_UNIT.reserve2
  is '预留字段2';
comment on column SRVS_MEASUREMENT_UNIT.gid
  is '主键，自增长';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_MEASUREMENT_UNIT
  add constraint SRVS_MEASUREMENT_UNIT_GID primary key (GID) USING INDEX tablespace pivas_index_tablespace;
alter table SRVS_MEASUREMENT_UNIT
  add constraint SRVS_MEASUREMENT_UNIT_CODE unique (CODE) USING INDEX tablespace pivas_index_tablespace;
  
--核对类型
create table SRVS_CHECKTYPE
(
  orderid   NUMBER(20, 0) not null,
  checkname VARCHAR2(64) not null,
  isshow    NUMBER not null,
  ischarge  NUMBER not null,
  iseffect  NUMBER not null,
  reserve0  VARCHAR2(100),
  reserve1  VARCHAR2(100),
  reserve2  VARCHAR2(100),
  gid       VARCHAR2(32) not null,
  checktype NUMBER not null
) tablespace pivas_tablespace;
-- Add comments to the table 
comment on table SRVS_CHECKTYPE
  is '核对类型';
-- Add comments to the columns 
comment on column SRVS_CHECKTYPE.orderid
  is '顺序id';
comment on column SRVS_CHECKTYPE.checkname
  is '核对步骤名';
comment on column SRVS_CHECKTYPE.isshow
  is '是否默认显示所有的瓶签（0：显示，1：否）默认0';
comment on column SRVS_CHECKTYPE.ischarge
  is '是否收取配置费（0：是，1：否）默认0';
comment on column SRVS_CHECKTYPE.iseffect
  is '是否生效等字段信息（0：是，1：否）默认0';
comment on column SRVS_CHECKTYPE.reserve0
  is '预留字段0';
comment on column SRVS_CHECKTYPE.reserve1
  is '预留字段1';
comment on column SRVS_CHECKTYPE.reserve2
  is '预留字段2';
comment on column SRVS_CHECKTYPE.gid
  is '主键';
comment on column SRVS_CHECKTYPE.checktype
  is '核对类型  0入仓核对  2出仓核对 1 仓内扫描';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_CHECKTYPE
  add constraint SRVS_CHECKTYPE_GID primary key (GID) USING INDEX tablespace pivas_index_tablespace;
  
alter table SRVS_CHECKTYPE add isstock NUMBER default 1 not null ;
comment on column SRVS_CHECKTYPE.isstock
  is '是否减少库存 0是1否';
--审方错误类型表
create table SRVS_TRIAL_ERRORTYPE
(
  gid        NUMBER(20, 0) not null,
  trialname  VARCHAR2(64) not null,
  triallevel VARCHAR2(64) not null,
  reserve0   VARCHAR2(100),
  reserve1   VARCHAR2(100),
  reserve2   VARCHAR2(100),
  trialcolor   VARCHAR2(100)
) tablespace pivas_tablespace;
-- Add comments to the table 
comment on table SRVS_TRIAL_ERRORTYPE
  is '审方错误类型';
-- Add comments to the columns 
comment on column SRVS_TRIAL_ERRORTYPE.gid
  is '主键';
comment on column SRVS_TRIAL_ERRORTYPE.trialname
  is '名称';
comment on column SRVS_TRIAL_ERRORTYPE.triallevel
  is '等级';
comment on column SRVS_TRIAL_ERRORTYPE.reserve0
  is '预留字段0';
comment on column SRVS_TRIAL_ERRORTYPE.reserve1
  is '预留字段1';
comment on column SRVS_TRIAL_ERRORTYPE.reserve2
  is '预留字段2';
comment on column SRVS_TRIAL_ERRORTYPE.trialcolor
  is '颜色';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_TRIAL_ERRORTYPE
  add constraint PIVAS_ERRORTYPE_GID primary key (GID) USING INDEX tablespace pivas_index_tablespace;
alter table SRVS_TRIAL_ERRORTYPE
  add constraint TPIVAS_ERRORTYPE_RIALNAME unique (TRIALNAME) USING INDEX tablespace pivas_index_tablespace;
  
--瓶签与配置费的关系
create table SRVS_LABEL_REF_CONFIG_FEE
(
  gid        NUMBER(20, 0) not null,
  PIDSJ		 VARCHAR2(40 CHAR),
  FEE_RESULT VARCHAR2(1)
) tablespace pivas_tablespace;
-- Add comments to the table 
comment on table SRVS_LABEL_REF_CONFIG_FEE
  is '瓶签与配置费的关系';
-- Add comments to the columns 
comment on column SRVS_LABEL_REF_CONFIG_FEE.gid
  is '主键';
comment on column SRVS_LABEL_REF_CONFIG_FEE.FEE_RESULT
  is '计费结果 1:成功，0：失败';
alter table SRVS_LABEL_REF_CONFIG_FEE add isstock NUMBER default 1 not null ;
comment on column SRVS_LABEL_REF_CONFIG_FEE.isstock
  is '是否减少库存 0是1否';
  
-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_LABEL_REF_CONFIG_FEE
  add constraint SRVS_LABEL_REF_CONFIG_FEE_GID primary key (GID) USING INDEX tablespace pivas_index_tablespace;
alter table SRVS_LABEL_REF_CONFIG_FEE add fee_result_details VARCHAR2(20 CHAR);
alter table SRVS_LABEL_REF_CONFIG_FEE add sqrq_details       VARCHAR2(100 CHAR);
  
comment on column SRVS_LABEL_REF_CONFIG_FEE.fee_result_details
  is '计算详细结果 0,配置费收取失败 1,配置费收费成功 2,配置费退费失败  3、配置费退费成功 ';
comment on column SRVS_LABEL_REF_CONFIG_FEE.sqrq_details
  is '收费日期详细信息';
  
--员工信息表
create table SRVS_EMPLOYEEINFO
(
  gid        VARCHAR2(12) not null,
  staff_code VARCHAR2(24),
  staff_name VARCHAR2(64)
)
tablespace pivas_tablespace;
-- Add comments to the table 
comment on table SRVS_EMPLOYEEINFO
  is '员工表';
-- Add comments to the columns 
comment on column SRVS_EMPLOYEEINFO.gid
  is '主键ID，自增长';
comment on column SRVS_EMPLOYEEINFO.staff_code
  is '员工工号';
comment on column SRVS_EMPLOYEEINFO.staff_name
  is '员工姓名';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_EMPLOYEEINFO
  add constraint SRVS_EMPLOYEEINFO_GID primary key (GID) USING INDEX tablespace pivas_index_tablespace;

create table SRVS_OPR_LOG
(
  logtype  VARCHAR2(50),
  oprtime  DATE,
  userid   NUMBER(20),
  username VARCHAR2(50),
  tabid    NUMBER(20),
  tabid2   VARCHAR2(50),
  oldvalue VARCHAR2(300),
  newvalue VARCHAR2(300)
) tablespace pivas_tablespace;

comment on column SRVS_OPR_LOG.logtype
  is '操作类型：审方yzcheck，排批次pcchange';
comment on column SRVS_OPR_LOG.oprtime
  is '操作时间';
comment on column SRVS_OPR_LOG.userid
  is '操作的用户ID';
comment on column SRVS_OPR_LOG.username
  is '操作的用户名';
comment on column SRVS_OPR_LOG.tabid
  is '主键ID';
comment on column SRVS_OPR_LOG.tabid2
  is '备用主键';
comment on column SRVS_OPR_LOG.oldvalue
  is '原值[医嘱：医嘱审核状态][药单：批次ID]';
comment on column SRVS_OPR_LOG.newvalue
  is '新值[医嘱：医嘱审核状态][药单：批次ID]';

-- 药品修改记录
create table SRVS_MEDICAMENT_LOG
(
  id            VARCHAR2(32) not null,
  drugcode      VARCHAR2(32),
  drugname      VARCHAR2(256),
  drugplacecode VARCHAR2(32),
  drugplace     VARCHAR2(32),
  checkcode     VARCHAR2(32),
  operator      VARCHAR2(32) not null,
  updatedate    DATE default sysdate,
  stock_last    VARCHAR2(32),
  stock_now     VARCHAR2(32),
  categoryid    VARCHAR2(32),
  categoryname  VARCHAR2(32)
);
-- Add comments to the columns 
comment on column SRVS_MEDICAMENT_LOG.id
  is '主键id';
comment on column SRVS_MEDICAMENT_LOG.drugcode
  is '药品编码';
comment on column SRVS_MEDICAMENT_LOG.drugname
  is '药品名称';
comment on column SRVS_MEDICAMENT_LOG.drugplacecode
  is '药品产地编码';
comment on column SRVS_MEDICAMENT_LOG.drugplace
  is '药品产地';
comment on column SRVS_MEDICAMENT_LOG.checkcode
  is '药品速查码';
comment on column SRVS_MEDICAMENT_LOG.operator
  is '修改人';
comment on column SRVS_MEDICAMENT_LOG.updatedate
  is '修改时间';
comment on column SRVS_MEDICAMENT_LOG.stock_last
  is '上次药品库存量';
comment on column SRVS_MEDICAMENT_LOG.stock_now
  is '当前药品库存量';
comment on column SRVS_MEDICAMENT_LOG.categoryid
  is '药品分类编码';
comment on column SRVS_MEDICAMENT_LOG.categoryname
  is '药品分类名称';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_MEDICAMENT_LOG
  add constraint SRVS_MEDICAMENT_LOG_ID primary key (ID) USING INDEX tablespace pivas_index_tablespace;
  
  
--bianxw 优先级规则 配置表
create table SRVS_PRIORITY_RULES
(
  pr_id      NUMBER(20) not null,
  pr_type    INTEGER not null,
  deptcode   VARCHAR2(32 CHAR) not null,
  medic_id   VARCHAR2(32 CHAR) not null,
  batch_id   NUMBER(20),
  enabled    INTEGER default 1 not null,
  pr_order   INTEGER not null,
  pr_user_id NUMBER(20) not null,
  pr_time    DATE not null,
  medic_type INTEGER not null
)tablespace pivas_tablespace;

comment on table SRVS_PRIORITY_RULES
  is '优先级规则表';

comment on column SRVS_PRIORITY_RULES.pr_id
  is '优先级主键';
comment on column SRVS_PRIORITY_RULES.pr_type
  is '类型：11药物 高优先级规则，强制规则31';
comment on column SRVS_PRIORITY_RULES.deptcode
  is '病区code';
comment on column SRVS_PRIORITY_RULES.medic_id
  is '药品编码';
comment on column SRVS_PRIORITY_RULES.batch_id
  is '批次ID';
comment on column SRVS_PRIORITY_RULES.enabled
  is '是否启用0不启用，1启用';
comment on column SRVS_PRIORITY_RULES.pr_order
  is '排序序号';
comment on column SRVS_PRIORITY_RULES.pr_user_id
  is '更新人id';
comment on column SRVS_PRIORITY_RULES.pr_time
  is '更新时间';
 comment on column SRVS_PRIORITY_RULES.medic_type
  is '药品区分：1为药品 2为医药类型';

alter table SRVS_PRIORITY_RULES
  add constraint PK_PRIORITY_RULES primary key (PR_ID)
   USING INDEX tablespace pivas_index_tablespace ;

--bianxw 容积规则 配置表
create table SRVS_VOLUME_RULES
(
  vr_id      NUMBER(20) not null,
  vr_type    INTEGER not null,
  deptcode   VARCHAR2(32 CHAR) not null,
  batch_id   NUMBER(20) not null,
  overrun	 NUMBER(20),
  maxval	 NUMBER(20),
  minval	 NUMBER(20),
  vr_user_id NUMBER(20) not null,
  vr_time    DATE not null
)tablespace pivas_tablespace;

comment on table SRVS_VOLUME_RULES
  is '容积规则表';

comment on column SRVS_VOLUME_RULES.vr_id
  is '主键';
comment on column SRVS_VOLUME_RULES.vr_type
  is '类型：21容积规则';
comment on column SRVS_VOLUME_RULES.deptcode
  is '病区code';
comment on column SRVS_VOLUME_RULES.batch_id
  is '批次ID';
comment on column SRVS_VOLUME_RULES.overrun
  is '超限值';
comment on column SRVS_VOLUME_RULES.maxval
  is '上线';
comment on column SRVS_VOLUME_RULES.minval
  is '下限';
comment on column SRVS_VOLUME_RULES.vr_user_id
  is '更新人id';
comment on column SRVS_VOLUME_RULES.vr_time
  is '更新时间';

alter table SRVS_VOLUME_RULES
  add constraint PK_SRVS_VOLUME_RULES primary key (vr_id)
   USING INDEX tablespace pivas_index_tablespace ;

create table SRVS_OTHER_RULE(
  OR_ID 	NUMBER(20) NOT NULL,
  OR_TYPE   INTEGER NOT NULL,
  OR_NAME	VARCHAR2(50),
  OR_DESC   VARCHAR2(200),
  OR_SORT   INTEGER,
  ENABLED    INTEGER DEFAULT 1 NOT NULL 
)tablespace pivas_tablespace;

alter table SRVS_OTHER_RULE
  add constraint PK_SRVS_OTHER_RULE primary key (OR_ID)
   USING INDEX tablespace pivas_index_tablespace ;
  
comment on table SRVS_OTHER_RULE
  is '其他规则表';

comment on column SRVS_OTHER_RULE.OR_ID
  is '主键';
comment on column SRVS_OTHER_RULE.OR_TYPE
  is '类型：41其他规则';
comment on column SRVS_OTHER_RULE.OR_NAME
  is '规则名称';
comment on column SRVS_OTHER_RULE.OR_DESC
  is '规则描述';
comment on column SRVS_OTHER_RULE.OR_SORT
  is '排序';
comment on column SRVS_OTHER_RULE.ENABLED
  is '是否启用1启用 0不启用';

--自我知识库数据分析结果
create table srvs_autocheckdata
(
  key    VARCHAR2(1024) not null,
  result INTEGER not null,
  count  INTEGER default 0 not null
)tablespace pivas_tablespace;
-- Add comments to the table 
comment on table srvs_autocheckdata
  is '自我审方知识库';
-- Add comments to the columns 
comment on column srvs_autocheckdata.key
  is '自我知识库，key值';
comment on column srvs_autocheckdata.result
  is '历史数据中审核结果，0：通过，1：不通过,-1：结果有冲突';
comment on column srvs_autocheckdata.count
  is '数据出现次数';
-- Create/Recreate primary, unique and foreign key constraints 
alter table srvs_autocheckdata
  add constraint srvs_autocheckdata_KEY primary key (KEY) USING INDEX tablespace pivas_index_tablespace;
-- Add/modify columns 
alter table srvs_autocheckdata add oprdate date default sysdate not null;
-- Add comments to the columns 
comment on column srvs_autocheckdata.oprdate
  is '操作时间';

-- 医嘱审方结果
create table SRVS_DOCTOR_ADVICE_CHECKRESULT
(
  parent_no   VARCHAR2(32 CHAR),
  ward_code   VARCHAR2(32 CHAR),
  wardname    VARCHAR2(32 CHAR),
  doctor      VARCHAR2(32 CHAR),
  doctor_name VARCHAR2(32 CHAR),
  yzshbtglx   INTEGER,
  sfysmc      VARCHAR2(32 CHAR),
  sfyscode    VARCHAR2(32 CHAR),
  yzshzt      INTEGER,
  sfrq        DATE,
  druginfo    VARCHAR2(512 CHAR),
  dianp       VARCHAR2(1024 CHAR)
)
tablespace PIVAS_TABLESPACE;
-- Add comments to the table 
comment on table SRVS_DOCTOR_ADVICE_CHECKRESULT
  is '医嘱审方结果';
-- Add comments to the columns 
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.parent_no
  is '父医嘱编码或组编码';
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.ward_code
  is '病区(科室)编码';
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.wardname
  is '病区(科室)名称';
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.doctor
  is '开嘱医生的工号。';
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.doctor_name
  is '开嘱医生姓名。';
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.yzshbtglx
  is '审方问题，关联审方错误类型表';
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.sfysmc
  is '医嘱审核药师名名称，如[詹姆斯]';
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.sfyscode
  is '医嘱审核药师名code，如[1001]';
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.yzshzt
  is 'HIS医嘱审核状态。0：未审核 1：审核通过 2：审核不通过';
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.sfrq
  is '医嘱审核时间 YYYY-MM-DD HH:MM:SS';
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.druginfo
  is '药品信息';
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.dianp
  is '点评';
-- Add/modify columns 
alter table SRVS_DOCTOR_ADVICE_CHECKRESULT add id varchar2(32);
alter table SRVS_DOCTOR_ADVICE_CHECKRESULT add supply_code VARCHAR2(400 CHAR);
alter table SRVS_DOCTOR_ADVICE_CHECKRESULT add dose VARCHAR2(400 CHAR);
alter table SRVS_DOCTOR_ADVICE_CHECKRESULT add dose_unit VARCHAR2(400 CHAR);
alter table SRVS_DOCTOR_ADVICE_CHECKRESULT add quantity VARCHAR2(400 CHAR);
alter table SRVS_DOCTOR_ADVICE_CHECKRESULT add freq_code VARCHAR2(32 CHAR);
alter table SRVS_DOCTOR_ADVICE_CHECKRESULT add transfusion VARCHAR2(64 CHAR);
alter table SRVS_DOCTOR_ADVICE_CHECKRESULT add dropspeed VARCHAR2(64 CHAR);
alter table SRVS_DOCTOR_ADVICE_CHECKRESULT add sex INTEGER;
alter table SRVS_DOCTOR_ADVICE_CHECKRESULT add age VARCHAR2(24 CHAR);
alter table SRVS_DOCTOR_ADVICE_CHECKRESULT add ageunit INTEGER;
alter table SRVS_DOCTOR_ADVICE_CHECKRESULT add avdp VARCHAR2(24 CHAR);
-- Add comments to the columns 
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.id
  is 'id';
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.supply_code
  is '医嘱的用药途径。如: 静推; IVGTT等。';
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.dose
  is '医嘱的药品单次剂量。';
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.dose_unit
  is '医嘱的药品单次剂量单位。';
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.quantity
  is '药品数量';
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.freq_code
  is '医嘱的执行频率(或用法)。如: 每日一次; BID; Q12H等.';
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.transfusion
  is '输液量ML';
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.dropspeed
  is '医嘱滴速100ML/MIN';
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.sex
  is '性别：0女，1男，默认0';
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.age
  is '病人年龄';
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.ageunit
  is '年龄单位，0天 1月 2年';
comment on column SRVS_DOCTOR_ADVICE_CHECKRESULT.avdp
  is '病人体重';
alter table SRVS_DOCTOR_ADVICE_CHECKRESULT add constraint PK_CHECK_RESULT_ID primary key (ID) using index tablespace PIVAS_TABLESPACE;
  
-- 病区管理批次修改记录表
create table srvs_batch_LOG
(
  pidsj         VARCHAR2(40) not null,
  content       VARCHAR2(200) not null,
  updatetime    VARCHAR2(20) not null,
  updateaccount VARCHAR2(32) not null
)
tablespace PIVAS_TABLESPACE;
-- Add comments to the columns 
comment on column srvs_batch_LOG.pidsj
  is '药单id';
comment on column srvs_batch_LOG.content
  is '描述';
comment on column srvs_batch_LOG.updatetime
  is '更新时间';
comment on column srvs_batch_LOG.updateaccount
  is '更新账户';  

  
--APP登录记录表  
create table "SYS_APP_USER_LOGIN"
(
  "ID"         INTEGER not null,
  user_id    INTEGER,
  login_date DATE,
  ip_addr    VARCHAR2(32 CHAR),
  port       INTEGER,
  constraint PK_SYS_APP_USER_LOGIN primary key (ID) USING INDEX  tablespace pivas_index_tablespace , 
  constraint FK_SM_APP_USER foreign key (USER_ID) references SYS_USER (USER_ID) 
)
tablespace pivas_tablespace;
comment on table SYS_APP_USER_LOGIN
  is 'APP登录表';
-- Add comments to the columns 
comment on column SYS_APP_USER_LOGIN.id
  is '主键';
comment on column SYS_APP_USER_LOGIN.user_id
  is 'USERID对应SYS_USER表中对应字段';
comment on column SYS_APP_USER_LOGIN.login_date
  is '最后登录时间';
comment on column SYS_APP_USER_LOGIN.ip_addr
  is 'IP';
comment on column SYS_APP_USER_LOGIN.port
  is '端口';
  
  
--结点表 
create table "SYS_SERVER_NODE"
(
  "ID"    INTEGER not null,
  IP      VARCHAR2(32 CHAR),
  NAME    VARCHAR2(32 CHAR),
  FLAG    INTEGER,
  PORT    VARCHAR2(32 CHAR),
  DOMAINNAME  VARCHAR2(100 CHAR),
  constraint SYS_SERVER_NODE primary key (ID) USING INDEX  tablespace pivas_index_tablespace  
)tablespace pivas_tablespace;
comment on table SYS_SERVER_NODE
  is '服务器登录结点';
-- Add comments to the columns 
comment on column SYS_SERVER_NODE.id
  is '主键';
comment on column SYS_SERVER_NODE.IP
  is 'IP地址';
comment on column SYS_SERVER_NODE.NAME
  is '机器名';
comment on column SYS_SERVER_NODE.FLAG
  is '主备标识 0主 1备';
comment on column SYS_SERVER_NODE.PORT
  is 'PIVAS/DEM/MQ端口号，以,相隔';
comment on column SYS_SERVER_NODE.DOMAINNAME
  is 'PIVAS/DEM/MQ前缀名，以,相隔';
  
create table SRVS_GL_NURSE_AREA
(
  user_id  INTEGER,
  account  VARCHAR2(32) not null,
  deptcode VARCHAR2(32) not null,
  gl_date  DATE
)tablespace pivas_tablespace;

alter table SRVS_GL_NURSE_AREA
  add constraint PK_SRVS_GL_NURSE_AREA primary key (ACCOUNT, DEPTCODE)
  USING INDEX tablespace pivas_index_tablespace ;  

comment on table SRVS_GL_NURSE_AREA
  is '护士与地区关联表';

comment on column SRVS_GL_NURSE_AREA.user_id
  is '用户id';
comment on column SRVS_GL_NURSE_AREA.account
  is '用户账号';
comment on column SRVS_GL_NURSE_AREA.deptcode
  is '病区编码';
comment on column SRVS_GL_NURSE_AREA.gl_date
  is '关联时间';
  
-- 配置费收费任务表
create table SRVS_CONFIGFEE_TASK
(
  yd_pidsj   VARCHAR2(32) not null,
  costcode   VARCHAR2(32) not null,
  caseid     VARCHAR2(32),
  account    VARCHAR2(32),
  amount     VARCHAR2(32),
  result     INTEGER,
  adddate    DATE default sysdate,
  resultdesc VARCHAR2(64)
)tablespace pivas_tablespace;
-- Add comments to the table 
comment on table SRVS_CONFIGFEE_TASK
  is '配置费收费任务表';
-- Add comments to the columns 
comment on column SRVS_CONFIGFEE_TASK.yd_pidsj
  is '对应SRVS_PRESCRIPTION_main中pidsj，唯一';
comment on column SRVS_CONFIGFEE_TASK.costcode
  is '配置费/材料费 编码';
comment on column SRVS_CONFIGFEE_TASK.caseid
  is '住院流水号';
comment on column SRVS_CONFIGFEE_TASK.account
  is '收费人工号';
comment on column SRVS_CONFIGFEE_TASK.amount
  is '费用';
comment on column SRVS_CONFIGFEE_TASK.result
  is '收费结果 收费结果 1:成功  -1:失败 0:调用配置费收费失败';
comment on column SRVS_CONFIGFEE_TASK.adddate
  is '数据增加时间';
comment on column SRVS_CONFIGFEE_TASK.resultdesc
  is '错误原因';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_CONFIGFEE_TASK
  add constraint CONFIG_TASK_PIDSJ primary key (YD_PIDSJ, COSTCODE) USING INDEX tablespace pivas_index_tablespace;

-- 配置费收费结果表  
create table SRVS_CONFIGFEE_RESULT as select * from SRVS_CONFIGFEE_TASK where 1=2;
-- Add comments to the table 
comment on table SRVS_CONFIGFEE_RESULT
  is '配置费收费结果表';
-- Add comments to the columns 
comment on column SRVS_CONFIGFEE_RESULT.yd_pidsj
  is '对应SRVS_PRESCRIPTION_main中pidsj，唯一';
comment on column SRVS_CONFIGFEE_RESULT.costcode
  is '配置费/材料费 编码';
comment on column SRVS_CONFIGFEE_RESULT.caseid
  is '住院流水号';
comment on column SRVS_CONFIGFEE_RESULT.account
  is '收费人工号';
comment on column SRVS_CONFIGFEE_RESULT.amount
  is '费用';
comment on column SRVS_CONFIGFEE_RESULT.result
  is '收费结果 收费结果 1:成功  -1:失败 0:调用配置费收费失败';
comment on column SRVS_CONFIGFEE_RESULT.adddate
  is '数据增加时间';
comment on column SRVS_CONFIGFEE_RESULT.resultdesc
  is '错误原因';
  
--增加医嘱表首日用药次数
alter table SRVS_DOCTOR_ADVICE add firstusecount INTEGER;
comment on column SRVS_DOCTOR_ADVICE.firstusecount
  is '首日用药次数';
alter table SRVS_DOCTOR_ADVICE_MAIN add firstusecount INTEGER;
comment on column SRVS_DOCTOR_ADVICE_MAIN.firstusecount
  is '首日用药次数';
  
-- 护士医嘱审核时间
alter table SRVS_DOCTOR_ADVICE add check_date VARCHAR2(32 CHAR);
comment on column SRVS_DOCTOR_ADVICE.confirm_date
  is '医生医嘱确认时间';
comment on column SRVS_DOCTOR_ADVICE.check_date
  is '护士医嘱审核时间';
  
alter table SRVS_DOCTOR_ADVICE_MAIN add check_date VARCHAR2(32 CHAR);
comment on column SRVS_DOCTOR_ADVICE_MAIN.confirm_date
  is '医生医嘱确认时间';
comment on column SRVS_DOCTOR_ADVICE_MAIN.check_date
  is '护士医嘱审核时间';
  
-- 病人信息增加预出院
alter table SRVS_PATIENT add hosouttime VARCHAR2(24 CHAR);
comment on column SRVS_PATIENT.hosouttime
  is '预出院时间';
  
-- 药单执行记录
create table SRVS_PRESCRIPTION_RECORD
(
  id           VARCHAR2(32) not null,
  recipeid     VARCHAR2(32),
  groupno      VARCHAR2(32),
  druglistid   VARCHAR2(32),
  drugfreq     VARCHAR2(32),
  drugcode     VARCHAR2(32),
  drugname     VARCHAR2(512),
  quantity     VARCHAR2(32),
  quantityunit VARCHAR2(32),
  schedule     VARCHAR2(32),
  occdt        VARCHAR2(32),
  chargedt     VARCHAR2(32),
  infusiondate VARCHAR2(32),
  labelno      VARCHAR2(32),
  begindt      VARCHAR2(32),
  enddt        VARCHAR2(32),
  syndate      DATE default sysdate not null
)
tablespace PIVAS_TABLESPACE;
-- Add comments to the table 
comment on table SRVS_PRESCRIPTION_RECORD
  is '药单执行记录';
-- Add comments to the columns 
comment on column SRVS_PRESCRIPTION_RECORD.recipeid
  is '医嘱编码，HIS产生的药单中的执行医嘱的编码';
comment on column SRVS_PRESCRIPTION_RECORD.groupno
  is '组号';
comment on column SRVS_PRESCRIPTION_RECORD.druglistid
  is '药单号，医嘱执行单的编号';
comment on column SRVS_PRESCRIPTION_RECORD.drugfreq
  is '用药频次';
comment on column SRVS_PRESCRIPTION_RECORD.drugcode
  is '药品编码';
comment on column SRVS_PRESCRIPTION_RECORD.drugname
  is '药品名称';
comment on column SRVS_PRESCRIPTION_RECORD.quantity
  is '药品数量';
comment on column SRVS_PRESCRIPTION_RECORD.quantityunit
  is '数量单位';
comment on column SRVS_PRESCRIPTION_RECORD.schedule
  is '执行序号，例如一个BID的医嘱，药单要生成两个，这个序号为1和2。如HIS无此值，可为空。或选用HIS 药单唯一标示编码';
comment on column SRVS_PRESCRIPTION_RECORD.occdt
  is '用药时间，可仅筛选出今明两日的。';
comment on column SRVS_PRESCRIPTION_RECORD.chargedt
  is '收费时间';
comment on column SRVS_PRESCRIPTION_RECORD.infusiondate
  is '输液时间';
comment on column SRVS_PRESCRIPTION_RECORD.labelno
  is '瓶签号/或药单唯一号';
comment on column SRVS_PRESCRIPTION_RECORD.begindt
  is '药单起始时间';
comment on column SRVS_PRESCRIPTION_RECORD.enddt
  is '药单终止时间';
comment on column SRVS_PRESCRIPTION_RECORD.syndate
  is '同步时间';
-- Create/Recreate indexes 
create index PK_YDRECORD_DRUGLISTID on SRVS_PRESCRIPTION_RECORD (DRUGLISTID)
  tablespace PIVAS_TABLESPACE;
create index PK_YDRECORD_GROUPNO on SRVS_PRESCRIPTION_RECORD (GROUPNO)
  tablespace PIVAS_TABLESPACE;
-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_PRESCRIPTION_RECORD
  add constraint PK_YDRECORD_ID primary key (ID)  using index  tablespace PIVAS_TABLESPACE;
-- Add/modify columns 
alter table SRVS_PRESCRIPTION_RECORD add batch_id VARCHAR2(32);
alter table SRVS_PRESCRIPTION_RECORD add amount VARCHAR2(3);
-- Add comments to the columns 
comment on column SRVS_PRESCRIPTION_RECORD.batch_id
  is '对应srvs_batch中ID';
comment on column SRVS_PRESCRIPTION_RECORD.amount
  is '用药次数';
alter table SRVS_PRESCRIPTION_RECORD add state INTEGER;
comment on column SRVS_PRESCRIPTION_RECORD.state
  is '药单执行记录状态 0：正常  1：停止  2：退费';
-- Add/modify columns 
alter table SRVS_PRESCRIPTION_RECORD add drugeddate VARCHAR2(32 CHAR);
-- Add comments to the columns 
comment on column SRVS_PRESCRIPTION_RECORD.drugeddate
  is '摆药时间';
  -- Add/modify columns 
alter table SRVS_PRESCRIPTION_RECORD add bedno VARCHAR2(32 CHAR);
alter table SRVS_PRESCRIPTION_RECORD add patname VARCHAR2(32 CHAR);
-- Add comments to the columns 
comment on column SRVS_PRESCRIPTION_RECORD.bedno
  is '床号';
comment on column SRVS_PRESCRIPTION_RECORD.patname
  is '病人姓名';

--创建病区分组
create table SRVS_GROUP
(
  id       NUMBER not null,
  deptcode VARCHAR2(32) not null,
  deptname VARCHAR2(60) not null,
  parentid NUMBER,
  enabled  VARCHAR2(1),
  state    NUMBER,
  levelnum NUMBER,
  ordernum NUMBER
)
tablespace PIVAS_TABLESPACE;
-- Add comments to the table 
comment on table SRVS_GROUP
  is '病区分组';
-- Add comments to the columns 
comment on column SRVS_GROUP.id
  is '主键';
comment on column SRVS_GROUP.deptcode
  is '病区编码';
comment on column SRVS_GROUP.deptname
  is '病区名称';
comment on column SRVS_GROUP.parentid
  is '父节点';
comment on column SRVS_GROUP.enabled
  is '是否启用,0:不启用,1:启用';
comment on column SRVS_GROUP.state
  is '是否自定义病区,0:否,1:是';
comment on column SRVS_GROUP.levelnum
  is '层级';
comment on column SRVS_GROUP.ordernum
  is '优先级';
alter table SRVS_GROUP
  add constraint PK_ID primary key (ID)
  using index 
  tablespace PIVAS_TABLESPACE;

create index inx_group_DEPTCODE on SRVS_GROUP (DEPTCODE) tablespace PIVAS_INDEX_TABLESPACE;
-- Create table
create table SRVS_TRACKINGRECORD
(
  id             NUMBER not null,
  relevance      VARCHAR2(60) not null,
  operator       VARCHAR2(32) not null,
  operation_time DATE not null,
  type_num       NUMBER not null,
  type_name      VARCHAR2(32) not null
)
tablespace PIVAS_TABLESPACE;
-- Add comments to the table 
comment on table SRVS_TRACKINGRECORD
  is '追踪记录';
-- Add comments to the columns 
comment on column SRVS_TRACKINGRECORD.id
  is '主键';
comment on column SRVS_TRACKINGRECORD.relevance
  is '关联id';
comment on column SRVS_TRACKINGRECORD.operation_time
  is '操作时间';
comment on column SRVS_TRACKINGRECORD.type_num
  is '追踪类型';
comment on column SRVS_TRACKINGRECORD.type_name
  is '追踪名称';
alter table SRVS_TRACKINGRECORD
  add constraint PKID primary key (ID)
  using index 
  tablespace PIVAS_TABLESPACE;

--打印瓶签条件设置  
create table SRVS_PRINTLABEL_CON
(
  id            VARCHAR2(32) not null,
  name          VARCHAR2(32),
  yyrq          NUMBER(1),
  batchid       VARCHAR2(512),
  mediccategory VARCHAR2(64),
  mediclabel    VARCHAR2(512),
  printstate    NUMBER(1),
  medical       VARCHAR2(512),
  deptcode      VARCHAR2(512)
)
tablespace PIVAS_TABLESPACE;
-- Add comments to the table 
comment on table SRVS_PRINTLABEL_CON
  is '打印瓶签条件设置';
-- Add comments to the columns 
comment on column SRVS_PRINTLABEL_CON.id
  is '主键id';
comment on column SRVS_PRINTLABEL_CON.name
  is '条件名';
comment on column SRVS_PRINTLABEL_CON.yyrq
  is '用药日期： 0今日 1明日 2昨日';
comment on column SRVS_PRINTLABEL_CON.batchid
  is '批次';
comment on column SRVS_PRINTLABEL_CON.mediccategory
  is '药品分类';
comment on column SRVS_PRINTLABEL_CON.mediclabel
  is '药品标签';
comment on column SRVS_PRINTLABEL_CON.printstate
  is '打印状态 0：是 1：否';
comment on column SRVS_PRINTLABEL_CON.medical
  is '溶媒';
comment on column SRVS_PRINTLABEL_CON.deptcode
  is '病区编码';
alter table SRVS_PRINTLABEL_CON add constraint PIVAS_PRINTLABEL_ID primary key (ID) using index tablespace PIVAS_TABLESPACE;

alter table SRVS_PRINTLABEL_CON add ordernum number;
alter table SRVS_PRINTLABEL_CON add ispack varchar2(1);
alter table SRVS_PRINTLABEL_CON add usetype NUMBER(1);

-- Add comments to the columns 
comment on column SRVS_PRINTLABEL_CON.ordernum
  is '优先级';
comment on column SRVS_PRINTLABEL_CON.ispack
  is '是否打包条件，0：否，1：是';
  
comment on column SRVS_PRINTLABEL_CON.usetype
  is '是否用于药品统计 0：否，1：是';

--医嘱/批次条件设置    
CREATE TABLE "SRVS_SHOWDRUGLIST_MAIN"
(
   "CONF_ID"            NUMBER(20, 0)              NOT NULL,
   "CONF_NAME"          VARCHAR2(64 CHAR),
   "CONF_TYPE"          NUMBER(2, 0) DEFAULT 1,
   "USE_BY"             VARCHAR2(256 CHAR), 
   "CREATE_TIME"        DATE NOT NULL,
   RESERVE1 			      VARCHAR2(20 CHAR), 
	 RESERVE2			        VARCHAR2(20 CHAR), 
	 RESERVE3 			      VARCHAR2(20 CHAR),                 
   CONSTRAINT PK_SRVS_SHOWDRUGLIST_MAIN PRIMARY KEY ("CONF_ID")  USING INDEX tablespace pivas_index_tablespace
) tablespace pivas_tablespace;

COMMENT ON TABLE "SRVS_SHOWDRUGLIST_MAIN" IS
'医嘱/药单列表配置主表';

COMMENT ON COLUMN "SRVS_SHOWDRUGLIST_MAIN"."CONF_ID" IS
'主键';
COMMENT ON COLUMN "SRVS_SHOWDRUGLIST_MAIN"."CONF_NAME" IS
'配置名称';
COMMENT ON COLUMN "SRVS_SHOWDRUGLIST_MAIN"."CONF_TYPE" IS
'配置类型  1为医嘱 2为药单';
COMMENT ON COLUMN "SRVS_SHOWDRUGLIST_MAIN"."USE_BY" IS
'用户';
COMMENT ON COLUMN "SRVS_SHOWDRUGLIST_MAIN"."CREATE_TIME" IS
'创建时间';
COMMENT ON COLUMN "SRVS_SHOWDRUGLIST_MAIN"."RESERVE1" IS
'预留字段1';
COMMENT ON COLUMN "SRVS_SHOWDRUGLIST_MAIN"."RESERVE2" IS
'预留字段2';
COMMENT ON COLUMN "SRVS_SHOWDRUGLIST_MAIN"."RESERVE3" IS
'预留字段3';


CREATE TABLE "SRVS_SHOWDRUGLIST"
(
   "CONF_ID"            NUMBER(20, 0)              NOT NULL,
   "FIELD"              VARCHAR2(32 CHAR),
   "PRIORITY"           NUMBER(3, 0), 
	RESERVE1 			      VARCHAR2(20 CHAR), 
	RESERVE2			        VARCHAR2(20 CHAR), 
	RESERVE3 			      VARCHAR2(20 CHAR)
) tablespace pivas_tablespace;
COMMENT ON TABLE "SRVS_SHOWDRUGLIST" IS
'医嘱/药单列表配置子表';
COMMENT ON COLUMN "SRVS_SHOWDRUGLIST"."CONF_ID" IS
'配置主表ID';
COMMENT ON COLUMN "SRVS_SHOWDRUGLIST"."FIELD" IS
'字段名称';
COMMENT ON COLUMN "SRVS_SHOWDRUGLIST"."PRIORITY" IS
'优先级';
COMMENT ON COLUMN "SRVS_SHOWDRUGLIST"."RESERVE1" IS
'预留字段1';
COMMENT ON COLUMN "SRVS_SHOWDRUGLIST"."RESERVE2" IS
'预留字段2';
COMMENT ON COLUMN "SRVS_SHOWDRUGLIST"."RESERVE3" IS
'预留字段3';

--药品破损登记表
CREATE TABLE "SRVS_DRUG_DAMAGE"
(
   "GID"                NUMBER(20, 0)      NOT NULL,
   "DRUG_CODE"          VARCHAR2(64 CHAR),
   "DRUG_NAME"          VARCHAR2(256 CHAR),
   "SPECIFICATIONS"     VARCHAR2(64 CHAR), 
   "DRUG_PLACE"         VARCHAR2(128 CHAR),
   "DAMAGE_PROBLEM"     NUMBER(2, 0), 
   "DAMAGE_LINK"        NUMBER(2, 0), 
   "REGIST_QUANTITY"    NUMBER(10, 0), 
   "REGIST_NAME"        VARCHAR2(32 CHAR),
   "REGIST_TIME"        VARCHAR2(40 CHAR),
   "RESERVE1"           VARCHAR2(20 CHAR), 
   "RESERVE2"           VARCHAR2(20 CHAR), 
   "RESERVE3"           VARCHAR2(20 CHAR),                 
   CONSTRAINT PK_SRVS_DRUG_DAMAGE PRIMARY KEY ("GID")  USING INDEX tablespace pivas_index_tablespace
) tablespace pivas_tablespace;

COMMENT ON TABLE "SRVS_DRUG_DAMAGE" IS
'药品破损登记表';

COMMENT ON COLUMN "SRVS_DRUG_DAMAGE"."GID" IS
'主键';
COMMENT ON COLUMN "SRVS_DRUG_DAMAGE"."DRUG_CODE" IS
'药品编码';
COMMENT ON COLUMN "SRVS_DRUG_DAMAGE"."DRUG_NAME" IS
'药品名称';
COMMENT ON COLUMN "SRVS_DRUG_DAMAGE"."SPECIFICATIONS" IS
'药品规格';
COMMENT ON COLUMN "SRVS_DRUG_DAMAGE"."DRUG_PLACE" IS
'药品产地';
COMMENT ON COLUMN "SRVS_DRUG_DAMAGE"."DAMAGE_PROBLEM" IS
'质量问题';
COMMENT ON COLUMN "SRVS_DRUG_DAMAGE"."DAMAGE_LINK" IS
'破损环节';
COMMENT ON COLUMN "SRVS_DRUG_DAMAGE"."REGIST_QUANTITY" IS
'数量';
COMMENT ON COLUMN "SRVS_DRUG_DAMAGE"."REGIST_NAME" IS
'登记人';
COMMENT ON COLUMN "SRVS_DRUG_DAMAGE"."REGIST_TIME" IS
'登记时间';
COMMENT ON COLUMN "SRVS_DRUG_DAMAGE"."RESERVE1" IS
'预留字段1';
COMMENT ON COLUMN "SRVS_DRUG_DAMAGE"."RESERVE2" IS
'预留字段2';
COMMENT ON COLUMN "SRVS_DRUG_DAMAGE"."RESERVE3" IS
'预留字段3';

--瓶签打印历史表
CREATE TABLE "SRVS_PRINT_HISTORY"
(
   "GID"                NUMBER(20, 0)      NOT NULL,
   "PIDSJ"               VARCHAR2(60),
   "PRINT_INFO"         CLOB,
   "PAGE_INDEX"         NUMBER(6,0),
   "PRINT_DATE"         DATE
) tablespace pivas_tablespace;

COMMENT ON TABLE "SRVS_PRINT_HISTORY" IS
'瓶签打印历史表';

COMMENT ON COLUMN "SRVS_PRINT_HISTORY"."GID" IS
'打印序号';
COMMENT ON COLUMN "SRVS_PRINT_HISTORY"."PIDSJ" IS
'瓶签唯一编码';
COMMENT ON COLUMN "SRVS_PRINT_HISTORY"."PRINT_INFO" IS
'瓶签内容';
COMMENT ON COLUMN "SRVS_PRINT_HISTORY"."PAGE_INDEX" IS
'页码';
COMMENT ON COLUMN "SRVS_PRINT_HISTORY"."PRINT_DATE" IS
'打印时间';

--瓶签打印历史备份表
create table SRVS_PRINT_HISTORY_HIS as select * from SRVS_PRINT_HISTORY where 1=2;
-- 拆药量确认表
create table SRVS_DRUGOPEN_AMOUNT
(
  id              INTEGER not null,
  code            VARCHAR2(32 CHAR),
  name            VARCHAR2(256 CHAR),
  place_code      VARCHAR2(32 CHAR),
  place           VARCHAR2(32 CHAR),
  amount          VARCHAR2(32 CHAR),
  openconfirmdate VARCHAR2(32 CHAR),
  opendate        VARCHAR2(32 CHAR),
  operator        VARCHAR2(32 CHAR),
  operatetime     DATE,
  amountplan      VARCHAR2(32 CHAR)
)
tablespace PIVAS_TABLESPACE;
-- Add comments to the table 
comment on table SRVS_DRUGOPEN_AMOUNT
  is '药品库存拆药量';
-- Add comments to the columns 
comment on column SRVS_DRUGOPEN_AMOUNT.id
  is '主键id';
comment on column SRVS_DRUGOPEN_AMOUNT.code
  is '药品编码';
comment on column SRVS_DRUGOPEN_AMOUNT.name
  is '药品名称';
comment on column SRVS_DRUGOPEN_AMOUNT.place_code
  is '药品产地编码';
comment on column SRVS_DRUGOPEN_AMOUNT.place
  is '药品产地';
comment on column SRVS_DRUGOPEN_AMOUNT.amount
  is '拆药量';
comment on column SRVS_DRUGOPEN_AMOUNT.openconfirmdate
  is '拆药量确认时间';
comment on column SRVS_DRUGOPEN_AMOUNT.opendate
  is '拆药时间';
comment on column SRVS_DRUGOPEN_AMOUNT.operator
  is '拆药人';
comment on column SRVS_DRUGOPEN_AMOUNT.operatetime
  is '操作时间';
comment on column SRVS_DRUGOPEN_AMOUNT.amountplan
  is '计划拆药量';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_DRUGOPEN_AMOUNT add constraint SRVS_DRUGOPEN_AMOUNT_ID primary key (ID) using index tablespace PIVAS_TABLESPACE;
alter table SRVS_DRUGOPEN_AMOUNT add constraint PIVAS_CODE_CONFIEMDATE unique (CODE, OPENCONFIRMDATE) using index tablespace PIVAS_TABLESPACE;
  
--瓶签打印日志表
CREATE TABLE "SRVS_PRINT_LOG"
(
   "GID"                NUMBER(20, 0)      NOT NULL,
   "OPRENAME"           VARCHAR2(60),
   "PRINT_INDEX"        NUMBER(6, 0),
   "PRINT_CONDITION"    VARCHAR2(1024),
   "PRINT_DATE"         VARCHAR2(40 CHAR),
   CONSTRAINT PK_SRVS_PRINT_LOG PRIMARY KEY ("GID")  USING INDEX tablespace pivas_index_tablespace
) tablespace pivas_tablespace;

COMMENT ON TABLE "SRVS_PRINT_LOG" IS
'瓶签打印日志表';

COMMENT ON COLUMN "SRVS_PRINT_LOG"."GID" IS
'主键';

COMMENT ON COLUMN "SRVS_PRINT_LOG"."OPRENAME" IS
'操作人';

COMMENT ON COLUMN "SRVS_PRINT_LOG"."PRINT_INDEX" IS
'打印序号';

COMMENT ON COLUMN "SRVS_PRINT_LOG"."PRINT_CONDITION" IS
'打印条件';

COMMENT ON COLUMN "SRVS_PRINT_LOG"."PRINT_DATE" IS
'打印时间';


--检查医嘱临时表
create table SRVS_CHECK_DOCTORADVICE_TEMP
(
  groupno VARCHAR2(32) not null
)
tablespace PIVAS_TABLESPACE;
-- Add comments to the table 
comment on table SRVS_CHECK_DOCTORADVICE_TEMP
  is '定时任务检查YZ临时表';
-- Add comments to the columns 
comment on column SRVS_CHECK_DOCTORADVICE_TEMP.groupno
  is '父医嘱';
  
--第二天需要检查医嘱数据
create table SRVS_CHECKYZ_TOMORROW as select * from SRVS_CHECK_DOCTORADVICE_TEMP where 1=2;
  
-- 文件删除任务表
create table SRVS_DELETEFIEL_TASK
(
  filepath varchar2(512),
  day      number default 0
);
comment on table SRVS_DELETEFIEL_TASK
  is '文件删除任务表';
comment on column SRVS_DELETEFIEL_TASK.filepath
  is '文件删除路径';
comment on column SRVS_DELETEFIEL_TASK.day
  is '保留天数';
  
--不合理药单
create table SRVS_PRESCRIPTION_REFUND
(
  id           VARCHAR2(32 CHAR) not null,
  recipeid     VARCHAR2(512 CHAR),
  groupno      VARCHAR2(32 CHAR),
  druglistid   VARCHAR2(32 CHAR),
  drugfreq     VARCHAR2(32 CHAR),
  drugcode     VARCHAR2(512 CHAR),
  drugname     VARCHAR2(512 CHAR),
  quantity     VARCHAR2(32 CHAR),
  quantityunit VARCHAR2(32 CHAR),
  occdt        VARCHAR2(32 CHAR),
  chargedt     VARCHAR2(32 CHAR),
  infusiondate VARCHAR2(32 CHAR),
  labelno      VARCHAR2(32 CHAR),
  begindt      VARCHAR2(32 CHAR),
  enddt        VARCHAR2(32 CHAR),
  batch_id     VARCHAR2(32 CHAR),
  amount       VARCHAR2(3 CHAR),
  state        INTEGER,
  bedno        VARCHAR2(32 CHAR),
  patname      VARCHAR2(32 CHAR),
  wardcode     VARCHAR2(32 CHAR),
  wardname     VARCHAR2(32 CHAR),
  processstate VARCHAR2(2 CHAR) default 0
)
tablespace PIVAS_TABLESPACE;
-- Add comments to the table 
comment on table SRVS_PRESCRIPTION_REFUND
  is '不合理药单';
-- Add comments to the columns 
comment on column SRVS_PRESCRIPTION_REFUND.recipeid
  is '医嘱编码，HIS产生的药单中的执行医嘱的编码';
comment on column SRVS_PRESCRIPTION_REFUND.groupno
  is '组号';
comment on column SRVS_PRESCRIPTION_REFUND.druglistid
  is '药单号，医嘱执行单的编号';
comment on column SRVS_PRESCRIPTION_REFUND.drugfreq
  is '用药频次';
comment on column SRVS_PRESCRIPTION_REFUND.drugcode
  is '药品编码';
comment on column SRVS_PRESCRIPTION_REFUND.drugname
  is '药品名称';
comment on column SRVS_PRESCRIPTION_REFUND.quantity
  is '药品数量';
comment on column SRVS_PRESCRIPTION_REFUND.quantityunit
  is '数量单位';
comment on column SRVS_PRESCRIPTION_REFUND.occdt
  is '用药时间，可仅筛选出今明两日的。';
comment on column SRVS_PRESCRIPTION_REFUND.chargedt
  is '收费时间';
comment on column SRVS_PRESCRIPTION_REFUND.infusiondate
  is '输液时间';
comment on column SRVS_PRESCRIPTION_REFUND.labelno
  is '瓶签号/或药单唯一号';
comment on column SRVS_PRESCRIPTION_REFUND.begindt
  is '药单起始时间';
comment on column SRVS_PRESCRIPTION_REFUND.enddt
  is '药单终止时间';
comment on column SRVS_PRESCRIPTION_REFUND.batch_id
  is '对应srvs_batch中ID';
comment on column SRVS_PRESCRIPTION_REFUND.amount
  is '用药次数';
comment on column SRVS_PRESCRIPTION_REFUND.state
  is '药单执行记录状态 0：正常  1：停止  2：退费';
comment on column SRVS_PRESCRIPTION_REFUND.bedno
  is '床号';
comment on column SRVS_PRESCRIPTION_REFUND.patname
  is '病人姓名';
comment on column SRVS_PRESCRIPTION_REFUND.wardcode
  is '病区(科室)编码';
comment on column SRVS_PRESCRIPTION_REFUND.wardname
  is '病区(科室)名称';
comment on column SRVS_PRESCRIPTION_REFUND.processstate
  is '药单退费处理状态 0：未处理  1：已处理';
alter table SRVS_PRESCRIPTION_REFUND add errortype VARCHAR2(32 CHAR);
-- Add comments to the columns 
comment on column SRVS_PRESCRIPTION_REFUND.errortype
  is '审方错误类型';
-- Create/Recreate indexes 
create index PIVAS_INDEX_BEDNO on SRVS_PRESCRIPTION_REFUND (BEDNO)
  tablespace PIVAS_TABLESPACE;
create index PIVAS_INDEX_GROUPNO on SRVS_PRESCRIPTION_REFUND (GROUPNO)
  tablespace PIVAS_TABLESPACE;
create index PIVAS_INDEX_PATNAME on SRVS_PRESCRIPTION_REFUND (PATNAME)
  tablespace PIVAS_TABLESPACE;
-- Create/Recreate primary, unique and foreign key constraints 
alter table SRVS_PRESCRIPTION_REFUND
  add constraint PIVAS_PRIMARY_ID primary key (ID)
  using index 
  tablespace PIVAS_TABLESPACE;
  
--打印机配置
CREATE TABLE "SRVS_PRINTER_CONF"
(
   "GID"         NUMBER(20,0) NOT NULL,
   "COMPNAME"    VARCHAR2(128),
   "IPADDR"      VARCHAR2(32),
   "PRINNAME"    VARCHAR2(128),
   CONSTRAINT PK_SRVS_PRINTER_CONF PRIMARY KEY ("GID")  USING INDEX tablespace pivas_index_tablespace
) tablespace pivas_tablespace;

COMMENT ON TABLE "SRVS_PRINTER_CONF" IS
'打印机配置表';

COMMENT ON COLUMN "SRVS_PRINTER_CONF"."GID" IS
'主键';

COMMENT ON COLUMN "SRVS_PRINTER_CONF"."COMPNAME" IS
'主机名';

COMMENT ON COLUMN "SRVS_PRINTER_CONF"."IPADDR" IS
'IP地址';

COMMENT ON COLUMN "SRVS_PRINTER_CONF"."PRINNAME" IS
'打印机名';

--创建历史表
create table SRVS_DOCTOR_ADVICE_his as select * from SRVS_DOCTOR_ADVICE where 1=2;
create table SRVS_DOCTOR_ADVICE_main_his  as select * from SRVS_DOCTOR_ADVICE_main where 1=2;
create table SRVS_PRESCRIPTION_his  as select * from SRVS_PRESCRIPTION where 1=2;
create table SRVS_PRESCRIPTION_main_his  as select * from SRVS_PRESCRIPTION_main where 1=2;
create table SRVS_LABEL_his  as select * from SRVS_LABEL where 1=2;
create table SRVS_PRESCRIPTION_FEES_his  as select * from SRVS_PRESCRIPTION_FEES where 1=2;
create table SRVS_PRESCRIPTION_SCAN_his  as select * from SRVS_PRESCRIPTION_SCAN where 1=2;
create table SRVS_LABEL_ref_config_fee_his  as select * from SRVS_LABEL_ref_config_fee where 1=2;
--备份药单记录表
create table SRVS_PRESCRIPTION_RECORD_his as select * from SRVS_PRESCRIPTION_RECORD where 1=2;
create table SRVS_TRACKINGRECORD_his as select * from SRVS_TRACKINGRECORD where 1=2;


--打印用pidsj临时表
CREATE TABLE "SRVS_PIDSJ_TEMP"
(
   "GID"      NUMBER(20,0) NOT NULL,
   "PIDSJ"    VARCHAR2(128)
) tablespace pivas_tablespace;

COMMENT ON TABLE "SRVS_PIDSJ_TEMP" IS
'打印用pidsj临时表';

COMMENT ON COLUMN "SRVS_PIDSJ_TEMP"."GID" IS
'主键';

COMMENT ON COLUMN "SRVS_PIDSJ_TEMP"."PIDSJ" IS
'pidsj';


/*==============================================================*/
/* Table: "SCHE_RECORD"                                       */
/*==============================================================*/
create table "SCHE_RECORD"
(
   "ID"                 INTEGER           not null,
   "GROUPID"            INTEGER           not null,
   "USER_ID"            INTEGER           not null,
   "USER_NAME"          VARCHAR2(32)      not null,
   "POSTID"             INTEGER,
   "WEEK_OWETIME"       VARCHAR2(16),
   "TOTAL_OWETIME"      VARCHAR2(16),
   "TOTAL_TIME"         VARCHAR2(16),
   "STARTDATE"          VARCHAR2(10)      not null,
   "ENDDATE"            VARCHAR2(10)      not null,
   constraint PK_SCHEDULE_DATA primary key ("ID")
);

comment on table "SCHE_RECORD" is
'排班数据';

comment on column "SCHE_RECORD"."ID" is
'id';

comment on column "SCHE_RECORD"."GROUPID" is
'分组id';

comment on column "SCHE_RECORD"."USER_ID" is
'人员id';

comment on column "SCHE_RECORD"."USER_NAME" is
'人员名称';

comment on column "SCHE_RECORD"."POSTID" is
'岗位id';

comment on column "SCHE_RECORD"."WEEK_OWETIME" is
'本周欠休';

comment on column "SCHE_RECORD"."TOTAL_OWETIME" is
'累计欠休';

comment on column "SCHE_RECORD"."TOTAL_TIME" is
'总共时';

comment on column "SCHE_RECORD"."STARTDATE" is
'起始日期';

comment on column "SCHE_RECORD"."ENDDATE" is
'结束日期';


/*==============================================================*/
/* Table: "SCHE_RECORD_HIS"                               */
/*==============================================================*/
create table "SCHE_RECORD_HIS"
(
   "ID"                 INTEGER              not null,
   "GROUPID"            INTEGER           	 not null,
   "USER_ID"            INTEGER              not null,
   "USER_NAME"          VARCHAR2(32)         not null,
   "POSTID"             INTEGER,
   "WEEK_OWETIME"       VARCHAR2(16),
   "TOTAL_OWETIME"      VARCHAR2(16),
   "TOTAL_TIME"         VARCHAR2(16),
   "STARTDATE"          VARCHAR2(10)         not null,
   "ENDDATE"            VARCHAR2(10)         not null,
   constraint PK_SCHE_RECORD_HIS primary key ("ID")
);

comment on table "SCHE_RECORD_HIS" is
'排班数据历史表';

comment on column "SCHE_RECORD_HIS"."ID" is
'id';

comment on column "SCHE_RECORD_HIS"."GROUPID" is
'分组id';

comment on column "SCHE_RECORD_HIS"."USER_ID" is
'人员id';

comment on column "SCHE_RECORD_HIS"."USER_NAME" is
'人员名称';

comment on column "SCHE_RECORD_HIS"."POSTID" is
'岗位id';

comment on column "SCHE_RECORD_HIS"."WEEK_OWETIME" is
'本周欠休';

comment on column "SCHE_RECORD_HIS"."TOTAL_OWETIME" is
'累计欠休';

comment on column "SCHE_RECORD_HIS"."TOTAL_TIME" is
'总共时';

comment on column "SCHE_RECORD_HIS"."STARTDATE" is
'起始日期';

comment on column "SCHE_RECORD_HIS"."ENDDATE" is
'结束日期';



/*==============================================================*/
/* Table: "SCHE_RECORD_WEEK"                                  */
/*==============================================================*/
create table "SCHE_RECORD_WEEK"
(
   "ID"                 INTEGER              not null,
   "USER_ID"            INTEGER              not null,
   "WEEKTYPE"           VARCHAR2(1)          not null,
   "WORKID"             INTEGER,
   "SORT"               INTEGER,
   "WORKDATE"           VARCHAR2(10)         not null
);

comment on table "SCHE_RECORD_WEEK" is
'一周排班表';

comment on column "SCHE_RECORD_WEEK"."ID" is
'数据id';

comment on column "SCHE_RECORD_WEEK"."USER_ID" is
'人员id';

comment on column "SCHE_RECORD_WEEK"."WEEKTYPE" is
'周几,1,2,3,4,5,6,7分别对应星期';

comment on column "SCHE_RECORD_WEEK"."WORKID" is
'班次';

comment on column "SCHE_RECORD_WEEK"."SORT" is
'排序';

comment on column "SCHE_RECORD_WEEK"."WORKDATE" is
'日期';


/*==============================================================*/
/* Table: "SCHE_RECORD_WEEK_HIS"                          */
/*==============================================================*/
create table "SCHE_RECORD_WEEK_HIS"
(
   "ID"                 INTEGER              not null,
   "USER_ID"            INTEGER              not null,
   "WEEKTYPE"           VARCHAR2(1)          not null,
   "WORKID"             INTEGER,
   "SORT"               INTEGER,
   "WORKDATE"           VARCHAR2(10)         not null
);

comment on table "SCHE_RECORD_WEEK_HIS" is
'一周排班历史表';

comment on column "SCHE_RECORD_WEEK_HIS"."ID" is
'数据id';

comment on column "SCHE_RECORD_WEEK_HIS"."USER_ID" is
'人员id';

comment on column "SCHE_RECORD_WEEK_HIS"."WEEKTYPE" is
'周几,1,2,3,4,5,6,7分别对应星期';

comment on column "SCHE_RECORD_WEEK_HIS"."WORKID" is
'班次';

comment on column "SCHE_RECORD_WEEK_HIS"."SORT" is
'排序';

comment on column "SCHE_RECORD_WEEK_HIS"."WORKDATE" is
'日期';



/*==============================================================*/
/* Table: "SCHE_DICT"                                 */
/*==============================================================*/
create table "SCHE_DICT"
(
   "DIC_ID"             INTEGER              not null,
   "CATEGORY"           VARCHAR2(32)         not null,
   "DESCRIPTION"        VARCHAR2(32)         not null,
   "CODE"               VARCHAR2(32)         not null,
   "SORT"               INTEGER              not null,
   constraint PK_SCHE_DICT primary key ("DIC_ID")
);

comment on table "SCHE_DICT" is
'数据字典';

comment on column "SCHE_DICT"."DIC_ID" is
'id';

comment on column "SCHE_DICT"."CATEGORY" is
'分类';

comment on column "SCHE_DICT"."DESCRIPTION" is
'描述';

comment on column "SCHE_DICT"."CODE" is
'代码';

comment on column "SCHE_DICT"."SORT" is
'排序';


/*==============================================================*/
/* Table: "SCHE_GROUP"                                 */
/*==============================================================*/
create table "SCHE_GROUP"
(
   "GROUPID"            INTEGER              not null,
   "USER_ID"            INTEGER,
   "USER_NAME"          VARCHAR2(32),
   "VALIDITY_START"     VARCHAR2(10)         not null,
   "VALIDITY_END"       VARCHAR2(10)         not null,
   "SORT"               INTEGER
);

comment on table "SCHE_GROUP" is
'分组人员信息';

comment on column "SCHE_GROUP"."GROUPID" is
'分组id';

comment on column "SCHE_GROUP"."USER_ID" is
'人员id';

comment on column "SCHE_GROUP"."USER_NAME" is
'人员名称';

comment on column "SCHE_GROUP"."VALIDITY_START" is
'有效时间开始';

comment on column "SCHE_GROUP"."VALIDITY_END" is
'有效时间结束';

comment on column "SCHE_GROUP"."SORT" is
'排序';


/*==============================================================*/
/* Table: "SCHE_GROUP_WORK"                                 */
/*==============================================================*/
create table "SCHE_GROUP_WORK"
(
   "GROUPID"            INTEGER              not null,
   "WORKID"             INTEGER              not null
);

comment on table "SCHE_GROUP_WORK" is
'分组指定班次';

comment on column "SCHE_GROUP_WORK"."GROUPID" is
'分组id';

comment on column "SCHE_GROUP_WORK"."WORKID" is
'班次id';


/*==============================================================*/
/* Table: "SCHE_HOLIDAY"                                   */
/*==============================================================*/
create table "SCHE_HOLIDAY"
(
   "ID"                 INTEGER              not null,
   "NAME"               VARCHAR2(50)         not null,
   "STARTDATE"          VARCHAR2(10)         not null,
   "ENDDATE"            VARCHAR2(10)         not null,
   constraint PK_SCHE_HOLIDAY primary key ("ID")
);

comment on table "SCHE_HOLIDAY" is
'节假日表';

comment on column "SCHE_HOLIDAY"."ID" is
'id';

comment on column "SCHE_HOLIDAY"."NAME" is
'节假日名称';

comment on column "SCHE_HOLIDAY"."STARTDATE" is
'开始日期';

comment on column "SCHE_HOLIDAY"."ENDDATE" is
'结束日期';


/*==============================================================*/
/* Table: "SCHE_HOLIDAY_WORK"                              */
/*==============================================================*/
create table "SCHE_HOLIDAY_WORK"
(
   "USER_ID"            INTEGER              not null,
   "USER_NAME"          VARCHAR2(32)         not null,
   "TIMES"              INTEGER              not null,
   "UPDATEDATA"         VARCHAR2(10)         not null,
   constraint PK_SCHE_HOLIDAY_WORK primary key ("USER_ID")
);

comment on table "SCHE_HOLIDAY_WORK" is
'节假日上班人员统计';

comment on column "SCHE_HOLIDAY_WORK"."USER_ID" is
'人员id';

comment on column "SCHE_HOLIDAY_WORK"."USER_NAME" is
'人员名称';

comment on column "SCHE_HOLIDAY_WORK"."TIMES" is
'上班次数';

comment on column "SCHE_HOLIDAY_WORK"."UPDATEDATA" is
'更新时间';



/*==============================================================*/
/* Table: "SCHE_USER"                                  */
/*==============================================================*/
create table "SCHE_USER"
(
   "USER_ID"            INTEGER              not null,
   "USER_NAME"          VARCHAR2(32)         not null,
   "PARTAKE"            VARCHAR2(1)          default '0' not null,
   "EMAIL"              VARCHAR2(128),
   "TELLPHONE"          VARCHAR2(32),
   "ACCOUNT_STATUS"     VARCHAR2(1)          default '0',
   "PASSWORD"           VARCHAR2(32),
   "CREATIONTIME"       DATE                 not null,
   "CREATER"            INTEGER              not null,
   "DELTYPE"            VARCHAR2(1)          default '0',
   constraint PK_SCHE_USER primary key ("USER_ID")
);

comment on table "SCHE_USER" is
'人员信息表';

comment on column "SCHE_USER"."USER_ID" is
'人员id';

comment on column "SCHE_USER"."USER_NAME" is
'人员名称';

comment on column "SCHE_USER"."PARTAKE" is
'参与排班，1参与/0不参与';

comment on column "SCHE_USER"."EMAIL" is
'email';

comment on column "SCHE_USER"."TELLPHONE" is
'电话';

comment on column "SCHE_USER"."ACCOUNT_STATUS" is
'账号状态,1创建/0未创建';

comment on column "SCHE_USER"."PASSWORD" is
'密码';

comment on column "SCHE_USER"."CREATIONTIME" is
'创建时间';

comment on column "SCHE_USER"."CREATER" is
'创建人';

comment on column "SCHE_USER"."DELTYPE"is
'软删除标志，0:未删除，1:已删除';

/*==============================================================*/
/* Table: "SCHE_USER_GROUP"                            */
/*==============================================================*/
create table "SCHE_USER_GROUP"
(
   "GROUPID"            INTEGER              not null,
   "GROUPNAME"          VARCHAR2(32)         not null,
   "CREATIONTIME"       DATE                 not null,
   "CREATER"            INTEGER              not null,
   "TYPE"               VARCHAR2(1)          default '1' not null,
   "SORT"               INTEGER              not null,
   constraint PK_SCHE_USER_GROUP primary key ("GROUPID")
);

comment on table "SCHE_USER_GROUP" is
'人员分组表';

comment on column "SCHE_USER_GROUP"."GROUPID" is
'分组id';

comment on column "SCHE_USER_GROUP"."GROUPNAME" is
'分组名称';

comment on column "SCHE_USER_GROUP"."CREATIONTIME" is
'创建时间';

comment on column "SCHE_USER_GROUP"."CREATER" is
'创建人';

comment on column "SCHE_USER_GROUP"."TYPE" is
'类型，0:默认组,1:其他';

comment on column "SCHE_USER_GROUP"."SORT" is
'排序';

/*==============================================================*/
/* Table: "SCHE_POSITION"                                       */
/*==============================================================*/
create table "SCHE_POSITION"
(
   "POSTID"             INTEGER              not null,
   "POSTNAME"           VARCHAR2(32)         not null,
   "ENABLED"            VARCHAR2(1)          default '0' not null,
   "CREATIONTIME"       DATE                 not null,
   "CREATER"            INTEGER              not null,
   constraint PK_SCHE_POSITION primary key ("POSTID")
);

comment on table "SCHE_POSITION" is
'岗位表';

comment on column "SCHE_POSITION"."POSTID" is
'岗位id';

comment on column "SCHE_POSITION"."POSTNAME" is
'岗位名称';

comment on column "SCHE_POSITION"."ENABLED" is
'是否可用，1:是/0:否';

comment on column "SCHE_POSITION"."CREATIONTIME" is
'创建时间';

comment on column "SCHE_POSITION"."CREATER" is
'创建人';


/*==============================================================*/
/* Table: "SCHE_SYSCFG"                              */
/*==============================================================*/
create table "SCHE_SYSCFG"
(
   "CONFIGID"           INTEGER              not null,
   "CONFIG_INFO"        VARCHAR2(32)         not null,
   "CONFIG_VALUE"       VARCHAR2(1)          not null,
   "UPDATETIME"         DATE                 not null,
   "CREATER"            INTEGER              not null,
   constraint PK_SCHE_SYSCFG primary key ("CONFIGID")
);

comment on table "SCHE_SYSCFG" is
'系统设置表';

comment on column "SCHE_SYSCFG"."CONFIGID" is
'设置id';

comment on column "SCHE_SYSCFG"."CONFIG_INFO" is
'设置信息';

comment on column "SCHE_SYSCFG"."CONFIG_VALUE" is
'设置值,1:是/0:否';

comment on column "SCHE_SYSCFG"."UPDATETIME" is
'更新时间';

comment on column "SCHE_SYSCFG"."CREATER" is
'更新人';




/*==============================================================*/
/* Table: "SCHE_WORK"                                       */
/*==============================================================*/
create table "SCHE_WORK"
(
   "WORKID"             INTEGER              not null,
   "WORKNAME"           VARCHAR2(32)         not null,
   "DEFINETYPE"         VARCHAR2(1)          default '0' not null,
   "WORKTYPE"           VARCHAR2(1)          default '0' not null,
   "TOTALTIME"          VARCHAR2(16)         not null,
   "TIME_INTERVAL"      VARCHAR2(30)         not null,
   "WORKCOLOUR"         VARCHAR2(10)         not null,
   "WORKSTATUS"         VARCHAR2(1)          default '0' not null,
   "STATUS"             VARCHAR2(1),
   "COUNT"              INTEGER              default 0,
   "CREATIONTIME"       DATE                 not null,
   "CREATER"            INTEGER              not null,
   "COPYID"             INTEGER,
   "SPLITWORK"     		VARCHAR2(1) 		 default '0',
   constraint PK_SCHE_WORK primary key ("WORKID")
);

comment on table "SCHE_WORK" is
'班次设置表';

comment on column "SCHE_WORK"."WORKID" is
'班次id';

comment on column "SCHE_WORK"."WORKNAME" is
'班次名称';

comment on column "SCHE_WORK"."DEFINETYPE" is
'定义类型,默认值0：自定义';

comment on column "SCHE_WORK"."WORKTYPE" is
'班次类型，默认值0：工作 1:其他 2:正常休息 3:非工作  ';

comment on column "SCHE_WORK"."TOTALTIME" is
'总时间小时';

comment on column "SCHE_WORK"."TIME_INTERVAL" is
'起止时间';

comment on column "SCHE_WORK"."WORKCOLOUR" is
'班次配色，存放16进制颜色值';

comment on column "SCHE_WORK"."WORKSTATUS" is
'班次是否使用，0:否/1:是';

comment on column "SCHE_WORK"."STATUS" is
'班次状态，0:无效/1:有效';

comment on column "SCHE_WORK"."COUNT" is
'每日次数';

comment on column "SCHE_WORK"."CREATIONTIME" is
'创建时间';

comment on column "SCHE_WORK"."CREATER" is
'创建人';

comment on column "SCHE_WORK"."COPYID" is
'复制workid';

comment on column "SCHE_WORK"."SPLITWORK" is
'是否是两头班,0:否/1是';


create table "SCHE_NOTE"
(
   "ID"                 INTEGER              not null,
   "REMARK"             VARCHAR2(2000),
   "STARTDATE"          VARCHAR2(10),
   "ENDDATE"            VARCHAR2(10),
   constraint PK_SCHE_NOTE primary key ("ID")
);

comment on table "SCHE_NOTE" is
'排班管理备注';

comment on column "SCHE_NOTE"."ID" is
'唯一标示';

comment on column "SCHE_NOTE"."REMARK" is
'备注';

comment on column "SCHE_NOTE"."STARTDATE" is
'开始日期';

comment on column "SCHE_NOTE"."ENDDATE" is
'结束日期';


create table sche_sign
(
  id           NUMBER not null,
  userid       NUMBER,
  username     VARCHAR2(32),
  workid       NUMBER,
  workname     VARCHAR2(50),
  worktime     VARCHAR2(16),
  workdate     VARCHAR2(10),
  creationtime DATE,
  creater      NUMBER,
  constraint PK_sche_sign primary key (ID)
);
-- Add comments to the table
comment on table sche_sign
  is '签到表';
-- Add comments to the columns
comment on column sche_sign.id
  is '主键';
comment on column sche_sign.userid
  is '用户id';
comment on column sche_sign.username
  is '用户名';
comment on column sche_sign.workid
  is '班次id';
comment on column sche_sign.workname
  is '班次名';
comment on column sche_sign.worktime
  is '班次时长';
comment on column sche_sign.workdate
  is '排班时间';
comment on column sche_sign.creationtime
  is '创建时间';
comment on column sche_sign.creater
  is '创建人';

create table SYS_ERROR_LOG
(
  err_id     INTEGER not null,
  err_time   DATE not null,
  err_fun    VARCHAR2(100),
  err_cause  VARCHAR2(4000),
  err_mess   VARCHAR2(4000),
  err_stack  VARCHAR2(4000),
  err_userid INTEGER,
  err_param  VARCHAR2(500)
);
