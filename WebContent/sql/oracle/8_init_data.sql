-- -----------------------------------------------------------------------------------
-- 初始化数据（默认账号、默认建筑树、系统资产语言、默认缴费账号角色）
-- -----------------------------------------------------------------------------------
INSERT INTO sys_user (user_id, account, password, name,gender, telephone, email, create_time,description,creator,account_type,del_flag,resetPwd) VALUES(1, 'admin', '40941538609061b2c98b2cc12860cc52a6abab96230e16ad0f0011088fe52dd8', 'admin', 1, '18888888888', 'admin@zchl.com',  sysdate, '默认管理员',1,1,0,1);
INSERT INTO SYS_LANG (language_id, code, description) VALUES(1, 'zh', '中文');
INSERT INTO SYS_LANG (language_id, code, description) VALUES(2, 'en', 'English');



--------  权限管理 -----------

-------------------------------   PIVAS管理   --------------------------------------------
-----------------------------------------------------------------------------------------
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 1, 0, '业务管理', 0, NULL, '业务管理', 1, sysdate, 0, NULL, 'PIVAS_MENU_500', 1, 0, NULL);
-- 任务管理
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 501, 1, ' 工作流程',0, NULL, '业务管理', 1, sysdate, 0, NULL, 'PIVAS_MENU_501', 1, 1, NULL);

		--医嘱
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 502, 501, '医嘱信息', 1, '/doctorAdvice/main', '业务管理', 1, sysdate, 0, NULL, 'PIVAS_MENU_502', 1, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 504, 502, '批量审方', 2, NULL, '业务管理', 2, sysdate, 0, NULL, 'PIVAS_BTN_504', 1, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 505, 502, '同步', 2, NULL, '业务管理', 1, sysdate, 0, NULL, 'PIVAS_BTN_505', 1, 3, NULL);


		--审方核对
-- INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 511, 501, '审方核对', 1, '/doctorAdvice/yzPCheck', '业务管理', 2, sysdate, 0, NULL, 'PIVAS_MENU_511', 1, 2, NULL);
-- INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 513, 511, '审方', 2, NULL, '业务管理', 2, sysdate, 0, NULL, 'PIVAS_BTN_513', 1, 3, NULL);

		--审方核对2
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 514, 501, '药师审方', 1, '/doctorAdvice/yzPCheck2', '业务管理', 3, sysdate, 0, NULL, 'PIVAS_MENU_514', 1, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 516, 514, '审方', 2, NULL, '业务管理', 2, sysdate, 0, NULL, 'PIVAS_BTN_516', 1, 3, NULL);

		--批次优化
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 517, 501, '批次优化', 1, '/doctorAdvice/batchOptn', '业务管理', 4, sysdate, 0, NULL, 'PIVAS_MENU_517', 1, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 518, 517, '优化确认', 2, NULL, '业务管理', 1, sysdate, 0, NULL, 'PIVAS_BTN_518', 1, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 519, 517, '自动落批次检查', 2, NULL, '业务管理', 2, sysdate, 0, NULL, 'PIVAS_BTN_519', 1, 3, NULL);

    --批次管理
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 628, 501, '批次管理', 1, '/doctorAdvice/batchManage', '业务管理', 5, sysdate, 0, NULL, 'PIVAS_MENU_628', 1, 2, NULL);

		--打印瓶签
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 622, 501, '打印瓶签', 1, '/printLabel/init', '业务管理', 6, sysdate, 0, NULL, 'PIVAS_MENU_622', 1, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 623, 622, '打印', 2, NULL, '业务管理', 1, sysdate, 0, NULL, 'PIVAS_BTN_623', 1, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 624, 622, '重新打印', 2, NULL, '业务管理', 2, sysdate, 0, NULL, 'PIVAS_BTN_624', 1, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 625, 622, '打印汇总单', 2, NULL, '业务管理', 3, sysdate, 0, NULL, 'PIVAS_BTN_625', 1, 3, NULL);


		--瓶签扫描 (扫描首页)
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 592, 501, '瓶签扫描', 1, '/scans/init', '业务管理', 7, sysdate, 0, NULL, 'PIVAS_MENU_592', 1, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 593, 592, '扫描', 2, '', '业务管理', 1, sysdate, 0, NULL, 'PIVAS_BTN_593', 1, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 594, 592, '打印4#接收单', 2, '', '业务管理', 2, sysdate, 0, NULL, 'PIVAS_BTN_594', 1, 3, NULL);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'业务管理',1,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'Service Management',1,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'任务管理',501,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'Task Management;',501,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'医嘱信息',502,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'Prescribed',502,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'批量审方',504,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'Batch trial',504,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'同步',505,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',505,1);

-- INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'审方核对',511,1);
-- INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',511,1);

-- INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'审方',513,1);
-- INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',513,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'药师审方2',514,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',514,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'审方',516,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',516,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'批次优化',517,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'Batch trial',517,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'优化确认',518,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',518,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'自动落批次检查',519,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',519,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'打印瓶签',622,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',622,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'打印',623,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',623,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'重新打印',624,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',624,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'打印汇总单',625,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',625,1);

--INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'药品扫描',911,1);
--INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'Batch trial',911,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'瓶签扫描',592,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',592,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'扫描',593,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',593,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'打印4#接收单',594,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',594,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'批次管理',628,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',628,1);

-------------------------------   日常维护   --------------------------------------------
-----------------------------------------------------------------------------------------
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 2, 0, '日常维护', 0, NULL, '日常维护', 2, sysdate, 0, NULL, 'PIVAS_MENU_2', 2, 0, NULL);
-- 数据维护
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 201, 2, '数据维护', 0, NULL, '日常维护', 1, sysdate, 0, NULL, 'PIVAS_MENU_201', 2, 1, NULL);
		--  药品
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 221, 201, '药品管理', 1, '/mdcm/initMdcms', '日常维护', 1, sysdate, 0, NULL, 'PIVAS_MENU_221', 2, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 223, 221, '修改', 2, NULL, '日常维护', 2, sysdate, 0, NULL, 'PIVAS_BTN_223', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 224, 221, '同步数据', 2, NULL, '日常维护', 3, sysdate, 0, NULL, 'PIVAS_BTN_224', 2, 3, NULL);

		--  病区
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 231, 201, '病区管理', 1, '/ipta/initIpta', '日常维护', 2, sysdate, 0, NULL, 'PIVAS_MENU_231', 2, 2, NULL);
--INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 233, 231, '修改', 2, NULL, '日常维护', 2, sysdate, 0, NULL, 'PIVAS_BTN_233', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 234, 231, '同步数据', 2, NULL, '日常维护', 3, sysdate, 0, NULL, 'PIVAS_BTN_234', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 235, 231, '启动', 2, NULL, '日常维护', 4, sysdate, 0, NULL, 'PIVAS_BTN_235', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 236, 231, '停止', 2, NULL, '日常维护', 5, sysdate, 0, NULL, 'PIVAS_BTN_236', 2, 3, NULL);

		--  病人
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 241, 201, '病人信息', 1, '/pati/initPati', '日常维护', 3, sysdate, 0, NULL, 'PIVAS_MENU_241', 2, 2, NULL);
--INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 243, 241, '修改', 2, NULL, '日常维护', 2, sysdate, 0, NULL, 'PIVAS_BTN_243', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 244, 241, '同步数据', 2, NULL, '日常维护', 3, sysdate, 0, NULL, 'PIVAS_BTN_244', 2, 3, NULL);

		-- 员工信息
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 921, 201, '员工信息', 1, '/empy/initEmpy', '日常维护', 4, sysdate, 0, NULL, 'PIVAS_MENU_921', 2, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 927, 921, '同步数据', 2, '', '日常维护', 1, sysdate, 0, NULL, 'PIVAS_BTN_927', 2, 3, NULL);

		-- 用药途径
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 251, 201, '用药途径', 1, '/dw/initDw', '日常维护', 5, sysdate, 0, NULL, 'PIVAS_MENU_251', 2, 2, NULL);
--INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 253, 251, '修改', 2, NULL, '日常维护', 2, sysdate, 0, NULL, 'PIVAS_BTN_253', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 254, 251, '同步数据', 2, NULL, '日常维护', 3, sysdate, 0, NULL, 'PIVAS_BTN_254', 2, 3, NULL);

		-- 用药频次
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 321, 201, '用药频次', 1, 'cm/mdcFreq', '日常维护', 6, sysdate, 0, NULL, 'PIVAS_MENU_321', 2, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 322, 321, '同步数据', 2, NULL, '日常维护', 1, sysdate, 0, NULL, 'PIVAS_BTN_322', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 325, 321, '修改', 2, NULL, '日常维护', 4, sysdate, 0, NULL, 'PIVAS_BTN_325', 2, 3, NULL);

	----------------- i18n -------------------
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'日常维护',2,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',2,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'数据维护',201,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',201,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'药品管理',221,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',221,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'修改',223,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',223,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'同步数据',224,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',224,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'病区管理',231,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',231,1);

--INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'修改',233,1);
--INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',233,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'同步数据',234,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',234,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'启动',235,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',235,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'停止',236,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',236,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'病人信息',241,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',241,1);

--INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'修改',243,1);
--INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',243,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'同步数据',244,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',244,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'员工信息',921,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',921,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'同步数据',927,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',927,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'用药途径',251,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',251,1);

--INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'修改',253,1);
--INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',253,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'同步数据',254,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',254,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'用药频次',321,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',321,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'同步数据',322,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',322,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'修改',325,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',325,1);	
	
	------------------------------------------


-- 库存管理
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 631, 2, '库存管理', 0, NULL, '日常维护', 1, sysdate, 0, NULL, 'PIVAS_MENU_631', 2, 1, NULL);

-- 库存单
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 632, 631, '库存单', 1, '/mdictStk/initStock', '日常维护', 1, sysdate, 0, NULL, 'PIVAS_MENU_632', 2, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 633, 632, '导出', 2, NULL, '日常维护', 1, sysdate, 0, NULL, 'PIVAS_BTN_633', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 634, 632, '导入', 2, NULL, '日常维护', 2, sysdate, 0, NULL, 'PIVAS_BTN_634', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 636, 632, '实际消耗量', 2, NULL, '日常维护', 3, sysdate, 0, NULL, 'PIVAS_BTN_636', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 637, 632, '库存检查', 2, NULL, '日常维护', 4, sysdate, 0, NULL, 'PIVAS_BTN_637', 2, 3, NULL);
-- 药品破损登记
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 941, 631, '药品破损登记', 1, '/dgDmg/initDrugDamage', '日常维护', 2, sysdate, 0, NULL, 'PIVAS_MENU_941', 2, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 948, 941, '新增', 2, NULL, '日常维护', 1, sysdate, 0, NULL, 'PIVAS_BTN_948', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 947, 941, '删除', 2, NULL, '日常维护', 2, sysdate, 0, NULL, 'PIVAS_BTN_947', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 946, 941, '修改', 2, NULL, '日常维护', 4, sysdate, 0, NULL, 'PIVAS_BTN_946', 2, 3, NULL);

-- 拆药量确认
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 942, 631, '拆药量确认', 1, '/mdictStk/initDrugOpen', '日常维护', 3, sysdate, 0, NULL, 'PIVAS_MENU_942', 2, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 943, 942, '库管管理', 2, NULL, '日常维护', 1, sysdate, 0, NULL, 'PIVAS_BTN_943', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 944, 942, '拆药人操作', 2, NULL, '日常维护', 2, sysdate, 0, NULL, 'PIVAS_BTN_944', 2, 3, NULL);

---------------------------- i18n ---------------------------

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'库存管理',631,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',631,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'库存单',632,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',632,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'导出',633,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',633,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'导入',634,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',634,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'实际消耗量',636,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',636,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'库存检查',637,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',637,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'药品破损登记',941,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',941,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'新增',948,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',948,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'删除',947,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',947,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'修改',946,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',946,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'拆药量确认',942,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',942,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'库管管理',943,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',943,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'拆药人操作',944,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',944,1);
-------------------------------------------------------------
-----------------------------------------------------------------------------------------

-------------------------------   病区管理  --------------------------------------------
--病区管理
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 961, 2, '病区管理', 0, NULL, '日常维护', 1, sysdate, 0, NULL, 'PIVAS_MENU_961', 2, 1, NULL);
		-- 病区分组（未完成）
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 924, 961, '病区分组', 1, '/wardRegionGroup/init', '日常维护', 1, sysdate, 0, NULL, 'PIVAS_MENU_924', 2, 2, NULL);
insert into SYS_PRIVILEGE (privilege_id, parent_id, name, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) values (983, 924, '删除', 2, null, '日常维护', 3, sysdate, 0, null, 'PIVAS_BTN_983', 2, 3, null);
insert into SYS_PRIVILEGE (privilege_id, parent_id, name, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) values (982, 924, '修改', 2, null, '日常维护', 2, sysdate, 0, null, 'PIVAS_BTN_982', 2, 3, null);
insert into SYS_PRIVILEGE (privilege_id, parent_id, name, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) values (981, 924, '新增', 2, null, '日常维护', 1, sysdate, 0, null, 'PIVAS_BTN_981', 2, 3, null);

		--输液单管理
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 962, 961, '输液单管理', 1, '/doctorAdvice/infusionSheet', '日常维护', 2, sysdate, 0, NULL, 'PIVAS_MENU_962', 2, 2, NULL);

insert into SYS_PRIVILEGE (privilege_id, parent_id, name, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon)
values (964, 962, '签收', 2, null, '病区维护', 1, sysdate, 0, null, 'PIVAS_BTN_964', 7, 3, null);

		--医嘱管理
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 963, 961, '医嘱管理', 1, '/doctorAdvice/doctorAdviceMgr', '日常维护', 3, sysdate, 0, NULL, 'PIVAS_MENU_963', 2, 2, NULL);

---------------------------- i18n ---------------------------
-- INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'病区维护',7,1);
-- INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',7,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'病区管理',961,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',961,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'输液单管理',962,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',962,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'医嘱管理',963,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',963,1);

insert into SYS_I18N (language_id, name, object_id, type)
values (1, '签收', 964, 1);

insert into SYS_I18N (language_id, name, object_id, type)
values (2, null, 964, 1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'病区分组',924,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',924,1);
insert into SYS_I18N (language_id, name, object_id, type) values (1, '新增', 981, 1);
insert into SYS_I18N (language_id, name, object_id, type) values (2, 'Add', 981, 1);
insert into SYS_I18N (language_id, name, object_id, type) values (1, '修改', 982, 1);
insert into SYS_I18N (language_id, name, object_id, type) values (2, 'Modify', 982, 1);
insert into SYS_I18N (language_id, name, object_id, type) values (1, '删除', 983, 1);
insert into SYS_I18N (language_id, name, object_id, type) values (2, 'Delete', 983, 1);
--------------------------------------------------------------


-- 批次管理
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 311, 2, '批次管理', 0, NULL, '日常维护', 5, sysdate, 0, NULL, 'PIVAS_MENU_311', 2, 1, NULL);
		
		-- 批次定义 
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 312, 311, '批次定义', 1, 'cm/bat', '日常维护', 1, sysdate, 0, NULL, 'PIVAS_MENU_312', 2, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 313, 312, '新增', 2, NULL, '日常维护', 1, sysdate, 0, NULL, 'PIVAS_BTN_313', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 314, 312, '删除', 2, NULL, '日常维护', 2, sysdate, 0, NULL, 'PIVAS_BTN_314', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 316, 312, '修改', 2, NULL, '日常维护', 4, sysdate, 0, NULL, 'PIVAS_BTN_316', 2, 3, NULL);

		-- 一般规则		
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 331, 311, '一般规则', 1, '/sc/genlRule', '日常维护', 2, sysdate, 0, NULL, 'PIVAS_MENU_331', 2, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 332, 331, '新增', 2, NULL, '日常维护', 1, sysdate, 0, NULL, 'PIVAS_BTN_332', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 333, 331, '删除', 2, NULL, '日常维护', 2, sysdate, 0, NULL, 'PIVAS_BTN_333', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 335, 331, '修改', 2, NULL, '日常维护', 4, sysdate, 0, NULL, 'PIVAS_BTN_335', 2, 3, NULL);
	----------------- i18n -------------------
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'批次管理',311,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',311,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'批次定义',312,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',312,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'新增',313,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',313,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'删除',314,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',314,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'修改',316,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',316,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'一般规则',331,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',331,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'新增',332,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',332,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'删除',333,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',333,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'修改',335,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',335,1);	
	------------------------------------------
	
