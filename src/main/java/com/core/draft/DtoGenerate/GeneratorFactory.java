package com.core.draft.DtoGenerate;

import com.core.draft.DtoGenerate.AsmImpl.AsmGenerator;

/**
 * @author YangWenjun
 * @date 2019/11/1 14:52
 * @project MockFramework
 * @title: GeneratorFactory
 * @description:
 */
public class GeneratorFactory {

    public static AsmGenerator getInstance(){
        return new AsmGenerator();
    }
}
