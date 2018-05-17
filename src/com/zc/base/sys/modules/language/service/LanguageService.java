package com.zc.base.sys.modules.language.service;

import com.zc.base.sys.modules.language.entity.Language;

import java.util.List;

public abstract interface LanguageService {
    public abstract List<Language> getAllLanguages();
}
