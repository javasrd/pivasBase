package com.zc.pivas.synresult.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * 同步任务结果
 *
 * @author kunkka
 * @version 1.0
 */
public class TaskResultBean implements Serializable {

    private static final long serialVersionUID = 1L;


    //任务ID
    private String taskID;

    //任务名称
    private String taskName;

    private String[] taskNames;

    public String[] getTaskNames() {
        return taskNames;
    }

    public void setTaskNames(String[] taskNames) {
        this.taskNames = taskNames;
    }


    //任务类型，0,定时任务 1,一次性任务
    private int taskType;

    //任务类型
    private String taskTypeName;

    //执行结果  0成功  1失败
    private int taskResult;

    //执行结果
    private String taskResultName;

    /**
     * 开始执行时间  YYYY-MM-DD HH:MM:SS
     */
    private Timestamp taskExecStartTime;

    /**
     * 结束执行时间  YYYY-MM-DD HH:MM:SS
     */
    private Timestamp taskExecStopTime;

    /**
     * 开始执行时间  YYYY-MM-DD HH:MM:SS
     */
    private String taskStartTime;

    /**
     * 结束执行时间  YYYY-MM-DD HH:MM:SS
     */
    private String taskStopTime;

    /**
     * 内容类型  0,病人、1,医嘱、2,病区、3,用药方式、4,医嘱频次、5,药品
     */
    private int taskContentType;

    /**
     * 内容类型  0,病人、1,医嘱、2,病区、3,用药方式、4,医嘱频次、5,药品
     */
    private String taskContentTypeName;

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

    public int getTaskResult() {
        return taskResult;
    }

    public void setTaskResult(int taskResult) {
        this.taskResult = taskResult;
    }

    public String getTaskResultName() {
        return taskResultName;
    }

    public void setTaskResultName(String taskResultName) {
        this.taskResultName = taskResultName;
    }

    public Timestamp getTaskExecStartTime() {
        return taskExecStartTime;
    }

    public void setTaskExecStartTime(Timestamp taskExecStartTime) {
        this.taskExecStartTime = taskExecStartTime;
    }

    public Timestamp getTaskExecStopTime() {
        return taskExecStopTime;
    }

    public void setTaskExecStopTime(Timestamp taskExecStopTime) {
        this.taskExecStopTime = taskExecStopTime;
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
        return "TaskResultBean [taskID=" + taskID + ", taskName=" + taskName + ", taskNames="
                + Arrays.toString(taskNames) + ", taskType=" + taskType + ", taskTypeName=" + taskTypeName
                + ", taskResult=" + taskResult + ", taskResultName=" + taskResultName + ", taskExecStartTime="
                + taskExecStartTime + ", taskExecStopTime=" + taskExecStopTime + ", taskContentType=" + taskContentType
                + ", taskContentTypeName=" + taskContentTypeName + ", reserve0=" + reserve0 + ", reserve1=" + reserve1
                + ", reserve2=" + reserve2 + "]";
    }

    public String getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(String taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public String getTaskStopTime() {
        return taskStopTime;
    }

    public void setTaskStopTime(String taskStopTime) {
        this.taskStopTime = taskStopTime;
    }

}
