package com.zc.pivas.synset.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * 同步任务设置bean
 *
 * @author kunkka
 * @version 1.0
 */
public class SynSettingBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键标识，任务创建成功后由数据中间件返回写入
     */
    private String taskID;

    /**
     * 任务名称
     */
    private String taskName;

    private String[] taskNames;

    /**
     * 任务类型，0,定时任务 1,一次性任务
     */
    private int taskType;

    /**
     * 任务类型名称
     */
    private String taskTypeName;

    /**
     * 任务执行模式，仅选择定时任务时有效, 0,分钟 1,天
     */
    private Integer taskExecuteMode;

    /**
     * 任务执行模式名称，仅选择定时任务时有效, 0,分钟 1,天
     */
    private String taskExecuteModeName;

    /**
     * 任务优先级
     */
    private int taskTaskPriority;

    /**
     * 任务执行间隔  仅选择定时任务时有效
     */
    private Integer taskInteval;

    /**
     * 任务执行时间  YYYY-MM-DD HH:MM:SS
     */
    private String taskExecuteTime;

    /**
     * 任务执行时间  YYYY-MM-DD HH:MM:SS
     */
    private Timestamp executeTime;

    private Timestamp createTime;

    private Timestamp updateTime;

    /**
     * 当执行失败，重复次数
     */
    private String retryTimes;

    /**
     * 时间间隔
     */
    private String retryInterval;

    public String getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(String retryTimes) {
        this.retryTimes = retryTimes;
    }

    public String getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(String retryInterval) {
        this.retryInterval = retryInterval;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 任务执行内容类型  0,病人、1,医嘱、2,病区、3,用药方式、4,医嘱频次、5,药品
     */
    private int taskContentType;

    /**
     * 任务执行内容类型  0,病人、1,医嘱、2,病区、3,用药方式、4,医嘱频次、5,药品
     */
    private String taskContentTypeName;

    /**
     * 任务状态  0,启动  1,停止
     */
    private int taskStatus;

    /**
     * 任务状态名称  0,启动  1,停止
     */
    private String taskStatusName;

    /**
     * 任务备注信息
     */
    private String taskRemark;

    /**
     * 预留字段0
     */
    private String reserve0;

    /**
     * 预留字段1
     */
    private String reserve1;

    /**
     * 预留字段2
     */
    private String reserve2;

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String[] getTaskNames() {
        return taskNames;
    }

    public void setTaskNames(String[] taskNames) {
        this.taskNames = taskNames;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getTaskTypeName() {
        return taskTypeName;
    }

    public void setTaskTypeName(String taskTypeName) {
        this.taskTypeName = taskTypeName;
    }

    public Integer getTaskExecuteMode() {
        return taskExecuteMode;
    }

    public void setTaskExecuteMode(Integer taskExecuteMode) {
        this.taskExecuteMode = taskExecuteMode;
    }

    public String getTaskExecuteModeName() {
        return taskExecuteModeName;
    }

    public void setTaskExecuteModeName(String taskExecuteModeName) {
        this.taskExecuteModeName = taskExecuteModeName;
    }

    public int getTaskTaskPriority() {
        return taskTaskPriority;
    }

    public void setTaskTaskPriority(int taskTaskPriority) {
        this.taskTaskPriority = taskTaskPriority;
    }

    public Integer getTaskInteval() {
        return taskInteval;
    }

    public void setTaskInteval(Integer taskInteval) {
        this.taskInteval = taskInteval;
    }

    public String getTaskExecuteTime() {
        return taskExecuteTime;
    }

    public void setTaskExecuteTime(String taskExecuteTime) {
        this.taskExecuteTime = taskExecuteTime;
    }

    public Timestamp getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Timestamp executeTime) {
        this.executeTime = executeTime;
    }

    public int getTaskContentType() {
        return taskContentType;
    }

    public void setTaskContentType(int taskContentType) {
        this.taskContentType = taskContentType;
    }

    public String getTaskContentTypeName() {
        return taskContentTypeName;
    }

    public void setTaskContentTypeName(String taskContentTypeName) {
        this.taskContentTypeName = taskContentTypeName;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskStatusName() {
        return taskStatusName;
    }

    public void setTaskStatusName(String taskStatusName) {
        this.taskStatusName = taskStatusName;
    }

    public String getTaskRemark() {
        return taskRemark;
    }

    public void setTaskRemark(String taskRemark) {
        this.taskRemark = taskRemark;
    }

    public String getReserve0() {
        return reserve0;
    }

    public void setReserve0(String reserve0) {
        this.reserve0 = reserve0;
    }

    public String getReserve1() {
        return reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1;
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2;
    }

    @Override
    public String toString() {
        return "SynSettingBean [taskID=" + taskID + ", taskName=" + taskName + ", taskNames="
                + Arrays.toString(taskNames) + ", taskType=" + taskType + ", taskTypeName=" + taskTypeName
                + ", taskExecuteMode=" + taskExecuteMode + ", taskExecuteModeName=" + taskExecuteModeName
                + ", taskTaskPriority=" + taskTaskPriority + ", taskInteval=" + taskInteval + ", taskExecuteTime="
                + taskExecuteTime + ", executeTime=" + executeTime + ", createTime=" + createTime + ", updateTime="
                + updateTime + ", retryTimes=" + retryTimes + ", retryInterval=" + retryInterval + ", taskContentType="
                + taskContentType + ", taskContentTypeName=" + taskContentTypeName + ", taskStatus=" + taskStatus
                + ", taskStatusName=" + taskStatusName + ", taskRemark=" + taskRemark + ", reserve0=" + reserve0
                + ", reserve1=" + reserve1 + ", reserve2=" + reserve2 + "]";
    }

}
