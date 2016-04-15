package com.nlte.phonesafe.entity;

import android.graphics.drawable.Drawable;

/**软件属性实体类
 * Created by NLTE on 2016/4/14 0014.
 */
public class SoftInfo {
    private String name;//软件名称
    private String versionName;//版本名称
    private String packageName;//包名
    private Drawable icon;//软件图标
    private boolean isUser;//系统应用或者用户应用
    private boolean isSdcard;//存储在Sdcard或者内部存储

    public SoftInfo(String name, String versionName, String packageName, Drawable icon, boolean isUser, boolean isSdcard) {
        this.name = name;
        this.versionName = versionName;
        this.packageName = packageName;
        this.icon = icon;
        this.isUser = isUser;
        this.isSdcard = isSdcard;
    }

    public SoftInfo() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }

    public boolean isSdcard() {
        return isSdcard;
    }

    public void setSdcard(boolean sdcard) {
        isSdcard = sdcard;
    }

    @Override
    public String toString() {
        return "SoftInfo{" +
                "name='" + name + '\'' +
                ", versionName='" + versionName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", icon=" + icon +
                ", isUser=" + isUser +
                ", isSdcard=" + isSdcard +
                '}';
    }
}