-- 批次规则
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 641, 2, '批次规则', 0, NULL, '日常维护', 6, sysdate, 0, NULL, 'PIVAS_MENU_641', 2, 1, NULL);

		-- 药物优先级
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 642, 641, '药物优先级', 1, '/ydRule/mdcmRulePage', '日常维护', 1, sysdate, 0, NULL, 'PIVAS_MENU_642', 2, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 643, 642, '是否应用到所有病区', 2, NULL, '日常维护', 1, sysdate, 0, NULL, 'PIVAS_BTN_643', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 644, 642, '添加', 2, NULL, '日常维护', 2, sysdate, 0, NULL, 'PIVAS_BTN_644', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 645, 642, '向上移', 2, NULL, '日常维护', 3, sysdate, 0, NULL, 'PIVAS_BTN_645', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 646, 642, '向下移', 2, NULL, '日常维护', 4, sysdate, 0, NULL, 'PIVAS_BTN_646', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 647, 642, '删除', 2, NULL, '日常维护', 5, sysdate, 0, NULL, 'PIVAS_BTN_647', 2, 3, NULL);

		-- 容积规则
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 652, 641, '容积规则', 1, '/ydRule/volRulePage', '日常维护', 2, sysdate, 0, NULL, 'PIVAS_MENU_652', 2, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 653, 652, '是否应用到所有病区', 2, NULL, '日常维护', 1, sysdate, 0, NULL, 'PIVAS_BTN_653', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 654, 652, '添加', 2, NULL, '日常维护', 2, sysdate, 0, NULL, 'PIVAS_BTN_654', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 655, 652, '修改', 2, NULL, '日常维护', 3, sysdate, 0, NULL, 'PIVAS_BTN_655', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 656, 652, '删除', 2, NULL, '日常维护', 4, sysdate, 0, NULL, 'PIVAS_BTN_656', 2, 3, NULL);

		-- 强制规则
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 662, 641, '强制规则', 1, '/ydRule/forceRulePage', '日常维护', 3, sysdate, 0, NULL, 'PIVAS_MENU_662', 2, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 663, 662, '是否应用到所有病区', 2, NULL, '日常维护', 1, sysdate, 0, NULL, 'PIVAS_BTN_663', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 664, 662, '添加', 2, NULL, '日常维护', 2, sysdate, 0, NULL, 'PIVAS_BTN_664', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 665, 662, '修改', 2, NULL, '日常维护', 3, sysdate, 0, NULL, 'PIVAS_BTN_665', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 666, 662, '删除', 2, NULL, '日常维护', 4, sysdate, 0, NULL, 'PIVAS_BTN_666', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 667, 662, '向上移', 2, NULL, '日常维护', 5, sysdate, 0, NULL, 'PIVAS_BTN_667', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 668, 662, '向下移', 2, NULL, '日常维护', 6, sysdate, 0, NULL, 'PIVAS_BTN_668', 2, 3, NULL);

		-- 其他规则
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 672, 641, '其他规则', 1, '/ydRule/otherRulePage', '日常维护', 4, sysdate, 0, NULL, 'PIVAS_MENU_672', 2, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 673, 672, '修改', 2, NULL, '日常维护', 1, sysdate, 0, NULL, 'PIVAS_BTN_673', 2, 3, NULL);
	----------------- i18n -------------------
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'批次规则',641,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',641,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'药物优先级',642,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',642,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'是否应用到所有病区',643,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',643,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'添加',644,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',644,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'向上移',645,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',645,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'向下移',646,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',646,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'删除',647,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',647,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'容积规则',652,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',652,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'是否应用到所有病区',653,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',653,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'添加',654,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',654,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'修改',655,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',655,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'删除',656,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',656,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'强制规则',662,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',662,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'是否应用到所有病区',663,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',663,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'添加',664,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',664,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'修改',665,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',665,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'删除',666,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',666,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'向上移',667,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',667,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'向下移',668,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',668,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'其他规则',672,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',672,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'修改',673,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',673,1);	
	------------------------------------------	
-----------------------------------------------------------------------------------------


-------------------------------   统计中心  3 --------------------------------------------
-----------------------------------------------------------------------------------------
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 3, 0, '统计中心', 0, NULL, '统计中心', 4, sysdate, 0, NULL, 'PIVAS_MENU_3', 3, 0, NULL);

-- 常用报表
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 551, 3, '常用报表', 0, NULL, '统计中心', 1, sysdate, 0, NULL, 'PIVAS_MENU_551',3, 1, NULL);

		-- 药单统计
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 552, 551, '药单统计', 1, '/statistics/prescription', '统计中心', 1, sysdate, 0, NULL, 'PIVAS_MENU_552', 3, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 554, 552, '导出', 2, NULL, '统计中心', 2, sysdate, 0, NULL, 'PIVAS_BTN_554', 3, 3, NULL);
		
		-- 配置费收费统计
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 561, 551, '配置费收费统计', 1, '/statistics/configFee', '统计中心', 2, sysdate, 0, NULL, 'PIVAS_MENU_561', 3, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 563, 561, '导出', 2, NULL, '统计中心', 2, sysdate, 0, NULL, 'PIVAS_BTN_563', 3, 3, NULL);
		
		-- 不合理医嘱统计
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 571, 551, '不合理医嘱统计', 1, '/statistics/doctorAdvice', '统计中心', 3, sysdate, 0, NULL, 'PIVAS_MENU_571', 3, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 573, 571, '导出', 2, NULL, '统计中心', 2, sysdate, 0, NULL, 'PIVAS_BTN_573', 3, 3, NULL);
		
		-- 药物使用统计
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 581, 551, '药物使用统计', 1, '/statistics/drugUse', '统计中心', 4, sysdate, 0, NULL, 'PIVAS_MENU_581', 3, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 583, 581, '导出', 2, NULL, '统计中心', 2, sysdate, 0, NULL, 'PIVAS_BTN_583', 3, 3, NULL);

---------------------------- i18n ---------------------------
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'统计中心',3,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',3,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'常用报表',551,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',551,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'药单统计',552,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',552,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'导出',554,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',554,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'配置费收费统计',561,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',561,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'导出',563,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',563,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'不合理医嘱统计',571,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',571,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'导出',573,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',573,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'药物使用统计',581,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',581,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'导出',583,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',583,1);
-------------------------------------------------------------
	
-- 绩效统计
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 701, 3, '绩效统计', 0, NULL, '统计中心', 2, sysdate, 0, NULL, 'PIVAS_MENU_701',3, 1, NULL);

		-- 拆药统计
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 973, 701, '拆药统计', 1, '/statistics/drugOpenWorkload', '统计中心', 1, sysdate, 0, NULL, 'PIVAS_MENU_973', 3, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 974, 973, '导出', 2, '', '统计中心', 1, sysdate, 0, NULL, 'PIVAS_BTN_974', 3, 3, NULL);
		
		-- 审方工作量统计
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 711, 701, '审方工作量统计', 1, '/statistics/auditPrescription', '统计中心', 2, sysdate, 0, NULL, 'PIVAS_MENU_711', 3, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 713, 711, '导出', 2, NULL, '统计中心', 2, sysdate, 0, NULL, 'PIVAS_BTN_713', 3, 3, NULL);

		-- 配置工作量统计
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 721, 701, '配置工作量统计', 1, '/statistics/allocationWorkload', '统计中心', 3, sysdate, 0, NULL, 'PIVAS_MENU_721', 3, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 723, 721, '导出', 2, NULL, '统计中心', 2, sysdate, 0, NULL, 'PIVAS_BTN_723', 3, 3, NULL);

		-- 扫描作量统计
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 724, 701, '扫描作量统计', 1, '/statistics/scanWorkload', '统计中心', 4, sysdate, 0, NULL, 'PIVAS_MENU_724', 3, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 725, 724, '导出', 2, NULL, '统计中心', 2, sysdate, 0, NULL, 'PIVAS_BTN_725', 3, 3, NULL);

---------------------------- i18n ---------------------------
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'绩效统计',701,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',701,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'拆药统计',973,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',973,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'导出',974,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',974,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'审方工作量统计',711,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',711,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'导出',713,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',713,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'配置工作量统计',721,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',721,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'导出',723,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',723,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'扫描工作量统计',724,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',724,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'导出',725,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',725,1);
-------------------------------------------------------------
-----------------------------------------------------------------------------------------

-------------------------------   排班  4 --------------------------------------------
-----------------------------------------------------------------------------------------
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 4  ,   0, '工作排班', 0, NULL, '工作排班', 3, sysdate, 0, NULL, 'PIVAS_MENU_4'  , 4, 0, NULL);

-- 排班管理
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 800,  4, '排班管理', 0, NULL, '工作排班', 1, sysdate, 0, NULL, 'PIVAS_MENU_800', 4, 1, NULL);
		
		--  开始排班
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 801, 800, '开始排班', 1, '/scheduleMgr/index', '工作排班', 1, sysdate, 0, NULL, 'PIVAS_MENU_801', 4, 2, NULL);
		
		--  排班统计
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 831, 800, '排班统计', 1, '/stat/index', '工作排班', 2, sysdate, 0, NULL, 'PIVAS_MENU_831', 4, 2, NULL);

INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 841, 800, '签到查询', 1, '/signIn/index', '工作排班', 3, sysdate, 0, NULL, 'PIVAS_MENU_841', 4, 2, NULL);

INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 851, 800, '班次统计', 1, '/workReport/initCount', '工作排班', 4, sysdate, 0, NULL, 'PIVAS_MENU_851', 4, 2, NULL);

---------------------------- i18n ---------------------------
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'工作排班',4,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',4,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'排班管理',800,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',800,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'开始排班',801,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',801,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'排班统计',831,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',831,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'签到查询',841,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',841,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'班次统计',851,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',851,1);

-------------------------------------------------------------
	--------------------------------------
