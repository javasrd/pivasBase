package com.zc.base.common.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.*;

import java.io.*;
import java.util.Calendar;
import java.util.Date;


public final class FileUtil {
    public FileUtil() {
    }

    public static void mkdirs(String path) {
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }

    }

    public static void deleteFile(String targetPath) throws IOException {
        File file = new File(targetPath);
        if (file.exists()) {
            if (!file.isDirectory()) {
                file.delete();
            } else if (file.isDirectory()) {
                File[] filelist = file.listFiles();

                for (int i = 0; i < filelist.length; ++i) {
                    File delfile = filelist[i];
                    if (!delfile.isDirectory()) {
                        delfile.delete();
                    } else if (delfile.isDirectory()) {
                        deleteFile(filelist[i].getAbsolutePath());
                    }
                }

                file.delete();
            }
        }
    }

    public static void deleteFile(String folder, int days) {
        File file = new File(folder);
        File[] filelist = file.listFiles();
        Date dateTime = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.set(5, calendar.get(5) - days);
        dateTime = calendar.getTime();

        for (int i = 0; i < filelist.length; ++i) {
            File delfile = filelist[i];
            Date fileData = new Date(delfile.lastModified());
            if (!delfile.isDirectory() && fileData.getTime() < dateTime.getTime()) {
                delfile.delete();
            }
        }

    }
}
