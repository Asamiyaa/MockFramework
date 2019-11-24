package com.core.draft.tmpBBSP.builder.base;

import com.draft1.dto.DraftBuildInfo;
import com.draft1.dto.DraftHead;

/**
 * @author YangWenjun
 * @date 2019/8/14 16:23
 * @project hook
 * @title: AbstractDraftBuilder
 * @description:  封装了 bean  - xml 转换
 */
public abstract class AbstractDraftBuilder {

    public String assembleDraft(DraftBuildInfo dbd){

        String msgBody = buildBody(dbd);
        msgBody="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+msgBody;

        schemaValidate(dbd,msgBody);

        String  msgHead = buildHead(dbd,msgBody);

        return msgHead + msgBody ;

    }
    private String buildHead(DraftBuildInfo dbd, String msgBody) {

        DraftHead head = new DraftHead();
        //head.setxxx();
       return head.draftHeadAsString() ;   // 面向  领域编程 ，所有的都可以直接操作String-二进制，向上抽象就是为了扩展和复用。
                                           // 所以说 head 对象不仅仅是面向数据，而没有行为。
    }

    private void schemaValidate(DraftBuildInfo dbd, String msgBody) {

        /***xml 知识点扩展  - json 本质还是调用xml.schemaValidate...只是框架封装，那么如何理解这些框架的api，如何学习???***/

        //XmlOptions option = new XmlOptions();
        //Map<String,String> map = new HashMap<String,String>();
        //String namespace = "http://www."+dbd.getDraftNo.toLowerCase()+"u.afxxx.cn";
        //map.put("",namespace);
        //option.setLoadSubstituteNamespace(map);
        //XmlObject xml = XmlObject.Factory.parse(msgBody,option);
        //List<XmlError> errors = new ArrayList<XmlError>();
        //options.setErrorListner(errors);
        //boolean isValid = xml.validate(option);
        //if(!isValid){
        //   String errMsg = "schema校验不通过：";
        //   for(XmlError err:errors){
        //       XmlCursor curr = err.getCursorLocation();
        //       errMsg += cur.getDomNode().getLocalName()+":"+err.getMessage()+"\n";
        //   }
        //   throw new Exception(errMsg);
        //}

    }

    protected abstract String buildBody(DraftBuildInfo dbd);
        //xml - bean 转换逻辑 oxm框架使用  / 加签.. -- 结合com.utils.convertUtils.XMLConvertUtil自定义和Xstream对其处理

    /*protected XmlOptions createXmlOptions(String draftNo){
        //子类调用
    }*/

    //加签
    /*protect String addSign(String doc , String isAddSign) throws Exception{ //单一性，所以不是在子类具体属性拼接中判断是否加签

            int start = doc.index("<mainBody>");
            int end = doc.index("</mainBody>")+11;
            String signPart = doc.subString(start.end);
            String ptcptSntr = SignManager.addSign(signPart);
            return doc.replace("</document>","<PtcptSgntr>"+tcptSntr+"</ptcptSgnct></document>");

    }*/

}