-- 工作查询
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 601, 3, '工作查询',0, NULL, '统计中心', 3, sysdate, 0, NULL, 'PIVAS_MENU_601', 1, 1, NULL);

		--历史药单
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 531, 601, '历史药单', 1, '/doctorAdvice/prescriptionHis', '工作查询', 2, sysdate, 0, NULL, 'PIVAS_MENU_531', 1, 2, NULL);

		--配置费收费明细
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 541, 601, '配置费收费明细', 1, '/chargedetails/index', '工作查询', 3, sysdate, 0, NULL, 'PIVAS_MENU_541', 1, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 543, 541, '收费', 2, NULL, '工作查询', 2, sysdate, 0, NULL, 'PIVAS_BTN_543', 1, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 544, 541, '退费', 2, NULL, '工作查询', 3, sysdate, 0, NULL, 'PIVAS_BTN_544', 1, 3, NULL);

  INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 530, 601, '退费药单', 1, '/refundRP/index', '工作查询', 2, sysdate, 0, NULL, 'PIVAS_MENU_530', 1, 2, NULL);

	-------------- i18n ---------------------

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'工作查询',601,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',601,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'退费药单',530,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',530,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'历史药单',531,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',531,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'配置费收费明细',541,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',541,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'收费',543,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',543,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'退费',544,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',544,1);


--排班设置
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 820,  4, '排班设置', 0, NULL, '工作排班', 2, sysdate, 0, NULL, 'PIVAS_MENU_820', 4, 1, NULL);

		-- 人员信息
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 811, 820, '人员信息', 1, 'user/index', '工作排班', 1, sysdate, 0, NULL, 'PIVAS_MENU_811', 4, 2, NULL);

		-- 人员分组
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 812, 820, '人员分组', 1, 'userGroup/index', '工作排班', 2, sysdate, 0, NULL, 'PIVAS_MENU_812', 4, 2, NULL);

		-- 班次设置
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 821, 820, '班次设置', 1, 'workSetting/index', '工作排班', 3, sysdate, 0, NULL, 'PIVAS_MENU_821', 4, 2, NULL);

		-- 岗位设置
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 822, 820, '岗位设置', 1, 'position/index', '工作排班', 4, sysdate, 0, NULL, 'PIVAS_MENU_822', 4, 2, NULL);

		-- 排班规则
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 823, 820, '排班规则', 1, 'scheduleRule/index', '工作排班', 5, sysdate, 0, NULL, 'PIVAS_MENU_823', 4, 2, NULL);

		-- 节假日管理
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 824, 820, '节假日管理', 1, 'holidayMgr/index', '工作排班', 6, sysdate, 0, NULL, 'PIVAS_MENU_824', 4, 2, NULL);
---------------------------- i18n ---------------------------
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'排班设置',820,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',820,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'人员信息',811,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',811,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'人员分组',812,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',812,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'班次设置',821,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',821,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'岗位设置',822,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',822,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'排班规则',823,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',823,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'节假日管理',824,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',824,1);

-------------------------------------------------------------
-----------------------------------------------------------------------------------------
-------------------------------   数据配置   --------------------------------------------
-----------------------------------------------------------------------------------------
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 6, 0, '数据配置', 0, NULL, '数据配置', 5, sysdate, 0, NULL, 'PIVAS_MENU_6', 6, 0, NULL);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'数据配置',6,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',6,1);


-- 配置数据
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 202, 6, '配置数据', 0, NULL, '数据配置', 4, sysdate, 0, NULL, 'PIVAS_MENU_202', 2, 1, NULL);

		-- 药品标签
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 203, 202, '药品标签', 1, '/drugslabel/initDrugsLabel', '数据配置', 1, sysdate, 0, NULL, 'PIVAS_MENU_203', 2, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 204, 203, '新增', 2, NULL, '数据配置', 1, sysdate, 0, NULL, 'PIVAS_BTN_204', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 205, 203, '删除', 2, NULL, '数据配置', 2, sysdate, 0, NULL, 'PIVAS_BTN_205', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 207, 203, '修改', 2, NULL, '数据配置', 4, sysdate, 0, NULL, 'PIVAS_BTN_207', 2, 3, NULL);

		-- 药品分类
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 211, 202, '药品分类', 1, '/drugscategory/initDrugsCategory', '数据配置', 2, sysdate, 0, NULL, 'PIVAS_MENU_211', 2, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 212, 211, '新增', 2, NULL, '数据配置', 1, sysdate, 0, NULL, 'PIVAS_BTN_212', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 213, 211, '删除', 2, NULL, '数据配置', 2, sysdate, 0, NULL, 'PIVAS_BTN_213', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 215, 211, '修改', 2, NULL, '数据配置', 4, sysdate, 0, NULL, 'PIVAS_BTN_215', 2, 3, NULL);

		-- 核对类型
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 271, 202, '核对类型', 1, '/inspectType/initInspectType', '数据配置', 3, sysdate, 0, NULL, 'PIVAS_MENU_271', 2, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 272, 271, '新增', 2, NULL, '数据配置', 1, sysdate, 0, NULL, 'PIVAS_BTN_272', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 273, 271, '删除', 2, NULL, '数据配置', 2, sysdate, 0, NULL, 'PIVAS_BTN_273', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 275, 271, '修改', 2, NULL, '数据配置', 4, sysdate, 0, NULL, 'PIVAS_BTN_275', 2, 3, NULL);

		-- 配置费类别
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 306, 202, '配置费类别', 1, '/configFeeType/init', '数据配置', 4, sysdate, 0, NULL, 'PIVAS_MENU_306', 2, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 307, 306, '新增', 2, NULL, '数据配置', 1, sysdate, 0, NULL, 'PIVAS_BTN_307', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 308, 306, '删除', 2, NULL, '数据配置', 2, sysdate, 0, NULL, 'PIVAS_BTN_308', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 310, 306, '修改', 2, NULL, '数据配置', 4, sysdate, 0, NULL, 'PIVAS_BTN_310', 2, 3, NULL);

		-- 配置费/材料费
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 291, 202, '配置费/材料费', 1, '/configFee/init', '数据配置', 5, sysdate, 0, NULL, 'PIVAS_MENU_291', 2, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 292, 291, '新增', 2, NULL, '数据配置', 1, sysdate, 0, NULL, 'PIVAS_BTN_292', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 293, 291, '删除', 2, NULL, '数据配置', 2, sysdate, 0, NULL, 'PIVAS_BTN_293', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 295, 291, '修改', 2, NULL, '数据配置', 4, sysdate, 0, NULL, 'PIVAS_BTN_295', 2, 3, NULL);

		-- 配置费规则
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 281, 202, '配置费规则', 1, '/configFeeRule/initFeeRule', '数据配置', 6, sysdate, 0, NULL, 'PIVAS_MENU_281', 2, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 282, 281, '新增', 2, NULL, '数据配置', 1, sysdate, 0, NULL, 'PIVAS_BTN_282', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 283, 281, '删除', 2, NULL, '数据配置', 2, sysdate, 0, NULL, 'PIVAS_BTN_283', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 285, 281, '修改', 2, NULL, '数据配置', 4, sysdate, 0, NULL, 'PIVAS_BTN_285', 2, 3, NULL);

		-- 审方错误类型
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 301, 202, '审方错误类型', 1, '/errType/initErrType', '数据配置', 7, sysdate, 0, NULL, 'PIVAS_MENU_301', 2, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 302, 301, '新增', 2, NULL, '数据配置', 1, sysdate, 0, NULL, 'PIVAS_BTN_302', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 303, 301, '删除', 2, NULL, '数据配置', 2, sysdate, 0, NULL, 'PIVAS_BTN_303', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 305, 301, '修改', 2, NULL, '数据配置', 4, sysdate, 0, NULL, 'PIVAS_BTN_305', 2, 3, NULL);

		-- 医嘱列表配置(未完成)925
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 925, 202, '医嘱列表配置', 1, '/doctorShowTitle/initDdoctorShowTitle', '数据配置', 9, sysdate, 0, NULL, 'PIVAS_MENU_925', 2, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 951, 925, '新增', 2, NULL, '数据配置', 1, sysdate, 0, NULL, 'PIVAS_BTN_951', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 952, 925, '删除', 2, NULL, '数据配置', 2, sysdate, 0, NULL, 'PIVAS_BTN_952', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 954, 925, '修改', 2, NULL, '数据配置', 4, sysdate, 0, NULL, 'PIVAS_BTN_954', 2, 3, NULL);
		-- 打印条件配置(未完成)926
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 926, 202, '瓶签条件配置', 1, '/printLabelCon/initBottleLabelCon', '数据配置', 10, sysdate, 0, NULL, 'PIVAS_MENU_926', 2, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 928, 926, '新增', 2, '', '数据配置', 1, sysdate, 0, NULL, 'PIVAS_BTN_928', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 929, 926, '修改', 2, '', '数据配置', 2, sysdate, 0, NULL, 'PIVAS_BTN_929', 2, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 930, 926, '删除', 2, '', '数据配置', 3, sysdate, 0, NULL, 'PIVAS_BTN_930', 2, 3, NULL);



	----------------- i18n -------------------

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'配置数据',202,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',202,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'药品标签',203,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',203,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'新增',204,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',204,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'删除',205,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',205,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'修改',207,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',207,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'药品分类',211,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',211,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'新增',212,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',212,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'删除',213,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',213,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'修改',215,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',215,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'核对类型',271,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',271,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'新增',272,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',272,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'删除',273,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',273,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'修改',275,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',275,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'配置费类别',306,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',306,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'新增',307,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',307,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'删除',308,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',308,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'修改',310,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',310,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'配置费/材料费',291,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',291,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'新增',292,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',292,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'删除',293,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',293,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'修改',295,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',295,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'配置费规则',281,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',281,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'新增',282,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',282,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'删除',283,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',283,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'修改',285,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',285,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'审方错误类型',301,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',301,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'新增',302,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',302,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'删除',303,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',303,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'修改',305,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',305,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'医嘱列表配置',925,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',925,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'新增',951,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',951,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'删除',952,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',952,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'修改',954,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',954,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'打印条件配置',926,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',926,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'新增',928,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',928,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'修改',929,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',929,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'删除',930,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',930,1);

	------------------------------------------

