package com.utils.convert.XStream;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author YangWenjun
 * @date 2019/8/15 17:46
 * @project hook
 * @title: CityList
 * @description:
 *
重命名注解：@XStreamAlias()
        省略集合根节点：@XStreamImplicit
        把字段节点设置成属性：@XStreamAsAttribute
        这些命名都需要和解析的xml的属性名一一对应，一旦不对应就会报
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XStreamAlias("c")
public class CityList {
    @XStreamImplicit(itemFieldName="d")
    private List<City> cityList;
}
