package com.zc.pivas.docteradvice.syndatasz.message.resp;

import com.zc.pivas.docteradvice.syndatasz.message.resp.msg.YzRow;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 
 * 消息行数bean
 *
 * @author  cacabin
 * @version  1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Msg
{
    @XmlElement(name = "row")
    private YzRow yzRow;
    
    public YzRow getYzRow()
    {
        return yzRow;
    }
    
    public void setYzRow(YzRow yzRow)
    {
        this.yzRow = yzRow;
    }
    
}
