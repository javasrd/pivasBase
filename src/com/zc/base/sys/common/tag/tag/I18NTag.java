package com.zc.base.sys.common.tag.tag;

import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.web.i18n.MessageHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


public class I18NTag extends TagSupport {
    private static final long serialVersionUID = 1L;
    private Logger log = LoggerFactory.getLogger(I18NTag.class);

    private MessageHolder messageHolder;

    private String code;

    private Integer type;

    private String unit;

    private Boolean closed = Boolean.valueOf(true);

    private String placeHolder;

    public int doEndTag()
            throws JspException {
        try {
            String msg = getMessage();
            JspWriter out = this.pageContext.getOut();
            out.write(msg);
            out.flush();
        } catch (IOException e) {
            this.log.error(e.getMessage(), e);
            throw new JspException(e);
        }
        return 6;
    }

    public int doStartTag()
            throws JspException {
        return 0;
    }

    protected String getMessage() {
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext());
        String msg = "";
        this.messageHolder = ((MessageHolder) context.getBean(MessageHolder.class));
        if (DefineStringUtil.isNotEmpty(this.placeHolder)) {
            String[] args = this.placeHolder.split(",");
            msg = this.messageHolder.getMessage(this.code, args);
        } else {
            msg = this.messageHolder.getMessage(this.code);
        }

        return msg;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Boolean getClosed() {
        return this.closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public String getPlaceHolder() {
        return this.placeHolder;
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }
}
