package com.core.draft;

/**
 * @author YangWenjun
 * @date 2019/11/12 17:45
 * @project MockFramework
 * @title: IDraftTemplate
 * @description:
 */
public interface IDraftTemplate {

    /**
     * 将报文模板存在一个大的blog字段中
     * @param draftTemplate
     * @return
     */
    Integer updateDraftTemplate(String draftNo , String draftTemplate);

    String getDraftTemplate(String draftNo);

}