-------------------------------   系统管理   --------------------------------------------
-----------------------------------------------------------------------------------------	
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 10, 0, '系统管理', 0, NULL, '系统管理', 10, sysdate, 0, NULL, 'PIVAS_MENU_5', 10, 0, NULL);

-- 用户权限
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 101, 10, '用户权限', 0, NULL, '系统管理', 1, sysdate, 0, NULL, 'PIVAS_MENU_101', 10, 1, null);

		-- 用户管理
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 117, 101, '用户管理', 1, '/sys/user/initUser', '系统管理', 1, sysdate, 0, NULL, 'PIVAS_MENU_117', 5, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 119, 117, '用户新增', 2, NULL, '系统管理', 1, sysdate, 0, NULL, 'PIVAS_BTN_119', 10, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 120, 117, '用户修改', 2, NULL, '系统管理', 3, sysdate, 0, NULL, 'PIVAS_BTN_120', 10, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 121, 117, '用户删除', 2, NULL, '系统管理', 2, sysdate, 0, NULL, 'PIVAS_BTN_121', 10, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 122, 117, '用户授权', 2, NULL, '系统管理', 8, sysdate, 0, NULL, 'PIVAS_BTN_122', 10, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 123, 117, '用户解锁', 2, NULL, '系统管理', 5, sysdate, 0, NULL, 'PIVAS_BTN_123', 10, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 124, 117, '用户密码重置', 2, NULL, '系统管理', 6, sysdate, 0, NULL, 'PIVAS_BTN_124', 10, 3, NULL);


		-- 角色管理
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 118, 101, '角色管理', 1, '/sys/role/initRole', '系统管理', 2, sysdate, 0, NULL, 'PIVAS_MENU_118', 10, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 125, 118, '角色新增', 2, NULL, '系统管理', 1, sysdate, 0, NULL, 'PIVAS_BTN_125',10, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 126, 118, '角色修改', 2, NULL, '系统管理', 4, sysdate, 0, NULL, 'PIVAS_BTN_126', 10, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 127, 118, '删除', 2, NULL, '系统管理', 2, sysdate, 0, NULL, 'PIVAS_BTN_127', 10, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 128, 118, '角色授权', 2, NULL, '系统管理', 5, sysdate, 0, NULL, 'PIVAS_BTN_128', 10, 3, NULL);
---------------------------- i18n ---------------------------

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'系统管理',10,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',10,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'用户权限',101,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',101,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'用户管理',117,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',117,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'用户新增',119,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',119,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'用户修改',120,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',120,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'用户删除',121,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',121,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'用户授权',122,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',122,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'用户解锁',123,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',123,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'用户密码重置',124,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',124,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'角色管理',118,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',118,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'角色新增',125,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',125,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'角色修改',126,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',126,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'删除',127,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',127,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'角色授权',128,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',128,1);
-------------------------------------------------------------
-- 同步设置
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 341, 10, '同步设置', 0, NULL, '系统管理', 2, sysdate, 0, NULL, 'PIVAS_MENU_341', 10, 1, NULL);

		-- 同步任务设置
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 342, 341, '同步任务设置', 1, '/synSet/initSynSet', '系统管理', 1, sysdate, 0, NULL, 'PIVAS_MENU_342', 10, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 343, 342, '新增', 2, NULL, '系统管理', 1, sysdate, 0, NULL, 'PIVAS_BTN_343', 10, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 344, 342, '删除', 2, NULL, '系统管理', 2, sysdate, 0, NULL, 'PIVAS_BTN_344', 10, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 346, 342, '修改', 2, NULL, '系统管理', 4, sysdate, 0, NULL, 'PIVAS_BTN_346', 10, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 347, 342, '启动', 2, NULL, '系统管理', 5, sysdate, 0, NULL, 'PIVAS_BTN_347', 10, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 348, 342, '停止', 2, NULL, '系统管理', 6, sysdate, 0, NULL, 'PIVAS_BTN_348', 10, 3, NULL);

		-- 同步结果查询
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 349, 341, '同步结果查询', 1, '/taskRsut/initTask', '系统管理', 2, sysdate, 0, NULL, 'PIVAS_MENU_349', 10, 2, NULL);

	----------------- i18n -------------------
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'同步设置',341,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',341,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'同步任务设置',342,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',342,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'新增',343,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',343,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'删除',344,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',344,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'修改',346,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',346,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'启动',347,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',347,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'停止',348,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',348,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'同步结果查询',349,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',349,1);

------------------------------------------

-- 系统配置
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 106, 10, '系统配置', 0, NULL, '系统管理', 3, sysdate, 0, NULL, 'PIVAS_MENU_106', 10, 1, null);

		-- 安全配置
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 114, 106, '安全配置',1, '/systemConfig/SecurityConfig', '系统管理', 1, sysdate, 0, NULL, 'PIVAS_MENU_114', 10, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 115, 114, '修改',2, NULL, '系统管理', 1, sysdate, 0, NULL, 'PIVAS_MENU_115', 10, 3, NULL);

		-- 打印机配置926
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 401, 106, '打印机配置', 1, '/printerIP/init', '系统管理', 11, sysdate, 0, NULL, 'PIVAS_MENU_401', 10, 2, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 402, 401, '新增', 2, NULL, '系统管理', 1, sysdate, 0, NULL, 'PIVAS_BTN_402', 10, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 403, 401, '修改', 2, NULL, '系统管理', 2, sysdate, 0, NULL, 'PIVAS_BTN_403', 10, 3, NULL);
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 404, 401, '删除', 2, NULL, '系统管理', 3, sysdate, 0, NULL, 'PIVAS_BTN_404', 10, 3, NULL);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'打印机配置',401,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',401,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'新增',402,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',402,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'修改',403,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',403,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'删除',404,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',404,1);

---------------------------- i18n ---------------------------
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'系统配置',106,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',106,1);	

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'安全配置',114,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',114,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'修改',115,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',115,1);
-------------------------------------------------------------
-- 操作记录
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 104, 10, '操作记录', 0, NULL, '系统管理', 10, sysdate, 0, NULL, 'PIVAS_MENU_104', 10, 1, null);

		-- 打印记录(未完成)
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 931, 104, '打印记录', 1, '/printLog/toPrintLog', '系统管理', 1, sysdate, 0, NULL, 'PIVAS_MENU_931', 10, 2, NULL);
		
		-- 操作日志
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 105, 104, '操作日志', 1, '/log/main', '系统管理', 2, sysdate, 0, NULL, 'PIVAS_MENU_105', 10, 2, null);
--INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 160, 105, '查询',2, NULL, '系统管理', 1, sysdate, 0, NULL, 'PIVAS_MENU_160', 5, 3, NULL);

		-- 库存修改记录
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 635, 104, '库存修改记录', 1, '/mdcmtLog/toStkLog', '系统管理', 3, sysdate, 0, NULL, 'PIVAS_MENU_635', 10, 2, NULL);

		-- 登录二维码
INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 108, 104, '登录二维码', 2, '/qrcode/CreateQrcode', '系统管理', 1, sysdate, 0, NULL, 'PIVAS_MENU_108', 10, 2, null);

---------------------------- i18n ---------------------------
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'操作记录',104,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',104,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'打印记录',931,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',931,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'操作日志',105,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',105,1);

--INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'查询',160,1);
--INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',160,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'库存修改记录',635,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',635,1);

INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(1,'登录二维码',108,1);
INSERT INTO SYS_I18N ( language_id,name,object_id,type ) VALUES(2,'',108,1);
-------------------------------------------------------------
-----------------------------------------------------------------------------------------		

-----------------------------------------------------------------------------------------

------------------------------------  新系统不需要的  -----------------------------------

--INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 591, 1, '扫描核对', 0, NULL, 'PIVAS管理', 3, sysdate, 0, NULL, 'PIVAS_MENU_591', 1, 1, NULL);
--INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 621, 1, '打印管理', 0, NULL, 'PIVAS管理', 4, sysdate, 0, NULL, 'PIVAS_MENU_621', 1, 1, NULL);

----人员绩效管理
--INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 4, 0, '人员绩效管理', 0, NULL, '人员绩效管理', 4, sysdate, 0, NULL, 'PIVAS_MENU_4', 4, 0, NULL);
--INSERT INTO SYS_PRIVILEGE ( privilege_id, parent_id, NAME, type, url, module, seq, create_time, is_https, description, privilege_code, sys_code, "LEVEL", icon) VALUES( 701, 4, '人员绩效统计', 0, NULL, '人员绩效管理', 1, sysdate, 0, NULL, 'PIVAS_MENU_701', 4, 1, null);

