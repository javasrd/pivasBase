package com.zc.pivas.app.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.sys.modules.user.service.UserService;
import com.zc.pivas.app.bean.*;
import com.zc.pivas.app.common.ScanServiceConstant;
import com.zc.pivas.app.common.ScanServiceConstant.ScanBottleRetConstant;
import com.zc.pivas.app.common.ScanServiceUtil;
import com.zc.pivas.app.dao.AppUserLoginDao;
import com.zc.pivas.app.dao.BottleInfoDao;
import com.zc.pivas.app.service.ScanBottleService;
import com.zc.pivas.common.util.DES3Util;
import com.zc.pivas.synresult.bean.CheckOrderStatusRespon;
import com.zc.pivas.synresult.service.SendToRestful;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * APP请求交互
 *
 * @author kunkka
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/app")
public class AppController extends SdBaseController {
    private Logger logger = LoggerFactory.getLogger(AppController.class);

    @Resource
    private UserService userService;

    @Resource
    private ScanBottleService scanBottleService;

    @Resource
    private BottleInfoDao bottleInfoDao;

    @Resource
    private SendToRestful sendRestful;

    @Resource
    private AppUserLoginDao AppUserLogindao;

    /**
     * 专门处理手持APP登录
     *
     * @param request  请求
     * @param response response 响应
     * @return 视图
     */
    @RequestMapping(value = "/applogin", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public String applogin(HttpServletRequest request, HttpServletResponse response) {
        int ret = -1;

        String strUser = "";
        String Pwd = "";
        String strRet = "";
        String errorMsg = "";

        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(request.getInputStream()));
        } catch (IOException e) {
            logger.error("applogin failed", e);
            errorMsg = "解析JSON失败";
            strRet = "{\"ret\":" + ret + ",\"msg\":\"" + errorMsg + "\"}";
            return strRet;
        }

        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            logger.error("applogin failed", e);
            errorMsg = "解析JSON失败";
            strRet = "{\"ret\":" + ret + ",\"msg\":\"" + errorMsg + "\"}";
            return strRet;
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(sb.toString());
        } catch (org.codehaus.jettison.json.JSONException e1) {
            logger.error("applogin failed", e1);
            errorMsg = "解析JSON失败";
            strRet = "{\"ret\":" + ret + ",\"msg\":\"" + errorMsg + "\"}";
            return strRet;
        }

        try {
            strUser = jsonObject.getString("user");
            Pwd = jsonObject.getString("pwd");
        } catch (org.codehaus.jettison.json.JSONException e) {
            logger.error("applogin failed", e);
            errorMsg = "解析JSON失败";
            strRet = "{\"ret\":" + ret + ",\"msg\":\"" + errorMsg + "\"}";
            return strRet;
        }

        if ((null == strUser) || (null == Pwd)) {
            errorMsg = "用户名或者密码为空";
            strRet = "{\"ret\":" + ret + ",\"msg\":\"" + errorMsg + "\"}";
            return strRet;
        }

        try {
            Pwd = DES3Util.decode(Pwd);
        } catch (Exception e) {
            logger.error("applogin failed", e);
            errorMsg = "密码处理失败，解密失败";
            strRet = "{\"ret\":" + ret + ",\"msg\":\"" + errorMsg + "\"}";
            return strRet;
        }

        //校验用户
        User user = new User();
        user.setAccount(strUser);
        user.setPassword(Pwd);

        user = userService.checkUser(user);
        if (null == user) {
            errorMsg = "账号或者密码错误";
            strRet = "{\"ret\":" + ret + ",\"msg\":\"" + errorMsg + "\"}";
            return strRet;
        }

        ret = 0;
        errorMsg = "登录成功";
        strRet = "{\"ret\":" + ret + ",\"msg\":\"" + errorMsg + "\"}";

        //成功后有记录更新日期，无记录插入
