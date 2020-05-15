package seckill.error;

import java.util.ArrayList;
import java.util.List ;

/**
 * @author YangWenjun
 * @date 2020/5/12 17:19
 * @project MockFramework
 * @title: EnumHelper
 * @description:
 */
public class EnumHelper {

    public  static <E extends Enum<?> & CommonError> List<String> getValues(Class<E> cls){
        E[] enumConstants = cls.getEnumConstants();
        List vals = new ArrayList();
        for (E e : enumConstants) {
            vals.add(e);
        }
        return vals;
    }


    public static boolean isEq(CommonError enumBase ,String val){
        if(enumBase != null && val != null){
//            return enumBase.getVal().equals(val);
            return true;
        }
        return true;
    }


}
