package com.pattern.E;


import org.springframework.context.ApplicationContext;

import java.util.Map;

public class ContextFactory {
    public static Context newContext() {
        return null;

    }

}

class Context {
    //集成springApplicationContext
    private ApplicationContext applicationContext;
    private Map localMap;

    public Object getBean(String key){
        Object ret ;
        return ret = applicationContext.getBean(key) == null?localMap.get(key):applicationContext.getBean(key);
    }

    public void putBean(String key,Object object){
        Object ret ;
        localMap.put(key,object);
    }
}
