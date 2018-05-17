package com.zc.pivas.activemq;

import com.zc.pivas.common.client.AnalyResponMessage;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * 获取MQ数据：病区
 *
 * @author  cacabin
 * @version  1.0
 */
public class EmployeeInfoMQReceiver
{
    /**
     * 解析同步数据
     */
    private AnalyResponMessage analyResponMessage;
    
    public void receive(SyncData syncData)
        throws Exception
    {
        JSONObject param = new JSONObject(syncData.getData());
        analyResponMessage.analyEmployeeInfoRespon(param);
    }
    
    @Autowired
    public void setAnalyResponMessage(AnalyResponMessage analyResponMessage)
    {
        this.analyResponMessage = analyResponMessage;
    }
}
