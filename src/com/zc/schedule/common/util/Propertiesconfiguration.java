/*
 * 文 件 名:  Propertiesconfiguration.java
 * 版    权:  zc Technologies Co., Ltd. Copyright 2013-2023,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  gongzhuang
 * 修改时间:  2013-3-25
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.zc.schedule.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 属性文件管理类
 *
 * @author Justin
 * @version v1.0
 */
public class Propertiesconfiguration {

    /**
     * 加载资源文件中系统属性配置文件
     */
    private static final String APP_PROPERTIES_FILE = "/application.properties";

    /**
     * 加载日志文件
     */
    private static final Log logger = LogFactory.getLog(Propertiesconfiguration.class);

    /**
     * 定义Properties对象
     */
    private static Properties p = null;

    static {
        init();
    }

    /**
     * 默认构造函数
     */
    public Propertiesconfiguration() {
    }

    /**
     * 初始化加载系统配置文件
     */
    protected static void init() {
        InputStream in = null;

        try {
            // 读取系统配置文件文件流
            in = Propertiesconfiguration.class.getResourceAsStream(APP_PROPERTIES_FILE);

            if (in != null) {
                if (p == null) {
                    p = new Properties();
                }
                p.load(in);
            }
        } catch (IOException e) {
            logger.error("load " + APP_PROPERTIES_FILE + " into Constants error!");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("close " + APP_PROPERTIES_FILE + " error!");
                }
            }
        }
    }

    /**
     * 根据传入的key值，获取相应的Value
     *
     * @param key 传入的key
     * @return String 相应的Value
     */
    public static String getStringProperty(String key) {
        if (null != p) {
            return p.getProperty(key);
        }
        return "";
    }
}
