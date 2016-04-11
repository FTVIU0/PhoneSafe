package com.nlte.phonesafe;

import android.test.AndroidTestCase;
import android.util.Log;

import com.nlte.phonesafe.db.dao.AddressDao;
import com.nlte.phonesafe.db.dao.BlackNumDao;
import com.nlte.phonesafe.utils.ToastUtil;

import java.util.Random;

/**
 * Created by NLTE on 2016/3/24 0024.
 */
public class MyTest extends AndroidTestCase {
    public void testQueryAddress(){
        String location = AddressDao.queryAdress(getContext(), "1889887");
        Log.d("testQueryAddress", location);
    }
    public void testAdd(){
        BlackNumDao blackNumDao=new BlackNumDao(getContext());
        //用随机数来初始化拦截模式
        Random random=new Random();
        for(int i=0;i<50;i++){
            blackNumDao.add((1325678986+6*i)+""+9, random.nextInt(3));//in the half-open range [0, n).
        }
    }
    public void testDelete(){
        BlackNumDao blackNumDao=new BlackNumDao(getContext());
        blackNumDao.delete("13256789929");//in the half-open range [0, n).
    }
}
