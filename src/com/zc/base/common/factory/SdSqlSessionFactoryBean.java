package com.zc.base.common.factory;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedIOException;

import java.io.IOException;


public class SdSqlSessionFactoryBean
        extends SqlSessionFactoryBean {
    private Logger logger = LoggerFactory.getLogger(getClass());


    protected SqlSessionFactory buildSqlSessionFactory()
            throws IOException {
        try {
            return super.buildSqlSessionFactory();

        } catch (NestedIOException e) {
            this.logger.error("Nested Exception: \r\n" + e.getMessage());
            throw e;
        }
    }
}
