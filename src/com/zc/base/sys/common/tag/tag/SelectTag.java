package com.zc.base.sys.common.tag.tag;

import com.zc.base.common.util.TreeUtil;
import com.zc.base.sys.common.tag.entity.Vmapping;
import com.zc.base.sys.common.tag.repository.SelectDao;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.modules.architecture.bo.ArchModeBo;
import com.zc.base.sys.modules.architecture.bo.ArchTypeBo;
import com.zc.base.sys.modules.system.facade.DictFacade;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class SelectTag extends TagSupport {
    private static final long serialVersionUID = 6743377368515708087L;
    private Logger log = LoggerFactory.getLogger(SelectTag.class);
    private String defaultValue;
    private String tableName;
    private String style;
    private String clazz;
    private String parentId;
    private boolean required;
    private String defaultOption;
    private String categoryName;
    private Integer beginNo;
    private Integer endNo;
    private String onchange;
    private String name;
    private String width;
    private String attrs;
    private Boolean jqueryui = Boolean.valueOf(true);

    public int doEndTag() throws JspException {
        ApplicationContext context =
                WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext());
        SelectDao selectDao = (SelectDao) context.getBean(SelectDao.class);
        String language = RequestContextUtils.getLocale((HttpServletRequest) this.pageContext.getRequest()).getLanguage();
        List<Vmapping> datas = null;
        int i;
        if ("bam_dictionary".equalsIgnoreCase(this.tableName)) {
            datas = selectDao.selectByTableAndSearchCode(this.tableName, this.parentId, language);
        } else if ("SYS_DICT".equalsIgnoreCase(this.tableName)) {
            if ("sm_architecture.type".equalsIgnoreCase(this.categoryName)) {
                List<ArchModeBo> modes = TreeUtil.getArchModeBos();
                datas = new ArrayList();
                if ((modes != null) && (modes.size() > 0)) {
                    Integer maxLevel = Integer.valueOf(0);
                    for (ArchModeBo mode : modes) {
                        List<ArchTypeBo> types = TreeUtil.getAllArchType(mode.getId());
                        if ((types != null) && (types.size() > 0)) {
                            if (maxLevel.intValue() < ((ArchTypeBo) types.get(0)).getMaxLevel()) {
                                maxLevel = Integer.valueOf(((ArchTypeBo) types.get(0)).getMaxLevel());
                            }
                        }
                    }

                    ResourceBundle bundle = ResourceBundle.getBundle("i18n.message", new Locale(language));
                    int level = 1;
                    if ((this.beginNo != null) && (this.endNo != null) && (this.endNo.intValue() != 0)) {
                        level = this.beginNo.intValue();
                        maxLevel = Integer.valueOf(this.endNo.intValue() > maxLevel.intValue() ? maxLevel.intValue() : this.endNo.intValue());
                    }
                    for (; level <= maxLevel.intValue(); level++) {
                        Vmapping mapping = new Vmapping();
                        mapping.setName(bundle.getString("common.level") + " " + level);
                        mapping.setValue(String.valueOf(level));
                        datas.add(mapping);
                    }
                }
            } else {
                String[][] sm_data = DictFacade.getDictByCategory(this.categoryName, language);
                if ((sm_data != null) && (sm_data.length > 0)) {
                    datas = new ArrayList();
                    int begin = 0;
                    int end = 0;
                    if ((this.beginNo != null) && (this.endNo != null)) {
                        begin = this.beginNo.intValue() - 1;
                        end = this.endNo.intValue() - 1;
                    } else {
                        end = sm_data.length - 1;
                    }

                    for (i = begin; i <= end; i++) {
                        Vmapping mapping = new Vmapping();
                        mapping.setName(sm_data[i][1]);
                        mapping.setValue(sm_data[i][0]);
                        datas.add(mapping);
                    }

                }

            }
        } else {
            throw new JspException(
                    "invalid param(tableName).which is must be one of 'bam_dictionary' and 'SYS_DICT'.");
        }

        boolean emptyid = ("".equals(this.id)) || (this.id == null);
        if (emptyid) {
            this.id = ("zchl" + System.currentTimeMillis());
        }
        StringBuffer selectTagHtml = null;
        if ((datas != null) && (datas.size() > 0)) {
            selectTagHtml = new StringBuffer("<select id='" + this.id + "' autoDestroy readonly='readonly'");


            if (StringUtils.isNotBlank(this.width)) {
                selectTagHtml.append(" style='width:").append(this.width).append(";'");
            }


            if (StringUtils.isNotBlank(this.name)) {
                selectTagHtml.append(" name='").append(this.name).append("'");
            }


            if (DefineStringUtil.isNotEmpty(this.onchange)) {
                selectTagHtml.append("onchange='").append(this.onchange).append("'");
            }

            if (StringUtils.isNotBlank(this.style)) {
                selectTagHtml.append(" style='").append(this.style).append("'");
            }
            if (StringUtils.isNotBlank(this.clazz)) {
                selectTagHtml.append(" class='").append(this.clazz).append("'");
            }
            if (StringUtils.isNotBlank(this.attrs)) {
                selectTagHtml.append(" ").append(this.attrs).append(" ");
            }
            selectTagHtml.append(">");
            if (!this.required) {
                selectTagHtml.append("<option value=''>");


                ResourceBundle bundle = ResourceBundle.getBundle("i18n.message", new Locale(language));
                selectTagHtml.append(StringUtils.isNotBlank(this.defaultOption) ? this.defaultOption :
                        bundle.getString("common.select"));

                selectTagHtml.append("</option>");
            }
            for (Vmapping mapping : datas) {
                selectTagHtml.append("<option value='").append(mapping.getValue()).append("'");
                if ((StringUtils.isNotBlank(this.defaultValue)) && (this.defaultValue.equals(mapping.getValue()))) {
                    selectTagHtml.append(" selected='selected' ");
                }
                selectTagHtml.append(">").append(mapping.getName()).append("</option>");
            }
            selectTagHtml.append("</select>");

            if (this.jqueryui.booleanValue()) {
                if (emptyid) {
                    selectTagHtml.append("<script type='text/javascript'>if($.browser.msie&&parseInt($.browser.version)==8){$(function() {$('#" +
                            this.id +
                            "').combobox().removeAttr('id');});}else{$('#" +
                            this.id +
                            "').combobox().removeAttr('id');}</script>");
                } else {
                    selectTagHtml.append("<script type='text/javascript'>if($.browser.msie&&parseInt($.browser.version)==8){$(function() {$('#" +
                            this.id + "').combobox();});}else{$('#" + this.id + "').combobox();}</script>");
                }
            }

            JspWriter out = this.pageContext.getOut();
            try {
                out.write(selectTagHtml.toString());
                out.flush();
            } catch (IOException e) {
                this.log.error(e.getMessage(), e);
            }
        }
        return 6;
    }


    public int doStartTag()
            throws JspException {
        return 0;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getStyle() {
        return this.style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getClazz() {
        return this.clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isRequired() {
        return this.required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDefaultOption() {
        return this.defaultOption;
    }

    public void setDefaultOption(String defaultOption) {
        this.defaultOption = defaultOption;
    }

    public Integer getBeginNo() {
        return this.beginNo;
    }

    public void setBeginNo(Integer beginNo) {
        this.beginNo = beginNo;
    }

    public Integer getEndNo() {
        return this.endNo;
    }

    public void setEndNo(Integer endNo) {
        this.endNo = endNo;
    }

    public String getOnchange() {
        return this.onchange;
    }

    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWidth() {
        return this.width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getAttrs() {
        return this.attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    public Boolean getJqueryui() {
        return this.jqueryui;
    }

    public void setJqueryui(Boolean jqueryui) {
        this.jqueryui = jqueryui;
    }
}
