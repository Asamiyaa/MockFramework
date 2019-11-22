package com.core.draft.impl;

import com.core.draft.DtoGenerate.DtoGenerator;
import com.core.draft.IDraft;

/**
 * @author YangWenjun
 * @date 2019/11/12 18:05
 * @project MockFramework
 * @title: DraftImpl
 * @description:解析根据规则定义的xml文件  - 是否接入spring bean 模式 识别xml - bind
 * TODO:classLoader
 *     机具项目中绑定  -- spring丰富的选择 - dubbo ->spi机制
 */

public class DraftImpl implements IDraft {
    @Override
    public void marshal() {
        /**
         * 将从页面来的值和xml标签匹配赋值
         */
        new DtoGenerator().createDto();
    }

    @Override
    public void unmarshal() {
        /**
         * 将从报文来的值赋值到对象
         */
    }
}
