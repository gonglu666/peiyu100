package com.peiyu100.model;

public enum UpdateStatus {
    UNTREATED(0),         //未处理
    SUCCESS(1),           //成功处理
    UNABLE_TO_LAND(2);    //登录失败或处理异常


    private int value;

    UpdateStatus(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
