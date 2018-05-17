package com.zc.base.common.util;

import com.zc.base.sys.modules.sysconfig.entity.SystemConfig;
import com.zc.base.sys.modules.sysconfig.facade.SystemConfigFacade;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.net.InetAddress;
import java.util.List;
import java.util.Properties;


public class MailUtil {
    private static Log log = LogFactory.getLog(MailUtil.class);

    public static void mail(String attach, List<String> address, String content, String title)
            throws Exception {
        try {
            String emailType = ((SystemConfig) SystemConfigFacade.getMap().get("emailType")).getSys_value();
            if ("on".equals(emailType)) {

                if (!SystemConfigFacade.isLoaded()) {
                    throw new Exception("The system config data is not loaded");
                }


                if ((address == null) || (address.size() == 0)) {
                    throw new Exception("The send email address is empty");
                }


                if ((title == null) || ("".equals(title))) {
                    throw new Exception("The send email title is empty");
                }


                String smtp = ((SystemConfig) SystemConfigFacade.getMap().get("smtpServer")).getSys_value();

                String username = ((SystemConfig) SystemConfigFacade.getMap().get("smtpServerUserName")).getSys_value();

                String password = ((SystemConfig) SystemConfigFacade.getMap().get("smtpServerUserPassword")).getSys_value();

                String sender = ((SystemConfig) SystemConfigFacade.getMap().get("sendFrom")).getSys_value();

                JavaMailSenderImpl sendimpl = new JavaMailSenderImpl();
                Properties javaMailProp = new Properties();
                javaMailProp.setProperty("mail.smtp.auth", "true");

                InetAddress addr = InetAddress.getLocalHost();
                String ip = addr.getHostAddress();
                javaMailProp.put("mail.smtp.localhost", ip);
                javaMailProp.put("mail.smtp.timeout", Integer.valueOf(20000));

                sendimpl.setJavaMailProperties(javaMailProp);
                sendimpl.setHost(smtp);
                sendimpl.setUsername(username);
                sendimpl.setPassword(password);

                MimeMessage message = sendimpl.createMimeMessage();

                MimeMessageHelper help = new MimeMessageHelper(message, true, "utf-8");

                String[] emailArray = new String[address.size()];
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < address.size(); i++) {
                    emailArray[i] = ((String) address.get(i));
                    buffer.append(emailArray[i]).append(",");
                }
                help.setTo(emailArray);
                help.setFrom(sender);
                help.setSubject(title);


                String reportFilePath = ((SystemConfig) SystemConfigFacade.getMap().get("reportFilePath")).getSys_value();

                File file = null;
                if (attach != null) {
                    file = new File(reportFilePath + "/" + new String(attach.getBytes("UTF-8"), "UTF-8"));
                }


                if ((file != null) && (file.exists())) {
                    help.setText(content);
                    FileSystemResource resource = new FileSystemResource(file);
                    help.addAttachment(MimeUtility.encodeWord(attach), resource);
                    sendimpl.send(message);
                    log.info("send mail to " + buffer.toString() + " success");
                } else {
                    help.setText(content);
                    sendimpl.send(message);
                    log.info("send mail to " + buffer.toString() + " success");
                }
            } else {
                log.info("send mail switch is close");
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }


    public static void mail(List<String> address, String content, String title)
            throws Exception {
        try {
            String emailType = ((SystemConfig) SystemConfigFacade.getMap().get("emailType")).getSys_value();
            if ("on".equals(emailType)) {


                if (!SystemConfigFacade.isLoaded()) {
                    throw new Exception("The system config data is not loaded");
                }


                if ((address == null) || (address.size() == 0)) {
                    throw new Exception("The send email address is empty");
                }


                if ((title == null) || ("".equals(title))) {
                    throw new Exception("The send email title is empty");
                }


                String smtp = ((SystemConfig) SystemConfigFacade.getMap().get("smtpServer")).getSys_value();

                String username = ((SystemConfig) SystemConfigFacade.getMap().get("smtpServerUserName")).getSys_value();

                String password = ((SystemConfig) SystemConfigFacade.getMap().get("smtpServerUserPassword")).getSys_value();

                String sender = ((SystemConfig) SystemConfigFacade.getMap().get("sendFrom")).getSys_value();

                JavaMailSenderImpl sendimpl = new JavaMailSenderImpl();
                Properties javaMailProp = new Properties();
                javaMailProp.setProperty("mail.smtp.auth", "true");

                InetAddress addr = InetAddress.getLocalHost();
                String ip = addr.getHostAddress();
                javaMailProp.put("mail.smtp.localhost", ip);
                javaMailProp.put("mail.smtp.timeout", Integer.valueOf(20000));

                sendimpl.setJavaMailProperties(javaMailProp);
                sendimpl.setHost(smtp);
                sendimpl.setUsername(username);
                sendimpl.setPassword(password);

                MimeMessage message = sendimpl.createMimeMessage();

                MimeMessageHelper help = new MimeMessageHelper(message, true, "gbk");

                String[] emailArray = new String[address.size()];
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < address.size(); i++) {
                    emailArray[i] = ((String) address.get(i));
                    buffer.append(emailArray[i]).append(",");
                }
                help.setTo(emailArray);
                help.setFrom(sender);
                help.setSubject(title);

                help.setText(content, true);

                sendimpl.send(message);
                log.info("send mail to " + buffer.toString() + " success");
            } else {
                log.info("send mail switch is close");
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }


    public static void checkoutMail(String smtpServer, String smtpServerUserName, String smtpServerUserPassword, String novelEmail, String title, String content)
            throws Exception {
        try {
            String smtp = smtpServer;

            String username = smtpServerUserName;

            String password = smtpServerUserPassword;

            String sender = novelEmail;

            JavaMailSenderImpl sendimpl = new JavaMailSenderImpl();
            Properties javaMailProp = new Properties();
            javaMailProp.setProperty("mail.smtp.auth", "true");

            InetAddress addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress();
            javaMailProp.put("mail.smtp.localhost", ip);
            javaMailProp.put("mail.smtp.timeout", Integer.valueOf(20000));

            sendimpl.setJavaMailProperties(javaMailProp);
            sendimpl.setHost(smtp);
            sendimpl.setUsername(username);
            sendimpl.setPassword(password);

            MimeMessage message = sendimpl.createMimeMessage();

            MimeMessageHelper help = new MimeMessageHelper(message, true, "gbk");


            help.setTo(sender);
            help.setFrom(sender);
            help.setSubject(title);

            help.setText(content, true);

            sendimpl.send(message);
        } catch (Exception e) {
            throw e;
        }
    }
}
