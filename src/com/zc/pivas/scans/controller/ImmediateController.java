package com.zc.pivas.scans.controller;

import com.zc.base.common.controller.SdBaseController;
import com.zc.pivas.scans.service.ScansService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.Path;

/**
 * 
 * <服务大屏的restful 即时命令>
 *
 * @author  cacabin
 * @version  1.0
 */
@Component
@Path("vehicle222")
public class ImmediateController extends SdBaseController
{
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(ImmediateController.class);
    
    /**
     * 获取扫描信息
     */
    @Resource
    private ScansService scansSerivce;
    
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("data222")
//    public JSONObject getData(JSONObject jsonEntity)
//        throws Exception
//    {
//        String scansType = jsonEntity.getString("scansType");
//        String scansSN = jsonEntity.getString("scansSN");
//        
//        //handl.sendMessageToUser(new TextMessage(scansSN));
//        
//        BottleLabelBean scansBatch =
//            scansSerivce.packageBottleLabelInfo(scansSN, scansType, DateUtil.getCurrentDay8Str());//bianxw 动态传入日期
//        String msg = new Gson().toJson(scansBatch);
//        
//        SysWebSocketHandler inhandl = new SysWebSocketHandler();
//        inhandl.sendMessageToUser(msg);
//        
//        JSONObject responseJson = new JSONObject();
//        responseJson.put("resultCode", "sucessfull");
//        responseJson.put("resultDesc", "OK");
//        
//        return responseJson;
//        
//    }
}
