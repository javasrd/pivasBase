package com.zc.base.web.logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;


public abstract class LogbackConfigurer {
    public static final String CLASSPATH_URL_PREFIX = "classpath:";
    public static final String XML_FILE_EXTENSION = ".xml";
    private static LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
    private static JoranConfigurator configurator = new JoranConfigurator();

    public static void initLogging(String location)
            throws FileNotFoundException {
        String resolvedLocation = SystemPropertyUtils.resolvePlaceholders(location);
        URL url = ResourceUtils.getURL(resolvedLocation);
        if (resolvedLocation.toLowerCase().endsWith(".xml")) {
            configurator.setContext(lc);
            lc.reset();
            try {
                configurator.doConfigure(url);
            } catch (JoranException ex) {
                throw new FileNotFoundException(url.getPath());
            }
            lc.start();
        }
    }

    public static void shutdownLogging() {
        lc.stop();
    }

    public static void setWorkingDirSystemProperty(String key) {
        System.setProperty(key, new File("").getAbsolutePath());
    }
}
