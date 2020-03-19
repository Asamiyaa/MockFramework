package com.dubbo;


import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

@Service(version = "1.0.0")
public class DefaultDemoService implements DemoService{

    /**
     * The default value of ${dubbo.application.name} is ${spring.application.name}
     */
    @Value("${spring.application.name}")
    private String serviceName;
    private String name ;

    public DefaultDemoService(String s) {

    }


    public DefaultDemoService() {



    }

    @Override
    public String toString() {
        return "DefaultDemoService{" +
                "serviceName='" + serviceName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public DemoService sayHello(String name) {
        this.name = name ;
        return new DefaultDemoService(name + serviceName);
    }

    @Override
    public void sayHello2() {
        System.out.println("调用感到了---------");
    }
}