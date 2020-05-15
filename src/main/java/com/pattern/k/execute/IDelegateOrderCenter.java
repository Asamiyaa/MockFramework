package com.pattern.k.execute;

import com.core.rule.bean.dataObj.DraftDo;
import org.springframework.stereotype.Service;

@Service
public interface IDelegateOrderCenter {

     void notifyOrder(DraftDo draftDo);
}
