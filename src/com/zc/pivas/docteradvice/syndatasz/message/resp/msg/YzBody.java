package com.zc.pivas.docteradvice.syndatasz.message.resp.msg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
/**
 * 
 * 医嘱消息体
 *
 * @author  cacabin
 * @version  1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class YzBody
{
    @XmlElement(name = "row")
    private YzRow row;
    
    public YzRow getRow()
    {
        return row;
    }
    
    public void setRow(YzRow row)
    {
        this.row = row;
    }
}
