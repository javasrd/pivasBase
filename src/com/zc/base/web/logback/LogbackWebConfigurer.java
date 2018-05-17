package com.zc.base.web.logback;

import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletContext;
import java.io.FileNotFoundException;


public abstract class LogbackWebConfigurer {
    public static final String CONFIG_LOCATION_PARAM = "logbackConfigLocation";
    public static final String REFRESH_INTERVAL_PARAM = "logbackRefreshInterval";
    public static final String EXPOSE_WEB_APP_ROOT_PARAM = "logbackExposeWebAppRoot";

    public static void initLogging(ServletContext servletContext) {
        if (exposeWebAppRoot(servletContext)) {
            WebUtils.setWebAppRootSystemProperty(servletContext);
        }


        String location = servletContext.getInitParameter("logbackConfigLocation");
        if (location != null) {
            try {

                if (!ResourceUtils.isUrl(location)) {
                    location = SystemPropertyUtils.resolvePlaceholders(location);
                    location = WebUtils.getRealPath(servletContext, location);
                }


                servletContext.log("Initializing logback from [" + location + "]");


                LogbackConfigurer.initLogging(location);
            } catch (FileNotFoundException ex) {
                throw new IllegalArgumentException("Invalid 'logbackConfigLocation' parameter: " + ex.getMessage());
            }
        }
    }


    public static void shutdownLogging(ServletContext servletContext) {
        servletContext.log("Shutting down logback");
        try {
            LogbackConfigurer.shutdownLogging();
        } finally {
            if (exposeWebAppRoot(servletContext)) {
                WebUtils.removeWebAppRootSystemProperty(servletContext);
            }
        }
    }


    private static boolean exposeWebAppRoot(ServletContext servletContext) {
        String exposeWebAppRootParam = servletContext.getInitParameter("logbackExposeWebAppRoot");
        return (exposeWebAppRootParam == null) || (Boolean.valueOf(exposeWebAppRootParam).booleanValue());
    }
}
