package com.pattern.k.execute;

import com.core.rule.bean.dataObj.DraftDo;
import org.springframework.stereotype.Service;

@Service
public class DelegateOrderCenterImpl implements IDelegateOrderCenter{

    @AsyncTask(taskType = 1,exeInterval = 60)
    @Override
    public void notifyOrder(DraftDo draftDo) {
        System.out.println("notify center"+draftDo);
    }
}
