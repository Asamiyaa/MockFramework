package com.pattern.k.spring;

import java.util.Date;

/**
 * 补偿任务object
 */
public class TaskPo {

    private int id ;  //本地自增器
    private String exeData;//调用方法，对象，参数 现场信息
    private Date gmtExeTime;
    private Date gmetCreatedTime;
    private int exeStatus;
    private Date gmtUpdate;

    public TaskPo(int id, String exeData, Date gmtExeTime, Date gmetCreatedTime, int exeStatus, Date gmtUpdate, String type) {
        this.id = id;
        this.exeData = exeData;
        this.gmtExeTime = gmtExeTime;
        this.gmetCreatedTime = gmetCreatedTime;
        this.exeStatus = exeStatus;
        this.gmtUpdate = gmtUpdate;
        this.type = type;
    }

    public TaskPo(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExeData() {
        return exeData;
    }

    public void setExeData(String exeData) {
        this.exeData = exeData;
    }

    public Date getGmtExeTime() {
        return gmtExeTime;
    }

    public void setGmtExeTime(Date gmtExeTime) {
        this.gmtExeTime = gmtExeTime;
    }

    public Date getGmetCreatedTime() {
        return gmetCreatedTime;
    }

    public void setGmetCreatedTime(Date gmetCreatedTime) {
        this.gmetCreatedTime = gmetCreatedTime;
    }

    public int getExeStatus() {
        return exeStatus;
    }

    public void setExeStatus(int exeStatus) {
        this.exeStatus = exeStatus;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type ;

}
