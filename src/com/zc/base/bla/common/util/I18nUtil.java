package com.zc.base.bla.common.util;

import com.zc.base.sys.modules.sysconfig.entity.SystemConfig;
import com.zc.base.sys.modules.sysconfig.facade.SystemConfigFacade;

import java.util.Locale;
import java.util.ResourceBundle;


public class I18nUtil {
    public static String getString(String message) {
        String language = ((SystemConfig) SystemConfigFacade.getMap().get("exportExcelLanguage")).getSys_value();

        Locale myLocale = null;
        if (language.equals("zh_CN")) {
            myLocale = new Locale("zh");
        } else {
            myLocale = new Locale("en");
        }

        ResourceBundle bundle = ResourceBundle.getBundle("i18n.message", myLocale);

        try {
            return bundle.getString(message);
        } catch (Exception e) {
        }

        return message;
    }


    public static String getString(String message, String[] params) {
        String language = (SystemConfigFacade.getMap().get("exportExcelLanguage")).getSys_value();

        Locale myLocale = null;
        String value = null;
        if (language.equals("zh_CN")) {
            myLocale = new Locale("zh");
        } else {
            myLocale = new Locale("en");
        }

        ResourceBundle bundle = ResourceBundle.getBundle("i18n.message", myLocale);

        try {
            value = bundle.getString(message);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    value = value.replace("{" + i + "}", params[i]);
                }
            }
            return value;
        } catch (Exception e) {
        }

        return message;
    }


    public static boolean getLocal() {
        String language = (SystemConfigFacade.getMap().get("exportExcelLanguage")).getSys_value();

        if (language.equals("zh_CN")) {
            return true;
        }


        return false;
    }
}