-----------------------------------------------------------------------------------------

-- -----------------------------------------------------------------------------------------
-- 子系统国际化
-- -----------------------------------------------------------------------------------------
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(1,'SYS_LOG_OPER.sub_system','业务管理','1',1);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(2,'SYS_LOG_OPER.sub_system','日常维护','2',2);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(3,'SYS_LOG_OPER.sub_system','统计中心','3',3);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(4,'SYS_LOG_OPER.sub_system','工作排班','4',4);
-- INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(5,'SYS_LOG_OPER.sub_system','系统管理','5',5);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(6,'SYS_LOG_OPER.sub_system','数据配置','6',6);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(10,'SYS_LOG_OPER.sub_system','系统管理','10',7);
-- -----------------------------------------------------------------------------------------
-- 操作日志；子系统国际化（中文）
-- -----------------------------------------------------------------------------------------
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(1,'1','业务管理');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(2,'1','日常维护');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(3,'1','统计中心');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(4,'1','工作排班');
-- INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(5,'1','系统管理');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(6,'1','数据配置');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(10,'1','系统管理');
-- -----------------------------------------------------------------------------------------
-- 操作日志；子系统国际化（英文）
-- -----------------------------------------------------------------------------------------
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(1,'2','PIVAS Management');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(2,'2','Daily Maintenance');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(3,'2','Statistical Center');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(4,'2','Scheduling system');
-- INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(5,'2','System Management');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(6,'2','Other Management');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(10,'2','System Management');



-- ----------------------------------------------------------------------------------------------
-- 操作日志： 系统管理（SM）
-- ----------------------------------------------------------------------------------------------
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(10003,'SYS_LOG_OPER.module.sm','登录','SM_100',4);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(10004,'SYS_LOG_OPER.module.sm','注销','SM_101',5);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(10005,'SYS_LOG_OPER.module.sm','个人信息修改','SM_102',6);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(10006,'SYS_LOG_OPER.module.sm','密码修改','SM_103',7);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(10007,'SYS_LOG_OPER.status','失败','0',1);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(10008,'SYS_LOG_OPER.status','成功','1',2);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(20001,'scans.scansResult','成功','0',1);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(20002,'scans.scansResult','失败','1',2);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(20003,'scans.scansResult','重复扫描','2',3);

-- ----------------------------------------------------------------------------------------------
-- 操作日志： 系统管理（SM）国际化（中文）
-- ----------------------------------------------------------------------------------------------
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(10003,'1','登录');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(10004,'1','注销');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(10005,'1','个人信息修改');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(10006,'1','密码修改');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(10007,'1','失败');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(10008,'1','成功');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(20001,'1','成功');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(20002,'1','失败');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(20003,'1','重复扫描');

-- ----------------------------------------------------------------------------------------------
-- 操作日志： 系统管理（SM）国际化（英文）
-- ----------------------------------------------------------------------------------------------
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(10003,'2','登录en');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(10004,'2','注销en');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(10005,'2','个人信息修改en');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(10006,'2','密码修改en');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(10007,'2','failure');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(10008,'2','success');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(20001,'2','success');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(20002,'2','failure');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(20003,'2','repeat');

-- pivas管理
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(101,'SYS_LOG_OPER.module.pm','医嘱','PM_1',1);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(102,'SYS_LOG_OPER.module.pm','审方核对','PM_2',2);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(103,'SYS_LOG_OPER.module.pm','批次优化','PM_3',3);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(104,'SYS_LOG_OPER.module.pm','打印瓶签','PM_4',4);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(105,'SYS_LOG_OPER.module.pm','瓶签扫描','PM_5',5);  

-- 日常维护
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(111,'SYS_LOG_OPER.module.cf','药品','CF_1',1);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(112,'SYS_LOG_OPER.module.cf','病区','CF_2',2);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(113,'SYS_LOG_OPER.module.cf','病人','CF_3',3);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(114,'SYS_LOG_OPER.module.cf','员工信息','CF_4',4);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(115,'SYS_LOG_OPER.module.cf','用药途径','CF_5',5);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(116,'SYS_LOG_OPER.module.cf','用药频次','CF_6',6);

INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(117,'SYS_LOG_OPER.module.cf','同步任务设置','CF_7',7);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(118,'SYS_LOG_OPER.module.cf','同步结果查询','CF_8',8);

INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(119,'SYS_LOG_OPER.module.cf','病区拆分规则','CF_9',9);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(120,'SYS_LOG_OPER.module.cf','病区分组','CF_10',10);

INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(121,'SYS_LOG_OPER.module.cf','药品标签','CF_11',11);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(122,'SYS_LOG_OPER.module.cf','药品分类','CF_12',12);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(123,'SYS_LOG_OPER.module.cf','核对类型','CF_13',13);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(124,'SYS_LOG_OPER.module.cf','配置费类别','CF_14',14);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(125,'SYS_LOG_OPER.module.cf','配置费/材料费','CF_15',15);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(126,'SYS_LOG_OPER.module.cf','配置费规则','CF_16',16);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(127,'SYS_LOG_OPER.module.cf','审方错误类型','CF_17',17);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(128,'SYS_LOG_OPER.module.cf','医嘱列表配置','CF_18',18);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(129,'SYS_LOG_OPER.module.cf','打印条件配置','CF_19',19);

INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(131,'SYS_LOG_OPER.module.cf','批次定义','CF_21',21);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(132,'SYS_LOG_OPER.module.cf','一般规则','CF_22',22);  

INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(133,'SYS_LOG_OPER.module.cf','药物优先级','CF_23',23);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(134,'SYS_LOG_OPER.module.cf','容积规则','CF_24',24);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(135,'SYS_LOG_OPER.module.cf','强制规则','CF_25',25);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(136,'SYS_LOG_OPER.module.cf','其他规则','CF_26',26);
--INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(137,'SYS_LOG_OPER.module.cf','计量单位','CF_27',27);

--统计中心
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(141,'SYS_LOG_OPER.module.tj','历史药单','TJ_1',1);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(142,'SYS_LOG_OPER.module.tj','配置费收费明细','TJ_2',2);

--排班
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(151,'SYS_LOG_OPER.module.sc','开始排班','SC_1',1);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(152,'SYS_LOG_OPER.module.sc','排班统计','SC_2',2);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(153,'SYS_LOG_OPER.module.sc','人员信息','SC_3',3);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(154,'SYS_LOG_OPER.module.sc','人员分组','SC_4',4);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(155,'SYS_LOG_OPER.module.sc','班次设置','SC_5',5);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(156,'SYS_LOG_OPER.module.sc','岗位设置','SC_6',6);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(157,'SYS_LOG_OPER.module.sc','排班规则','SC_7',7);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(158,'SYS_LOG_OPER.module.sc','节假日管理','SC_8',8);

--系统管理
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(161,'SYS_LOG_OPER.module.sm','用户管理','SM_1',1);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(162,'SYS_LOG_OPER.module.sm','角色管理','SM_2',2);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(163,'SYS_LOG_OPER.module.sm','安全配置','SM_3',3);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(164,'SYS_LOG_OPER.module.sm','打印记录','SM_4',4);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(165,'SYS_LOG_OPER.module.sm','操作日志','SM_5',5);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(166,'SYS_LOG_OPER.module.sm','库存修改记录','SM_6',6);

--其他管理
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(171,'SYS_LOG_OPER.module.oc','库存单','OC_1',1);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(172,'SYS_LOG_OPER.module.oc','拆药量确认','OC_2',2);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(173,'SYS_LOG_OPER.module.oc','药品破损登记','OC_3',3);

-- pivas管理
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(101,'1','医嘱');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(101,'2','Prescribed');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(102,'1','审方核对');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(102,'2','Trial check');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(103,'1','批次优化');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(103,'2','Batch optimization');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(104,'1','打印瓶签');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(104,'2','Print bottle label');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(105,'1','瓶签扫描');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(105,'2','Bottle scan');

-- 日常维护
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(111,'1','药品');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(111,'2','Drugs');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(112,'1','病区');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(112,'2','Inpatient Area');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(113,'1','病人');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(113,'2','Patient');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(114,'1','员工信息');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(114,'2','Employee information');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(115,'1','用药途径');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(115,'2','Medication route');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(116,'1','用药频次');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(116,'2','Medication frequency');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(117,'1','同步任务设置');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(117,'2','Synchronous task settings');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(118,'1','同步结果查询');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(118,'2','Synchronous result query');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(119,'1','病区拆分规则');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(119,'2','Ward split rule');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(120,'1','病区分组');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(120,'2','Ward grouping');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(121,'1','药品标签');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(121,'2','Drug labels');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(122,'1','药品分类');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(122,'2','Drug class');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(123,'1','核对类型');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(123,'2','Check type');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(124,'1','配置费类别');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(124,'2','Configuration fee type');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(125,'1','配置费/材料费');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(125,'2','Configuration fee');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(126,'1','配置费规则');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(126,'2','Configuration fee rule');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(127,'1','审方错误类型');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(127,'2','Trial error type');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(128,'1','医嘱列表配置');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(128,'2','Prescribed list configuration');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(129,'1','打印条件配置');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(129,'2','Print configuration');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(131,'1','批次定义');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(131,'2','Batch define');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(132,'1','一般规则');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(132,'2','General rule');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(133,'1','药物优先级');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(133,'2','Drug priority');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(134,'1','容积规则');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(134,'2','Volume rule');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(135,'1','强制规则');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(135,'2','Mandatory rules');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(136,'1','其他规则');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(136,'2','Other rules');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(137,'1','计量单位');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(137,'2','Unit of measurement');

