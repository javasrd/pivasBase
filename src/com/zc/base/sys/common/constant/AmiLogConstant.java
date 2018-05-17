package com.zc.base.sys.common.constant;


public abstract interface AmiLogConstant {
    public static abstract interface BRANCH_SYSTEM {
        public static final Integer PM = Integer.valueOf(1);

        public static final Integer CF = Integer.valueOf(2);

        public static final Integer TJ = Integer.valueOf(3);

        public static final Integer SC = Integer.valueOf(4);

        public static final Integer SM = Integer.valueOf(5);

        public static final Integer OC = Integer.valueOf(6);

        public static final Integer DC = Integer.valueOf(7);
    }

    public static abstract interface MODULE_BRANCH {
        public static abstract interface CF {
            public static final String DRUGS = "CF_1";
            public static final String INPATIENT_AREA = "CF_2";
            public static final String PATIENT = "CF_3";
            public static final String EMPLOYEE_INFO = "CF_4";
            public static final String DRUG_WAY = "CF_5";
            public static final String DRUG_FREQUENCY = "CF_6";
            public static final String SYNCHRONOUS_TASK_SETTING = "CF_7";
            public static final String SYNCHRONOUS_RESULT_QUERY = "CF_8";
            public static final String WARD_SPLIT_RULE = "CF_9";
            public static final String WARD_GROUP = "CF_10";
            public static final String DRUG_LABELS = "CF_11";
            public static final String DRUG_CLASS = "CF_12";
            public static final String CHECK_TYPE = "CF_13";
            public static final String CONFIG_FEE_TYPE = "CF_14";
            public static final String CONFIGFEE = "CF_15";
            public static final String CONFIG_FEE_RULE = "CF_16";
            public static final String TRIAL_ERROR_TYPE = "CF_17";
            public static final String TITLE_SHOW = "CF_18";
            public static final String print_conf = "CF_19";
            public static final String BATCH = "CF_21";
            public static final String GENERAL_RULE = "CF_22";
            public static final String ROW_MEDIC_RULE = "CF_23";
            public static final String ROW_VOLUME_RULE = "CF_24";
            public static final String ROW_FORCE_RULE = "CF_25";
            public static final String ROW_OTHER_RULE = "CF_26";
            public static final String MEASUREMENT_UNIT = "CF_27";
        }

        public static abstract interface OC {
            public static final String STOCK_LIST = "OC_1";
            public static final String DOSE_VERIFICATION = "OC_2";
            public static final String DRUG_DAMAGE = "OC_3";
        }

        public static abstract interface PM {
            public static final String Doctor = "PM_1";
            public static final String PHARMACIST_REVIEW = "PM_2";
            public static final String ROW_BATCH_REORDER = "PM_3";
            public static final String ROW_BATCH_CONFIG = "PM_4";
            public static final String PRINT_BOTTLE = "PM_5";
            public static final String BOTTLE_SCAN = "PM_6";
        }

        public static abstract interface SC {
            public static final String START_SCHEDULE = "SC_1";
            public static final String SCHEDULE_STATISTICS = "SC_2";
            public static final String PERSONNEL_INFO = "SC_3";
            public static final String PERSONNEL_GROUP = "SC_4";
            public static final String SHIFT_SETTING = "SC_5";
            public static final String POST_SETTING = "SC_6";
            public static final String SCHEDULING_RULES = "SC_7";
            public static final String HOLIDAY_MANAGEMENT = "SC_8";
        }

        public static abstract interface SM {
            public static final String USER_MANAGEMENT = "SM_1";
            public static final String ROLE_MANAGEMENT = "SM_2";
            public static final String SECURITY_CONFIG = "SM_3";
            public static final String PRINT_RECORD = "SM_4";
            public static final String OPERATION_LOG = "SM_5";
            public static final String INVENTORY_MODIF_RECORD = "SM_6";
            public static final String LOGIN = "SM_100";
            public static final String LOGOUT = "SM_101";
            public static final String MODIFY_SELF_INFO = "SM_102";
            public static final String MODIFY_SELF_PASSWORD = "SM_103";
        }

        public static abstract interface TJ {
            public static final String HISTORY_MEDICINE = "TJ_1";
            public static final String CONFIGURATION_FEE_CHARGE = "TJ_2";
        }
    }
}
