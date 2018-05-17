package com.zc.pivas.docteradvice.syndatasz;

import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.pivas.docteradvice.dtsystem.JaxbBinder;
import com.zc.pivas.docteradvice.syndatasz.message.req.ESBEntry.*;
import com.zc.pivas.docteradvice.syndatasz.message.req.HIPMessageServer;
import com.zc.pivas.docteradvice.syndatasz.message.req.SoapBody;
import com.zc.pivas.docteradvice.syndatasz.message.req.SoapEnvelopeReq;
import com.zc.pivas.docteradvice.syndatasz.message.resp.Msg;
import com.zc.pivas.docteradvice.syndatasz.message.resp.SoapEnvelope;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 
 * 某XXX医院数据同步请求消息拼装
 *
 * @author  cacabin
 * @version  0.1
 */
public class SetMessageForSynSZ
{
    /** 日志类 */
    private static final Logger log = LoggerFactory.getLogger(SetMessageForSynSZ.class);
    
    /**
     * 排头
     */
    private static final String SCRAP = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>";
    
    /**
     * 同步成功代码
     */
    private static final String SYN_SUCCESS = "1";
    
    /**
     * 
     * 拼接请求
     * @return
     */
    public static String setSynReq(String Fid, String msg, Query query)
    {
        JaxbBinder jaxbBinder = new JaxbBinder(ESBEntry.class);
        ESBEntry esbEntry = new ESBEntry();
        MessageESBEntryHeader messageHeader = new MessageESBEntryHeader();
        AccessControl accessControl = new AccessControl();
        accessControl.setFid(Fid);
        messageHeader.setFid(Fid);
        MsgESBEntryInfo msgInfo = new MsgESBEntryInfo();
        msgInfo.setMsg(msg);
        msgInfo.setQuery(query);
        
        esbEntry.setAccessControl(accessControl);
        esbEntry.setMessageHeader(messageHeader);
        esbEntry.setMsgInfo(msgInfo);
        
        String esbentry = jaxbBinder.toXml(esbEntry, "utf-8");
        
        jaxbBinder = new JaxbBinder(SoapEnvelopeReq.class);
        SoapEnvelopeReq req = new SoapEnvelopeReq();
        SoapBody body = new SoapBody();
        HIPMessageServer hipMessageServer = new HIPMessageServer();
        hipMessageServer.setInPut(esbentry.replace(SCRAP, ""));
        body.setHipMessageServer(hipMessageServer);
        req.setBody(body);
        
        return jaxbBinder.toXml(req, "utf-8").replace("SCRAP", "").replaceAll("\n", "");
    }
    
    /**
     * 
     * 解析响应报文
     * @param synRespon
     * @return
     */
    public static List<Msg> analySynYZRespon(String synRespon)
    {
        JaxbBinder jaxbBinder = new JaxbBinder(SoapEnvelope.class);
        SoapEnvelope envelopecc = jaxbBinder.fromXml(synRespon.replaceAll("&", "&amp;"));
        
        if (null != envelopecc && null != envelopecc.getBody()
            && null != envelopecc.getBody().getHipMessageServerResponse()
            && null != envelopecc.getBody().getHipMessageServerResponse().getOutPutYz()
            && null != envelopecc.getBody().getHipMessageServerResponse().getOutPutYz().getEsbEntry())
        {
            String retCode =
                envelopecc.getBody()
                    .getHipMessageServerResponse()
                    .getOutPutYz()
                    .getEsbEntry()
                    .getRetInfo()
                    .getRetCode();
            
            if (SYN_SUCCESS.equals(retCode))
            {
                List<Msg> msgList =
                    envelopecc.getBody()
                        .getHipMessageServerResponse()
                        .getOutPutYz()
                        .getEsbEntry()
                        .getMsgInfo()
                        .getMsgList();
                return msgList;
            }
        }
        
        return null;
    }
    
    /**
     * 
     * 发送请求报文
     * @param fid
     * @param request
     * @return
     * @throws IOException 
     * @throws HttpException
     */
    public static String sendRequestMessage(String fid, String request)
        throws HttpException, IOException
    {
        // 获取同步地址
        String synUrl =
            Propertiesconfiguration.getStringProperty("synhis.synchronizationurl")
                .replaceAll("FID", fid)
                .replace("PORT", Propertiesconfiguration.getStringProperty("synhis.synchronization.port.actionAdvice"));
        
        PostMethod postMethod = new PostMethod(synUrl);
        byte[] b = request.getBytes("utf-8");
        InputStream inputStream = new ByteArrayInputStream(b, 0, b.length);
        RequestEntity re = new InputStreamRequestEntity(inputStream, b.length, "application/soap+xml; charset=utf-8");
        postMethod.setRequestEntity(re);
        
        HttpClient httpClient = new HttpClient();
        int statusCode = httpClient.executeMethod(postMethod);
        
        log.error("调用his同步数据,返回码为:" + String.valueOf(statusCode));
        
        String respon = postMethod.getResponseBodyAsString();
        return StringEscapeUtils.unescapeXml(respon);
    }
}
