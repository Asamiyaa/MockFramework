package com.core.draft.tmpBBSP;

import com.draft1.builder.base.AbstractDraftBuilder;

/**
 * @author YangWenjun
 * @date 2019/8/14 16:11
 * @project hook
 * @title: DraftFactory
 * @description:
 */
public class DraftFactory {

    public static AbstractDraftBuilder getDraftBuilder(String draftName) {
        try {
            //这里反射获得实例就是 子类  。 只是为了上层的代码统一，使用了父类。 即使这里向上转换，其实际仍然是 子类
            return (AbstractDraftBuilder) Class.forName("com.draft1.builder." + draftName.toUpperCase() + "DraftBuilder").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }return null ;
    }

    //...
}

