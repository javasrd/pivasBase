package com.zc.pivas.common.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;

/**
 * 对接dem，同步任务的创建，修改，删除，启动
 *
 * @author  cacabin
 * @version  1.0
 */
public class SynTaskClient
{
    private static final Logger logger = LoggerFactory.getLogger(SynTaskClient.class);
    
    /**
     * 加密密钥
     */
    private static final String AES128_KEY = Propertiesconfiguration.getStringProperty("jdbc.encrypt.key");
    
    /**
     * 编码格式
     */
    private static final String AES128_ENCODING = Propertiesconfiguration.getStringProperty("jdbc.encrypt.encoding");
    
    /**
     * 对接dem
     * 
     * @param url 请求地址
     * @param obj 请求消息
     * @return 响应消息
     */
    public static JSONObject sendToDemServer(String url, JSONObject request)
    {
        // 响应消息
        JSONObject response = null;
        
        Client client = Client.create();
        WebResource resource = client.resource(url);
        
        if (null == request)
        {
            try
            {
                Builder builder = resource.type(MediaType.APPLICATION_JSON_TYPE);
                if (builder != null)
                {
                    response = builder.delete(JSONObject.class, null);
                }
            }
            catch (Exception e)
            {
                logger.error("sendToDemServer fail", e);
                return null;
            }
        }
        else
        {
            try
            {
                Builder builder = resource.type(MediaType.APPLICATION_JSON_TYPE);
                if (builder != null)
                {
                    response = builder.post(JSONObject.class, request);
                }
            }
            catch (Exception e)
            {
                logger.error("sendToDemServer fail", e);
                return null;
            }
        }
        
        return response;
    }
    
    /**
     * 拼接对接Restful请求
     * 
     * @param type 
     * @param function
     * @param inputParam
     * @return 请求消息
     * @throws JSONException
     * @throws UnsupportedEncodingException 
     */
    public static JSONObject setRequestMsgToRestful(String type, String function, int inputParam)
        throws JSONException, UnsupportedEncodingException
    {
        JSONObject request = new JSONObject();
        
        JSONObject param = new JSONObject();
        
        param.put("dateType", inputParam);
        
        request.put("param", param.toString());
        request.put("type", type);
        request.put("function", function);
        return request;
    }
}
