//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.7 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2019.12.14 时间 11:08:38 AM CST 
//


package com.xmlPlatform.com.fengyilin.hr.schemas;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;


/**
 * <p>HolidayType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="HolidayType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HolidayType", propOrder = {
    "startDate",
    "endDate"
})
public class HolidayType {

    @XmlElement(name = "StartDate", required = true)
    @XmlSchemaType(name = "date")
    protected Date startDate;
    @XmlElement(name = "EndDate", required = true)
    @XmlSchemaType(name = "date")
    protected Date endDate;

    /**
     * 获取startDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 设置startDate属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(Date value) {
        this.startDate = value;
    }

    /**
     * 获取endDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 设置endDate属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(Date value) {
        this.endDate = value;
    }

}
