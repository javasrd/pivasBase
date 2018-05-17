package com.zc.base.common.util;

import com.zc.base.common.bo.Node;
import com.zc.base.sys.common.constant.SysConstant;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.modules.architecture.bo.ArchModeBo;
import com.zc.base.sys.modules.architecture.bo.ArchTypeBo;

import java.util.*;
import java.util.Map.Entry;


public class TreeUtil {
    private static Map<Long, List<ArchTypeBo>> archTypeMap;
    private static List<ArchModeBo> archModeBos;

    public TreeUtil() {
    }

    public static void setArchitectureIcon(Node node, Integer type) {
        if (node != null) {
            if (type == null) {
                node.setIconSkin("treeArchTop");
            } else if (!SysConstant.DicCode.ARCHITECTURE_TYPE_UNIT.equals(type) && !SysConstant.DicCode.ARCHITECTURE_TYPE_REGION.equals(type)) {
                if (SysConstant.DicCode.ARCHITECTURE_TYPE_BUILDING.equals(type)) {
                    node.setIconSkin("treeBuliding");
                } else if (SysConstant.DicCode.ARCHITECTURE_TYPE_SITE.equals(type)) {
                    node.setIconSkin("treeSite");
                } else if (SysConstant.DicCode.ARCHITECTURE_TYPE_FLOOR.equals(type)) {
                    node.setIconSkin("treeFloor");
                } else if (SysConstant.DicCode.ARCHITECTURE_TYPE_ROOM.equals(type)) {
                    node.setIconSkin("treeRoom");
                }
            } else {
                node.setIconSkin("treeCompany");
            }
        }

    }

    public static List<ArchModeBo> getArchModeBos() {
        return archModeBos;
    }

    public static void setArchModeBos(List<ArchModeBo> archModeBos) {
        archModeBos = archModeBos;
    }

    public static void setArchMap(List<ArchTypeBo> typeList) {
        if (archTypeMap == null) {
            archTypeMap = new LinkedHashMap();
        }

        Iterator var2 = typeList.iterator();

        while (var2.hasNext()) {
            ArchTypeBo typeBo = (ArchTypeBo) var2.next();
            List<ArchTypeBo> list = (List) archTypeMap.get(typeBo.getModeId());
            if (list != null) {
                list.add(typeBo);
            } else {
                List<ArchTypeBo> list1 = new ArrayList();
                list1.add(typeBo);
                archTypeMap.put(typeBo.getModeId(), list1);
            }
        }

        var2 = archTypeMap.entrySet().iterator();

        while (var2.hasNext()) {
            Entry<Long, List<ArchTypeBo>> entry = (Entry) var2.next();
            Map<Integer, List<ArchTypeBo>> maxLevelMap = new LinkedHashMap();
            List<ArchTypeBo> types = (List) entry.getValue();
            Iterator var6 = types.iterator();

            while (var6.hasNext()) {
                ArchTypeBo bo = (ArchTypeBo) var6.next();
                List<ArchTypeBo> list = (List) maxLevelMap.get(bo.getParentId());
                if (list != null) {
                    list.add(bo);
                } else {
                    List<ArchTypeBo> list1 = new ArrayList();
                    list1.add(bo);
                    maxLevelMap.put(bo.getParentId(), list1);
                }
            }

            Integer level = 1;
            Integer maxLevel = maxLevelMap.size();

            for (Iterator var8 = maxLevelMap.entrySet().iterator(); var8.hasNext(); level = level + 1) {
                Entry<Integer, List<ArchTypeBo>> entry1 = (Entry) var8.next();
                List<ArchTypeBo> subLevels = (List) entry1.getValue();
                Iterator var11 = subLevels.iterator();

                while (var11.hasNext()) {
                    ArchTypeBo archTypeBo = (ArchTypeBo) var11.next();
                    archTypeBo.setLevel(level);
                    archTypeBo.setMaxLevel(maxLevel);
                    archTypeBo.setHasChild(maxLevelMap.get(archTypeBo.getId()) != null);
                }
            }
        }

    }

    public static String getArchTypeName(Long modeId, Integer type) throws Exception {
        String name = "";
        ArchTypeBo typeBo = getArchType(modeId, type);
        if (typeBo != null) {
            name = typeBo.getName();
        }

        return name;
    }

    public static List<ArchTypeBo> getSubArchTypes(Long modeId, Integer parentType) {
        List<ArchTypeBo> subList = new ArrayList();
        List<ArchTypeBo> list = getAllArchType(modeId);
        if (DefineCollectionUtil.isNotEmpty(list)) {
            Iterator var5 = list.iterator();

            while (var5.hasNext()) {
                ArchTypeBo archType = (ArchTypeBo) var5.next();
                if (parentType.equals(archType.getParentId())) {
                    subList.add(archType);
                }
            }
        }

        return subList;
    }

    public static ArchTypeBo getArchType(Long modeId, Integer type) throws Exception {
        List<ArchTypeBo> list = getAllArchType(modeId);
        if (DefineCollectionUtil.isNotEmpty(list)) {
            Iterator var4 = list.iterator();

            while (var4.hasNext()) {
                ArchTypeBo archType = (ArchTypeBo) var4.next();
                if (type.equals(archType.getId())) {
                    return archType;
                }
            }
        }

        return null;
    }

    public static List<ArchTypeBo> getAllArchType(Long modeId) {
        if (archTypeMap == null) {
            return null;
        } else {
            List<ArchTypeBo> list = (List) archTypeMap.get(modeId);
            return list;
        }
    }
}