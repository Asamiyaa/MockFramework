package com.core.draft.impl;

import com.core.draft.IDraftTemplate;
import com.core.rule.bean.dataObj.DraftDo;
import com.core.rule.dao.DraftDoMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;

/**
 * @author YangWenjun
 * @date 2019/11/12 17:54
 * @project MockFramework
 * @title: DraftTemplateImpl
 * @description:
 */
public class DraftTemplateImpl implements IDraftTemplate {

    @Autowired
    private DraftDoMapper draftDoMapper;

    @Override
    public Integer insertDraft(String draftNo, String draftDesc, String draftTemplate) {

        try {

            draftDoMapper.insert(new DraftDo(draftNo,draftDesc,draftTemplate.getBytes("gbk")));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*@Override
    public Integer updateDraftTemplate(String draftNo, String draftDesc, String draftTemplate) {
        try {

            draftDoMapper.insert(new DraftDo(draftNo,draftDesc,draftTemplate.getBytes("gbk")));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }*/

    @Override
    public String getDraftTemplate(String draftNo) {

        try {

            return new String(draftDoMapper.selectByDraftNo(draftNo).getDrafttemplate(),
                                                                            "gbk");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null ;
    }
}
