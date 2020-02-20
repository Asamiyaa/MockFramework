package com.product;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
@RequestMapping({"/cpu"})
public class Cpu {

  /*  public static void main(String[] args) {

        new Cpu().deadCircle();

    }*/


//这种设置只适合于本地。无法发布后出发，所以还会提供简单的controller入口模拟请求

    @RequestMapping("/dc")
    public void dc(){
        deadCircle();
    }

    //死循环
    private void deadCircle(){
        while(true){
            System.out.println("---deadCircle doing --------");
        }
    }



    @RequestMapping("/dl")
    public void dl(){

        deadlock();
    }

    private void deadlock() {


    }
}