//        Date date = new Date();
//        AppUserLoginBean bean = AppUserLogindao.QueryAppUserLoginInfo(user.getAccount());
//        if (null == bean)
//        {
//            bean = new AppUserLoginBean();
//            bean.setUser_id(user.getUserId());
//            bean.setLogin_date(date);
//            bean.setIp_addr(request.getRemoteAddr());
//            bean.setPort(request.getRemotePort());
//            AppUserLogindao.InsertAppUser(bean);
//            logger.info("New User:" + user.getAccount() + " id:" + bean.getUser_id() + " IP:" + bean.getIp_addr()
//                + " port:" + bean.getPort() + "date:" + bean.getLogin_date());
//        }
//        else
//        {
//            bean.setIp_addr(request.getRemoteAddr());
//            bean.setPort(request.getRemotePort());
//            bean.setLogin_date(date);
//            AppUserLogindao.UpdateAppUser(bean);
//            logger.info("Update User:" + user.getAccount() + " id:" + bean.getUser_id() + " IP:" + bean.getIp_addr()
//                + " port:" + bean.getPort() + "date:" + bean.getLogin_date());
//        }

        return strRet;
    }

    /**
     * 处理扫描
     *
     * @param request  请求
     * @param response response 响应
     * @return 视图
     * @throws JSONException
     */
    @RequestMapping(value = "/scanBottle", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String scanBottle(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ScanOutputParamBean bean = new ScanOutputParamBean();
        bean.ret = -1;
        Gson gson = new Gson();
        do {
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(request.getInputStream()));
            } catch (IOException e) {
                bean.msg = "解析JSON失败";
                break;
            }

            StringBuilder sb = new StringBuilder();
            String line = null;

            try {
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                bean.msg = "解析JSON失败";
                break;
            }

            try {
                java.lang.reflect.Type type = new TypeToken<ScanInputParamBean>() {
                }.getType();
                ScanInputParamBean inputBean = gson.fromJson(sb.toString(), type);

                logger.info("BottleNum:" + inputBean.bottleNum + "|User:" + inputBean.user);

//                boolean islogin = isLoginValid(inputBean.user);
//                if (!islogin)
//                {
//                    bean.ret = -6;
//                    bean.msg = "登录超时";
//                    
//                    AppUserLogindao.DeleteAppUser(inputBean.user);
//                    break;
//                }

                //处理扫描
                bean = scanBottleService.scanBottleAction(inputBean.bottleNum, inputBean.user);

                if (bean.msg.contains("重复扫描")) {
                    bean.ret = ScanBottleRetConstant.ScanOK;
                }
            } catch (Exception e) {
                logger.error("瓶签号无效", e);
            }
        } while (false);

        String retJson = gson.toJson(bean);
        return retJson;
    }

    /**
     * 处理扫描
     *
     * @param request  请求
     * @param response response 响应
     * @return 视图
     * @throws JSONException
     */
    @RequestMapping(value = "/checkOrderStatus", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String checkOrderStatus(HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        CheckOrderStatusBean bean = new CheckOrderStatusBean();
        bean.ret = -1;
        Gson gson = new Gson();
        do {
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(request.getInputStream()));
            } catch (IOException e) {
                bean.msg = "解析JSON失败";
                break;
            }

            StringBuilder sb = new StringBuilder();
            String line = null;

            try {
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                bean.msg = "解析JSON失败";
                break;
            }

            java.lang.reflect.Type type = new TypeToken<ScanInputParamBean>() {
            }.getType();
            ScanInputParamBean inputBean = gson.fromJson(sb.toString(), type);

            logger.info("BottleNum:" + inputBean.bottleNum + "User:" + inputBean.user);
//            boolean islogin = isLoginValid(inputBean.user);
//            if (!islogin)
//            {
//                bean.ret = -2;
//                bean.msg = "登录超时";
//                AppUserLogindao.DeleteAppUser(inputBean.user);
//                break;
//            }

            if (null == inputBean.bottleNum) {
                bean.ret = ScanServiceConstant.CheckOrderStatusConstant.Checkfailed;
                bean.msg = "无效瓶签号";
                break;
            }

            if (inputBean.bottleNum.isEmpty()) {
                bean.ret = ScanServiceConstant.CheckOrderStatusConstant.Checkfailed;
                bean.msg = "无效瓶签号";
                break;
            }

            //查找瓶签表
            BottleInfo bottleInfo = new BottleInfo();
            bottleInfo.bottleNum = inputBean.bottleNum;
            List<BottleInfo> bottleLabelList = bottleInfoDao.queryBottleInfo(bottleInfo);

            if (bottleLabelList.size() < 1) {
                bean.ret = ScanServiceConstant.CheckOrderStatusConstant.Checkfailed;
                bean.msg = "没有找到符合条件的瓶签号";
                break;
            }

            BottleInfo bottleLabelInfo = (BottleInfo) bottleLabelList.get(0);
            if (null == bottleLabelInfo) {
                bean.ret = ScanServiceConstant.CheckOrderStatusConstant.Checkfailed;
                bean.msg = "没有找到符合条件的瓶签号";
                break;
            }

            JSONObject data = new JSONObject();
            List<JSONObject> bottleNumList = new ArrayList<JSONObject>();
            data.put("parentNo", bottleLabelInfo.parentNo);
            data.put("bottleNo", bottleLabelInfo.bottleNum);
            bottleNumList.add(data);

            //获取医嘱状态
            List<CheckOrderStatusRespon> checkOrderList = null;
            try {
                checkOrderList = sendRestful.checkOrderStatus(bottleNumList);
            } catch (Exception e) {
                logger.error("QueryDoctorAdvice failed", e);
                bean.ret = ScanServiceConstant.CheckOrderStatusConstant.Checkfailed;
                bean.msg = "无法获取医嘱状态";
                break;
            }

            if (null == checkOrderList) {
                bean.ret = ScanServiceConstant.CheckOrderStatusConstant.Checkfailed;
                bean.msg = "无法获取医嘱状态";
                break;
            }

            if (checkOrderList.size() != 1) {
                bean.ret = ScanServiceConstant.CheckOrderStatusConstant.Checkfailed;
                bean.msg = "无法获取医嘱状态";
                break;
            }

            bean.ret = ScanServiceConstant.CheckOrderStatusConstant.Checkfailed;
            bean.msg = "获取医嘱状态返回结果失败";
            CheckOrderStatusRespon respon = (CheckOrderStatusRespon) checkOrderList.get(0);
            if (respon != null) {
                String result = respon.getResult();
                if (result != null) {
                    if (!result.isEmpty()) {
                        bean.ret = bean.doctorAdviceStatus;
                        bean.msg = ScanServiceUtil.getDoctorAdviceStatusByRet(respon);
                    }
                }
            }
        } while (false);

        String retJson = gson.toJson(bean);
        return retJson;
    }

    /**
     * 退出
     *
     * @param request  请求
     * @param response response 响应
     * @return 视图
     */
    @RequestMapping(value = "/exit", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String exit(HttpServletRequest request, HttpServletResponse response) {
        ExitOutputParamBean bean = new ExitOutputParamBean();
        bean.ret = -1;
        Gson gson = new Gson();

        do {
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(request.getInputStream()));
            } catch (IOException e) {
                bean.msg = "解析JSON失败";
                break;
            }

            StringBuilder sb = new StringBuilder();
            String line = null;

            try {
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                bean.msg = "解析JSON失败";
                break;
            }

            java.lang.reflect.Type type = new TypeToken<ExitInputParamBean>() {
            }.getType();
            ExitInputParamBean exitBean = gson.fromJson(sb.toString(), type);

            logger.info("exit:" + exitBean.user);

            AppUserLogindao.DeleteAppUser(exitBean.user);

            bean.ret = 0;
            bean.msg = "成功退出";
        } while (false);

        String retJson = gson.toJson(bean);
        return retJson;
    }

    /**
     * 处理心跳
     *
     * @param request  请求
     * @param response response 响应
     * @return 视图
     */
    @RequestMapping(value = "/heart", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String heart(HttpServletRequest request, HttpServletResponse response) {
        AppRetBean bean = new AppRetBean();
        bean.ret = -1;
        Gson gson = new Gson();

        do {
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(request.getInputStream()));
            } catch (IOException e) {
                break;
            }

            StringBuilder sb = new StringBuilder();
            String line = null;

            try {
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                break;
            }

            java.lang.reflect.Type type = new TypeToken<HeartInputBean>() {
            }.getType();
            HeartInputBean heartInputBean = gson.fromJson(sb.toString(), type);

            if (heartInputBean == null) {
                logger.error("invalid heartInputBean");
                break;
            }

            logger.info("heartbeat ip:" + heartInputBean.ip);
            bean.ret = 0;
        } while (false);

        String retJson = gson.toJson(bean);
        return retJson;
    }

    private boolean isLoginValid(String useraccount) {
        //判断登录是否有效     
        AppUserLoginBean AppBean = AppUserLogindao.QueryAppUserLoginInfo(useraccount);
        Date date = new Date();
        long diff = (date.getTime() - AppBean.getLogin_date().getTime()) / 1000;

        String timeoutStr = Propertiesconfiguration.getStringProperty("app.timeout");
        logger.info("User:" + useraccount + " now:" + date + " login_date:" + AppBean.getLogin_date());
        if (timeoutStr == null) {
            timeoutStr = "";
        }

        long timeout = 0;
        try {
            timeout = Long.valueOf(timeoutStr);
        } catch (NumberFormatException e) {
            logger.error("Invalid cast:" + e.getMessage());
            timeout = 0;
        }

        if (timeout > 0) {
            if ((diff < 0) || (diff > timeout)) {
                return false;
            }
        }

        return true;
    }
}
