package com.zc.pivas.activemq;

import com.zc.pivas.common.client.AnalyResponMessage;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 病人MQ获取数据
 *
 * @author  cacabin
 * @version  1.0
 */
public class PatientMQReceiver
{
    /**
     * 解析同步数据
     */
    private AnalyResponMessage analyResponMessage;
    
    public void receive(SyncData syncData)
        throws Exception
    {
        JSONObject param = new JSONObject(syncData.getData());
        analyResponMessage.analyPatientRespon(param);
    }
    
    @Autowired
    public void setAnalyResponMessage(AnalyResponMessage analyResponMessage)
    {
        this.analyResponMessage = analyResponMessage;
    }
    
}
