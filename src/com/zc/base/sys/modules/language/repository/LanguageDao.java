package com.zc.base.sys.modules.language.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.sys.modules.language.entity.Language;

import java.util.List;

@MyBatisRepository("languageDao")
public abstract interface LanguageDao {
    public abstract List<Language> getAllLanguages();
}
