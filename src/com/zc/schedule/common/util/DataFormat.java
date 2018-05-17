package com.zc.schedule.common.util;

import java.util.List;

import com.zc.schedule.common.entity.PageInfo;
import com.zc.schedule.common.entity.ResultData;
import com.zc.schedule.common.entity.ResultPage;

/**
 * 数据格式化工具类
 *
 * @author Justin
 * @version v1.0
 */
public class DataFormat {
    /**
     * 格式化分页
     *
     * @param items
     * @return PageInfo
     */
    public static PageInfo formatPage(List<?> items) {
        PageInfo page = new PageInfo();
        if (items != null) {
            page.setPageNumber(items.size());
            page.setPage(1);
            page.setTotalPage(items.size());
            page.setTotalRow(items.size());
        } else {
            page.setPageNumber(0);
            page.setPage(1);
            page.setTotalPage(0);
            page.setTotalRow(0);
        }
        return page;
    }

    /**
     * 格式化数据
     *
     * @param items
     * @return ResultPage
     */
    public static ResultPage formatPageData(List<?> items) {
        ResultPage resultPage = new ResultPage();
        resultPage.setPageInfo(formatPage(items));
        resultPage.setTotalRow(items == null ? 0 : items.size());
        resultPage.setItems(items);
        resultPage.setCode(SysConstant.RET_OK);
        resultPage.setMsg(SysConstant.resultMess.qryOK);
        return resultPage;
    }

    /**
     * 格式化分页数据
     *
     * @param items
     * @param page
     * @return ResultPage
     */
    public static ResultPage formatPageData(List<?> items, PageInfo page) {
        ResultPage resultPage = new ResultPage();
        resultPage.setPageInfo(page);
        resultPage.setTotalRow(page != null ? page.getTotalRow() : 0);
        resultPage.setItems(items);
        resultPage.setCode(SysConstant.RET_OK);
        resultPage.setMsg(SysConstant.resultMess.qryOK);
        return resultPage;
    }

    /**
     * 返回成功数据
     *
     * @return ResultData
     */
    public static ResultData formatSucc() {
        return formatSucc(null, null);
    }

    /**
     * 返回成功数据
     *
     * @param data
     * @return ResultData
     */
    public static ResultData formatSucc(Object data) {
        return formatSucc(null, data);
    }

    /**
     * 返回成功数据
     *
     * @param mess
     * @param data
     * @return ResultData
     */
    public static ResultData formatSucc(String mess, Object data) {
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.RET_OK);
        resultData.setMsg(mess == null ? SysConstant.resultMess.OK : mess);
        resultData.setData(data);
        return resultData;
    }

    /**
     * 返回失败数据
     *
     * @return ResultData
     */
    public static ResultData formatFail() {
        return formatFail(null, null);
    }

    /**
     * 返回失败数据
     *
     * @param mess
     * @param data
     * @return ResultData
     */
    public static ResultData formatFail(String mess, Object data) {
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.RET_ERR);
        resultData.setMsg(mess == null ? SysConstant.resultMess.Err : mess);
        resultData.setData(data);
        return resultData;
    }

    /**
     * 返回添加信息
     *
     * @param row
     * @param mess
     * @param data
     * @return ResultData
     */
    public static ResultData formatAdd(int row, String mess, Object data) {
        ResultData resultData = new ResultData();
        resultData.setCode(row > 0 ? SysConstant.RET_OK : SysConstant.RET_ERR);
        resultData.setMsg(mess == null ? (row > 0 ? SysConstant.resultMess.addOK : SysConstant.resultMess.addErr) : mess);
        resultData.setData(data);
        return resultData;
    }

    /**
     * 返回添加信息
     *
     * @param row
     * @param mess
     * @return ResultData
     */
    public static ResultData formatAdd(int row, String mess) {
        return formatAdd(row, mess, null);
    }

    /**
     * 返回添加信息
     *
     * @param row
     * @param data
     * @return ResultData
     */
    public static ResultData formatAdd(int row, Object data) {
        return formatAdd(row, null, data);
    }

    /**
     * 返回添加信息
     *
     * @param row
     * @return ResultData
     */
    public static ResultData formatAdd(int row) {
        return formatAdd(row, null, null);
    }

    /**
     * 返回更新信息
     *
     * @param row
     * @param mess
     * @param data
     * @return ResultData
     */
    public static ResultData formatUpd(int row, String mess, Object data) {
        ResultData resultData = new ResultData();
        resultData.setCode(row > 0 ? SysConstant.RET_OK : SysConstant.RET_ERR);
        resultData.setMsg(mess == null ? (row > 0 ? SysConstant.resultMess.updOK : SysConstant.resultMess.updErr) : mess);
        resultData.setData(data);
        return resultData;
    }

    /**
     * 返回更新信息
     *
     * @param row
     * @param mess
     * @return ResultData
     */
    public static ResultData formatUpd(int row, String mess) {
        return formatUpd(row, mess, null);
    }

    /**
     * 返回更新信息
     *
     * @param row
     * @return ResultData
     */
    public static ResultData formatUpd(int row) {
        return formatUpd(row, null, null);
    }

    /**
     * 返回删除信息
     *
     * @param row
     * @param mess
     * @param data
     * @return ResultData
     */
    public static ResultData formatDel(int row, String mess, Object data) {
        ResultData resultData = new ResultData();
        resultData.setCode(row > 0 ? SysConstant.RET_OK : SysConstant.RET_ERR);
        resultData.setMsg(mess == null ? (row > 0 ? SysConstant.resultMess.delOK : SysConstant.resultMess.delErr) : mess);
        resultData.setData(data);
        return resultData;
    }

    /**
     * 返回删除信息
     *
     * @param row
     * @param mess
     * @return ResultData
     */
    public static ResultData formatDel(int row, String mess) {
        return formatDel(row, mess, null);
    }

    /**
     * 返回删除信息
     *
     * @param row
     * @return ResultData
     */
    public static ResultData formatDel(int row) {
        return formatDel(row, null, null);
    }

    public static int getObjInt(Object obj, int i) {
        if (obj == null || obj.equals("") || obj.equals("null")) {
            return i;
        }
        try {
            return new Double(obj.toString()).intValue();
        } catch (Exception e) {
            return i;
        }
    }

    public static final Long getObjLong(Object obj) {
        if (obj == null || obj.equals("") || obj.equals("null")) {
            return null;
        }
        try {
            return new Double(obj.toString()).longValue();
        } catch (Exception e) {
            return null;
        }
    }

    public static final String getObjString(Object obj) {
        if (obj == null || obj.equals("") || obj.equals("null")) {
            return null;
        }
        return obj.toString();
    }
}
