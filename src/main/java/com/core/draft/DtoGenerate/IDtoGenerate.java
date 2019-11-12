package com.core.draft.DtoGenerate;

/**
 * @author YangWenjun
 * @date 2019/11/1 14:25
 * @project MockFramework
 * @title: IDtoCreate
 * @description:  从配置文件or数据库读取bean属性 -- > 根据上传的xml模板动态生成对应的java类
 */
public interface IDtoGenerate {

        Object createDto();
}
