package com.zc.schedule.common.util;

public interface SysConstant {
    // 语言类型
    String ZH = "zh";
    String EN = "en";

    String DEFAULT_LANGUAGE = ZH;

    // 状态
    int SUCCESS = 1;
    int FAIL = 0;

    // 返回代码

    int RET_OK = 1;
    int RET_ERR = -1;

    interface resultMess {

        String OK = "操作成功";
        String Err = "操作失败";

        String qryOK = "查询成功";
        String qryErr = "查询失败";

        String addOK = "添加成功";
        String addErr = "添加失败";

        String updOK = "更新成功";
        String updErr = "更新失败";

        String delOK = "删除成功";
        String delErr = "删除失败";

    }

    int PAGENUMBER = 10;

    interface sessionName {
        String userId = "session_userId";
        String userName = "session_userName";
        String userNick = "session_userNick";
        String language = "session_language";//zh en
        String setting = "session_setting";
    }


    /**
     * 排班管理常量
     */
    int SCHEDULEMGR = 4;

    String 人员信息 = "SC_1";
    String 人员分组 = "SC_2";
    String 班次设置 = "SC_3";
    String 岗位设置 = "SC_4";
    String 排班规则 = "SC_5";
    String 排班管理 = "SC_6";
    String 统计报表 = "SC_7";
    String 节假日管理 = "SC_8";
    String 签到 = "SC_9";

}
