package com.zc.base.sc.modules.batch.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.base.sc.modules.batch.service.BatchService;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.modules.user.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 批次定义
 *
 * @author Ray
 * @version 1.0
 */
@Controller
@RequestMapping("/cm/bat")
public class BatchController extends SdBaseController {

    @Resource
    private BatchService batchService;
    @Resource
    private SdExceptionFactory exceptionFactory;

    /**
     * 跳转到批次定义页面
     *
     * @return
     */
    @RequestMapping({""})
    public String toMain() {
        return "cm/batch/batchMain";
    }


    /**
     * 按条件分页查询
     *
     * @param paging
     * @param condition
     * @return
     * @throws Exception
     */
    @RequestMapping("/batList")
    @ResponseBody
    public String queryByPaging(JqueryStylePaging paging, Batch condition) throws Exception {
        condition.setNum(DefineStringUtil.escapeAllLike(condition.getNum()));
        condition.setName(DefineStringUtil.escapeAllLike(condition.getName()));

        String[] nameArray = condition.getNames();
        if (nameArray != null) {
            for (int i = 0; i < nameArray.length; i++) {
                nameArray[i] = DefineStringUtil.escapeAllLike(nameArray[i]);
            }
        }
        String[] columns = {"num", "name"};
        JqueryStylePagingResults<Batch> results = new JqueryStylePagingResults(columns);

        List<Batch> datas = this.batchService.queryByPaging(paging, condition);
        int total = this.batchService.getTotalCount(condition);
        results.setDataRows(datas);
        results.setPage(paging.getPage());
        results.setTotal(Integer.valueOf(total));
        return new Gson().toJson(results);
    }


    /**
     * 添加批次
     *
     * @param batch
     * @return
     */
    @RequestMapping("/addBat")
    @ResponseBody
    public String addBatch(Batch batch) {
        User user = getCurrentUser();
        try {
            int count = this.batchService.queryBatchRepeat(batch.getId(), batch.getNum(), batch.getName());
            if (count != 0) {
                return buildFailJsonMsg("batch.repeat");
            }
            List<Batch> batchList = this.batchService.queryBatchAllList();
            Map<Object, Object> batchMap = new HashMap();
            if (batchList != null) {
                for (Batch bean : batchList) {
                    batchMap.put(bean.getOrderNum(), bean.getOrderNum());
                }

                if (batchMap.containsKey(batch.getOrderNum())) {
                    return buildFailJsonMsg("batch.orderNum.repeat");
                }
            }
            this.batchService.insert(batch);
            addOperLog("CF_21", AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.batch.add", new String[]{user.getAccount(), batch.getName()}), true);
            return buildSuccessJsonMsg("common.op.success");
        } catch (Exception e) {
            addOperLog("CF_21", AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.batch.add", new String[]{user.getAccount(), batch.getName()}), false);
            throw e;
        }
    }


    /**
     * 根据ID获取批次定义
     *
     * @param id
     * @return
     */
    @RequestMapping("/getBat")
    @ResponseBody
    public String getBatch(Long id) {
        Batch batch = this.batchService.selectByPrimaryKey(id);
        if (batch == null) {
            this.exceptionFactory.createSdException("00001", null, null);
        }
        return new Gson().toJson(batch);
    }


    /**
     * 更新批次定义
     *
     * @param batch
     * @return
     */
    @RequestMapping("/updBat")
    @ResponseBody
    public String updateBatch(Batch batch) {
        User user = getCurrentUser();
        try {
            if (this.batchService.selectByPrimaryKey(batch.getId()) == null) {
                this.exceptionFactory.createSdException("00001", null, null);
            }
            int count = this.batchService.queryBatchRepeat(batch.getId(), batch.getNum(), batch.getName());
            if (count != 0) {
                return buildFailJsonMsg("batch.repeat");
            }
            List<Batch> batchList = this.batchService.queryBatchAllList();
            Batch oldBatch = this.batchService.selectByPrimaryKey(batch.getId());
            Map<Object, Object> batchMap = new HashMap();
            if (batchList != null) {
                for (Batch bean : batchList) {
                    batchMap.put(bean.getOrderNum(), bean.getOrderNum());
                }
                if ((batchMap.containsKey(batch.getOrderNum())) && (batch.getOrderNum().intValue() != oldBatch.getOrderNum().intValue())) {
                    return buildFailJsonMsg("batch.orderNum.repeat");
                }
            }
            this.batchService.updateByPrimaryKeySelective(batch);
            addOperLog("CF_21", AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.batch.update", new String[]{user.getAccount(), batch.getName()}), true);
            return buildSuccessJsonMsg("common.op.success");
        } catch (Exception e) {
            addOperLog("CF_21", AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.batch.update", new String[]{user.getAccount(), batch.getName()}), false);
            throw e;
        }
    }


    /**
     * 删除批次定义
     * @param ids
     * @return
     */
    @RequestMapping("/delBat")
    @ResponseBody
    public String deleteBatch(Long[] ids) {
        User user = getCurrentUser();
        Batch batch = null;
        try {
            if (DefineStringUtil.isArrayNotEmpty(ids)) {
                Long[] arrayOfLong;
                int j = (arrayOfLong = ids).length;
                for (int i = 0; i < j; i++) {
                    Long id = arrayOfLong[i];
                    batch = this.batchService.selectByPrimaryKey(id);
                    if (batch == null) {
                        this.exceptionFactory.createSdException("00001", null, null);
                    }
                    int total = this.batchService.batchRefRule(id);
                    if (total != 0) {
                        return buildFailJsonMsg("batch.isRefRule");
                    }
                    this.batchService.deleteByPrimaryKey(id);
                    addOperLog("CF_21", AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.batch.del", new String[]{user.getAccount(), batch.getName()}), true);
                }
            }
            return buildSuccessJsonMsg("common.op.success");
        } catch (Exception e) {
            addOperLog("CF_21", AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.batch.del", new String[]{user.getAccount(), batch != null ? batch.getName() : ""}), false);
            throw e;
        }
    }
}
