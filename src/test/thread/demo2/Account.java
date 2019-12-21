package thread.demo2;

/**
 * @author YangWenjun
 * @date 2019/7/13 9:04
 * @project BaseJava
 * @title: Account
 * @description:
 */
public class Account {

    private String name ;
    private Double accMoney ;

    public Account(String name, Double accMoney) {
        this.name = name;
        this.accMoney = accMoney;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccMoney(Double accMoney) {
        this.accMoney = accMoney;
    }

    public String getName() {
        return name;
    }

    public Double getAccMoney() {
        return accMoney;
    }
}
