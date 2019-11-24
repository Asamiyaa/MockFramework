package com.utils.convert.XStream;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YangWenjun
 * @date 2019/8/15 17:18
 * @project hook
 * @title: City
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XStreamAlias("d")
public class City {

    @XStreamAsAttribute
    @XStreamAlias("d1")
    private String cityId;

    @XStreamAsAttribute
    @XStreamAlias("d2")
    private String cityName;

    @XStreamAlias("d3")
    @XStreamAsAttribute
    private String cityCode;

    @XStreamAsAttribute
    @XStreamAlias("d4")
    private String province;
}