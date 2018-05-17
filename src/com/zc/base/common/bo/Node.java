package com.zc.base.common.bo;

import java.util.HashMap;
import java.util.Map;


public class Node {
    private String sensorId;
    private String searchCode;
    private int searchType;
    private Object id;
    private Boolean checked;
    private Boolean chkDisabled;
    private String click;
    private String halfCheck;
    private String icon;
    private String iconOpen;
    private String iconClose;
    private String iconSkin;
    private Long pId;
    private String name;
    private Boolean open;
    private Boolean isParent;
    private Boolean isHidden;
    private Boolean isAjaxing;
    private Boolean zasync;
    private String font;
    private String url;
    private String target;
    private Boolean collapse;
    private Boolean expand;
    private Boolean nocheck;
    private Boolean doCheck;
    private Boolean drag;
    private Boolean dropInner;
    private Boolean childOuter;
    private int isCheck;
    private int unit;
    private String propertiesName;
    private int Level;
    private int type;
    private int archLevel;
    private Boolean isLeaf;
    private Long modeId;
    private Map<String, String> attrMap = new HashMap();

    public Map<String, String> getAttrMap() {
        return this.attrMap;
    }

    public void setAttrMap(Map<String, String> attrMap) {
        this.attrMap = attrMap;
    }


    public int getType() {
        return this.type;
    }


    public void setType(int type) {
        this.type = type;
    }

    public int getLevel() {
        return this.Level;
    }

    public void setLevel(int level) {
        this.Level = level;
    }

    public String getPropertiesName() {
        return this.propertiesName;
    }

    public void setPropertiesName(String propertiesName) {
        this.propertiesName = propertiesName;
    }

    public int getIsCheck() {
        return this.isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }

    public int getUnit() {
        return this.unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public Object getId() {
        return this.id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Boolean getChecked() {
        return this.checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getChkDisabled() {
        return this.chkDisabled;
    }

    public void setChkDisabled(Boolean chkDisabled) {
        this.chkDisabled = chkDisabled;
    }

    public String getClick() {
        return this.click;
    }

    public void setClick(String click) {
        this.click = click;
    }

    public String getHalfCheck() {
        return this.halfCheck;
    }

    public void setHalfCheck(String halfCheck) {
        this.halfCheck = halfCheck;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconOpen() {
        return this.iconOpen;
    }

    public void setIconOpen(String iconOpen) {
        this.iconOpen = iconOpen;
    }

    public String getIconClose() {
        return this.iconClose;
    }

    public void setIconClose(String iconClose) {
        this.iconClose = iconClose;
    }

    public String getIconSkin() {
        return this.iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public Long getpId() {
        return this.pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOpen() {
        return this.open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getIsParent() {
        return this.isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public Boolean getIsHidden() {
        return this.isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    public Boolean getIsAjaxing() {
        return this.isAjaxing;
    }

    public void setIsAjaxing(Boolean isAjaxing) {
        this.isAjaxing = isAjaxing;
    }

    public Boolean getZasync() {
        return this.zasync;
    }

    public void setZasync(Boolean zasync) {
        this.zasync = zasync;
    }

    public String getFont() {
        return this.font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Boolean getCollapse() {
        return this.collapse;
    }

    public void setCollapse(Boolean collapse) {
        this.collapse = collapse;
    }

    public Boolean getExpand() {
        return this.expand;
    }

    public void setExpand(Boolean expand) {
        this.expand = expand;
    }

    public Boolean getNocheck() {
        return this.nocheck;
    }

    public void setNocheck(Boolean nocheck) {
        this.nocheck = nocheck;
    }

    public Boolean getDoCheck() {
        return this.doCheck;
    }

    public void setDoCheck(Boolean doCheck) {
        this.doCheck = doCheck;
    }

    public Boolean getDrag() {
        return this.drag;
    }

    public void setDrag(Boolean drag) {
        this.drag = drag;
    }

    public Boolean getDropInner() {
        return this.dropInner;
    }

    public void setDropInner(Boolean dropInner) {
        this.dropInner = dropInner;
    }

    public Boolean getChildOuter() {
        return this.childOuter;
    }

    public void setChildOuter(Boolean childOuter) {
        this.childOuter = childOuter;
    }

    public String getSearchCode() {
        return this.searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    public int getSearchType() {
        return this.searchType;
    }

    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }

    public int getArchLevel() {
        return this.archLevel;
    }

    public void setArchLevel(int archLevel) {
        this.archLevel = archLevel;
    }


    public Boolean getIsLeaf() {
        return this.isLeaf;
    }


    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public void setModeId(Long modeId) {
        this.modeId = modeId;
    }

    public Long getModeId() {
        return this.modeId;
    }

    public String getSensorId() {
        return this.sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }
}
