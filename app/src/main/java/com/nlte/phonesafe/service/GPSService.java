package com.nlte.phonesafe.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.nlte.phonesafe.utils.CacheUtil;

import java.util.List;

/**Android本地定位
 * Created by NLTE on 2016/3/28 0028.
 */
public class GPSService extends Service {
    private LocationManager locationManager;
    @Override
    public void onCreate() {
        if (
                ContextCompat.checkSelfPermission( GPSService.this, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( GPSService.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //取得所有的定位方式
            locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            List<String> allProviders = locationManager.getAllProviders();
            for (String provider : allProviders){
                System.out.println(provider);
            }
            Criteria criteria = new Criteria();//设置定位配置
            criteria.setAltitudeRequired(true);
            //获取最好的定位方式
            String bestProvider = locationManager.getBestProvider(criteria, true);
            /**
             * 请求位置更新
             * provider： 定位的方式
             * minTime： 最新的时间间隔，以毫秒为单位
             * minDistance： 最小的距离 ：以米为单位
             */
            locationManager.requestLocationUpdates(bestProvider, 2000, 10, new MyLocationListener());
            super.onCreate();
        }

    }
    private class MyLocationListener implements LocationListener{
        //当位置发生改变时，回调该方法
        @Override
        public void onLocationChanged(Location location) {
            // 位置信息都封装到location中
            //取得经度和维度
            double latitude = location.getLatitude();//取得当前位置的维度
            double longitude = location.getLongitude();//取得当前位置的经度
            System.out.println("维度:"+latitude +"  经度"+longitude);
            CacheUtil.putString(GPSService.this, "latitude", latitude+"");
            CacheUtil.putString(GPSService.this, "longitude", longitude+"");
        }
        //当定位方式的状态发生改变，则回调该方法  wifi 从可用 到不 可用
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
        // 定位方式可用时回调
        @Override
        public void onProviderEnabled(String provider) {

        }
        //定位方式不可用时回调
        @Override
        public void onProviderDisabled(String provider) {

        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

}
