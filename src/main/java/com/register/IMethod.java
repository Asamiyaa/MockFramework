package com.register;

import com.register.bean.MethodSearchBean;
import com.register.bean.dataObj.RgctMethod;

import java.util.List;

/**
 * @author YangWenjun
 * @date 2019/8/28 20:44
 * @project hook
 * @title: IMethod
 * @description:
 */
public interface IMethod {

    /**
     * 通过searchBean封装参数查询登记方法
     * @param methodSearchBean
     * @return
     */
    List<RgctMethod> listRgctMethod(MethodSearchBean methodSearchBean);

}
