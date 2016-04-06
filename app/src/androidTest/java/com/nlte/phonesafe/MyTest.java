package com.nlte.phonesafe;

import android.test.AndroidTestCase;
import android.util.Log;

import com.nlte.phonesafe.db.dao.AddressDao;
import com.nlte.phonesafe.utils.ToastUtil;

/**
 * Created by NLTE on 2016/3/24 0024.
 */
public class MyTest extends AndroidTestCase {
    public void testQueryAddress(){
        String location = AddressDao.queryAdress(getContext(), "1889887");
        Log.d("testQueryAddress", location);
    }
}
