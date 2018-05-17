package com.zc.pivas.scans.constant;

/**
 * 
 * 扫描静态参数
 *
 * @author  cacabin
 * @version  1.0
 */
public interface ScansConstant
{
    /**
     * 
     * 瓶签状态
     *
     * @author  cacabin
     * @version  1.0
     */
    public interface BottleLabelStatus
    {
        /**
         * 执行
         */
        public final static Integer EXECUTE = 0;
        
        /**
         * 停止
         */
        public final static Integer STOP = 1;
        
        /**
         * 停止
         */
        public final static Integer REVOKE = 2;
        
        /**
         * 未打印
         */
        public final static Integer UN_PRINT = 3;
        
        /**
         * 已打印
         */
        public final static Integer PRINT = 4;
        
        /**
         * 入仓扫描核对完成
         */
        public final static Integer IN = 5;
        
        /**
         * 仓内扫描核对完成
         */
        public final static Integer INSIDE = 6;
        
        /**
         * 出仓扫描核对完成
         */
        public final static Integer OUT = 7;
    }
    
    public interface BottleLabelAction
    {
        /**
         * 入仓扫描核对完成
         */
        public final static String IN = "0";
        
        /**
         * 出仓扫描核对完成
         */
        public final static String OUT = "2";
        
        /**
         * 仓内扫描核对完成
         */
        public final static String INSIDE = "1";
    }
    
    public interface ScansResult
    {
        /**
         * 扫描核对完成
         */
        public final static Integer SUCCESS = 0;
        
        /**
         * 扫描核对失败
         */
        public final static Integer FAIL = 1;
        
        /*
         * 扫描核对重复
         */
        public final static Integer REPEAT = 2;
        
        /*
         * 网络段开
         */
        public final static Integer NET_ERROR = 3;
    }
    
    //药单配置费结果
    public interface ConfigFreeResult
    {
        //配置费收取失败
        public final static Integer CONFIG_FEE_COLLECT_FAIL = 0;
        
        //配置费收费成功
        public final static Integer CONFIG_FEE_COLLECT_SUCESS = 1;
        
        //配置费退费失败
        public final static Integer REFUND_CHARGE_FAIL = 2;
        
        //配置费退费成功
        public final static Integer REFUND_CHARGE_SUCESS = 3;
    }
    
    public interface BatchType
    {
        //空批
        Integer EMPTY_BATCH = 1;
        
        //非空批
        Integer NO_EMPTY_BATCH = 0;
        
        //0批
        Integer IS_OP = 1;
        
        //1批
        Integer NO_OP = 0;
    }
}
