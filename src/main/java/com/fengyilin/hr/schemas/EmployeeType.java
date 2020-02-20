/*
//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.7 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2020.02.18 时间 07:31:27 PM CST 
//


package com.fengyilin.hr.schemas;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


*/
/**
 * <p>EmployeeType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="EmployeeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="FirstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LastName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 *//*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmployeeType", propOrder = {
    "number",
    "firstName",
    "lastName"
})
public class EmployeeType {

    @XmlElement(name = "Number", required = true)
    protected BigInteger number;
    @XmlElement(name = "FirstName", required = true)
    protected String firstName;
    @XmlElement(name = "LastName", required = true)
    protected String lastName;

    */
/**
     * 获取number属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     *//*

    public BigInteger getNumber() {
        return number;
    }

    */
/**
     * 设置number属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     *//*

    public void setNumber(BigInteger value) {
        this.number = value;
    }

    */
/**
     * 获取firstName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     *//*

    public String getFirstName() {
        return firstName;
    }

    */
/**
     * 设置firstName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     *//*

    public void setFirstName(String value) {
        this.firstName = value;
    }

    */
/**
     * 获取lastName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     *//*

    public String getLastName() {
        return lastName;
    }

    */
/**
     * 设置lastName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     *//*

    public void setLastName(String value) {
        this.lastName = value;
    }

}
*/
