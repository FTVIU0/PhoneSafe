package com.nlte.phonesafe.entity;

/**联系人的实体类
 * Created by NLTE on 2016/3/26 0026.
 */
public class ContactInfo {
    private String name;//mingc
    private String num;//电话号码

    public ContactInfo(String name, String num) {
        this.name = name;
        this.num = num;
    }

    public ContactInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "ContactInfo [name ="+name+", num= "+ num+"]";
    }
}
