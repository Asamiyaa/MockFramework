package com.core.draft.tmpBBSP.builder;

import com.draft1.builder.base.AbstractDraftBuilder;
import com.draft1.dto.DraftBuildInfo;

/**
 * @author YangWenjun
 * @date 2019/8/14 16:32
 * @project hook
 * @title: CesBuilder
 * @description:   一般公共代码,公共流程抽到(模板方法)/父类/接口/抽象/工厂/..  + impl package ...
 */
public class CesBuilder extends AbstractDraftBuilder {

    @Override
    protected String buildBody(DraftBuildInfo dbd) {

        //xxDraft vo =  dbd.getReqInfo();
        //XmlOptions option = createXmlOptions(dbd.getDraftNo);  //抽到父类  vo父类(抽属性) 和(抽方法) --> 都抽
        //DocumentDocument document = DocumentDocument.Fatory.newInstance(option); //如何结合技术和项目中提供的api进行糅合处理
        //Document doc  = document.addNewMainBody(); //节点，操作 Element
        //MainBody body = doc.addNewMainBody();
        //MsgId msg = body.addNewMsgId();            //对象嵌套
        //msg.setId(vo.getxxx)...
        //....
        //return addSign(draft,dbd.getIsAddSign());  //加签  加密  加密机
        return "";

    }
}
