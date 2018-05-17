package com.zc.schedule.common.intercept;

import com.zc.schedule.common.entity.PageInfo;
import com.zc.schedule.common.util.SysConstant;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 分页实现类
 *
 * @author Justin
 * @version v1.0
 */
@Intercepts({@Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class})})
public class PageInterceptor implements Interceptor {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PageInterceptor.class);

    /**
     * 分页拦截器
     */
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
        BoundSql boundSql = delegate.getBoundSql();
        Object paramObj = boundSql.getParameterObject();
        PageInfo page = null;
        if (paramObj instanceof PageInfo) { // 当只有一个参数时
            page = (PageInfo) paramObj;
        } else if (paramObj instanceof Map) { // 当有多个参数的情况下，查找第一个Page的参数
            for (Map.Entry<String, Object> e : ((Map<String, Object>) paramObj).entrySet()) {
                if (e.getValue() instanceof PageInfo) {
                    page = (PageInfo) e.getValue();
                    break;
                }
            }
        }
        if (page != null) {
            MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
            Connection connection = (Connection) invocation.getArgs()[0];
            String sql = boundSql.getSql();
            if (page.getPageType().intValue() == 1) {
                this.setTotalRecord(page, mappedStatement, connection, paramObj);
            }
            String pageSql = this.getPageSql(page, sql);
            ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
            log.debug(pageSql);
        }
        return invocation.proceed();
    }


    /**
     * 拦截器对应的封装原始对象的方法
     */
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 设置注册拦截器时设定的属性
     */
    public void setProperties(Properties properties) {
    }

    /**
     * 根据page对象获取对应的分页查询Sql语句
     *
     * @param page 分页对象
     * @param sql  原sql语句
     * @return String
     */
    private String getPageSql(PageInfo page, String sql) {
        StringBuffer sqlBuffer = new StringBuffer(sql);

        return getOraclePageSql(page, sqlBuffer);
    }


    /**
     * 获取Oracle数据库的分页查询语句
     *
     * @param page      分页对象
     * @param sqlBuffer 包含原sql语句的StringBuffer对象
     * @return Oracle数据库的分页查询语句
     */
    private String getOraclePageSql(PageInfo page, StringBuffer sqlBuffer) {
        int offset = 0;
        try {
            if (page != null) {
                if (page.getPage() != null && page.getPage() > 0) {
                } else {
                    page.setPage(1);
                }
            } else {
                page = new PageInfo();
            }
            if (page.getPageNumber() != null && page.getPageNumber() > 0) {
            } else {
                page.setPageNumber(SysConstant.PAGENUMBER);
            }
            offset = (page.getPage() - 1) * page.getPageNumber();

            sqlBuffer.insert(0, "select u.*, rownum r from (").append(") u where rownum <= ").append(offset + page.getPageNumber());
            sqlBuffer.insert(0, "select * from (").append(") where r > ").append(offset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sqlBuffer.toString();
    }

    /**
     * 给当前的参数对象page设置总记录数
     *
     * @param page            Mapper映射语句对应的参数对象
     * @param mappedStatement Mapper映射语句
     * @param connection      当前的数据库连接
     */
    private void setTotalRecord(PageInfo page, MappedStatement mappedStatement, Connection connection, Object obj) {
        BoundSql boundSql = mappedStatement.getBoundSql(obj);
        String sql = boundSql.getSql();
        String countSql = this.getCountSql(sql);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, obj);
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, obj, countBoundSql);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(countSql);
            parameterHandler.setParameters(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                page.setTotalRow(rs.getInt(1));
                page.setTotalPage((page.getTotalRow() + page.getPageNumber() - 1) / page.getPageNumber());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据原Sql语句获取对应的查询总记录数的Sql语句
     *
     * @param sql
     * @return String
     */
    private String getCountSql(String sql) {
        int index = sql.toUpperCase().indexOf("FROM");
        return "select count(*) " + sql.substring(index);
    }

    /**
     * 利用反射进行操作的一个工具类
     */
    private static class ReflectUtil {
        /**
         * 利用反射获取指定对象的指定属性
         *
         * @param obj       目标对象
         * @param fieldName 目标属性
         * @return 目标属性的值
         */
        public static Object getFieldValue(Object obj, String fieldName) {
            Object result = null;
            Field field = ReflectUtil.getField(obj, fieldName);
            if (field != null) {
                field.setAccessible(true);
                try {
                    result = field.get(obj);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        /**
         * 利用反射获取指定对象里面的指定属性
         *
         * @param obj       目标对象
         * @param fieldName 目标属性
         * @return 目标字段
         */
        private static Field getField(Object obj, String fieldName) {
            Field field = null;
            for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                try {
                    field = clazz.getDeclaredField(fieldName);
                    break;
                } catch (NoSuchFieldException e) {
                }
            }
            return field;
        }

        /**
         * 利用反射设置指定对象的指定属性为指定的值
         *
         * @param obj        目标对象
         * @param fieldName  目标属性
         * @param fieldValue 目标值
         */
        public static void setFieldValue(Object obj, String fieldName,
                                         String fieldValue) {
            Field field = ReflectUtil.getField(obj, fieldName);
            if (field != null) {
                try {
                    field.setAccessible(true);
                    field.set(obj, fieldValue);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}