package com.zc.base.sys.modules.system.facade;

import com.zc.base.sys.modules.system.entity.SysDict;

import java.util.*;


public class DictFacade {
    private static Map<String, Map<String, List<SysDict>>> dictMap;

    public DictFacade() {
    }

    public static Map<String, Map<String, List<SysDict>>> getMap() {
        return dictMap;
    }

    public static void setMap(Map<String, List<SysDict>> langDictMap) {
        Map<String, Map<String, List<SysDict>>> tempMap = new HashMap();
        if (langDictMap != null && langDictMap.size() != 0) {
            for (Map.Entry<String, List<SysDict>> langDict : langDictMap.entrySet()) {
                String language = langDict.getKey();
                List<SysDict> dictList = langDict.getValue();
                if (!tempMap.containsKey(language)) {
                    tempMap.put(language, new HashMap());
                    Map<String, List<SysDict>> categoryMap = tempMap.get(language);
                    for (SysDict dict : dictList) {
                        String category = dict.getCategory();
                        if (!categoryMap.containsKey(category)) {
                            List<SysDict> dicts = new ArrayList();
                            dicts.add(dict);
                            categoryMap.put(category, dicts);
                        } else {
                            categoryMap.get(category).add(dict);
                        }
                    }
                } else {
                    Map<String, List<SysDict>> categoryMap = tempMap.get(language);
                    for (SysDict dict : dictList) {
                        String category = dict.getCategory();
                        if (!categoryMap.containsKey(category)) {
                            List<SysDict> dicts = new ArrayList();
                            dicts.add(dict);
                            categoryMap.put(category, dicts);
                        } else {
                            categoryMap.get(category).add(dict);
                        }
                    }
                }
            }
        }
        dictMap = tempMap;
    }

    public static boolean isLoaded() {
        return dictMap != null && !dictMap.isEmpty();
    }

    public static String getName(String category, String code, String lang) {
        if (category != null && code != null && lang != null && dictMap != null) {
            Map<String, List<SysDict>> categoryMap = dictMap.get(lang);
            if (categoryMap != null && categoryMap.size() != 0) {
                List<SysDict> list = (List) categoryMap.get(category);
                if (list != null) {
                    Iterator iterator = list.iterator();

                    while (iterator.hasNext()) {
                        SysDict blaDict = (SysDict) iterator.next();
                        if (blaDict != null && category.equals(blaDict.getCategory()) && code.equals(blaDict.getCode())) {
                            return blaDict.getContent();
                        }
                    }
                }

                return "";
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public static String[][] getDictByCategory(String category, String lang) {
        if (category != null && lang != null && dictMap != null) {
            Map<String, List<SysDict>> categoryMap = dictMap.get(lang);
            if (categoryMap != null && categoryMap.size() != 0) {
                List<String[]> result = new ArrayList();
                List<SysDict> list = (List) categoryMap.get(category);
                if (list != null) {
                    Iterator iterator = list.iterator();

                    while (iterator.hasNext()) {
                        SysDict dict = (SysDict) iterator.next();
                        if (dict != null && category.equals(dict.getCategory())) {
                            String[] values = new String[]{String.valueOf(dict.getCode()), dict.getContent()};
                            result.add(values);
                        }
                    }
                }

                return result.toArray(new String[0][0]);
            } else {
                return new String[0][0];
            }
        } else {
            return new String[0][0];
        }
    }
}