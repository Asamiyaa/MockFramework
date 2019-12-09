package com.core.draft.tmpBBSP;

import com.core.draft.tmpBBSP.dto.DraftBuildInfo;
import com.core.draft.tmpBBSP.dto.RequestInfo;

/**
 * @author YangWenjun
 * @date 2019/8/14 9:10
 * @project hook
 * @title: MsgBuildHandler
 * @description: 本模块代码灰色部分都是在实际项目中应该存在的，考虑到庞大性，所以单表示流程逻辑
 *
 * hander vs manager :前者更关注于一点(最小整体  比如这里的报文解析 组装 )  后者关注于 可能多个角度的方法，组合  curd .。。根
 *                    接近xxxservice . 只不过service接近业务，其他使用..manager..
 */
public class MsgBuildHandler {

    public void buildDraft(RequestInfo requestInfo){

        /***参数校验 - 通用***/

        /***前置校验 - 业务**/
        //DraftMapping  draftMapping = draftMappingService.getDraftMapping(xx,xx);  --> 这些规则的定义 缓存模块
        //sysSts sts = sysStsService.getSysSts()  --> 缓存模块  将参数封为对象放到缓存  vs key value
        //canSendDraft(draftMapping,sts);

        /***核心 发送业务*  1.条件准备 (每个条件准备又有可能有嵌套) 2.发送*/
        DraftBuildInfo dbd = new DraftBuildInfo();  //每层的处理逻辑，每层的对象  层次对象
        //将reqestInfo信息传到dbd,其中有些值可以公用，有些值取自其他地方

        //AbstractDraftBuilder builder = DraftFactory.getDraftBuilder(xx.no); 反射获取对应报文编号的builder,具体的业务参数拼接
        //String draft = builder.assembleDraft(dbd)

        //INetCom netSender = INetComNetComFactory.getInstance(0);
        //netSender.send(draft);

        /***后置校验  日志记录 (文件+数据库) 异常处理   aop**/
        //DraftLog log = new DrafatLog();
        //log.setMsgId(reqestInfo.getMsgId);
        //log.setDateTime(DateUtil.getNow_yyyyMMdd()); 嵌套  每一个可能复用的都要抽出来。 闻到代码味道
        //...
        //logService.save(log);

        /** 引入了属性获取工具 ， 文件工具 ****/
        //String fileDir = PropUtil.getMsgDir().File.separator+log.getSendDate()+File.separator+"up"+File.separator;
        //Stirng fileName = fileDir+log.getMsgId();
        //LogUtil.doWrite(filedir,fileName,draft);

    }

    private void canSendDraft(){ //参数draftMapping sts
        //if(KeyValueEnumHelper.isEq(DraftEnum.CCM005 , draftMappigng.getDraftNo()) || ....){
        // return ;}
        //if(){throw new Exception()}
        //..
        //其他业务权限校验
    }
}
