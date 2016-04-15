package com.nlte.phonesafe.business;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.nlte.phonesafe.entity.SoftInfo;

import java.util.ArrayList;
import java.util.List;

/**软件管理业务类
 * Created by NLTE on 2016/4/14 0014.
 */
public class SoftManager {
    private Context mContext;

    public SoftManager(Context context) {
        mContext = context;
    }

    public List<SoftInfo> getAllSofts(){
        List<SoftInfo> softInfos = new ArrayList<SoftInfo>();
        PackageManager packageManager = mContext.getPackageManager();
        //取得所有的安装的包的信息
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        //遍历包
        for (PackageInfo packageInfo : installedPackages){
            String packageName = packageInfo.packageName;//包名称
            String versionName = packageInfo.versionName;//版本名称
            String name = packageInfo.applicationInfo.loadLabel(packageManager).toString();//应用名称
            Drawable icon = packageInfo.applicationInfo.loadIcon(packageManager);//取得应用程序图标
            boolean isUser= true;//默认为用户应用
            boolean isSdcard = false;//默认按装在内部存储
            int flags = packageInfo.applicationInfo.flags;
            if ((flags&ApplicationInfo.FLAG_SYSTEM)==ApplicationInfo.FLAG_SYSTEM){//判断是否为系统应用
                isUser = false;
            }
            if ((flags&ApplicationInfo.FLAG_EXTERNAL_STORAGE)==ApplicationInfo.FLAG_EXTERNAL_STORAGE){//判断是否存储在Sdcard
                isSdcard = true;
            }
            SoftInfo softInfo = new SoftInfo(name, versionName, packageName, icon, isUser, isSdcard);
            softInfos.add(softInfo);
        }
        return softInfos;
    }
}
