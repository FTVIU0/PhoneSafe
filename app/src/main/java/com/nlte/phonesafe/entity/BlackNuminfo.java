package com.nlte.phonesafe.entity;

/**
 * 黑名单实体类
 * Created by NLTE on 2016/4/10 0010.
 */
public class BlackNuminfo {
    private String num;
    private int mode;
    private int id;

    public BlackNuminfo() {
    }

    public BlackNuminfo(String num, int mode) {
        this.num = num;
        this.mode = mode;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BlackNuminfo{" +
                "num='" + num + '\'' +
                ", mode=" + mode +
                '}';
    }
}
