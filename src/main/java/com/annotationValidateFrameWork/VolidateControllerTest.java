package com.annotationValidateFrameWork;


import boot.SpringBootApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 1.写 - 需要的再去添加
 * 2.ringboot 单元测试报错MergedAnnotations$SearchStrategy https://blog.csdn.net/lhh143400/article/details/103230718
 *   引入starter-test没起作用是因为scope是test
 * 3.必须添加对应的返回 assert  如何在.do / .assert中拼接
 * 4.如何选择更少加载更少东西而不是整个扫描springBootTest all
 * 5.修改返回不是都是 ok 导致无法通过颜色判断是否成功
 * 6.不启动正式的spring项目也可以
 * 7.后台调用、事务配置 数据库mock..
 */
@SpringBootTest(classes = SpringBootApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//@ContextConfiguration(locations={"classpath:applicationContext.properties"}) //这个必须吗、
public class VolidateControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void Setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testVolidate() throws Exception {
        /*mockMvc.perform(post("/user").param("id", "1")) //注意这行
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(redirectedUrl("/user/1"));*/
        String retVal  = mockMvc.perform(get("/volidateMock").param("id","mockid")
                .param("name","mockname"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("mock v is "+ retVal);
    }

    @Test
    public void testVolidate2() throws Exception {
        /*mockMvc.perform(post("/user").param("id", "1")) //注意这行
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(redirectedUrl("/user/1"));*/
        String retVal  = mockMvc.perform(get("/volidateMock").param("id","mockid"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("mock v is "+ retVal);
    }

    @Test
    public void testVolidate3() throws Exception {
        String retVal  = mockMvc.perform(get("/volidateUser").param("id","mockUserid"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("mock v is "+ retVal);
    }

    @Test
    public void testVolidate4() throws Exception {
        String retVal  = mockMvc.perform(get("/volidatePath").param("id","mockUserPathid"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("mock v is "+ retVal);
    }

    @Test
    public void testVolidate5() throws Exception {
        String retVal  = mockMvc.perform(get("/volidatePath").param("id","mockUserPathid")
                .param("naem","mockUserPathName"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("mock v is "+ retVal);
    }

    @Test
    public void testVolidate6() throws Exception {
        String retVal  = mockMvc.perform(get("/volidateJson").param("id","mockUserPathid")
               /* .param("naem","mockUserPathName")*/)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("mock v is "+ retVal);
    }

    @Test
    public void testVolidate7() throws Exception {
        String retVal  = mockMvc.perform(get("/volidateAspect").param("id","mockUserAspectid")
                /* .param("naem","mockUserPathName")*/)
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("mock v is "+ retVal);
    }

    //TODO:如何写含有bindingResult的调用
    //TODO:对象相关

}
