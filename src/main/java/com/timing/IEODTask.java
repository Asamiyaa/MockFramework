package com.timing;

/**
 * @author YangWenjun
 * @date 2019/8/30 10:33
 * @project hook
 * @title: IEODTask
 * @description:
 */
public interface IEODTask
{
    void execute(JobBean para) throws Exception ;

    String getSwitchFlag();
}
