package com.zc.base.sys.modules.language.service.impl;

import com.zc.base.sys.modules.language.entity.Language;
import com.zc.base.sys.modules.language.repository.LanguageDao;
import com.zc.base.sys.modules.language.service.LanguageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("languageService")
public class LanguageServiceImpl implements LanguageService {
    private LanguageDao languageDao;

    public List<Language> getAllLanguages() {
        return this.languageDao.getAllLanguages();
    }

    public LanguageDao getLanguageDao() {
        return this.languageDao;
    }

    @Resource(name = "languageDao")
    public void setLanguageDao(LanguageDao languageDao) {
        this.languageDao = languageDao;
    }
}
