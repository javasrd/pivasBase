package com.zc.pivas.printlabel.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.docteradvice.entity.BatchBean;
import com.zc.pivas.medicamentslabel.entity.MedicLabel;
import com.zc.pivas.printlabel.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 打印瓶签DAO
 *
 * @author kunkka
 * @version 1.0
 */
@MyBatisRepository("bottleLabelDao")
public interface BottleLabelDao {
    /**
     * 查询瓶签信息
     *
     * @param bottleLabel
     * @return
     */
    public List<BottleLabel> queryBottleLabel(BottleLabel bottleLabel);

    /**
     * 把瓶签数据插入瓶签表
     *
     * @param bottleLabel
     */
    void insertBottleLabel(BottleLabel bottleLabel);

    /**
     * 更新药单瓶签码和打印标志
     *
     * @param bottleLabel
     */
    void updateYDBottleNum(BottleLabel bottleLabel);

    /**
     * 查询统排单信息
     *
     * @param bottleLabel
     * @return
     */
    List<BottleLabel> queryYDList(BottleLabel bottleLabel);

    void updateBedByBottNum(@Param("map") Map<String, Object> map);

    /**
     * 药物接收单，统计单批次按药品分类的使用量
     *
     * @param bottleLabel
     * @return
     */
    List<BottleLabel> queryYDReciverCount(BottleLabel bottleLabel);

    /**
     * 药物接收单明细
     *
     * @param bottleLabel
     * @return
     */
    List<BottleLabel> queryYDReciverDetail(BottleLabel bottleLabel);

    /**
     * 设置瓶签信息
     *
     * @param bottleInfo
     */
    void setPQBottle(Map<String, Object> bottleInfo);

    /**
     * 打印瓶签是查询信息
     *
     * @param bottleLabel
     * @return
     */
    List<BottleLabel> queryBottleLabelList(@Param("bottleLabel") BottleLabel bottleLabel);

    /**
     * 查询瓶签信息
     *
     * @param bottleLabel
     * @return
     */
    public List<BottleLabel> qryBottleLabelList(@Param("bottleLabel") BottleLabel bottleLabel, @Param("paging")
            JqueryStylePaging jquryStylePaging);

    /**
     * 查询瓶签信息，用于修改床号
     *
     * @param map
     * @return
     */
    public List<BottleLabel> queryPQBedNoForUpdate(Map<String, Object> map);


    /**
     * 查询界面列表要显示的药单信息
     *
     * @param printLabelBean
     * @return
     * @throws Exception
     */
    public List<PrintLabelBean> queryShowBottleLabelList(@Param("printLabelBean") PrintLabelBean printLabelBean) throws Exception;

    /**
     * 查询界面列表要显示的药单信息（历史记录）
     *
     * @param printLabelBean
     * @return
     * @throws Exception
     */
    public List<PrintLabelBean> queryShowBottleLabelHistoryList(@Param("printLabelBean") PrintLabelBean printLabelBean) throws Exception;

    /**
     * 查询要打印的药单信息
     *
     * @param printLabelBean
     * @return
     * @throws Exception
     */
    public List<PrintLabelBean> queryPrintBottleLabelList(@Param("printLabelBean") PrintLabelBean printLabelBean) throws Exception;

    /**
     * 查询打包的药单信息
     *
     * @param printLabelBean
     * @return
     * @throws Exception
     */
    public List<PrintLabelBean> queryPrintBottleLabelListKong(@Param("printLabelBean") PrintLabelBean printLabelBean) throws Exception;

    /**
     * 将打印信息添加到历史表中
     *
     * @param printHistoryBean
     * @throws Exception
     */
    public void addPrintInfoToHistory(@Param("printHistoryBean") PrintHistoryBean printHistoryBean) throws Exception;

    /**
     * 获取下一个GID
     *
     * @return
     * @throws Exception
     */
    public Integer getPrintHistoryGid() throws Exception;

    /**
     * 获取当前的GID
     *
     * @return
     * @throws Exception
     */
    public Integer getCurrHistoryGid() throws Exception;

    /**
     * 获取药品数量
     *
     * @return
     * @throws Exception
     */
    public List<Integer> getMedicamentsCounts() throws Exception;

    /**
     * 获取药单统计信息
     *
     * @param printLabelBean
     * @return
     * @throws Exception
     */
    public List<BottleLabel> printStatisicInfo(@Param("printLabelBean") PrintLabelBean printLabelBean) throws Exception;

    /**
     * 获取打包药单的统计信息
     *
     * @param printLabelBean
     * @return
     * @throws Exception
     */
    public List<BottleLabel> printStatisicInfoKong(@Param("printLabelBean") PrintLabelBean printLabelBean) throws Exception;

    /**
     * 获取所有的药品标签
     *
     * @return
     * @throws Exception
     */
    public List<MedicLabel> getAllMedicLabel() throws Exception;

    /**
     * 获取所有的药品
     *
     * @param printLabelBean
     * @return
     * @throws Exception
     */
    public List<PrintLabelBean> queryAllMedicaments(@Param("printLabelBean") PrintLabelBean printLabelBean) throws Exception;

    /**
     * 获取所有的打印配置条件
     *
     * @return
     */
    public List<PrintLabelConBean> queryPrintLabelConfig();

    /**
     * 获取单个药品的总量
     *
     * @param map
     * @return
     */
    public List<String> printMedicTotSingCount(@Param("map") Map<String, String> map);

    /**
     * 获取病区的编码
     *
     * @param wardCodes
     * @return
     */
    public String getWardNameByCode(@Param("wardCodes") String[] wardCodes);

    /**
     * 获取药品标签编码
     *
     * @param medicLabelCodes
     * @return
     */
    public String getMedicLabelByCode(@Param("medicLabelCodes") String[] medicLabelCodes);

    /**
     * 获取病区的名称
     *
     * @param batchIds
     * @return
     */
    public String getBatchNameById(@Param("batchIds") String[] batchIds);

    /**
     * 获取药品分类名称
     *
     * @param categorys
     * @return
     */
    public String getCategoryByCode(@Param("categorys") String[] categorys);

    /**
     * 更新打印时间
     *
     * @param printLabelBean
     * @return
     * @throws Exception
     */
    public int updatePrintTime(@Param("printLabelBean") PrintLabelBean printLabelBean) throws Exception;

    public void addDelFileTask(DelFileTaskBean bean);

    public List<DelFileTaskBean> queryDelFileTask();

    public ArrayList<BatchBean> queryYDCount(BottleLabel bottleLabel);

    public ArrayList<ReceiveBean> qryYdWardCode(BottleLabel bottleLabel);

    public ArrayList<PrintPqBean> queryPQCount(BottleLabel bottleLabel);

    public String qryBtachCode(@Param("btachName") String specialBtach);

    /**
     * 查询所有的药单
     *
     * @param param 查询所需条件
     * @return 药单集合
     */
    public List<BottleLabel> callMedicStatisticList(@Param("param") Map<String, Object> param);

    /**
     * 获取用于存储的pidsj index值
     *
     * @return
     */
    public Integer getPidsjIndex();

    /**
     * 添加到打印pidsj临时表
     *
     * @param param
     */
    public void addPidsjToTmp(@Param("param") Map<String, Object> param);

    /**
     * 删除临时表中的pidsj
     *
     */
    public void deleteAllPidsj(@Param("gid") int gid);
}


