package com.zc.base.common.controller;

import com.zc.base.common.response.ResponseContextHolder;
import com.zc.base.common.util.ExcelWriterUtil;
import com.zc.base.web.Servlets;

import javax.mail.internet.MimeUtility;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;


public class SdDownloadController extends SdBaseController {
    private static final int DEFAULT_BUF_SIZE = 1024;
    private int streamBufSize = 1024;

    public SdDownloadController() {
    }

    protected void setDownloadHeaders(HttpServletResponse response, String fileName, SdDownloadController.FileType fileType) throws Exception {
        response.setContentType(this.getContentType(fileType));
        HttpServletRequest request = Servlets.getCurrentRequest();
        response.addHeader("Content-disposition", "attachment;filename=" + this.encodeFileName(request.getHeader("USER-AGENT"), fileName));
    }

    protected void doDownload(InputStream inputStream, String fileName, SdDownloadController.FileType fileType) throws Exception {
        ServletOutputStream out = null;

        try {
            HttpServletResponse response = ResponseContextHolder.getCurrentResponse();
            out = response.getOutputStream();
            this.setDownloadHeaders(response, fileName, fileType);
            byte[] b = new byte[1024];
            boolean var7 = false;

            int len;
            while ((len = inputStream.read(b)) != -1) {
                out.write(b, 0, len);
            }

            out.flush();
        } catch (Exception var15) {
            throw var15;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (out != null) {
                    out.close();
                }
            } catch (IOException var14) {
                throw var14;
            }

        }
    }

    protected void doDownloadExcel(ExcelWriterUtil writer, String fileName) throws Exception {
        HttpServletResponse response = ResponseContextHolder.getCurrentResponse();
        ServletOutputStream outputStream = response.getOutputStream();

        try {
            response.setBufferSize(this.streamBufSize);
            this.setDownloadHeaders(response, fileName, SdDownloadController.FileType.EXCEL_TYPE);
            writer.writeExcel(outputStream);
        } catch (IOException var12) {
            throw var12;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException var13) {
                throw var13;
            }

        }

    }

    protected void doDownloadFile(File file, String outFileName, SdDownloadController.FileType outFileType, boolean isDelByDownloaded) throws Exception {
        try {
            this.doDownload(new FileInputStream(file), outFileName, outFileType);
        } catch (IOException var9) {
            throw var9;
        } finally {
            if (isDelByDownloaded) {
                file.delete();
            }

        }

    }

    private String encodeFileName(String agent, String fileName) throws UnsupportedEncodingException {
        if (agent != null && -1 != agent.indexOf("MSIE")) {
            return URLEncoder.encode(fileName, "UTF-8");
        } else {
            return agent != null && -1 != agent.indexOf("Mozilla") ? MimeUtility.encodeText(fileName, "UTF-8", "B") : fileName;
        }
    }

    private String getContentType(SdDownloadController.FileType fileType) throws Exception {
        if (fileType == SdDownloadController.FileType.EXCEL_TYPE) {
            return SdDownloadController.ContentType.EXCEL_FORMAT.toContentTypeString();
        } else if (fileType == SdDownloadController.FileType.CSV_TYPE) {
            return SdDownloadController.ContentType.CSV_FORMAT.toContentTypeString();
        } else if (fileType == SdDownloadController.FileType.PDF_TYPE) {
            return SdDownloadController.ContentType.PDF_FORMAT.toContentTypeString();
        } else if (fileType == SdDownloadController.FileType.OTHER_TYPE) {
            return SdDownloadController.ContentType.TEXT_FORMAT.toContentTypeString();
        } else if (fileType == SdDownloadController.FileType.DOC_TYPE) {
            return SdDownloadController.ContentType.WORD_FORMAT.toContentTypeString();
        } else if (fileType == SdDownloadController.FileType.ZIP_TYPE) {
            return SdDownloadController.ContentType.ZIP_FORMAT.toContentTypeString();
        } else {
            throw new Exception("unsupported format: " + fileType);
        }
    }

    public int getStreamBufSize() {
        return this.streamBufSize;
    }

    public void setStreamBufSize(int streamBufSize) {
        this.streamBufSize = streamBufSize;
    }

    public static class ContentType {
        public static final SdDownloadController.ContentType EXCEL_FORMAT = new SdDownloadController.ContentType("application/vnd.ms-excel;charset=UTF-8");
        public static final SdDownloadController.ContentType CSV_TXT_FORMAT = new SdDownloadController.ContentType("text/plain;charset=UTF-8");
        public static final SdDownloadController.ContentType PDF_FORMAT = new SdDownloadController.ContentType("application/pdf;charset=UTF-8");
        public static final SdDownloadController.ContentType WORD_FORMAT = new SdDownloadController.ContentType("application/msword;charset=UTF-8");
        public static final SdDownloadController.ContentType PPT_FORMAT = new SdDownloadController.ContentType("application/vnd.ms-powerpoint;charset=UTF-8");
        public static final SdDownloadController.ContentType ZIP_FORMAT = new SdDownloadController.ContentType("application/zip;charset=UTF-8");
        public static final SdDownloadController.ContentType TEXT_FORMAT = new SdDownloadController.ContentType("application/text;charset=UTF-8");
        public static final SdDownloadController.ContentType CSV_FORMAT = new SdDownloadController.ContentType("application/text;charset=GBK");
        private String contentTypeString;

        private ContentType(String contentTypeString) {
            this.contentTypeString = contentTypeString;
        }

        public String toContentTypeString() {
            return this.contentTypeString;
        }

        public static SdDownloadController.ContentType getContentTypeByFileName(String fileName) {
            String fileExt = null;
            if (fileName != null && fileName.lastIndexOf(46) > 0) {
                fileExt = fileName.substring(fileName.lastIndexOf(46) + 1);
            }

            return getContentTypeByExtName(fileExt);
        }

        public static SdDownloadController.ContentType getContentTypeByExtName(String fileExt) {
            if (fileExt != null) {
                if ("xls".equalsIgnoreCase(fileExt)) {
                    return EXCEL_FORMAT;
                }

                if ("txt".equalsIgnoreCase(fileExt) || "csv".equalsIgnoreCase(fileExt)) {
                    return CSV_TXT_FORMAT;
                }

                if ("doc".equalsIgnoreCase(fileExt)) {
                    return WORD_FORMAT;
                }

                if ("ppt".equalsIgnoreCase(fileExt)) {
                    return PPT_FORMAT;
                }

                if ("zip".equalsIgnoreCase(fileExt)) {
                    return ZIP_FORMAT;
                }
            }

            return TEXT_FORMAT;
        }
    }

    public static class FileType {
        public static final SdDownloadController.FileType EXCEL_TYPE = new SdDownloadController.FileType();
        public static final SdDownloadController.FileType CSV_TYPE = new SdDownloadController.FileType();
        public static final SdDownloadController.FileType PDF_TYPE = new SdDownloadController.FileType();
        public static final SdDownloadController.FileType OTHER_TYPE = new SdDownloadController.FileType();
        public static final SdDownloadController.FileType DOC_TYPE = new SdDownloadController.FileType();
        public static final SdDownloadController.FileType ZIP_TYPE = new SdDownloadController.FileType();

        public FileType() {
        }
    }
}
