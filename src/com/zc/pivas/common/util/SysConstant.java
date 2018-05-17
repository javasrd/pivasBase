package com.zc.pivas.common.util;

/**
 * 
 * 系统静态变量
 *
 * @author  cacabin
 * @version  1.0
 */
public interface SysConstant
{
    
    interface resultCode
    {
        
        int OK = 1;
        
        int Err = -1;

        int addOK = 1;
        
        int addErr = -1;
        
        int updOK = 1;
        
        int updErr = -1;
        
        int delOK = 1;
        
        int delErr = -1;
        
    }
    
    interface resultMess
    {
        
        String OK = "操作成功";
        
        String Err = "操作失败";
        
        String addOK = "添加成功";
        
        String addErr = "添加失败";
        
        String updOK = "更新成功";
        
        String updErr = "更新失败";
        
        String delOK = "删除成功";
        
        String delErr = "删除失败";
        
    }
    
    interface areaEnabled
    {
        String enable = "1";
        
        String unable = "0";
        
    }
    
    interface prType
    {
        int medicSingRule = 11;//药物优先级
        int forceSingRule = 31;//强制规则
    }
    
    interface prEnabled{
        int abled = 1;//启用
        int unabled = 0;//未启用
    }
    
    interface vrType
    {
        int volumeRule = 21;//容积规则
    }
    
    interface orType
    {
        int otherRule = 41;//其他规则
    }
    
    interface ydreorderStatus{
        int i0WaitConfig = 0 ;
        int i1HasConfig = 1 ;
    }
    //0待处理，11批次未调整-有异常，12批次有调整-有异常，21批次未调整-无异常，22批次有调整-无异常，23用户前台强制置为 成功，但保留失败信息mess,24不参批次优化
    interface ydreorderCode{
        int i0YDInit = 0 ;
        int i11NoChangeErr = 11 ;
        int i12HasChangeErr = 12 ;
        int i21NoChangeSucc = 21 ;
        int i22HasChangeSucc = 22 ;
        int i23ForceToSucc = 23 ;
        int i24NotReorder = 24 ;
    }
    
    interface trackingRecord{
        
        int yssh = 0;
        String ysshStr = "药单生成";
        
        int pcpd = 1;
        String pcpdStr = "批次优化";
        
        int dypq = 2;
        String dypqStr = "打印瓶签";
        
        int rcsm = 3;
        String rcsmStr = "入仓扫描";
        
        int ccsm = 4;
        String ccsmStr = "出仓扫描";
        
        int pctz = 5;
        String pctzStr = "批次调整";
            
        int qsqr = 6;
        String qsqrStr = "签收确认";
        
        int tbrw = 7;
        String tbrwStr = "同步任务更新药单";
        
        int cnsm = 8;
        String cnsmStr = "仓内扫描";
    }
    
    
    interface operLogRecord{
        String wardGroup = "CF_21";
    }
}