--统计中心
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(141,'1','历史药单');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(141,'2','The history of Medicine');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(142,'1','配置费收费明细');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(142,'2','Configuration fee details');

--排班
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(151,'1','开始排班');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(151,'2','Start scheduling');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(152,'1','排班统计');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(152,'2','Scheduling statistics');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(153,'1','人员信息');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(153,'2','Personnel information');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(154,'1','人员分组');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(154,'2','Personnel grouping');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(155,'1','班次设置');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(155,'2','Shift setting');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(156,'1','岗位设置');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(156,'2','Post setting');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(157,'1','排班规则');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(157,'2','Scheduling rules');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(158,'1','节假日管理');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(158,'2','Holiday management');

--系统管理
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(161,'1','用户管理');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(161,'2','user management');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(162,'1','角色管理');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(162,'2','Role management');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(163,'1','安全配置');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(163,'2','Security configuration');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(164,'1','打印记录');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(164,'2','Print record');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(165,'1','操作日志');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(165,'2','Operation log');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(166,'1','库存修改记录');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(166,'2','Inventory modification record');

--其他管理
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(171,'1','库存单');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(171,'2','Stock list');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(172,'1','拆药量确认');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(172,'2','Dose verification');

INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(173,'1','药品破损登记');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(173,'2','Drug damage registration');



-- ----------------------------------------------------------------------
-- 初始化数据（安全配置）
-- ----------------------------------------------------------------------

INSERT INTO SYS_CONFIG (sys_key,sys_value,type,description) VALUES('sdVersion', '1.0', '7', '系统版本号');
INSERT INTO SYS_CONFIG (sys_key,sys_value,type,description) VALUES('clear_time', '30', '7', '解锁时间');
INSERT INTO SYS_CONFIG (sys_key,sys_value,type,description) VALUES('error_count', '5', '7', '登陆错误次数');
INSERT INTO SYS_CONFIG (sys_key,sys_value,type,description) VALUES('error_time', '10', '7', '错误间隔时间');
INSERT INTO SYS_CONFIG (sys_key,sys_value,type,description) VALUES('sendFrom', 'public@zc.com', '2', '邮件发送方地址');
INSERT INTO SYS_CONFIG (sys_key,sys_value,type,description) VALUES('smtpServer', 'smtp.zc.com', '2', '邮件服务器地址');
INSERT INTO SYS_CONFIG (sys_key,sys_value,type,description) VALUES('smtpServerUserName', 'jqzheng', '2', '邮件服务器用户名');
INSERT INTO SYS_CONFIG (sys_key,sys_value,type,description) VALUES('smtpServerUserPassword', '253EE12B63E76D6E5087E6242088B8D2', '2', '邮件服务器密码');
INSERT INTO SYS_CONFIG (sys_key,sys_value,type,description) VALUES('max_online_count', '100',  '7', '最大在线用户数');
INSERT INTO SYS_CONFIG (sys_key,sys_value,type,description) VALUES('pwd_not_repeat_count', '12', '7', '前N个口令不重复的次数');
INSERT INTO SYS_CONFIG (sys_key,sys_value,type,description) VALUES('user_pwd_indate', '90', '7', '普通用户口令有效期（天）');
INSERT INTO SYS_CONFIG (sys_key,sys_value,type,description) VALUES('pwd_expire_notice_days', '7', '7', '口令到期提前通知天数（天）');
INSERT INTO SYS_CONFIG (sys_key,sys_value,type,description) VALUES('yz_localKnowledge_limit', '3', '7', '自我知识库,同种数据最低出现次数');
INSERT INTO SYS_CONFIG (sys_key,sys_value,type,description) VALUES('yz_localKnowledge_params', 'WARD_CODE;SUPPLY_CODE;CHARGE_CODE;SPECIFICATIONS;DOSE;DOSE_UNIT;QUANTITY', '7', '自我知识库,匹配字段');

-- 提交事务
COMMIT;

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10009, 1, '一般');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10009, 2, 'General');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10010, 1, '严重');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10010, 2, 'Serious');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10011, 1, '警告');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10011, 2, 'Warning');

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10009, 'pivas.errortype.level', '一般', '0', 1);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10010, 'pivas.errortype.level', '严重', '1', 2);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10011, 'pivas.errortype.level', '警告', '2', 3);


insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10012, 'pivas.measureunit.model', '大于参考单位', '0', 1);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10013, 'pivas.measureunit.model', '这个类别的参考计量单位', '1', 2);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10014, 'pivas.measureunit.model', '小于参考单位', '2', 3);

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10012, 1, '大于参考单位');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10012, 2, 'More than');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10013, 1, '这个类别的参考计量单位');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10013, 2, 'Equal');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10014, 1, '小于参考单位');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10014, 2, 'Less than');

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10015, 'pivas.configfee.type', '其他', '0', 1);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10016, 'pivas.configfee.type', '抗肿瘤', '1', 2);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10017, 'pivas.configfee.type', '营养液', '2', 3);

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10015, 1, '其它');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10015, 2, 'others');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10016, 1, '抗肿瘤');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10016, 2, 'Anti tumor');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10017, 1, '营养液');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10017, 2, 'Nutrient solution');

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10019, 'pivas.checktype.type', '入仓核对', '0', 1);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10020, 'pivas.checktype.type', '出仓核对', '2', 2);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10054, 'pivas.checktype.type', '仓内核对', '1', 3);

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10019, 1, '入仓核对');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10019, 2, 'Stock check');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10020, 1, '出仓核对');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10020, 2, 'Check out warehouse');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10054, 1, '仓内核对');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10054, 2, 'warehouse inside check');

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10021, 'pivas.patient.ageunit', '天', '0', 1);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10022, 'pivas.patient.ageunit', '月', '1', 2);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10023, 'pivas.patient.ageunit', '年', '2', 3);


insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10021, 1, '天');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10021, 2, 'Day');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10022, 1, '月');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10022, 2, 'Month');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10023, 1, '岁');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10023, 2, 'Year');

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10024, 'pivas.measureunit.unity', '升', '0', 1);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10025, 'pivas.measureunit.unity', '毫升', '1', 2);

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10024, 1, '升');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10024, 2, 'L');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10025, 1, '毫升');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10025, 2, 'ml');

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10026, 'pivas.common.yesorno', '是', '0', 1);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10027, 'pivas.common.yesorno', '否', '1', 2);


insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10026, 1, '是');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10026, 2, 'YES');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10027, 1, '否');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10027, 2, 'NO');

--间隔单位
insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10028, 'pivas.synsetting.intervalunit', '分钟', '0', 1);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10029, 'pivas.synsetting.intervalunit', '天', '1', 2);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10030, 'pivas.synsetting.tasktype', '定时任务', '0', 1);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10031, 'pivas.synsetting.tasktype', '一次性任务', '1', 2);


insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10028, 1, '分钟');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10028, 2, 'Minute');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10029, 1, '天');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10029, 2, 'Day');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10030, 1, '定时任务');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10030, 2, 'Timed task');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10031, 1, '一次性任务');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10031, 2, 'One time task');


insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10032, 'pivas.synsetting.taskcontenttype', '病人', '0', 1);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10033, 'pivas.synsetting.taskcontenttype', '医嘱', '1', 2);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10034, 'pivas.synsetting.taskcontenttype', '病区', '3', 4);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10035, 'pivas.synsetting.taskcontenttype', '用药途径', '5', 5);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10036, 'pivas.synsetting.taskcontenttype', '用药频次', '4', 4);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10037, 'pivas.synsetting.taskcontenttype', '药品', '2', 3);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10047, 'pivas.synsetting.taskcontenttype', '药单执行记录', '6', 6);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10048, 'pivas.synsetting.taskcontenttype', '员工', '7', 7);

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10048, 1, '员工');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10048, 2, 'employee');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10047, 1, '药单执行记录');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10047, 2, 'record');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10032, 1, '病人');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10032, 2, 'Patient');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10033, 1, '医嘱');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10033, 2, 'Doctor');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10034, 1, '病区');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10034, 2, 'Inpatient Area');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10035, 1, '用药途径');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10035, 2, 'Medication');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10036, 1, '用药频次');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10036, 2, 'Order frequency');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10037, 1, '药品');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10037, 2, 'Drugs');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10051, 1, '正常');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10051, 2, 'normal');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10052, 1, '停止');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10052, 2, 'stop');


insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10038, 'pivas.synsetting.taskstatus', '启动', '0', 1);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10039, 'pivas.synsetting.taskstatus', '停止', '1', 2);

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10038, 1, '启动');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10038, 2, 'Start');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10039, 1, '停止');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10039, 2, 'Stop');


insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10040, 'pivas.common.successorfail', '成功', '0', 1);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10041, 'pivas.common.successorfail', '失败', '1', 2);

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10040, 1, '成功');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10040, 2, 'Success');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10041, 1, '失败');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10041, 2, 'Fail');

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10042, 'pivas.measureunit.protype', '单件', '0', 1);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10043, 'pivas.measureunit.protype', '重量', '1', 2);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10044, 'pivas.measureunit.protype', '工作时间', '2', 3);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10045, 'pivas.measureunit.protype', '长度或距离', '3', 4);

insert into SYS_DICT (DICT_ID, CATEGORY, DESCRIPTION, CODE, SORT)
values (10046, 'pivas.measureunit.protype', '体积', '4', 5);


insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10042, 1, '单件');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10042, 2, 'Single');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10043, 1, '重量');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10043, 2, 'Weight');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10044, 1, '工作时间');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10044, 2, 'Working hours');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10045, 1, '长度或距离');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10045, 2, 'Length or distance');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10046, 1, '体积');

insert into SYS_DICT_INFO (DICT_ID, LANGUAGE_ID, CONTENT)
values (10046, 2, 'Volume');

-- --------------------------------- --------------------------------------------------------
-- 子系统国际化
-- -----------------------------------------------------------------------------------------
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(11101,'medicSingle.Statistics.type','批次统计','0',1);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(11102,'medicSingle.Statistics.type','病区统计','1',2);

-- -----------------------------------------------------------------------------------------
-- 操作日志；子系统国际化（中文）
-- -----------------------------------------------------------------------------------------
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(11101,'1','批次统计');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(11102,'1','病区统计');
-- -----------------------------------------------------------------------------------------
-- 操作日志；子系统国际化（英文）
-- -----------------------------------------------------------------------------------------
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(11101,'2','Batch statistics');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(11102,'2','Ward statistics');

-- -----------------------------------------------------------------------------------------
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(11201,'staticDoctor.Statistics.type','医生统计','0',1);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(11202,'staticDoctor.Statistics.type','病区统计','1',2);

--打印瓶签 单个药品总数量
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(30001,'pivas.print.medictotalcount','<=10','0',1);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(30002,'pivas.print.medictotalcount','=20','1',2);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(30003,'pivas.print.medictotalcount','>=35','2',3);


--打印瓶签 单个药单药品总数
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(30004,'pivas.print.medicsinglecount','=1','0',1);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(30005,'pivas.print.medicsinglecount','<=3','1',2);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES(30006,'pivas.print.medicsinglecount','>5','2',3);

INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES (30101,'pivas.drugdamage.damageproblem','一般','0',1);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES (30102,'pivas.drugdamage.damageproblem','良好', '1', 2);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES (30103,'pivas.drugdamage.damageproblem','较好', '2', 3);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES (30104,'pivas.drugdamage.damageproblem','严重', '3', 4);

INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES (30131,'pivas.drugdamage.damageLink','搬运一', '0', 1);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES (30132,'pivas.drugdamage.damageLink','搬运二', '1', 2);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES (30133,'pivas.drugdamage.damageLink','搬运三', '2', 3);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES (30134,'pivas.drugdamage.damageLink','搬运四', '3', 4);
INSERT INTO SYS_DICT (dict_id,category,description,code,sort) VALUES (30135,'pivas.drugdamage.damageLink','搬运五', '4', 5);

-- -----------------------------------------------------------------------------------------
-- 操作日志；子系统国际化（中文）
-- -----------------------------------------------------------------------------------------
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(11201,'1','医生统计');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(11202,'1','病区统计');
-- -----------------------------------------------------------------------------------------
-- 操作日志；子系统国际化（英文）
-- -----------------------------------------------------------------------------------------
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(11201,'2','Doctor statistics');
INSERT INTO SYS_DICT_INFO (dict_id,language_id,content) VALUES(11202,'2','Ward statistics');
commit;

--其他规则，需要初始化参数配置
insert into SRVS_OTHER_RULE (OR_ID, OR_TYPE, OR_NAME, OR_DESC, OR_SORT, ENABLED)
values (1, 41, 'treeRuleRun', '是否启用三大规则', 1, 1);

insert into SRVS_OTHER_RULE (OR_ID, OR_TYPE, OR_NAME, OR_DESC, OR_SORT, ENABLED)
values (2, 41, 'kongPCRun', '空批是否参与批次调整', 2, 1);

insert into SRVS_OTHER_RULE (OR_ID, OR_TYPE, OR_NAME, OR_DESC, OR_SORT, ENABLED)
values (3, 41, 'kongPCVolRun', '空批是否计算容积', 3, 1);

insert into SRVS_OTHER_RULE (OR_ID, OR_TYPE, OR_NAME, OR_DESC, OR_SORT, ENABLED)
values (4, 41, 'volAutoUpDown', '当输液无法继续时，剩余的一日的多次的输液往上靠', 4, 1);

insert into SRVS_OTHER_RULE (OR_ID, OR_TYPE, OR_NAME, OR_DESC, OR_SORT, ENABLED)
values (5, 41, 'oneMedToKong', '单个药品是否落空批', 5, 1);

--服务器结点配置样例
INSERT INTO SYS_SERVER_NODE VALUES(NULL,'127.0.0.1','MAINSERVER',0,'8080,8081,61616','/pivasBase/app/heart,/dem/pivasBase/heart/heartbeat,TEST');

--核对类型初始化参数
insert into SRVS_CHECKTYPE (orderid, checkname, isshow, ischarge, iseffect, reserve0, reserve1, reserve2, gid, checktype)
values (2, '仓内扫描', 0, 0, 0, null, null, null, NULL, 1);
insert into SRVS_CHECKTYPE (orderid, checkname, isshow, ischarge, iseffect, reserve0, reserve1, reserve2, gid, checktype)
values (1, '进仓核对', 0, 1, 0, null, null, null, NULL, 0);
insert into SRVS_CHECKTYPE (orderid, checkname, isshow, ischarge, iseffect, reserve0, reserve1, reserve2, gid, checktype)
values (3, '出仓扫描', 0, 1, 0, null, null, null,NULL, 2);


insert into srvs_batch (NUM_, NAME_, IS_EMPTY_BATCH_, START_TIME_, END_TIME_, IS_SECEND_ADVICE_, COLOR_, ENABLED_, RESERVE1_, RESERVE2_, RESERVE3_, IS0P, TIMETYPE, ORDER_NUM)
values ('1#', '1#', 0, null, null, 0, null, 1, '1', null, null, 0, 'other', 100);

insert into srvs_batch (NUM_, NAME_, IS_EMPTY_BATCH_, START_TIME_, END_TIME_, IS_SECEND_ADVICE_, COLOR_, ENABLED_, RESERVE1_, RESERVE2_, RESERVE3_, IS0P, TIMETYPE, ORDER_NUM)
values ('2#', '2#', 0, null, null, 0, null, 1, '1', null, null, 0, 'other', 101);

insert into srvs_batch (NUM_, NAME_, IS_EMPTY_BATCH_, START_TIME_, END_TIME_, IS_SECEND_ADVICE_, COLOR_, ENABLED_, RESERVE1_, RESERVE2_, RESERVE3_, IS0P, TIMETYPE, ORDER_NUM)
values ('3#', '3#', 0, null, null, 0, null, 1, '1', null, null, 0, 'other', 102);

insert into srvs_batch (NUM_, NAME_, IS_EMPTY_BATCH_, START_TIME_, END_TIME_, IS_SECEND_ADVICE_, COLOR_, ENABLED_, RESERVE1_, RESERVE2_, RESERVE3_, IS0P, TIMETYPE, ORDER_NUM)
values ('4#', '4#', 0, null, null, 0, null, 1, '1', null, null, 0, 'other', 103);

insert into srvs_batch (NUM_, NAME_, IS_EMPTY_BATCH_, START_TIME_, END_TIME_, IS_SECEND_ADVICE_, COLOR_, ENABLED_, RESERVE1_, RESERVE2_, RESERVE3_, IS0P, TIMETYPE, ORDER_NUM)
values ('5#', '5#', 0, null, null, 0, null, 1, '1', null, null, 0, 'other', 104);

commit;


INSERT INTO "SCHE_USER_GROUP" VALUES (sq_group.nextval, '默认组', sysdate, '1', '0', '0');

insert into SCHE_WORK ( WORKNAME, DEFINETYPE, WORKTYPE, TOTALTIME, TIME_INTERVAL, WORKCOLOUR, WORKSTATUS, STATUS, COUNT, CREATIONTIME, CREATER, COPYID, SPLITWORK)
values ('正常休息', '1', '2', '8', '08:00-16:00', '#FFFFFF', '1', '1', 0,sysdate, 1, null, '0');

commit;